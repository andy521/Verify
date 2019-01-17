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
          <!-- 登陆次数 -->
          <div id="login" style="width:100%; height:400px;"></div>
          <!-- 注册次数 -->
          <div id="register" style="width:100%; height:400px;"></div>
        </el-card>
      </el-col>
      <el-col :span="2"></el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  mounted() {

    let date = this.getDate();

    this.loginbar.xAxis[0].data = date;
    this.registerbar.xAxis[0].data = date;

    var loginDateMap = new Map();
    var registerDateMap = new Map();

    for (let i = 0;i < date.length;i++) {
      loginDateMap.set(date[i],0);
      registerDateMap.set(date[i],0);
    }

    this.$axios.get("accountLoginLog/getBeforeData?softId=").then((rsp) => {
      for (let i = 0;i < rsp.data.length;i++) {
        let data = rsp.data[i].split(" ");
        let d = data[0];

        for (let x of loginDateMap) {
          if (x[0] == d) {
            loginDateMap.set(x[0],x[1]+1);
          }
        }

      }
      for (let xx of loginDateMap) {
        this.loginbar.series[0].data.push(xx[1]);
      }

      let login = this.$echarts.init(document.getElementById("login"));
      login.setOption(this.loginbar);
    })
    this.$axios.get("accountRegisterLog/getBeforeData?softId=").then((rsp) => {
      for (let i = 0;i < rsp.data.length;i++) {
        let data = rsp.data[i].split(" ");
        let d = data[0];

        for (let x of registerDateMap) {
          if (x[0] == d) {
            registerDateMap.set(x[0],x[1]+1);
          }
        }

      }
      for (let xx of registerDateMap) {
        this.registerbar.series[0].data.push(xx[1]);
      }

      let register = this.$echarts.init(document.getElementById("register"));
      register.setOption(this.registerbar);
    })



    this.$axios.get("soft/count").then((rsp) => {
      this.softCount = rsp.data
    })
    this.$axios.get("card/count").then((rsp) => {
      this.cardCount = rsp.data
    })
    this.$axios.get("account/count").then((rsp) => {
      this.accountCount = rsp.data
    })

  },
  data() {
    return {
      activeNames: ['1'],
      softCount: 0,
      cardCount: 0,
      accountCount: 0,
      loginbar:{
        title : {
          text: '登陆次数',
          subtext: '登陆次数'
        },
        tooltip : {
          trigger: 'axis'
        },
        legend: {
          data:['登陆次数']
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
            data : [],
          }
        ],
        yAxis : [
          {
            type : 'value'
          }
        ],
        series : [
          {
            name:'登陆次数',
            type:'bar',
            data:[],
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
        ]
      },
      registerbar:{
        title : {
          text: '注册次数',
          subtext: '注册次数'
        },
        tooltip : {
          trigger: 'axis'
        },
        legend: {
          data:['注册次数']
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
            data : [],
          }
        ],
        yAxis : [
          {
            type : 'value'
          }
        ],
        series : [
          {
            name:'注册次数',
            type:'bar',
            data:[],
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
        ]
      },
    }
  },
  methods: {
    getDay(time){
      var now = new Date();//获取当前时间

      var nowMs = now.getTime();//获取当前时间的毫秒数

      var beforeMs =  nowMs -  1000 * 60 * 60 * 24 * parseInt(time);//前几天，n就取几，整数

      var beforeDate = new Date().setTime(beforeMs);

      let date = new Date(beforeDate);
      let year = date.getFullYear();
      let month = date.getMonth()+1;
      let day = date.getDate();
      month = month < 10 ? "0"+month:month;
      day = day < 10 ? "0"+day:day;
      beforeDate = year+'-'+month+'-'+day;
      return beforeDate;
    },
    getDate() {
      let date = [];
      for (let i = 7;i >= 0;i--) {
        date.push(this.getDay(i));
      }
      return date;
    },
}
}
</script>
