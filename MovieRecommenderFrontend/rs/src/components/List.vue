<template>
  <el-table :data="tabledata" style="width: 100%">
    <el-table-column width="100px" align="center" prop="name" label="名称">
    </el-table-column>
    <el-table-column width="100px" align="center" prop="score" label="评分">
    </el-table-column>
    <el-table-column width="300px" align="center" prop="picUrl" label="封面">
      <template slot-scope="scope">
        <img :src="url + scope.row.picUrl" @click="dump(scope.row.id)" />
      </template>
    </el-table-column>
    <el-table-column width="100px" align="center" prop="describe" label="简介">
    </el-table-column>
    <el-table-column width="100px" align="center" prop="peoples" label="评价">
    </el-table-column>
  </el-table>
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
    };
  },
  mounted() {
    this.getMovie(); //在html加载完成后进行，相当于在页面上同步显示后端数据
  },
  methods: {
    dump(id) {
      send(id);
      this.$router.push(`/info/${id}`);
    },
    getMovie() {
      Axios.send("/movie/top250", "get", {
        username: "winter",
      }).then((result) => {
        this.tabledata = result.data;
        console.log(this.tabledata);
      });
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
