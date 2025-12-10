// mock/users.js

// 生成 50 条假用户数据
const mockUserList = Array.from({ length: 50 }, (_, i) => ({
  userId: `u${String(i + 1).padStart(3, '0')}`,
  userName: `用户${i + 1}`,
  gender: i % 3 === 0 ? 0 : 1,
  phone: `13800138${String(i + 1000).slice(-4)}`,
  email: `user${i + 1}@example.com`
}))

export default [
  {
    url: '/api/users',
    method: 'get',
    response: ({ query }) => {
      const { page = 1, pageSize = 10 } = query
      const p = parseInt(page)
      const size = parseInt(pageSize)

      const total = mockUserList.length
      const start = (p - 1) * size
      const end = start + size
      const list = mockUserList.slice(start, end)

      return {
        code: 200,
        data: {
          list,
          total
        }
      }
    }
  }
]