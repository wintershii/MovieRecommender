<template>
  <el-row>
    <el-row :gutter="10">
      <el-col :span="10">
        <div style="text-align: right">
          <el-image
            style="width: 320px; height: 320px"
            :src="url"
            fit="fill"
          ></el-image>
        </div>
      </el-col>
      <el-col :span="10">
        <div style="padding: 10px">
          <div style="font-weight: bold; font-size: 22px">
            计算机网络：自顶向下方法（原书第7版）
          </div>
          <div style="font-size: 15px; margin-top: 13px">
            作者:詹姆斯·F.库罗斯 著出版社:机械工业出版社出版时间:2018年06月
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
          ¥ 70.30
        </div>
        <div style="margin-top: 20px">
          <el-rate
            v-model="value"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}"
          ></el-rate>
        </div>
        <div style="margin-top: 20px">
          <el-row>
            <el-button
              type="danger"
              v-click-stat="{
                dom_id: 'button-7-8',
                page: 'info-5-3',
                platform: 'h5',
                event_name: 'addToShoppingCart',
                type: 'button',
              }"
              >放入购物车</el-button
            >
            <el-button type="danger" plain>立即购买</el-button>
            <el-button type="success" plain>收藏</el-button>
          </el-row>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="10">
      <el-col :span="10">
        <div style="width: 320px; border: 1px solid white"></div>
      </el-col>
      <el-col :span="10">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column
            prop="command"
            lable="用户评价"
            width="320px"
            style="font-weight: bold"
          ></el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </el-row>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      url: "http://img3m2.ddimg.cn/74/10/25299722-1_w_1.jpg",
      value: 3.7,
      tableData: [
        { command: "信赖大品牌，这次购物很满意" },
        { command: "不错用着还可以的东西，还不错，要比想象中好多了" },
        { command: "质量还可以，好评的，很不错，朋友很满意" },
        { command: "质量很好，总之很满意" },
      ],
      favor_click:"{info:[platform:'h5',page:'info-5-3',dom:'button',dom_id:'button-7-8,event:'click']}"
    };
  },
};
</script>
