<template>
  <div style="max-width: 400px; margin: 50px auto; padding: 20px;">
    <h2 style="text-align: center; margin-bottom: 24px;">用户登录</h2>

    <a-form :model="formState" layout="vertical">
      <a-form-item label="用户名">
        <a-input v-model:value="username" placeholder="请输入用户名" />
      </a-form-item>

      <a-form-item label="密码">
        <a-input-password v-model:value="password" placeholder="请输入密码" />
      </a-form-item>

      <a-form-item label="验证码">
        <div style="display: flex; gap: 8px; align-items: center;">
          <a-input
            v-model:value="captchaInput"
            placeholder="请输入验证码"
            style="width: 160px;"
            maxlength="4"
          />
          <Captcha @uuid-ready="captchaUuidset" />
        </div>
      </a-form-item>

      <a-form-item>
        <a-button
          type="primary"
          block
          @click="handleLogin"
          :loading="loading"
        >
          登录
        </a-button>
      </a-form-item>
      <a-form-item style="text-align: center; margin-top: -16px;">
        还没有账号？
        <router-link to="/register" style="margin-left: 8px;">立即注册</router-link>
      </a-form-item>
      <a-form-item style="text-align: center; margin-top: -12px;">
        <router-link to="/forgot-password">忘记密码？</router-link>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import axios from 'axios'
import Captcha from '../components/Captcha.vue'

const username = ref('')
const password = ref('')
const captchaInput = ref('')
const captchaUuid = ref('')
const captchaUuidset = (uuid) => {
  captchaUuid.value = uuid
}
const loading = ref(false)
const formState = reactive({
  username,
  password,
  captchaInput,
  captchaUuid,
})
const handleLogin = async () => {
  console.log('handleLogin', captchaUuid.value, captchaInput.value)
  // 示例：调用验证接口（或在登录接口中一起校验）
  try {
    loading.value = true
    const verifyRes = await axios.post('/api/verify', {
    uuid: captchaUuid.value, 
    code: captchaInput.value   // 用户输入的验证码
    });
    if (verifyRes.data) {
      alert('验证码正确！')
       // 2. 调用登录接口
    const loginRes = await axios.post('/api/auth/login', {
      userName: formState.username,
      password: formState.password,
    })
    } else {
      alert('验证码错误！')
    }
  } catch (err) {
    console.error(err)
  } finally {
    loading.value = false; 
  }
}
</script>