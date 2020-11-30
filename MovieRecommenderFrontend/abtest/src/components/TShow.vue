<template>
  <el-row>
    <el-col>
      <div id="myChart" style="width:400px;height:400px;">结果展示</div>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'TShow',
  mounted(){
    this.drawLine();
  },
  methods:{
    drawLine(){
      let myChart = this.$echarts.init(document.getElementById('myChart'))
        // 绘制图表
        myChart.setOption({
            title: { text: '结果展示' },
            tooltip: {},
            xAxis: {
                data: ["k-means","决策树","随机森林","core","协同过滤","als"]
            },
            yAxis: {},
            series: [{
                name: '准确度',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            }]
        });
    }
  }
}
</script>
