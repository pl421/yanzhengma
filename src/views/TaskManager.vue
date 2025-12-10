<template>
  <a-layout style="padding: 24px">
    <a-card title="新增定时任务">
      <a-form :model="form" layout="vertical" @finish="handleCreate">
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="任务名" name="taskName" required>
              <a-input v-model:value="form.taskName" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="任务组" name="taskGroup">
              <a-input v-model:value="form.taskGroup" placeholder="默认 DEFAULT" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="Cron 表达式" name="cronExpression" required>
              <a-input v-model:value="form.cronExpression" placeholder="如：0 0/5 * * * ?" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item>
          <a-button type="primary" html-type="submit">添加任务</a-button>
        </a-form-item>
      </a-form>
    </a-card>

    <a-card title="任务列表" style="margin-top: 24px">
      <a-table :dataSource="tasks" :columns="columns" row-key="id">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button v-if="record.status === 'ACTIVE'" size="small" @click="pause(record.id)">暂停</a-button>
              <a-button v-if="record.status === 'PAUSED'" size="small" type="primary" @click="resume(record.id)">恢复</a-button>
              <a-button danger size="small" @click="remove(record.id)">删除</a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { Task, getTasks, createTask, pauseTask, resumeTask, deleteTask } from '@/api/task'

const form = ref({
  taskName: '',
  taskGroup: 'DEFAULT',
  cronExpression: ''
})

const tasks = ref<Task[]>([])

const columns = [
  { title: '任务名', dataIndex: 'taskName' },
  { title: '任务组', dataIndex: 'taskGroup' },
  { title: 'Cron', dataIndex: 'cronExpression' },
  { title: '状态', dataIndex: 'status' },
  { title: '操作', key: 'action' }
]

const loadTasks = async () => {
  tasks.value = await getTasks()
}

const handleCreate = async () => {
  try {
    // 补充 status 字段
    const taskToSubmit: Task = {
      ...form.value,
      status: 'ACTIVE' // 新建任务默认激活
    };
    await createTask(taskToSubmit)
    message.success('任务创建成功')
    form.value = { taskName: '', taskGroup: 'DEFAULT', cronExpression: '' }
    loadTasks()
  } catch (err: any) {
    message.error(err.response?.data || '创建失败')
  }
}

const pause = async (id: number) => {
  await pauseTask(id)
  message.success('已暂停')
  loadTasks()
}

const resume = async (id: number) => {
  await resumeTask(id)
  message.success('已恢复')
  loadTasks()
}

const remove = async (id: number) => {
  await deleteTask(id)
  message.success('已删除')
  loadTasks()
}

onMounted(() => {
  loadTasks()
})
</script>