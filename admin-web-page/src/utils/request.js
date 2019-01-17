import axios from 'axios'
import { Message, MessageBox, Loading } from 'element-ui'
import store from '../store'
import { getToken } from '@/utils/auth'
import Vue from 'vue'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 10000 // 请求超时时间
})

let load;

// request拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      // config.headers['X-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    load = Loading.service({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    });

    return config
  },
  error => {
    load.close();
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {

    load.close();

    const res = response.data
    if (res.code == 12) {

      Message.error("登陆失效，请重新登陆")
      store.dispatch('FedLogOut').then(() => {
        location.reload()
      })

    }

    return response.data

  },
  error => {
    load.close();
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

Vue.prototype.$axios = service;

export default service
