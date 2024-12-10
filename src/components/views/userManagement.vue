<script setup>
import { ref, reactive, onMounted } from 'vue'
import router from '../../routers/router.js';
import instance from "../../axios.js";
import { ElMessage , ElMessageBox} from 'element-plus';
import { View } from '@element-plus/icons-vue'
import { REPEAT_DELAY } from 'element-plus/es/directives/repeat-click/index.mjs';
import { filterFields } from 'element-plus/es/components/form/src/utils.mjs';
let pageNum = ref(1);
let pageSize = ref(5);
let tableData = ref([]);
let totalSize = ref(0);
let uId = ref('');
let usernameInput = ref('')
let user = reactive({})
let addUser = reactive({})
let modifyUserVisible = ref(false);
let addUserVisible = ref(false);
let siteNameList = ref([{sId: 0, siteName: ''}]);
let selectedSiteName = ref('');
const getTableData = () => {
  instance.get("/page/getPage", {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      pageName: 'userPage',
      input: usernameInput.value
    }
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
const handleModifyUser = () => {
  instance.post('/user/modifyUser', {
    uId: user.uId,
    username: user.username,
    password: user.password,
    realName: user.realName,
    age: user.age,
    phone: user.phone,
    email: user.email,
    siteId: user.sId
  }).then((res) => {
    if (res.data.code == 200) {
      console.log("修改:"+res.data.sId)
      ElMessage.success(res.data.msg);
      modifyUserVisible.value = false;
      getTableData();
    } else {
      ElMessage.error(res.data.msg);
    }
  }).catch((err) => {
    console.log(err);
  });
};
const deleteUser = (row) => {
  ElMessageBox.confirm('确定要删除这条记录吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
  instance.get('/user/deleteUserById',
    {
      params: {
        uId: row.uId
      }
    }
  ).then((res) => {
    if (res.data.code == 200) {
      ElMessage.success(res.data.msg);
      getTableData();
    } else {
      ElMessage.error(res.data.msg);
    }
  }).catch((err) => {
    console.log(err);
  });}
  )
};
const showModifyUser = (row) => {

  user.uId = row.uId;
  user.username = row.username;
  user.realName = row.realName;
  user.password = row.password;
  user.sId = row.sId;
  user.age = row.age;
  user.phone = row.phone;
  user.email = row.email;
  user.siteName = row.siteName;
  modifyUserVisible.value = true;
  console.log(user);
};
const showAddUserDialog = () => {
  addUser = reactive({})
  addUserVisible.value = true;
};
const getSiteNameList = () => {
  instance.get('/site/getSiteNameList')
    .then((res) => {
      if (res.data.code == 200) {
        siteNameList.value = res.data.data;
      } else {
        ElMessage.error(res.data.msg);
      }
    }).catch((err) => {
      console.log(err);
    });
};

//新增用户
const handleAddUser = () => {
  instance.post('/user/addUser', {
    username: addUser.username,
    password: addUser.password,
    realName: addUser.realName,
    age: addUser.age,
    phone: addUser.phone,
    email: addUser.email,
    siteId: addUser.sId
  }).then((res) => {
    if (res.data.code == 200) {
      ElMessage.success(res.data.msg);
      addUserVisible.value = false;
      getTableData();
    } else {
      ElMessage.error(res.data.msg);
    }
  }).catch((err) => {
    console.log(err);
  });
};
onMounted(() => {
  getTableData();
  getSiteNameList();
}
)

</script>

<template>
  <div class="main-container">
    <div class="search-block">
      <el-input v-model="usernameInput" style="width: 240px" placeholder="请输入要查找的用户名" />
      <el-button type="primary" @click="getTableData" style="margin-left: 3%;">搜索</el-button>
      <el-button type="primary" @click="showAddUserDialog">新增用户</el-button>
    </div>

    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="uId" label="序号" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="realName" label="姓名" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="siteName" label="负责工地" />
      <el-table-column prop="siteLocation" label="地点" />
      <el-table-column prop="siteDesc" label="工地描述" />
      <el-table-column label="操作" width="150px">
        <template #default="scope">
          <el-button type="primary" size="small" @click="showModifyUser(scope.row)">修改</el-button>
          <el-button type="danger" size="small" @click="deleteUser(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="paginationClass"> <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20, 40]" layout="total, sizes, prev, pager, next, jumper" :total="totalSize"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" /></div>

    <el-dialog v-model="modifyUserVisible" title="修改用户" width="30%">
      <el-form :model="user" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="user.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" show-password/>
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="user.realName" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="user.age" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="user.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" />
        </el-form-item>
        <el-form-item label="负责工地" prop="sId">
              <el-select v-model="user.sId" :placeholder="user.siteName" style="width: 115px">
                <el-option v-for="item in siteNameList" :key="item.sId" :label="item.siteName" :value="item.sid">
                </el-option>
              </el-select>
        </el-form-item>
      </el-form>
      <div>
        <el-button @click="modifyUserVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleModifyUser">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog v-model="addUserVisible" title="新增用户" width="30%">
      <el-form :model="addUser" label-width="100px">
        <el-form-item label="用户名" prop="username" >
          <el-input v-model="addUser.username" placeholder="用户名重名会添加失败"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addUser.password" show-password/>
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="addUser.realName" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="addUser.age" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="addUser.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addUser.email" />
        </el-form-item>
        <el-form-item label="负责工地" prop="sId">
              <el-select v-model="addUser.sId" :placeholder="addUser.siteName" style="width: 115px">
                <el-option v-for="item in siteNameList" :key="item.sId" :label="item.siteName" :value="item.sid">
                </el-option>
              </el-select>
        </el-form-item>
      </el-form>
      <div>
        <el-button @click="addUserVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAddUser">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<style scoped>
.main-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100vh;
  /* 占满整个可视区域 */
  padding: 16px;
  /* 可选的内边距 */
  box-sizing: border-box;
  overflow: hidden;
  /* 避免溢出 */
}

.el-table {
  max-height: 520px;
}

.search-block {
  margin-bottom: 10px;
}

.paginationClass {
  margin-top: 20px;
}
</style>
