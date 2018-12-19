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

            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" class="common-width"></el-input>
            </el-form-item>

            <el-form-item label="用户密码" prop="password">
              <el-input v-model="form.password" class="common-width"></el-input>
            </el-form-item>

            <el-form-item label="是否使用" prop="isUse">
              <el-radio-group v-model="form.isUse" size="medium">
                <el-radio border :label=0 >可用</el-radio>
                <el-radio border :label=1 >停用</el-radio>
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

      if (this.$route.params.id != null) {

        this.$axios.get("emailAccount/single",{
          params: {
            emailAccountId: this.$route.params.id,
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
          name: 'EmailAccountList',
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

        let url = "emailAccount/create";
        if (isUpdate == true) {
          data.id = this.$route.params.id;
          url = "emailAccount/update";
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

        emailNotificatio: true,
        registerStatus: true,
        serviceStatus: true,

        //表单配置
        form: {
          username: '',
          password: '',
          isUse: 0,
        },
        forms: {
          username: [
            {required: true, message: '请填写邮箱名称', trigger: 'blur'},
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
          ],
          password: [
            {required: true, message: '请填写密码', trigger: 'blur'},
            {min: 1, max: 255, message: '长度在 1 到 255 个字符', trigger: 'blur'}
          ],
          isUse: [
            {required: true, message: '请勾选使用状态', trigger: 'blur'},
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
