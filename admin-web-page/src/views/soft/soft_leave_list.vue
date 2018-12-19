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
            <span @click="openExpress" style="color: #409EFF;cursor: pointer;margin-left: 20px"> 上一页</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="workingArea = !workingArea">
              收起
            </el-button>
          </div>

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
              prop="content"
              label="留言内容"
              align="center"
            />
            <el-table-column
              prop="qq"
              align="center"
              label="留言联系QQ"
            />

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
    openExpress() {
      this.$router.push({
        name: 'SoftList',
      })
    },
    getTableData() {

      let data = this.seachForm
      data.current = this.tablePageNum
      data.size = this.tablePageSize
      data.softId = this.$route.params.id

      this.$axios.get('softLeaveMessage/page', {
        params: data
      }).then((rsp) => {
        this.tableTotal = rsp.data.total
        for (let i = 0;i < rsp.data.records.length;i++) {
          rsp.data.records[i].createDate = time.timeStampDate({time:rsp.data.records[i].createDate});
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
    removeRow(row) {
      this.$axios.post('softLeaveMessage/remove', this.$qs.stringify({
        softLeaveMessageId: row.id
      })).then((rsp) => {
        this.getTableData()
        this.$message.success(rsp.msg)
      })
    },
  }
}
</script>
