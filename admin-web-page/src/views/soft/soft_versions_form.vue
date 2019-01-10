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

            <el-form-item label="版本号" prop="number">
              <el-input v-model="form.number" class="common-width"></el-input>
            </el-form-item>

            <el-form-item label="更新公告(日志)" prop="notice">
              <el-input
                v-model="form.notice"
                class="common-width"
                type="textarea"
                :rows="15"
                placeholder="请输入更新公告"
              >
              </el-input>
            </el-form-item>

            <el-form-item label="更新地址" prop="updateUrl">
              <el-input v-model="form.updateUrl" class="common-width"></el-input>
            </el-form-item>

            <el-form-item label="是否强制更新" prop="novatioNecessaria">
              <el-radio-group v-model="form.novatioNecessaria" size="medium">
                <el-radio border :label=0 >不强制</el-radio>
                <el-radio border :label=1 >强制</el-radio>
              </el-radio-group>
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

      if (this.$route.params.versionsNum != null) {
        this.$axios.get("softVersions/getSingleBySoftId",{
          params: {
            softId: this.$route.params.id,
          }
        }).then((rsp) => {
          this.id = rsp.data.id;
          this.form =rsp.data;
        });

        this.formButtonName = '立即保存';
      }

    },
    methods: {
      //上一页
      openExpress() {
        this.$router.push({
          name: 'SoftList',
        })
      },
      //表单操作
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.$route.params.versionsNum != null) {
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

        let url = "softVersions/create";
        data.softId = this.$route.params.id;
        if (isUpdate == true) {
          data.id = this.id;
          url = "softVersions/update";
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

        id: 0,

        //表单配置
        form: {
          number: '',
          notice: '',
          novatioNecessaria: 0,
          updateUrl: '',
        },
        forms: {
          number: [
            {required: true, message: '请填写版本号', trigger: 'blur'},
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
