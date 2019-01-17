import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/interface',
    component: Layout,
    alwaysShow: true,
    name: 'Interface',
    redirect: 'noredirect',
    meta: { title: '接口管理', icon: 'eye' },
    children: [
      {
        path: 'interface_list',
        name: 'InterfaceList',
        component: () => import('@/views/interface/interface_list'),
        meta: { title: '开放接口列表', icon: 'eye' }
      },
      {
        path: 'interface_form',
        name: 'InterfaceForm',
        hidden: true,
        component: () => import('@/views/interface/interface_form'),
        meta: { title: '接口操作', icon: 'eye' }
      },
    ]
  },
  {
    path: '/soft',
    component: Layout,
    alwaysShow: true,
    name: 'Soft',
    redirect: 'noredirect',
    meta: { title: '软件管理', icon: 'software' },
    children: [
      {
        path: 'soft_list',
        name: 'SoftList',
        component: () => import('@/views/soft/soft_list'),
        meta: { title: '软件列表', icon: '软件管理' }
      },
      {
        path: 'soft_form',
        name: 'SoftForm',
        hidden: true,
        component: () => import('@/views/soft/soft_form'),
        meta: { title: '软件操作', icon: 'tree' }
      },
      {
        path: 'soft_versions_form',
        name: 'SoftVersionsForm',
        hidden: true,
        component: () => import('@/views/soft/soft_versions_form'),
        meta: { title: '软件版本操作', icon: 'tree' }
      },
      {
        path: 'soft_leave_list',
        name: 'SoftLeaveList',
        component: () => import('@/views/soft/soft_leave_list'),
        meta: { title: '反馈列表', icon: '反馈管理' }
      },
    ]
  },
  {
    path: '/log',
    component: Layout,
    alwaysShow: true,
    name: 'Log',
    redirect: 'noredirect',
    meta: { title: '日志管理', icon: 'eye' },
    children: [
      {
        path: 'soft_map',
        name: 'SoftMap',
        component: () => import('@/views/log/soft_map'),
        meta: { title: '软件日志图表', icon: 'eye' }
      },
      {
        path: 'soft_login_log_list',
        name: 'SoftLoginLogList',
        component: () => import('@/views/log/soft_login_log_list'),
        meta: { title: '登陆日志表格', icon: 'eye' }
      },
      {
        path: 'soft_register_log_list',
        name: 'SoftRegisterLogList',
        component: () => import('@/views/log/soft_register_log_list'),
        meta: { title: '注册日志表格', icon: 'eye' }
      },
    ]
  },
  {
    path: '/card',
    component: Layout,
    alwaysShow: true,
    name: 'Card',
    redirect: 'noredirect',
    meta: { title: '充值卡管理', icon: '充值' },
    children: [
      {
        path: 'card_type_list',
        name: 'CardTypeList',
        component: () => import('@/views/card/card_type_list'),
        meta: { title: '卡类列表', icon: '充值kl' }
      },
      {
        path: 'card_type_form',
        name: 'CardTypeForm',
        hidden: true,
        component: () => import('@/views/card/card_type_form'),
        meta: { title: '卡类操作', icon: '充值kl' }
      },
      {
        path: 'card_list',
        name: 'CardList',
        component: () => import('@/views/card/card_list'),
        meta: { title: '卡密列表', icon: '充值缴费' }
      },
      {
        path: 'card_form',
        name: 'CardForm',
        hidden: true,
        component: () => import('@/views/card/card_form'),
        meta: { title: '卡密操作', icon: 'table' }
      },
    ]
  },

  {
    path: '/account',
    component: Layout,
    alwaysShow: true,
    name: 'Account',
    redirect: 'noredirect',
    meta: { title: '用户管理', icon: '用户' },
    children: [
      {
        path: 'account_list',
        name: 'AccountList',
        component: () => import('@/views/account/account_list'),
        meta: { title: '用户列表', icon: '用户d' }
      },
    ]
  },

  {
    path: '/config',
    component: Layout,
    alwaysShow: true,
    name: 'Config',
    redirect: 'noredirect',
    meta: { title: '配置管理', icon: '配置' },
    children: [
      {
        path: 'email_account_list',
        name: 'EmailAccountList',
        component: () => import('@/views/config/email_account_list'),
        meta: { title: '邮箱列表', icon: '邮箱' }
      },
      {
        path: 'email_account_form',
        name: 'EmailAccountForm',
        hidden: true,
        component: () => import('@/views/config/email_account_form'),
        meta: { title: '邮箱操作', icon: 'table' }
      },
      {
        path: 'baidu_map_api_form',
        name: 'BaiduMapApiForm',
        component: () => import('@/views/config/baidu_map_api_form'),
        meta: { title: '百度地理api', icon: '地图' }
      },
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
