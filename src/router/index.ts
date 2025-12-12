// src/router/index.js
import { createRouter, createWebHistory, RouteRecordRaw,createWebHashHistory } from 'vue-router'
import Register from '@/views/Register.vue'
import Login from '@/views/Login.vue'
import UserList from '@/views/UserList.vue'
import TaskManager from '@/views/TaskManager.vue'
import Menu from '@/views/Menu.vue'
import GenerateCode from '@/views/GenerateCode.vue'
import ForgotPassword from '@/views/ForgotPassword.vue'
import ResetPassword from '@/views/ResetPassword.vue'

const routes: RouteRecordRaw[] = [
  { path: '/TaskManager', component: TaskManager },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/', redirect: '/login' }, // 默认跳转到登录
  {
  path: '/userList',
  component: UserList
  },
  {
  path: '/generateCode',
  component: GenerateCode
  },
  {
  path: '/forgot-password',
  component: ForgotPassword
  },
  {
    path: '/reset-password',
  component: ResetPassword
  },
  {
    path: '/menu',
    component: Menu,
    children: [
    //   { path: '', redirect: 'dashboard' }, // 默认重定向到 dashboard
    //   { path: 'dashboard', component: Dashboard },
    //   { path: 'profile', component: Profile },
    //   { path: 'messages', component: Messages }
    { path: 'user-list', component: UserList },
    ]
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router