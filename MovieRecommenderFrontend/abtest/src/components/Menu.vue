<template>
  <el-menu 
  :default-active="this.$route.path"
  router
  :default-openeds="['1']">
    <el-submenu index="1">
      <template slot="title"><i class="el-icon-message"></i>AB 测试</template>
      <el-menu-item index="/taction">创建测试</el-menu-item>
      <el-menu-item index="/tshow">结果展示</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "v-menu",
};
</script>

<style>
li{
  background-color:rgb(66,81,105);
}
.el-submenu__title,.el-menu-item{
  color:white;
}
.el-menu-item:hover{
  background-color:rgb(1,178,123);
}
.el-submenu__title:hover{
  background-color:rgb(66,81,105);
}
.el-menu-item:focus{
  background-color:rgb(1,178,123);
  color:white;
}
</style>