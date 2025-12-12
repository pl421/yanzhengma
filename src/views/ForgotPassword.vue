<template>
  <a-card title="忘记密码" style="max-width: 500px; margin: 50px auto">
    <!-- Step 1: 输入用户名 -->
    <div v-if="step === 1">
      <a-form :model="formState" layout="vertical" @finish="handleStep1">
        <a-form-item label="用户名" name="username"
          :rules="[{ required: true, message: '请输入用户名' }]">
          <a-input v-model:value="formState.username" />
        </a-form-item>
        <a-button type="primary" html-type="submit" block>下一步</a-button>
      </a-form>
    </div>

    <!-- Step 2: 输入邮箱 -->
    <div v-else-if="step === 2">
      <p>请输入您注册时绑定的邮箱：</p>
      <a-form :model="formState" layout="vertical" @finish="handleStep2">
        <a-form-item label="邮箱" name="email"
          :rules="[{ required: true, type: 'email', message: '请输入有效邮箱' }]">
          <a-input v-model:value="formState.email" />
        </a-form-item>
        <a-button type="primary" html-type="submit" block>发送重置邮件</a-button>
        <a-button style="margin-top: 10px" block @click="step = 1">返回</a-button>
      </a-form>
    </div>

    <!-- 成功提示 -->
    <div v-else-if="step === 3">
      <a-result status="success" title="邮件已发送" 
        sub-title="请查收您的邮箱，并点击重置链接完成密码修改。">
        <template #extra>
          <a-button type="primary" @click="$router.push('/login')">返回登录</a-button>
        </template>
      </a-result>
    </div>
  </a-card>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { message } from 'ant-design-vue'
import axios from 'axios'

const step = ref(1)
const formState = reactive({
  username: '',
  email: ''
})

const handleStep1 = async () => {
  try {
    await axios.post('/api/auth/forgot-password/step1', {
      username: formState.username
    })
    step.value = 2
  } catch (error) {
    message.error(error.response?.data?.error || '操作失败')
  }
}

const handleStep2 = async () => {
  try {
    await axios.post('/api/auth/forgot-password/step2', {
      username: formState.username,
      email: formState.email
    })
    step.value = 3
  } catch (error) {
    message.error(error.response?.data?.error || '操作失败')
  }
}
</script>