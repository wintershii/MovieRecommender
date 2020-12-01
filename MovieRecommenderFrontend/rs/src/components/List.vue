<template>
  <el-table :data="tabledata" style="width: 100%">
    <el-table-column width="100px" align="center" prop="name" label="名称">
    </el-table-column>
    <el-table-column width="100px" align="center" prop="score" label="评分">
    </el-table-column>
    <el-table-column width="300px" align="center" prop="picUrl" label="封面">
      <template slot-scope="scope">
        <img :src="url + scope.row.picUrl" />
      </template>
    </el-table-column>
    <el-table-column width="100px" align="center" prop="describe" label="简介">
    </el-table-column>
  </el-table>
</template>

<script>
import Axios from "@/axios";

export default {
  name: "App",
  data() {
    return {
      tabledata: [],
      url: "http://127.0.0.1/img/",
    };
  },
  mounted() {
    this.getMovie(); //在html加载完成后进行，相当于在页面上同步显示后端数据
  },
  methods: {
    dump() {
      this.$router.push("/info");
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
