import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from "../pages/login"
import Register from "../pages/register"
import Home from "../pages/home"
import NotFound from "../pages/errorPage/404"
import Forbidden from "../pages/errorPage/403"
import Layout from "../pages/layout"

// 获取原型对象的push函数
const originalPush = VueRouter.prototype.push;
// 修改 原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

// 获取原型对象的replace函数
const originalReplace = VueRouter.prototype.replace;
// 修改 原型对象中的replace方法
VueRouter.prototype.replace = function replace(location) {
  return originalReplace.call(this, location).catch(err => err)
};
Vue.use(VueRouter);

// 初始化路由
const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  }
];

/**
 * 根据用户的权限不同，所能看到的页面和可操作性也不同
 *  admin -> 所有页面都可以看得到
 *
 * addRouter()
 */
// 准备动态加载的路由
export const DynamicRoutes = [
  {
    path: "",
    component: Layout,
    name: 'container',
    redirect: "home",
    meta: {
      requiresAuth: true,
    },
    children: [
      {
        path: "home",
        component: Home,
        name: "home",
        meta: {
          // 匹配规则
          name: "首页",
          icon: "icon-name"
        }
      }
    ]
  },
  {
    path: "/403",
    component: Forbidden
  },
  {
    path: "*",
    component: NotFound
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
