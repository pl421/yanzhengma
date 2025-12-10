<!-- src/views/Menu.vue -->
<template>
  <a-layout style="min-height: 100vh;">
    <!-- 左侧导航栏 -->
    <a-layout-sider
      collapsible
      v-model:collapsed="collapsed"
      :width="240"
      style="background: #fff; overflow: auto; height: 100vh; position: fixed; left: 0; top: 0; z-index: 10;"
    >
      <div class="logo" style="height: 32px; margin: 16px; background: rgba(0, 0, 0, 0.05); border-radius: 6px;"></div>
      <a-menu
        theme="light"
        mode="inline"
        :selectedKeys="[current]"
        @click="handleMenuClick"
      >
        <a-menu-item key="dashboard">
          <UserOutlined />
          <span>个人中心</span>
        </a-menu-item>
        <a-menu-item key="profile">
          <SettingOutlined />
          <span>账户设置</span>
        </a-menu-item>
        <a-menu-item key="messages">
          <MessageOutlined />
          <span>消息通知</span>
        </a-menu-item>

        <!-- 用户管理（可扩展为管理员专属） -->
        <a-sub-menu key="user-management">
          <template #title>
            <TeamOutlined />
            <span>用户管理</span>
          </template>
          <a-menu-item key="user-list">
            <UsergroupAddOutlined />
            <span>用户一览</span>
          </a-menu-item>
        </a-sub-menu>

        <a-menu-item key="logout">
          <LogoutOutlined />
          <span>退出登录</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>

    <!-- 右侧内容区 -->
    <a-layout :style="{ marginLeft: collapsed ? '80px' : '240px', transition: 'margin-left 0.2s' }">
      <a-layout-header style="background: #fff; padding: 0 24px; height: 64px;"></a-layout-header>
      <a-layout-content style="margin: 24px 24px 0; padding: 24px; background: #fff; min-height: calc(100vh - 112px);">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import {
  UserOutlined,
  SettingOutlined,
  MessageOutlined,
  TeamOutlined,
  UsergroupAddOutlined,
  LogoutOutlined
} from '@ant-design/icons-vue'

const router = useRouter()
const route = useRoute()
const collapsed = ref(false)

// 根据当前路由自动高亮菜单（支持 /menu/dashboard 等）
const current = ref('dashboard')

onMounted(() => {
  // 从路径中提取子路径作为菜单 key
  const path = route.path.split('/').pop() || 'dashboard'
  if (['dashboard', 'profile', 'messages', 'user-list'].includes(path)) {
    current.value = path
  }
})

const handleMenuClick = ({ key }) => {
  if (key === 'logout') {
    handleLogout()
    return
  }

  current.value = key

  // 跳转到对应子路由
  if (key === 'user-list') {
    router.push('/menu/user-list')
  } else {
    router.push(`/menu/${key}`)
  }
}

const handleLogout = () => {
  localStorage.removeItem('token')
  // 可选：清除其他登录信息
  router.push('/login')
}
</script>

<style scoped>
.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #1890ff;
  font-weight: bold;
}
</style>