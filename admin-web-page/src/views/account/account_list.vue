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
            <el-form-item label="IP地址">
              <el-input v-model="seachForm.createIp" placeholder="IP地址" @keyup.enter.native="search"/>
            </el-form-item>
            <el-form-item label="用户名">
              <el-input v-model="seachForm.username" placeholder="用户名" @keyup.enter.native="search"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="search">查询</el-button>
            </el-form-item>
          </el-form>

        </el-card>

      </el-col>

    </el-row>

    <!--表格展示(操作)区-->
    <el-row :gutter="0">

      <el-col :span="24" style="margin-top: 10px">

        <el-card v-show="workingArea == false" shadow="always">
          <i class="el-icon-edit"/>
          操作
          <el-button style="float: right; padding: 3px 0" type="text" @click="workingArea = !workingArea">
            展示
          </el-button>
        </el-card>

        <el-card v-show="workingArea" class="box-card" shadow="always">
          <div slot="header" class="clearfix">
            <i class="el-icon-edit"/>
            <span> 操作</span>
            <span style="color: #409EFF;cursor: pointer;margin-left: 20px" @click="search(true)">刷新数据</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="workingArea = !workingArea">
              收起
            </el-button>
          </div>

          <!--按钮操作区-->
          <!-- <el-button type="primary" @click="openForm"><i class="el-icon-plus"/> 添加</el-button> -->

          <!--表格展示区-->
          <el-table
            :data="tableData"
            border
            style="width: 100%;margin-top: 10px">
            <el-table-column
              prop="createDate"
              label="创建时间"
              align="center"
            />
            <el-table-column
              prop="softName"
              label="软件名"
              align="center"
            />
            <el-table-column
              prop="username"
              label="用户名"
              align="center"
            />
            <el-table-column
              prop="password"
              label="密码"
              align="center"
            />
            <el-table-column
              prop="securityCode"
              label="安全码"
              align="center"
            />
            <el-table-column
              prop="qq"
              label="联系QQ"
              align="center"
            />
            <el-table-column
              prop="name"
              label="姓名"
              align="center"
            />
            <el-table-column
              prop="createIp"
              label="IP地址"
              align="center"
            />
            <el-table-column
              prop="createIpInfo"
              label="IP信息"
              align="center"
            />
            <el-table-column
              prop="blacklist"
              label="黑名单"
              align="center"
            >
              <template slot-scope="scope">
                <el-switch
                  @change="blacklistChange($event,scope.row)"
                  v-model="scope.row.blacklist"
                  active-color="#13ce66"
                  inactive-color="#ff4949">
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              align="center"
              label="操作"
              width="200">

              <template slot-scope="scope">
                <el-button type="text" size="small" style="color: red" @click="removeRow(scope.row)">删除</el-button>
              </template>

            </el-table-column>
          </el-table>

          <!--分页-->
          <el-pagination
            :page-sizes="tablePageSizes"
            :page-size="tablePageSize"
            :total="tableTotal"
            style="margin-top: 15px"
            background
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"/>

        </el-card>

      </el-col>

    </el-row>

  </div>
</template>

<script>
  var time = require('@/utils/time.js');
export default {
  data() {
    return {
      // 控制两块区域是否显示
      searchWorkspace: true,
      workingArea: true,

      softList: [],

      // 搜索表单
      seachForm: {
        softId: "",
        createIp: "",
        username: "",
      },

      // 表格
      tableTotal: 0,
      tableData: [],
      tablePageNum: 1,
      tablePageSize: 10,
      tablePageSizes: [10, 50, 100, 200]
    }
  },
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
    this.getTableData();
  },
  methods: {
    getTableData() {

      let data = this.seachForm
      data.current = this.tablePageNum
      data.size = this.tablePageSize

      this.$axios.get('account/page', {
        params: data
      }).then((rsp) => {
        this.tableTotal = rsp.data.total
        for (let i = 0; i < rsp.data.records.length; i++) {
          rsp.data.records[i].createDate = time.timeStampDate({time:rsp.data.records[i].createDate});
          rsp.data.records[i].blacklist = (rsp.data.records[i].blacklist == 0) ? false : true;
        }
        this.tableData = rsp.data.records
      })
    },
    handleSizeChange(val) {
      this.tablePageSize = val
      this.getTableData()
    },
    handleCurrentChange(val) {
      this.tablePageNum = val
      this.getTableData()
    },
    search(isPrompt) {
      if (isPrompt == true) {
        this.$message.success('执行刷新数据成功...')
      }
      this.getTableData()
    },
    updateRow(row) {
      this.openForm({ id: row.id })
    },
    blacklistChange(value,row) {

      this.$axios.post('account/blacklist', this.$qs.stringify({
        accountId: row.id,
        blacklist: value ? 1 : 0
      })).then((rsp) => {
        this.getTableData();
        this.$message(rsp.msg)
      })
    },
    removeRow(row) {
      this.$axios.post('account/remove', this.$qs.stringify({
        accountId: row.id
      })).then((rsp) => {
        this.getTableData();
        this.$message(rsp.msg)
      })
    },
  }
}
</script>
