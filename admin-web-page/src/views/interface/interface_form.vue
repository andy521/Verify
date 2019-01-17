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

            <el-form-item label="间隔次数" prop="ipVisits">
              <el-input v-model="form.ipVisits" class="common-width"></el-input>
            </el-form-item>

            <el-form-item label="缓存时间(分钟)" prop="ipRedisInterval">
              <el-input v-model="form.ipRedisInterval" class="common-width"></el-input>
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

        this.$axios.get("interfaceManagement/getSingle",{
          params: {
            key: this.$route.params.id,
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
          name: 'InterfaceList',
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
          url = "interfaceManagement/update";
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

        //表单配置
        form: {
          ipVisits: '',
          ipRedisInterval: '',
        },
        forms: {
          ipVisits: [
            {required: true, message: '请填写间隔次数', trigger: 'blur'},
          ],
          ipRedisInterval: [
            {required: true, message: '请填写缓存时间', trigger: 'blur'},
          ],
        },

      }
    }
  }
</script>

<style>
</style>
