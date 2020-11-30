<template>
  <el-row>
    <el-row :guter="10">
      <el-col :span="10">
        <div style="text-align: right">
          <el-image
            :src="url1"
            fit="fit"
            style="width: 200px; heght: 200px"
            @click="dump"
          >
          </el-image>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="grid-content bg-purple" style="padding: 10px">
          <div style="font-weight: bold; font-size: 18px">肖申克的救赎</div>
          <div style="font-size: 15px; margin-top: 13px"></div>
        </div>
        <div style="margin-top: 10px">
          <el-tag type="danger">9.7</el-tag>
          <el-tag>2196621</el-tag>
        </div>
        <div
          style="
            font-weight: bold;
            font-size: 25px;
            color: red;
            margin-top: 20px;
          "
        >
          希望让人自由
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="10">
        <div style="text-align: right">
          <el-image :src="url2" fit="fit" style="width: 200px; heght: 200px">
          </el-image>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="grid-content bg-purple" style="padding: 10px">
          <div style="font-weight: bold; font-size: 18px">霸王别姬</div>
          <div style="font-size: 15px; margin-top: 13px"></div>
        </div>
        <div style="margin-top: 10px">
          <el-tag type="danger">9.6</el-tag>
          <el-tag>1629471</el-tag>
        </div>
        <div
          style="
            font-weight: bold;
            font-size: 25px;
            color: red;
            margin-top: 20px;
          "
        >
          风华绝代
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="10">
        <div style="text-align: right">
          <el-image :src="url3" fit="fit" style="width: 200px; heght: 200px">
          </el-image>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="grid-content bg-purple" style="padding: 10px">
          <div style="font-weight: bold; font-size: 18px">阿甘正传</div>
          <div style="font-size: 15px; margin-top: 13px"></div>
        </div>
        <div style="margin-top: 10px">
          <el-tag type="danger">9.5</el-tag>
          <el-tag>1629471</el-tag>
        </div>
        <div
          style="
            font-weight: bold;
            font-size: 25px;
            color: red;
            margin-top: 20px;
          "
        >
          一部美国近现代史
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="10">
        <div style="text-align: right">
          <el-image :src="url4" fit="fit" style="width: 200px; heght: 200px">
          </el-image>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="grid-content bg-purple" style="padding: 10px">
          <div style="font-weight: bold; font-size: 18px">阿甘正传</div>
          <div style="font-size: 15px; margin-top: 13px"></div>
        </div>
        <div style="margin-top: 10px">
          <el-tag type="danger">9.5</el-tag>
          <el-tag>1629471</el-tag>
        </div>
        <div
          style="
            font-weight: bold;
            font-size: 25px;
            color: red;
            margin-top: 20px;
          "
        >
          一部美国近现代史
        </div>
      </el-col>
    </el-row>
  </el-row>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      url1:
        //"https://img2.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg",
        //url1: "http://img3m2.ddimg.cn/39/22/23709252-1_u_1.jpg",
        "http://127.0.0.1/img/pic.jpg",
      url2:
        "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2561716440.jpg",
      url3:
        "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p2372307693.jpg",
      url4:
        "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p2372307694.jpg",
    };
  },
  methods: {
    dump() {
      this.$router.push("/info");
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
  margin-top: 60px;
}
</style>
