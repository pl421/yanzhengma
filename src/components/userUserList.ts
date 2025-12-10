import { ref, onMounted } from 'vue'
import axios from 'axios'
import { message } from 'ant-design-vue'
export function useUserList(api) {
    const loading = ref(false);
    const userList = ref([])
    const pagination = ref({
    current: 1,
    pageSize: 10,
    total: 0
    })
    const fetchUsers = async (page = 1, pageSize = 10) => {
      try {
        loading.value = true;
        const res = await axios.get('/api/getUserList', {
            params: {
                page,
                pageSize
            }
            });
        const { list, total,message:msg } = res.data.data
        if (res.data.code === 200) {
          userList.value = list;
          pagination.value.total = total;
          pagination.value.current = page
          pagination.value.pageSize = pageSize
        } else {
          message.error(msg);
        }
      } catch (error) {
        message.error(error.message);
      } finally {
        loading.value = false;
      }
    };

    // 分页变化
    const handlePageChange = (page, pageSize) => {
        fetchUsers(page, pageSize)
    }

    // 每页条数变化
    const handlePageSizeChange = (current, size) => {
        fetchUsers(current, size)
    }
    return { loading,userList,fetchUsers,pagination,handlePageChange,handlePageSizeChange };
}
