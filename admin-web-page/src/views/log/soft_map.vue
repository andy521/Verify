<template>
  <div id="app">

    <!--搜索操作区-->
    <!-- gutter = 间距(控制每块col间距大小)-->
    <el-row :gutter="0">

      <el-col :span="24" style="margin-top: 10px">

        <el-card v-show="searchWorkspace == false" shadow="always">
          <i class="el-icon-search"/>
          <span> 搜索</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="searchWorkspace = !searchWorkspace">
            展示
          </el-button>
        </el-card>

        <el-card v-show="searchWorkspace == true" class="box-card" shadow="always">
          <div slot="header" class="clearfix">
            <i class="el-icon-search"/>
            <span> 搜索</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="searchWorkspace = !searchWorkspace">
              收起
            </el-button>
          </div>

          <el-form :inline="true" :model="seachForm" class="demo-form-inline" @submit.native.prevent>
            <el-form-item label="软件选择">
              <el-select v-model="seachForm.softId" placeholder="请选择软件">
                <el-option v-for="item in softList" :label="item.label" :key="item.value" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="search(seachForm.softId)">查询</el-button>
            </el-form-item>
          </el-form>

        </el-card>

      </el-col>

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

    this.$axios.get('soft/list').then((rsp) => {
      this.softList.push({
        label: "全部",
        value: "",
      });
      for (let i = 0;i < rsp.data.length;i++) {
        this.softList.push({
          label: rsp.data[i].name,
          value: rsp.data[i].id,
        });
      }
    });

    this.search('');

  },
  data() {
    return {

      // 控制两块区域是否显示
      searchWorkspace: true,
      workingArea: true,
      softList: [],
      // 搜索表单
      seachForm: {
        softId: "",
      },

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
    search(softId) {

      let date = this.getDate();

      this.loginbar.xAxis[0].data = date;
      this.registerbar.xAxis[0].data = date;

      var loginDateMap = new Map();
      var registerDateMap = new Map();

      for (let i = 0;i < date.length;i++) {
        loginDateMap.set(date[i],0);
        registerDateMap.set(date[i],0);
      }

      this.$axios.get("accountLoginLog/getBeforeData?softId="+softId).then((rsp) => {
        for (let i = 0;i < rsp.data.length;i++) {
          let data = rsp.data[i].split(" ");
          let d = data[0];

          for (let x of loginDateMap) {
            if (x[0] == d) {
              loginDateMap.set(x[0],x[1]+1);
            }
          }

        }
        this.loginbar.series[0].data = [];
        for (let xx of loginDateMap) {
          this.loginbar.series[0].data.push(xx[1]);
        }

        let login = this.$echarts.init(document.getElementById("login"));
        login.setOption(this.loginbar);
      })
      this.$axios.get("accountRegisterLog/getBeforeData?softId="+softId).then((rsp) => {
        for (let i = 0;i < rsp.data.length;i++) {
          let data = rsp.data[i].split(" ");
          let d = data[0];

          for (let x of registerDateMap) {
            if (x[0] == d) {
              registerDateMap.set(x[0],x[1]+1);
            }
          }

        }
        this.registerbar.series[0].data = [];
        for (let xx of registerDateMap) {
          this.registerbar.series[0].data.push(xx[1]);
        }

        let register = this.$echarts.init(document.getElementById("register"));
        register.setOption(this.registerbar);
      })
    },
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
