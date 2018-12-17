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
              <el-select v-model="defaultSoftList" placeholder="请选择软件">
                <el-option v-for="item in softList" :label="item.label" :key="item.value" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="卡类单位">
              <el-select v-model="defaultCardTypeUnit" placeholder="请选择卡类单位">
                <el-option v-for="item in cardTypeUnitList" :label="item.label" :key="item.value" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="使用状态">
              <el-select v-model="defaultUseStatus" placeholder="请选择使用状态">
                <el-option v-for="item in useStatusList" :label="item.label" :key="item.value" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="封停状态">
              <el-select v-model="defaultClosure" placeholder="请选择封停状态">
                <el-option v-for="item in closureList" :label="item.label" :key="item.value" :value="item.value">
                </el-option>
              </el-select>
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
            <span style="color: #409EFF;cursor: pointer;margin-left: 20px" @click="search">刷新数据</span>
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
              prop="cardNumber"
              label="充值卡号"
              align="center"
            />
            <el-table-column
              prop="useStatus"
              label="使用状态"
              align="center"
            />
            <el-table-column
              prop="closure"
              label="是否封停使用"
              align="center"
            />
            <el-table-column
              prop="softName"
              label="软件名称"
              align="center"
            />
            <el-table-column
              prop="cardTypeUnit"
              label="卡类单位"
              align="center"
            />
            <el-table-column
              prop="cardTypeValue"
              label="卡类面值"
              align="center"
            />
            <el-table-column
              prop="accountName"
              label="使用者账号"
              align="center"
            />
            <el-table-column
              prop="startDate"
              label="开始使用时间"
              align="center"
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

      defaultSoftList: "0",
      softList: [],
      defaultCardTypeUnit: 100,
      cardTypeUnitList: [],
      defaultUseStatus: 100,
      useStatusList: [
        {label: "全部",value: 100},
        {label: "未使用",value: 0},
        {label: "已使用",value: 1},
      ],
      defaultClosure: 100,
      closureList: [
        {label: "全部",value: 100},
        {label: "未封停",value: 0},
        {label: "已封停",value: 1},
      ],

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

    this.cardTypeUnitList.push(
        {
        label: "全部",
        value: 100,
        },
        {
        label: "分",
        value: 0,
        },
        {
        label: "时",
        value: 1,
        },
        {
        label: "天",
        value: 2,
        },
        {
        label: "周",
        value: 3,
        },
        {
        label: "月",
        value: 4,
        },
        {
        label: "年",
        value: 5,
        },
    );

    this.$axios.get('soft/list').then((rsp) => {
      this.softList.push({
        label: "全部",
        value: "0",
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
    softListChange(value) {
      if (value == "0") {
        this.getTableData();
      } else {
        this.$message.success('执行刷新数据成功...')
        this.getTableData({softId: value});
      }
    },
    getTableData(data, pageNum) {
      data = data || {}
      pageNum = pageNum || 1
      data.current = pageNum
      data.size = this.tablePageSize

      this.$axios.get('card/page', {
        params: data
      }).then((rsp) => {
        this.tableTotal = rsp.data.total
        for (let i = 0; i < rsp.data.records.length; i++) {
          rsp.data.records[i].createDate = time.timeStampDate({time:rsp.data.records[i].createDate});
          rsp.data.records[i].startDate = time.timeStampDate({time:rsp.data.records[i].startDate});
          switch (rsp.data.records[i].cardTypeUnit) {
            case 0:
              rsp.data.records[i].cardTypeUnit = "分";
              break;
            case 1:
              rsp.data.records[i].cardTypeUnit = "时";
              break;
            case 2:
              rsp.data.records[i].cardTypeUnit = "天";
              break;
            case 3:
              rsp.data.records[i].cardTypeUnit = "周";
              break;
            case 4:
              rsp.data.records[i].cardTypeUnit = "月";
              break;
            case 5:
              rsp.data.records[i].cardTypeUnit = "年";
              break;
          }
          if (rsp.data.records[i].useStatus == 0) {
            rsp.data.records[i].useStatus = "未使用";
          }else {
            rsp.data.records[i].useStatus = "已使用";
          }
          if (rsp.data.records[i].closure == 0) {
            rsp.data.records[i].closure = "未封停";
          }else {
            rsp.data.records[i].closure = "已封停";
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
    search() {
      this.$message.success('执行刷新数据成功...')
      if (this.defaultSoftList == "0") {
        this.getTableData();
      } else {
        this.getTableData({softId: value});
      }
    },
    removeRow(row) {
      this.$axios.post('card/remove', this.$qs.stringify({
        cardTypeId: row.id
      })).then((rsp) => {
        this.getTableData();
        this.$message.success(rsp.msg)
      })
    },
  }
}
</script>
