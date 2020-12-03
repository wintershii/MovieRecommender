<template>
  <el-row style="margin-top: 30px">
    <el-row :gutter="10">
      <el-col :span="10">
        <div style="text-align: right">
          <el-image
            style="width: 320px; height: 360px"
            :src="url + formData.picUrl"
            fit="fill"
          ></el-image>
        </div>
      </el-col>
      <el-col :span="10">
        <div style="padding: 10px">
          <div style="font-weight: bold; font-size: 22px">
            {{ formData.name }}
          </div>
          <div style="font-size: 15px; margin-top: 13px">
            导演:{{ formData.director }}<br />
            演员:{{ formData.actor }} <br />
            " {{ formData.describe }} "
          </div>
        </div>
        <div
          style="
            font-weight: bold;
            font-size: 30px;
            color: red;
            margin-top: 30px;
          "
        >
          {{ formData.score }}
        </div>
        {{ formData.peoples }}人评
        <div style="margin-top: 20px">
          <el-form>
            <el-rate
              v-model="value"
              show-score
              text-color="#ff9900"
              score-template="{value}"
            ></el-rate>
          </el-form>
        </div>
        <div style="margin-top: 20px">
          <el-row>
            <!-- <el-button
              type="danger"
              v-click-stat="{
                dom_id: 'button-7-8',
                page: 'info-5-3',
                platform: 'h5',
                event_name: 'addToShoppingCart',
                type: 'button',
              }"
              >放入购物车</el-button
            > -->
            <div v-if="this.scoreData === 0">
              <el-button type="danger" plain @click="submitForm()"
                >评分</el-button
              >
            </div>
            <!-- <el-button type="success" plain>收藏</el-button> -->
          </el-row>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="10">
      <el-col :span="10">
        <el-row>
          <div style="height: 50px"></div>
        </el-row>
        <div style="width: 500px; border: 1px; margin-left: 150px">
          <blockquote>
            <p>{{ formData.plot }}</p>
          </blockquote>
        </div>
      </el-col>
      <el-col :span="10">
        <el-table :data="comments" style="width: 500px; margin-left: 200px">
          <el-table-column
            lable="用户评价"
            width="320px"
            style="font-weight: bold"
          >
            <template slot-scope="scope">
              {{ scope.row }}
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </el-row>
</template>

<script>
import Axios from "@/axios";

export default {
  name: "App",
  data() {
    return {
      url: "http://127.0.0.1:81/img/",
      value: 0,
      favor_click:
        "{info:[platform:'h5',page:'info-5-3',dom:'button',dom_id:'button-7-8,event:'click']}",
      formData: {},
      comments: [],
      scoreData: 0,
    };
  },
  mounted() {
    this.getMovieDetail(); //在html加载完成后进行，相当于在页面上同步显示后端数据
    this.getValue();
  },
  methods: {
    getMovieDetail() {
      Axios.send("/movie/movie", "get", {
        id: this.$route.params.id,
      }).then((result) => {
        this.formData = result.data;
        this.comments = this.formData.comments;
        // console.log(this.comments);
      });
    },
    submitForm() {
      Axios.send("/movie/scoring", "post", {
        uid: localStorage.getItem("uid"),
        mid: this.$route.params.id,
        score: this.value,
      }).then((result) => {
        this.getValue();
      });
    },
    getValue() {
      Axios.send("/movie/score", "get", {
        uid: localStorage.getItem("uid"),
        mid: this.$route.params.id,
      })
        .then(
          (result) => {
            this.scoreData = result.data;
            // console.log(this.value);
            // console.log(this.scoreData);
            this.value = this.scoreData
          },
          (error) => {
            console.log("registerAxiosError", error);
          }
        )
        .catch((err) => {
          this.value = 0;
          throw err;
        });
        this.value = this.scoreData;
      return this.scoreData;
    },
  },
};
</script>