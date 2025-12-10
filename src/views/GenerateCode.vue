<!-- GenerateCode.vue -->
<template>
  <a-card title="MyBatis-Plus 代码生成器" style="max-width: 800px; margin: 20px auto">
    <a-form layout="vertical" @finish ="handleSubmit" :model="formState">
      <!-- Schema 选择 -->
      <a-form-item
        label="数据库 Schema"
        name="schema"
        :rules="[{ required: true, message: '请选择 Schema' }]"
      >
        <a-select
          v-model:value="formState.schema"
          placeholder="请选择数据库 Schema"
          style="width: 100%"
        >
          <a-select-option value="yanzhengma">yanzhengma</a-select-option>
          <!-- 可以按需添加更多 schema -->
          <!-- <a-select-option value="other_schema">other_schema</a-select-option> -->
        </a-select>
      </a-form-item>
      <a-form-item label="要生成的数据库表">
        <a-table
          :dataSource="formState.tables"
          :columns="columns"
          :pagination="false"
          size="small"
          bordered
        >
          <template #bodyCell="{ column, record, index }">
            <template v-if="column.key === 'tableName'">
              <a-input
                v-model:value="record.tableName"
                placeholder="例如：users"
                @change="validateTableNames"
              />
            </template>
            <template v-else-if="column.key === 'action'">
              <a-button
                type="link"
                danger
                @click="removeTable(index)"
                :disabled="formState.tables.length <= 1"
              >
                删除
              </a-button>
            </template>
          </template>
        </a-table>

        <a-button
          type="dashed"
          block
          icon-right
          style="margin-top: 12px"
          @click="addTable"
        >
          + 添加表
        </a-button>
      </a-form-item>

      <a-form-item>
        <a-button type="primary" html-type="submit" :loading="loading">
          生成后端代码
        </a-button>
      </a-form-item>
    </a-form>

    <a-alert
      v-if="resultMessage"
      :message="resultMessage"
      type="success"
      show-icon
      style="margin-top: 16px"
    />
  </a-card>
</template>

<script setup>
import { reactive, ref, nextTick } from 'vue'
import axios from 'axios'

const formState = reactive({
  schema: 'yanzhengma', 
  tables: [{ tableName: '' }]
})

const loading = ref(false)
const resultMessage = ref('')

// 表格列定义
const columns = [
  {
    title: '表名',
    key: 'tableName',
    width: '80%'
  },
  {
    title: '操作',
    key: 'action',
    width: '20%',
    align: 'center'
  }
]

const addTable = () => {
  debugger
  formState.tables.push({ tableName: '' })
}

const removeTable = (index) => {
  if (formState.tables.length > 1) {
    formState.tables.splice(index, 1)
  }
}

// 可选：实时校验（这里只用于提示，最终提交时再严格校验）
const validateTableNames = () => {
  // 你可以在这里加防重、格式校验等
}

const handleSubmit = async () => {
  
  // 提取非空且去重的表名
  const tableNames = [...new Set(
    formState.tables
      .map(t => t.tableName?.trim())
      .filter(name => name)
  )]

  if (tableNames.length === 0) {
    resultMessage.value = '请至少输入一个有效的表名'
    return
  }

  loading.value = true
  resultMessage.value = ''
  try {
    const res = await axios.post('/api/code/generate', {
      schema: formState.schema,
      tableNames // 发送字符串数组
    })
    resultMessage.value = res.data.message || '代码生成成功！'
  } catch (error) {
    console.error(error)
    resultMessage.value = '生成失败：' + (error.response?.data?.message || error.message)
  } finally {
    loading.value = false
  }
}
</script>