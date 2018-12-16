<template>

  <div id="app">
    <el-row :gutter="0">

      <el-col :span="24">

        <el-card shadow="always" v-show="searchWorkspace == false" style="text-align: center">
          <i class="el-icon-upload"></i>
          <span> 操作</span>
          <span @click="openExpress" style="color: #409EFF;cursor: pointer;margin-left: 20px"> 上一页</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="searchWorkspace = !searchWorkspace">
            展示
          </el-button>
        </el-card>

        <el-card class="box-card" shadow="always" v-show="searchWorkspace == true">
          <div slot="header" class="clearfix">
            <i class="el-icon-upload"></i>
            <span> 操作</span>
            <span @click="openExpress" style="color: #409EFF;cursor: pointer;margin-left: 20px"> 上一页</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="searchWorkspace = !searchWorkspace">
              收起
            </el-button>
          </div>

          <el-form :model="form" :rules="forms" :status-icon="true"
                   ref="form" label-width="100px" class="demo-ruleForm">

            <el-form-item label="软件选择" prop="softId">
              <el-select v-model="form.softId" placeholder="请选择软件">
                <el-option v-for="item in softList" :label="item.label" :key="item.value" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="卡类单位" prop="unit">
              <el-select v-model="form.unit" placeholder="请选择卡类单位">
                <el-option v-for="item in cardTypeUnitList" :label="item.label" :key="item.value" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="面值" prop="value">
              <el-input v-model="form.value" class="common-width"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('form')">{{ formButtonName }}</el-button>
              <el-button @click="resetForm('form')">重置</el-button>
            </el-form-item>

          </el-form>

        </el-card>

      </el-col>

    </el-row>

  </div>

</template>

<script>
  export default {
    mounted() {

      this.$axios.get('soft/list').then((rsp) => {
        for (let i = 0;i < rsp.data.length;i++) {
          this.softList.push({
            label: rsp.data[i].name,
            value: rsp.data[i].id,
          });
        }
      });

      this.cardTypeUnitList.push(
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

      if (this.$route.params.id != null) {

        this.$axios.get("cardType/single",{
          params: {
            cardTypeId: this.$route.params.id,
          }
        }).then((rsp) => {
          this.form =rsp.data;
        });

        this.formButtonName = '立即保存';
      }

    },
    methods: {
      //上一页
      openExpress() {
        this.$router.push({
          name: 'CardTypeList',
        })
      },
      //表单操作
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.$route.params.id != null) {
              this.submit(true);
            } else {
              this.submit(false);
            }
          } else {
            this.$message.error('提交错误');
            return false;
          }
        });
      },
      submit(isUpdate) {

        let data = this.form;

        let url = "cardType/create";
        if (isUpdate == true) {
          data.id = this.$route.params.id;
          url = "cardType/update";
        }

        this.$axios({
          method: 'post',
          url: url,
          data:this.$qs.stringify(data),
        }).then((rsp) => {
          this.$message(rsp.msg);
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    },
    data() {
      return {
        //收起放下
        searchWorkspace: true,

        formButtonName: '立即创建',

        softList: [],
        cardTypeUnitList: [],

        //表单配置
        form: {
          softId: '',
          unit: '',
          value: '',
        },
        forms: {
          softId: [
            {required: true, message: '请选择软件', trigger: 'blur'},
          ],
          unit: [
            {required: true, message: '请选择单位', trigger: 'blur'},
          ],
          value: [
            {required: true, message: '请填写面值', trigger: 'blur'},
            {min: 1, max: 6, message: '长度在 1 到 6 个字符', trigger: 'blur'}
          ],
        },

      }
    }
  }
</script>

<style>
  .common-width {
    width: 500px;
  }
</style>
