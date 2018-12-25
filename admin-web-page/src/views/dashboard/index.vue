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
          <div id="chartmainbar" style="width:100%; height:400px;"></div>
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
        title : {
          text: '某地区蒸发量和降水量',
          subtext: '纯属虚构'
        },
        tooltip : {
          trigger: 'axis'
        },
        legend: {
          data:['蒸发量','降水量']
        },
        toolbox: {
          show : true,
          feature : {
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
          }
        },
        calculable : true,
        xAxis : [
          {
            type : 'category',
            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
          }
        ],
        yAxis : [
          {
            type : 'value'
          }
        ],
        series : [
          {
            name:'蒸发量',
            type:'bar',
            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
            markPoint : {
              data : [
                {type : 'max', name: '最大值'},
                {type : 'min', name: '最小值'}
              ]
            },
            markLine : {
              data : [
                {type : 'average', name: '平均值'}
              ]
            }
          },
          {
            name:'降水量',
            type:'bar',
            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
            markPoint : {
              data : [
                {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183},
                {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
              ]
            },
            markLine : {
              data : [
                {type : 'average', name : '平均值'}
              ]
            }
          }
        ]
      }
    }
  },
  methods: {
  }
}
</script>
