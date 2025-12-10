<!-- src/views/Register.vue -->
<template>
  <div style="max-width: 400px; margin: 50px auto; padding: 20px;">
    <h2 style="text-align: center; margin-bottom: 24px;">用户注册</h2>

    <a-form
      :model="formState"
      :rules="rules"
      ref="registerFormRef"
      layout="vertical"
      @finish="handleRegister"
    >
      <a-form-item label="用户ID" name="user_id">
        <a-input v-model:value="formState.userId" placeholder="请输入用户ID（用于登录）" />
      </a-form-item>

      <a-form-item label="用户名" name="user_name">
        <a-input v-model:value="formState.userName" placeholder="请输入显示名称" />
      </a-form-item>

      <a-form-item label="密码" name="password">
        <a-input-password v-model:value="formState.password" placeholder="请输入密码" />
      </a-form-item>

      <a-form-item label="确认密码" name="confirmPassword">
        <a-input-password v-model:value="formState.confirmPassword" placeholder="请再次输入密码" />
      </a-form-item>

      <a-form-item label="性别" name="gender">
        <a-radio-group v-model:value="formState.gender">
          <a-radio :value="1">男</a-radio>
          <a-radio :value="0">女</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="手机号" name="phone">
        <a-input v-model:value="formState.phone" placeholder="请输入手机号" />
      </a-form-item>

      <a-form-item label="邮箱" name="email">
        <a-input v-model:value="formState.email" placeholder="请输入邮箱" />
      </a-form-item>

      <a-form-item label="验证码" name="captchaInput">
        <div style="display: flex; gap: 8px; align-items: center;">
          <a-input
            v-model:value="formState.captchaInput"
            placeholder="请输入验证码"
            style="width: 160px;"
            maxlength="4"
          />
          <Captcha @uuid-ready="setCaptchaUuid" />
        </div>
      </a-form-item>

      <a-form-item>
        <a-button
          type="primary"
          block
          @click="handleRegister"
          :loading="loading"
        >
          注册
        </a-button>
      </a-form-item>

      <a-form-item style="text-align: center; margin-top: -16px;">
        已有账号？
        <router-link to="/login" style="margin-left: 8px;">立即登录</router-link>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { ref ,reactive} from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import Captcha from '@/components/Captcha.vue'

const router = useRouter()

// 表单数据
const formState = reactive({
  userId: '',
  userName: '',
  password: '',
  confirmPassword: '',
  gender: 1,
  phone: '',
  email: '',
  captchaInput: ''
})
const confirmPassword = ref('')
// const captchaInput = ref('')
const captchaUuid = ref('')
const loading = ref(false)
const registerFormRef = ref()
const setCaptchaUuid = (uuid) => {
  captchaUuid.value = uuid
}

// 自定义校验规则：两次密码一致
const validateConfirmPassword = (_, value) => {
  if (!value || formState.password === value) {
    return Promise.resolve()
  }
  return Promise.reject(new Error('两次密码不一致'))
}

// 手机号正则（简单版）
const validatePhone = (_, value) => {
  const phoneRegex = /^1[3-9]\d{9}$/
  if (!value || phoneRegex.test(value)) {
    return Promise.resolve()
  }
  return Promise.reject(new Error('请输入正确的手机号'))
}

// 邮箱正则
const validateEmail = (_, value) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!value || emailRegex.test(value)) {
    return Promise.resolve()
  }
  return Promise.reject(new Error('请输入正确的邮箱'))
}

// 验证码校验（非空）
const validateCaptcha = (_, value) => {
  if (value && value.length === 4) {
    return Promise.resolve()
  }
  return Promise.reject(new Error('请输入4位验证码'))
}

const rules = {
  userId: [{ required: true, message: '请输入用户ID', trigger: 'blur' }],
  userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { validator: validatePhone, trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { validator: validateEmail, trigger: 'blur' }
  ],
  captchaInput: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { validator: validateCaptcha, trigger: 'blur' }
  ]
}

// 表单提交（自动校验通过后调用）
const handleRegister = async () => {
  try {
    // 等待所有异步校验完成（虽然这里都是同步，但习惯写 await）
    await registerFormRef.value.validateFields()
    const { confirmPassword, ...submitData } = formState

  // 补充 uuid（验证码唯一标识）
  const payload = {
    ...submitData,
    // code: captchaInput,        // 后端需要验证码内容
    uuid: captchaUuid.value    // 验证码唯一标识
  }
    
    loading.value = true
    await axios.post('/api/auth/register', payload)

    alert('注册成功！即将跳转到登录页')
    router.push('/menu')
  } catch (err) {
    console.error(err)
    // 注意：表单校验失败不会进这里，只有 API 失败才进
    alert('注册失败：' + (err.response?.data?.message || '请重试'))
  } finally {
    loading.value = false
  }
}
</script>