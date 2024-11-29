<script setup>
import { ref, reactive, onMounted } from 'vue'
import router from '../../routers/router.js';
import instance from "../../axios.js";
import { ElMessage } from 'element-plus';
import { View } from '@element-plus/icons-vue'

let pageNum = ref(1);
let pageSize = ref(5);
let tableData = ref([]);
let totalSize = ref(0);
let showPwdDialogVisible = ref(false);
let dialogPassword = ref('');
const getTableData = () => {
  instance.get("/getPage", {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      pageName: 'userPage'
    },
    headers: {
      "Authorization": "Bearer " + localStorage.getItem("token"),
    },
  }).then((res) => {
    if (res.data.code == 200) {
      tableData.value = res.data.data.list;
      console.log(res.data.data.list);
      totalSize.value = res.data.data.total;
      console.log("total:" + res.data.data.total);
    } else {
      ElMessage.error(res.data.msg);
    }
  }).catch((err) => {
    console.log(err);
  });
};

// 分页事件处理函数
const handleSizeChange = (newSize) => {
  pageNum.value = 1;
  pageSize.value = newSize;
  getTableData();
};

const handleCurrentChange = (newPage) => {
  pageNum.value = newPage;
  getTableData();
};

const showPassword = (password) => {
  dialogPassword.value = password;
  showPwdDialogVisible.value = true;
};
onMounted(
  getTableData
)
</script>

<template>
  <div class="main-container">
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="uid" label="序号" width="60px"/>
      <el-table-column prop="username" label="用户名" width="100px"/>
      <el-table-column label="密码" width="100px">

        <template #default="scope">
          <!-- 显示眼镜图标按钮 -->
          <div style="text-align: center;">
            <el-icon @click="showPassword(scope.row.password)">
              <View />
            </el-icon>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="realname" label="realname" width="100px" />
      <el-table-column prop="age" label="age" width="60px" />
      <el-table-column prop="phone" label="phone" width="100px" />
      <el-table-column prop="email" label="email" width="100px" />
      <el-table-column prop="siteName" label="siteName" width="100px" />
      <el-table-column prop="siteDesc" label="siteDesc" width="80px" />
    </el-table>
    <div class="pagination-block">
      <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 20, 40]"
        layout="total, sizes, prev, pager, next, jumper" :total="totalSize" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
    </div>



    <!-- 弹出对话框 -->
    <el-dialog v-model="showPwdDialogVisible" title="Password" width="30%" :before-close="handleCloseDialog">
      <p>{{ dialogPassword }}</p>
      <template #footer>
        <el-button @click="showPwdDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.main-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100vh; /* 占满整个可视区域 */
  padding: 16px; /* 可选的内边距 */
  box-sizing: border-box;
  overflow: hidden; /* 避免溢出 */
}
</style>
