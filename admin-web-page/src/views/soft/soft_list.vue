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
            <el-form-item label="软件名称">
              <el-input v-model="seachForm.name" placeholder="软件名称" @keyup.enter.native="search"/>
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
          <el-button type="primary" @click="openForm"><i class="el-icon-plus"/> 添加</el-button>

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
              prop="updateDate"
              label="更新时间"
              align="center"
            />
            <el-table-column
              prop="name"
              label="软件名称"
              align="center"
            />
            <el-table-column
              prop="id"
              align="center"
              label="软件id"
            />
            <el-table-column
              prop="serviceStatus"
              align="center"
              label="状态"
            >
              <template slot-scope="scope">
                <el-tag type="success">{{scope.row.serviceStatus}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              prop="accountTotal"
              align="center"
              label="用户数量"
            />
            <el-table-column
              prop="versionsNum"
              align="center"
              label="最新版本"
            />
            <el-table-column
              prop="leaveMessageNum"
              align="center"
              label="反馈留言数量"
            />
            <el-table-column
              fixed="right"
              align="center"
              label="操作"
              width="200">

              <template slot-scope="scope">
                <el-button type="text" size="small" @click="versionsUpdateRow(scope.row)">版本设置</el-button>
                <el-button type="text" size="small" @click="updateRow(scope.row)">编辑</el-button>
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

      // 搜索表单
      seachForm: {
        name: ''
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
    this.getTableData()
  },
  methods: {
    openForm(params) {
      params = params || {}
      params.id = params.id || null
      this.$router.push({
        name: 'SoftForm',
        params: params
      })
    },
    getTableData() {

      let data = this.seachForm
      data.current = this.tablePageNum
      data.size = this.tablePageSize

      this.$axios.get('soft/page', {
        params: data
      }).then((rsp) => {
        this.tableTotal = rsp.data.total
        for (let i = 0; i < rsp.data.records.length; i++) {
          rsp.data.records[i].createDate = time.timeStampDate({time:rsp.data.records[i].createDate})
          rsp.data.records[i].updateDate = time.timeStampDate({time:rsp.data.records[i].updateDate})
          if (rsp.data.records[i].serviceStatus == 0) {
            rsp.data.records[i].serviceStatus = '收费'
          } else if (rsp.data.records[i].serviceStatus == 1) {
            rsp.data.records[i].serviceStatus = '免费'
          } else if (rsp.data.records[i].serviceStatus == 2) {
            rsp.data.records[i].serviceStatus = '关闭'
          }
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
    versionsUpdateRow(row) {
      this.$router.push({
        name: 'SoftVersionsForm',
        params: {
          versionsNum: row.versionsNum,
          id: row.id
        }
      })
    },
    removeRow(row) {
      this.$axios.post('soft/remove', this.$qs.stringify({
        softId: row.id
      })).then((rsp) => {
        this.search()
        this.$message(rsp.msg)
      })
    },
  }
}
</script>
