<template>
  <a-card title="重置密码" style="max-width: 500px; margin: 50px auto">
    <a-form :model="form" layout="vertical" @finish="handleSubmit">
      <a-form-item label="新密码" name="newPassword"
        :rules="[{ required: true, min: 6, message: '至少6位' }]">
        <a-input-password v-model:value="form.newPassword" />
      </a-form-item>
      <a-form-item label="确认密码" name="confirmPassword"
        :rules="[{ required: true, validator: validateConfirm }]">
        <a-input-password v-model:value="form.confirmPassword" />
      </a-form-item>
      <a-button type="primary" html-type="submit" block>重置密码</a-button>
    </a-form>
  </a-card>
</template>

<script setup>
import { reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()
const form = reactive({
  newPassword: '',
  confirmPassword: ''
})

const validateConfirm = (rule, value) => {
  if (value !== form.newPassword) {
    return Promise.reject('两次密码不一致')
  }
  return Promise.resolve()
}

const handleSubmit = async () => {
  const token = route.query.token
  if (!token) {
    message.error('无效的重置链接')
    return
  }
  try {
    await axios.post('/api/auth/reset-password', {
      token,
      newPassword: form.newPassword
    })
    message.success('密码重置成功，请登录')
    router.push('/login')
  } catch (error) {
    message.error(error.response?.data?.error || '重置失败')
  }
}
</script>