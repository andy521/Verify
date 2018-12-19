<template>
  <div id="app">

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
              prop="username"
              label="用户名"
              align="center"
            />
            <el-table-column
              prop="password"
              align="center"
              label="用户密码"
            />
            <el-table-column
              prop="isUse"
              align="center"
              label="是否使用"
            />
            <el-table-column
              prop="total"
              align="center"
              label="被使用的次数"
            />
            <el-table-column
              fixed="right"
              align="center"
              label="操作"
              width="200">

              <template slot-scope="scope">
                <el-button type="text" size="small" @click="updateRow(scope.row)">编辑</el-button>
                <el-button type="text" size="small" style="color: red" @click="removeRow(scope.row)">删除</el-button>
              </template>

            </el-table-column>
          </el-table>

          <!--分页-->
          <!-- <el-pagination
            :page-sizes="tablePageSizes"
            :page-size="tablePageSize"
            :total="tableTotal"
            style="margin-top: 15px"
            background
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"/> -->

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
        name: 'EmailAccountForm',
        params: params
      })
    },
    getTableData() {
      
      // let data = this.seachForm
      // data.current = this.tablePageNum
      // data.size = this.tablePageSize

      this.$axios.get('emailAccount/list').then((rsp) => {
        // this.tableTotal = rsp.data.total
        for (let i = 0; i < rsp.data.length; i++) {
          rsp.data[i].createDate = time.timeStampDate({time:rsp.data[i].createDate})
          rsp.data[i].updateDate = time.timeStampDate({time:rsp.data[i].updateDate})
          rsp.data[i].isUse = (rsp.data[i].isUse == 0) ? "可用" : "停用"
        }
        this.tableData = rsp.data
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
    removeRow(row) {
      this.$axios.post('emailAccount/remove', this.$qs.stringify({
        emailAccountId: row.id
      })).then((rsp) => {
        this.search()
        this.$message(rsp.msg)
      })
    },
  }
}
</script>
