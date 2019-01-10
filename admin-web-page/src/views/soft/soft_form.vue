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

            <el-form-item label="软件名称" prop="name">
              <el-input v-model="form.name" class="common-width"></el-input>
            </el-form-item>

            <el-form-item label="软件公告" prop="notice">
              <el-input
                v-model="form.notice"
                class="common-width"
                type="textarea"
                :rows="8"
                placeholder="请输入软件公告"
              >
              </el-input>
            </el-form-item>

            <el-form-item label="换绑策略" prop="changeStrategy">
              <el-radio-group v-model="form.changeStrategy" size="medium">
                <el-radio border :label=0 >支持换绑定</el-radio>
                <el-radio border :label=1 >不支持换绑定</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="多开策略" prop="dosingStrategy">
              <el-radio-group v-model="form.dosingStrategy" size="medium">
                <el-radio border :label=0 >只支持单机</el-radio>
                <el-radio border :label=1 >无限制</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="软件被留言 是否邮件通知" prop="emailNotificatio">
              <el-radio-group v-model="form.emailNotificatio" size="medium">
                <el-radio border :label=0 @change="emailNotificatio = true">通知</el-radio>
                <el-radio border :label=1 @change="emailNotificatio = false">不通知</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item v-if="emailNotificatio == true" label="被通知的邮箱账户名" prop="emailName">
              <el-input v-model="form.emailName" class="common-width"></el-input>
            </el-form-item>

            <el-form-item label="注册状态" prop="registerStatus">
              <el-radio-group v-model="form.registerStatus" size="medium">
                <el-radio border :label=0 @change="registerStatus = true">开放注册</el-radio>
                <el-radio border :label=1 @change="registerStatus = false">关闭注册</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item v-if="registerStatus == false" label="关闭注册后的返回信息" prop="registeCloseMsg">
              <el-input
                v-model="form.registeCloseMsg"
                class="common-width"
                type="textarea"
                :rows="4"
                placeholder="请输入关闭注册后的返回信息"
              >
              </el-input>
            </el-form-item>

            <el-form-item label="服务状态" prop="serviceStatus">
              <el-radio-group v-model="form.serviceStatus" size="medium">
                <el-radio border :label=0 @change="serviceStatus = true">收费</el-radio>
                <el-radio border :label=1 @change="serviceStatus = true">免费开放</el-radio>
                <el-radio border :label=2 @change="serviceStatus = false">关闭开放使用</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item v-if="serviceStatus == false" label="关闭状态下的返回信息" prop="serviceCloseMsg">
              <el-input
                v-model="form.serviceCloseMsg"
                class="common-width"
                type="textarea"
                :rows="4"
                placeholder="请输入关闭状态下的返回信息"
              >
              </el-input>
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

        this.$axios.get("soft/single",{
          params: {
            softId: this.$route.params.id,
          }
        }).then((rsp) => {
          this.form =rsp.data;
          if (rsp.data.emailNotificatio == 1) {
            this.emailNotificatio = false;
          }
          if (rsp.data.registerStatus == 1) {
            this.registerStatus = false;
          }
          if (rsp.data.serviceStatus == 2) {
            this.serviceStatus = false;
          }
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

        let url = "soft/create";
        if (isUpdate == true) {
          data.id = this.$route.params.id;
          url = "soft/update";
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
          name: '',
          notice: '',
          changeStrategy: 0,
          dosingStrategy: 0,
          emailNotificatio: 0,
          emailName: '',
          registerStatus: 0,
          registeCloseMsg: '',
          serviceStatus: 1,
          serviceCloseMsg: '',
        },
        forms: {
          name: [
            {required: true, message: '请填写软件名称', trigger: 'blur'},
          ],
          notice: [
            {required: true, message: '请填写软件公告', trigger: 'blur'},
            {min: 1, max: 255, message: '长度在 1 到 255 个字符', trigger: 'blur'}
          ],
          changeStrategy: [
            {required: true, message: '请勾选换绑策略', trigger: 'blur'},
          ],
          dosingStrategy: [
            {required: true, message: '请勾选多开策略', trigger: 'blur'},
          ],
          emailNotificatio: [
            {required: true, message: '请勾选多开策略', trigger: 'blur'},
          ],
          emailName: [
            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
          ],
          registerStatus: [
            {required: true, message: '请勾选注册状态', trigger: 'blur'},
          ],
          registeCloseMsg: [
            {required: true, message: '请填写关闭注册后的返回信息', trigger: 'blur'},
            {min: 1, max: 255, message: '长度在 1 到 255 个字符', trigger: 'blur'}
          ],
          serviceStatus: [
            {required: true, message: '请勾选服务状态', trigger: 'blur'},
          ],
          serviceCloseMsg: [
            {required: true, message: '请填写关闭状态下的返回信息', trigger: 'blur'},
            {min: 1, max: 255, message: '长度在 1 到 255 个字符', trigger: 'blur'}
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
