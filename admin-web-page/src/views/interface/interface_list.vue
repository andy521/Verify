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

          <!--表格展示区-->
          <el-table
            :data="tableData"
            border
            style="width: 100%;margin-top: 10px">
            <el-table-column
              prop="remarks"
              label="接口备注"
              align="center"
            />
            <el-table-column
              prop="key"
              label="KEY"
              align="center"
            />
            <el-table-column
              prop="visit"
              label="是否开放接口"
              align="center"
            >
              <template slot-scope="scope">
                <el-switch
                  @change="visitChange($event,scope.row)"
                  v-model="scope.row.visit"
                  active-color="#13ce66"
                  inactive-color="#ff4949">
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column
              prop="ipHandle"
              label="IP限流"
              align="center"
            >
              <template slot-scope="scope">
                <el-switch
                  @change="ipHandleChange($event,scope.row)"
                  v-model="scope.row.ipHandle"
                  active-color="#13ce66"
                  inactive-color="#ff4949">
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column
              prop="ipVisits"
              align="center"
              label="间隔次数"
            />
            <el-table-column
              prop="ipRedisInterval"
              label="缓存时间(分钟)"
              align="center"
            >
            </el-table-column>
            <el-table-column
              fixed="right"
              align="center"
              label="操作"
              width="200">

              <template slot-scope="scope">
                <el-button type="text" size="small" @click="updateRow(scope.row)">编辑</el-button>
              </template>

            </el-table-column>
          </el-table>

        </el-card>

      </el-col>

    </el-row>

  </div>
</template>

<script>
export default {
  data() {
    return {
      // 控制两块区域是否显示
      searchWorkspace: true,
      workingArea: true,

      // 表格
      tableData: [],
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
        name: 'InterfaceForm',
        params: params
      })
    },
    getTableData() {

      this.$axios.get('interfaceManagement/list').then((rsp) => {

        for (let i = 0; i < rsp.data.length; i++) {
          rsp.data[i].visit = (rsp.data[i].visit == 0) ? false : true;
          rsp.data[i].ipHandle = (rsp.data[i].ipHandle == 0) ? false : true;
        }
        this.tableData = rsp.data

      })
    },
    search(isPrompt) {
      if (isPrompt == true) {
        this.$message.success('执行刷新数据成功...')
      }
      this.getTableData()
    },
    updateRow(row) {
      this.openForm({ id: row.key })
    },
    visitChange(value,row) {

      this.$axios.post('interfaceManagement/closeInterface', this.$qs.stringify({
        key: row.key,
        on: value ? 1 : 0
      })).then((rsp) => {
        this.getTableData();
        this.$message(rsp.msg)
      })
    },
    ipHandleChange(value,row) {

      this.$axios.post('interfaceManagement/ipHandle', this.$qs.stringify({
        key: row.key,
        on: value ? 1 : 0
      })).then((rsp) => {
        this.getTableData();
        this.$message(rsp.msg)
      })
    },
  }
}
</script>
