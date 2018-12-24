<template>
  <div id="app">

    <el-row type="flex" style="padding-top: 10px">
      <el-col :span="2"></el-col>
      <el-col :span="20">
        <el-card shadow="always" style="text-align: center">
          <i class="el-icon-star-on"></i>
          软件数量：{{softCount}}
          <i class="el-icon-star-on"></i>
        </el-card>
      </el-col>
      <el-col :span="2"></el-col>
    </el-row>

    <el-row type="flex" style="padding-top: 10px">
      <el-col :span="2"></el-col>
      <el-col :span="20">
        <el-card shadow="always" style="text-align: center">
          <i class="el-icon-star-on"></i>
          卡密数量：{{cardCount}}
          <i class="el-icon-star-on"></i>
        </el-card>
      </el-col>
      <el-col :span="2"></el-col>
    </el-row>

    <el-row type="flex" style="padding-top: 10px">
      <el-col :span="2"></el-col>
      <el-col :span="20">
        <el-card shadow="always" style="text-align: center">
          <i class="el-icon-star-on"></i>
          用户数量：{{accountCount}}
          <i class="el-icon-star-on"></i>
        </el-card>
      </el-col>
      <el-col :span="2"></el-col>
    </el-row>

    <el-row type="flex" style="padding-top: 10px">
      <el-col :span="2"></el-col>
      <el-col :span="20">
        <el-card shadow="always" style="text-align: center">
          <!-- 折线图 -->
          <div id="chartmainline" style="width:600px; height:400px;"></div>
          <!-- 柱状图 -->
          <div id="chartmainbar" style="width:600px; height:400px;"></div>
        </el-card>
      </el-col>
      <el-col :span="2"></el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  mounted() {
    this.$axios.get("soft/count").then((rsp) => {
      this.softCount = rsp.data
    })
    this.$axios.get("card/count").then((rsp) => {
      this.cardCount = rsp.data
    })
    this.$axios.get("account/count").then((rsp) => {
      this.accountCount = rsp.data
    })
    //基于准本好的DOM，初始化echarts实例
    let chartmainline = this.$echarts.init(document.getElementById("chartmainline"));
    let chartmainbar = this.$echarts.init(document.getElementById("chartmainbar"));
    //绘制图表
    chartmainline.setOption(this.optionline);
    chartmainbar.setOption(this.optionbar);
  },
  data() {
    return {
      activeNames: ['1'],
      softCount: 0,
      cardCount: 0,
      accountCount: 0,
      optionline:{
        title:{
          text:'ECharts 数据统计'
        },
        tooltip:{},
        legend:{
          data:['用户来源']
        },
        xAxis:{
          data:["Android","IOS","PC","Ohter"]
        },
        yAxis:{

        },
        series:[{
          name:'访问量',
          type:'line', //设置图表主题
          data:[500,200,360,100]
        }]
      },
      optionbar:{
        title:{
          text:'ECharts 数据统计'
        },
        tooltip:{},
        legend:{
          data:['用户来源']
        },
        xAxis:{
          data:["Android","IOS","PC","Ohter"]
        },
        yAxis:{

        },
        series:[{
          name:'访问量',
          type:'bar', //设置图表主题
          data:[500,200,360,100]
        }]
      }
    }
  },
  methods: {
  }
}
</script>
