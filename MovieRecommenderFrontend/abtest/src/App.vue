<template>
  <!-- <div id="app">
    <img src="./assets/logo.png">
    <router-view/>
  </div> -->
  <el-container style="height: 700px; border: 1px solid #eee">
    <el-header>
      <v-header />
    </el-header>

    <el-container>
      <!-- <el-header style="font-size: 12px; height: 100px">
        <el-steps>
          <el-step title="创建实验场景" status="process"></el-step>
          <el-step title="创建实验" status="process"></el-step>
          <el-step title="创建完成" status="process"></el-step>
        </el-steps>
      </el-header> -->

      <el-aside style="width=270px;background-color:rgb(238,241, 246)">
        <v-menu />
      </el-aside>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Menu from "@/components/Menu";
export default {
  name: "App",
  components: {
    "v-header": Header,
    "v-menu": Menu,
  },
};
</script>

<style>
</style>
