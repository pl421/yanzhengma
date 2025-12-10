<!-- src/views/UserList.vue -->
<template>
  <div style="padding: 24px;">
    <h2 style="margin-bottom: 24px;">用户一览表</h2>

    <a-spin :spinning="loading">
      <a-table
        :columns="columns"
        :data-source="userList"
        row-key="userId"
        :pagination="{
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: pagination.total,
          showSizeChanger: true,
          showQuickJumper: true,
          pageSizeOptions: ['5', '10', '20'],
          onChange: handlePageChange,
          onShowSizeChange: handlePageSizeChange
        }"
      >
        <!-- 性别列自定义渲染 -->
        <template #gender="{ text }">
          {{ text === 1 ? '男' : text === 0 ? '女' : '未知' }}
        </template>
      </a-table>
    </a-spin>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { message } from 'ant-design-vue'
import { useUserList } from '@/components/userUserList.js'
// const pagination = ref({
//   current: 1,
//   pageSize: 10,
//   total: 0
// })
// 表格列定义
const columns = [
  {
    title: '用户ID',
    dataIndex: 'userId',
    key: 'userId'
  },
  {
    title: '用户名',
    dataIndex: 'userName',
    key: 'userName'
  },
  {
    title: '性别',
    dataIndex: 'gender',
    key: 'gender',
    slots: { customRender: 'gender' }
  },
  {
    title: '手机号',
    dataIndex: 'phone',
    key: 'phone'
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    key: 'email'
  }
]

const {loading,userList,fetchUsers,pagination,handlePageChange,handlePageSizeChange} = useUserList('/api/getUserList');
// 响应式数据
// const userList = ref([])
// const loading = ref(false)

// 获取用户列表
// const fetchUsers = async (page = 1, pageSize = 10) => {
//   try {
//     loading.value = true
//     const res = await axios.get('/api/getUserList', {
//       params: {
//         page,
//         pageSize
//       }
//     })

//     // 假设后端返回格式：{ code: 200, data: { list, total } }
//     const { list, total } = res.data.data
//     userList.value = list
//     pagination.value.total = total
//     pagination.value.current = page
//     pagination.value.pageSize = pageSize
//   } catch (error) {
//     console.error('获取用户列表失败:', error)
//     message.error('加载用户数据失败，请稍后重试')
//     userList.value = []
//   } finally {
//     loading.value = false
//   }
// }

// 组件挂载时加载数据
onMounted(() => {
  fetchUsers()
})

// // 分页变化
// const handlePageChange = (page, pageSize) => {
//   fetchUsers(page, pageSize)
// }

// // 每页条数变化
// const handlePageSizeChange = (current, size) => {
//   fetchUsers(current, size)
// }
</script>

<style scoped>
/* 可选：调整表格样式 */
.ant-table {
  margin-top: 16px;
}
</style>