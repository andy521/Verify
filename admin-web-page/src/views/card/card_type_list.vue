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

          <el-form :inline="true" :model="seachFormInline" class="demo-form-inline" @submit.native.prevent>
            <el-form-item label="软件选择">
              <el-select v-model="defaultSoftList" placeholder="请选择软件" @change="softListChange">
                <el-option v-for="item in softList" :label="item.label" :key="item.value" :value="item.value">
                </el-option>
              </el-select>
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
              prop="softName"
              label="软件名称"
              align="center"
            />
            <el-table-column
              prop="unit"
              label="单位"
              align="center"
            />
            <el-table-column
              prop="value"
              align="center"
              label="面值"
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
          <el-pagination
            :page-sizes="tablePageSizes"
            :page-size="tablePageSize"
            :total="tableTotal"
            style="margin-top: 15px"
            background
            layout="total, sizes, prev, pager, next, jumper"
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

      defaultSoftList: null,
      softList: [],

      // 搜索表单
      seachFormInline: {
        name: ''
      },

      // 表格
      tableTotal: 0,
      tableData: [],
      tablePageSize: 10,
      tablePageSizes: [10, 50, 100, 200]
    }
  },
  mounted() {
    this.$axios.get('soft/list').then((rsp) => {
      this.softList.push({
        label: "全部",
        value: "0",
      });
      this.defaultSoftList = "0";
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
    softListChange(value) {
      if (value == "0") {
        this.getTableData();
      } else {
        this.$message.success('执行刷新数据成功...')
        this.getTableData({softId: value});
      }
    },
    openForm(params) {
      params = params || {}
      params.id = params.id || null
      this.$router.push({
        name: 'CardTypeForm',
        params: params
      })
    },
    getTableData(data, pageNum) {
      data = data || {}
      pageNum = pageNum || 1
      data.current = pageNum
      data.size = this.tablePageSize

      this.$axios.get('cardType/page', {
        params: data
      }).then((rsp) => {
        this.tableTotal = rsp.data.total
        for (let i = 0; i < rsp.data.records.length; i++) {
          rsp.data.records[i].createDate = time.timeStampDate({time:rsp.data.records[i].createDate});
          switch (rsp.data.records[i].unit) {
            case 0:
              rsp.data.records[i].unit = "分";
              break;
            case 1:
              rsp.data.records[i].unit = "时";
              break;
            case 2:
              rsp.data.records[i].unit = "天";
              break;
            case 3:
              rsp.data.records[i].unit = "周";
              break;
            case 4:
              rsp.data.records[i].unit = "月";
              break;
            case 5:
              rsp.data.records[i].unit = "年";
              break;
          }
        }
        this.tableData = rsp.data.records
      })
    },
    handleCurrentChange(val) {
      this.getTableData({
        name: this.seachFormInline.name
      }, val)
    },
    updateRow(row) {
      this.openForm({ id: row.id })
    },
    removeRow(row) {
      this.$axios.post('cardType/remove', this.$qs.stringify({
        cardTypeId: row.id
      })).then((rsp) => {
        this.defaultSoftList = "0";
        this.getTableData();
        this.$message.success(rsp.msg)
      })
    },
  }
}
</script>
