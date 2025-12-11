import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import { viteMockServe } from 'vite-plugin-mock'

// https://vite.dev/config/
export default defineConfig({
  base: './', 
  plugins: [
    vue(),
    vueDevTools(),
  //   viteMockServe({
  //     mockPath: 'mock',     // mock 文件目录
  //     enable: true,         // 开发环境开启
  //     watchFiles: true,     // 监听文件变化
  //     localEnabled: true,   // 本地 mock 启用
  //     prodEnabled: false,   // 生产环境关闭
  //   })
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
  proxy: {
    '/api': 'http://localhost:8080'
    }
  },
})
