<template>
  <div>
  <div style="float:left">
     <span class="title" style="font-size:30px">电影榜单</span>
  <el-table :data="dataShow" style="width: 100%">
    <el-table-column width="100px" align="center" prop="name" label="名称">
    </el-table-column>
    <el-table-column width="100px" align="center" prop="score" label="评分">
    </el-table-column>
    <el-table-column width="300px" align="center" prop="picUrl" label="封面">
      <template slot-scope="scope">
        <img :src="url + scope.row.picUrl" @click="dump(scope.row.id)"  height="250px" width="180px"/>
      </template>
    </el-table-column>
    <el-table-column width="100px" align="center" prop="describe" label="简介">
    </el-table-column>
    <el-table-column width="100px" align="center" prop="peoples" label="评价">
    </el-table-column>
  </el-table>
  <el-pagination
    @prev-click="prePage()"
    @next-click="nextPage()"
    @current-change="pageTo(page)"
    :current-page.sync="page"
    :page-size="25"
    layout="prev, pager, next"
    :total="250">
  </el-pagination>
  </div>
  <div style="float:right;top:10px;position:fixed;right:20px;height=300px">
    <span class="title" style="font-size:20px">猜你喜欢</span>
    <el-button style="top=5px;right=5px" @click="recommendMovie()">更换一批</el-button>
    <el-table :data="recommendData" style="width: 100%">
    <el-table-column width="150px" align="center" prop="name" v-for="(item, index) in transTitle" :key="index" h>
    <template slot-scope="scope">
    <div v-if="scope.$index == 0">
      <img :src="url + scope.row[index]" @click="clickRecommend(recommendIds[index])" height="180px" width="120px"/>
    </div>
    <div v-if="scope.$index == 1">
      {{scope.row[index]}}
    </div>
    </template>
    
    </el-table-column>
    </el-table>
  </div>
  <div style="float:right;bottom:10px;position:fixed;right:20px;height=300px">
    <span class="title" style="font-size:20px">我的收藏</span> 
    <el-button style="top=5px;right=5px" @click="collectDetail()">查看详情</el-button>
      <el-table :data="collectData" style="width: 100%">
    <el-table-column width="150px" align="center" prop="name" v-for="(item, index) in transTitle"  :key="index" h>
    <template slot-scope="scope">
    <div v-if="scope.$index == 0">
      <img :src="url + scope.row[index]" @click="dump(collectIds[index])" height="180px" width="120px"/>
    </div>
    <div v-if="scope.$index == 1">
      {{scope.row[index]}}
    </div>
    </template>
    
    </el-table-column>
    </el-table>
  </div>
  </div>
</template>

<script>
import Axios from "@/axios";
import {send} from '../../static/analytics';
import Base64 from '../../static/base64.min'

export default {
  name: "App",
  data() {
    return {
      tabledata: [],
      url: "http://127.0.0.1:81/img/",
      // 所有页面的数据
      totalPage: [],
      // 每页显示数量
      pageSize: 25,
      // 共几页
      pageNum: 10,
      // 当前显示的数据
      dataShow: [],
      // 默认当前显示第一页
      currentPage: 0,
      page:1,
      transTitle:[1,2,3,4],
      recommendData:[],
      recommendIds:[],
      collectData:[],
      collectIds:[],
    };
  },
  mounted() {
    this.getMovie(); //在html加载完成后进行，相当于在页面上同步显示后端数据
    this.recommendMovie();
    this.collectMovie();
  },
  methods: {
    dump(id) {
      send(id);
      let {href} = this.$router.resolve({path: `/info/${id}`});
      window.open(href, '_blank');
    },
    clickRecommend(id) {
      let {href} = this.$router.resolve({path: `/info/${id}`});
      window.open(href, '_blank');
    },
    collectDetail() {
      let {href} = this.$router.resolve({path: `/collect`});
      window.open(href, '_blank');
    },
    getMovie() {
      Axios.send("/movie/top250", "get", {
        username: "winter",
      }).then((result) => {
        this.tabledata = result.data;
        console.log(this.tabledata);
        for (let i = 0; i < this.pageNum; i++) {
          // 每一页都是一个数组 形如 [['第一页的数据'],['第二页的数据'],['第三页数据']]
          // 根据每页显示数量 将后台的数据分割到 每一页,假设pageSize为5， 则第一页是1-5条，即slice(0,5)，第二页是6-10条，即slice(5,10)...
          this.totalPage[i] = this.tabledata.slice(this.pageSize * i, this.pageSize * (i + 1))
        }
          // 获取到数据后显示第一页内容
          this.dataShow = this.totalPage[this.currentPage];
      });
    },
    recommendMovie() {
      Axios.send("/recommend/sort", "post", {
        uid: localStorage.getItem("uid"),
      }).then((result) => {
        this.recommendData = result.data;
        console.log(this.recommendData);
        this.recommendIds = this.recommendData[2];
        this.recommendData.length = this.recommendData.length-1;
      });
    },
    collectMovie() {
      Axios.send("/movie/collectShow", "get", {
        uid: localStorage.getItem("uid"),
      }).then((result) => {
        this.collectData = result.data;
        this.collectIds = this.collectData[2];
        this.collectData.length = this.collectData.length-1;
        console.log(this.collectData);
      });
    },
    nextPage() {
      if (this.currentPage === this.pageNum - 1) return ;
      this.dataShow = this.totalPage[++this.currentPage];
    },
    prePage() {
      if (this.currentPage === 0) return ;
      this.dataShow = this.totalPage[--this.currentPage];
    },
    pageTo(item){
      // this.page = item;
      this.currentPage = this.page - 1;
      this.dataShow = this.totalPage[this.currentPage];
    },
  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>