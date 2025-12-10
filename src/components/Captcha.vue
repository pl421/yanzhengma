<template>
  <div class="captcha-container">
    <img
      :src="captchaImage"
      alt="验证码"
      @click="loadCaptcha"
      style="cursor: pointer; border: 1px solid #ddd; border-radius: 4px;"
    />
    <p @click="loadCaptcha" style="font-size: 12px; color: #666; margin-top: 4px;">
      看不清？点击刷新
    </p>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const captchaImage = ref('')
const emit = defineEmits(['uuidReady'])

const loadCaptcha = async () => {
  console.log('loadCaptcha')    
  try {
    const response = await axios.get('/api/captcha');
    
    // ✅ 正确：只取 image 字段
    captchaImage.value = `data:image/png;base64,${response.data.image}`;
    
    // 如果你需要 uuid（用于验证），也要保存它
    emit('uuidReady', response.data.uuid)
  } catch (error) {
    console.error('加载验证码失败:', error);
  }
};

onMounted(() => {
  loadCaptcha()
})
</script>

<style scoped>
.captcha-container {
  display: inline-block;
}
</style>