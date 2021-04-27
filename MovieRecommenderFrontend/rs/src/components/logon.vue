<template>
  <div style="width: 270px; margin-top: 50px">
    <div
      style="
        text-align: middle;
        font-weight: 400;
        color: #666;
        ont: 400 30px/1.5 'Hiragino Sans GB', 'WenQuanYi Micro Hei', tahoma,
          sans-serif;
      "
    >
      账号注册
    </div>
    <el-form
      :model="logon"
      ref="logon"
      label-width="0"
      class="demo-ruleForm"
      style="margin: 0; padding: 0"
      :rules="rules"
    >
      <el-form-item label="" prop="user">
        <el-input
          style="margin-top: 8px; margin-bottom: 8px"
          placeholder="请输入用户名"
          prefix-icon="el-icon-user"
          v-model="logon.user"
        >
        </el-input>
      </el-form-item>

      <el-form-item label="" prop="pass">
        <el-input
          type="password"
          v-model="logon.pass"
          placeholder="请输入密码"
          autocomplete="off"
          prefix-icon="el-icon-lock"
        ></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-checkbox-group v-model="checkbox" @change="getValue()" size="mini" text-color="black">
          <el-checkbox-button v-for="(item,i) in items" :label="i+1" :key="i" :disabled="item.disabled" :checked="item.checked" >{{item.content}}</el-checkbox-button>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item>
        <el-button
          type="danger"
          style="width: 100%"
          @click="submitForm('logon')"
          >注册</el-button
        >
      </el-form-item>
    </el-form>
    <div
      style="
        text-align: left;
        font-weight: 400;
        color: #666;
        ont: 400 14px/1.5 'Hiragino Sans GB', 'WenQuanYi Micro Hei', tahoma,
          sans-serif;
      "
    >
      已有账号？<router-link
        :to="link"
        tag="span"
        style="color: red; cursor: pointer"
        >立即登录</router-link
      >
    </div>
  </div>
</template>

<script>
import Axios from "@/axios";
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.logon.checkPass !== "") {
          this.$refs.logon.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.logon.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      link: "/log/log",
      logon: {
        pass: "",
        checkPass: "",
        user: "",
      },
      rules: {
        user: [
          {
            required: true,
            message: "用户名不能为空",
          },
        ],
        checkPass: [
          {
            validator: validatePass2,
            trigger: "blur",
          },
        ],
        pass: [
          {
            validator: validatePass,
            trigger: "blur",
          },
        ],
      },
      checkbox:[],
      items:[
        {id:0,content:"剧情",disabled:false,checked:false,value:0},
        {id:1,content:"喜剧",disabled:false,checked:false},
        {id:2,content:"动作",disabled:false,checked:false},
        {id:3,content:"爱情",disabled:false,checked:false},
        {id:4,content:"科幻",disabled:false,checked:false},
        {id:5,content:"动画",disabled:false,checked:false},
        {id:6,content:"悬疑",disabled:false,checked:false},
        {id:7,content:"传记",disabled:false,checked:false},
        {id:8,content:"战争",disabled:false,checked:false},
      ],
      checkboxForm:{
        "movieTags":"",
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Axios.send("/user/register", "post", {
            name: this.logon.user,
            password: this.logon.pass,
            movieTags: this.checkboxForm.movieTags,
          })
            .then(
              (res) => {
                console.log(res);
                this.$router.push("/log");
              },
              (error) => {
                alert("用户名重复");
                console.log("registerAxiosError", error);
              }
            )
            .catch((err) => {
              throw err;
            });
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getValue(){
      console.log(this.checkbox);
      this.checkboxForm.movieTags = this.checkbox.join(",");
      console.log(this.checkboxForm.movieTags);
    }
  },
};
</script>

<style scoped>
</style>
