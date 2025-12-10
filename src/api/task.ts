import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8081/api'
})

export interface Task {
  id?: number
  taskName: string
  taskGroup: string
  cronExpression: string
  status: 'ACTIVE' | 'PAUSED'
  description?: string
}

export const getTasks = () => api.get<Task[]>('/tasks').then(r => r.data)
export const createTask = (task: Task) => api.post('/tasks', task)
export const pauseTask = (id: number) => api.put(`/tasks/${id}/pause`)
export const resumeTask = (id: number) => api.put(`/tasks/${id}/resume`)
export const deleteTask = (id: number) => api.delete(`/tasks/${id}`)