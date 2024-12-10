<script setup>
import { ref, reactive, onMounted } from 'vue'
import instance from "../../axios.js";
import { ElMessage ,ElMessageBox} from 'element-plus';

let pageNum = ref(1);
let pageSize = ref(5);
let tableData = ref([]);
let totalSize = ref(0);
let site = reactive({});
let siteNameInput = ref('');
let addSite = reactive({});
let modifySiteVisible = ref(false);
let addSiteVisible = ref(false);

const getTableData = () => {
  instance.get("/page/getPage", {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      pageName: 'sitePage',
      input: siteNameInput.value
    }
  }).then((res) => {
    if (res.data.code == 200) {
      tableData.value = res.data.data.list;
      totalSize.value = res.data.data.total;
    } else {
      ElMessage.error(res.data.msg);
    }
  }).catch((err) => {
    console.log(err);
  });
};

// 分页事件处理
const handleSizeChange = (newSize) => {
  pageNum.value = 1;
  pageSize.value = newSize;
  getTableData();
};

const handleCurrentChange = (newPage) => {
  pageNum.value = newPage;
  getTableData();
};

// 修改工地
const handleModifySite = () => {
  instance.post('/site/modifySite', {
    sId: site.sId,
    siteName: site.siteName,
    siteLocation: site.siteLocation,
    siteDesc: site.siteDesc
  }).then((res) => {
    if (res.data.code == 200) {
      ElMessage.success(res.data.msg);
      modifySiteVisible.value = false;
      getTableData();
    } else {
      ElMessage.error(res.data.msg);
    }
  }).catch((err) => {
    console.log(err);
  });
};

// 删除工地
const deleteSite = (row) => {
  ElMessageBox.confirm('确定要删除这条记录吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
  instance.get('/site/deleteSiteById', {
    params: {
      sId: row.sId
    }
  }).then((res) => {
    if (res.data.code == 200) {
      ElMessage.success(res.data.msg);
      getTableData();
    } else {
      ElMessage.error(res.data.msg);
    }
  }).catch((err) => {
    console.log(err);
  });
})
};

// 显示修改工地对话框
const showModifySite = (row) => {
  site.sId = row.sId;
  site.siteName = row.siteName;
  site.siteLocation = row.siteLocation;
  site.siteDesc = row.siteDesc;
  modifySiteVisible.value = true;
};

// 显示新增工地对话框
const showAddSiteDialog = () => {
  addSite = reactive({});
  addSiteVisible.value = true;
};

// 新增工地
const handleAddSite = () => {
  instance.post('/site/addSite', {
    siteName: addSite.siteName,
    siteLocation: addSite.siteLocation,
    siteDesc: addSite.siteDesc
  }).then((res) => {
    if (res.data.code == 200) {
      ElMessage.success(res.data.msg);
      addSiteVisible.value = false;
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
});
</script>


<template>
    <div class="main-container">
      <div class="search-block">
        <el-input v-model="siteNameInput" style="width: 240px" placeholder="请输入要查找的工地名" />
        <el-button type="primary" @click="getTableData" style="margin-left: 3%;">搜索</el-button>
        <el-button type="primary" @click="showAddSiteDialog">新增工地</el-button>
      </div>
  
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="sId" label="序号" />
        <el-table-column prop="siteName" label="工地名称" />
        <el-table-column prop="siteLocation" label="工地位置" />
        <el-table-column prop="siteDesc" label="工地描述" />
        <el-table-column label="操作" width="150px">
          <template #default="scope">
            <el-button type="primary" size="small" @click="showModifySite(scope.row)">修改</el-button>
            <el-button type="danger" size="small" @click="deleteSite(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="paginationClass">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalSize"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
  
      <!-- 修改工地对话框 -->
      <el-dialog v-model="modifySiteVisible" title="修改工地" width="30%">
        <el-form :model="site" label-width="100px">
          <el-form-item label="工地名称">
            <el-input v-model="site.siteName" />
          </el-form-item>
          <el-form-item label="工地位置">
            <el-input v-model="site.siteLocation" />
          </el-form-item>
          <el-form-item label="工地描述">
            <el-input v-model="site.siteDesc" />
          </el-form-item>
        </el-form>
        <div>
          <el-button @click="modifySiteVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleModifySite">确 定</el-button>
        </div>
      </el-dialog>
  
      <!-- 新增工地对话框 -->
      <el-dialog v-model="addSiteVisible" title="新增工地" width="30%">
        <el-form :model="addSite" label-width="100px">
          <el-form-item label="工地名称">
            <el-input v-model="addSite.siteName" />
          </el-form-item>
          <el-form-item label="工地位置">
            <el-input v-model="addSite.siteLocation" />
          </el-form-item>
          <el-form-item label="工地描述">
            <el-input v-model="addSite.siteDesc" />
          </el-form-item>
        </el-form>
        <div>
          <el-button @click="addSiteVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddSite">确 定</el-button>
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
    padding: 16px;
    box-sizing: border-box;
    overflow: hidden;
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
  
