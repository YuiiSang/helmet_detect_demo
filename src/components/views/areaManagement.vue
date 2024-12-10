<script setup>
import { ref, reactive, onMounted } from 'vue'
import instance from "../../axios.js";
import { ElMessage , ElMessageBox} from 'element-plus';

let pageNum = ref(1);
let pageSize = ref(5);
let tableData = ref([]);
let totalSize = ref(0);
let area = reactive({});
let areaNameInput = ref('');
let addArea = reactive({});
let modifyAreaVisible = ref(false);
let addAreaVisible = ref(false);
let siteNameList = ref([]);
// 获取数据
const getTableData = () => {
  instance.get("/page/getPage", {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      pageName: 'areaPage',
      input: areaNameInput.value
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

// 修改区域
const handleModifyArea = () => {
  instance.post('/area/modifyArea', {
    aid: area.aid,
    siteId: area.siteId,
    areaName: area.areaName,
    areaDescription: area.areaDescription,
    cameraIp: area.cameraIp,
    createTime: null,
    updateTime: null
  }).then((res) => {
    if (res.data.code == 200) {
      ElMessage.success(res.data.msg);
      modifyAreaVisible.value = false;
      getTableData();
    } else {
      ElMessage.error(res.data.msg);
    }
  }).catch((err) => {
    console.log(err);
  });
};

// 删除区域
const deleteArea = (row) => {
    ElMessageBox.confirm('确定要删除这条记录吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
  instance.get('/area/deleteAreaById', {
    params: {
      aId: row.aid
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
  });})
};

// 显示修改区域对话框
const showModifyArea = (row) => {
  Object.assign(area, row);
  modifyAreaVisible.value = true;
};

// 显示新增区域对话框
const showAddAreaDialog = () => {
  addArea = reactive({});
  addAreaVisible.value = true;
};

// 新增区域
const handleAddArea = () => {
  instance.post('/area/addArea', {
    siteId: addArea.siteId,
    areaName: addArea.areaName,
    areaDescription: addArea.areaDescription,
    cameraIp: addArea.cameraIp,
    createTime: new Date().toISOString(),
    updateTime: new Date().toISOString()
  }).then((res) => {
    if (res.data.code == 200) {
      ElMessage.success(res.data.msg);
      addAreaVisible.value = false;
      getTableData();
    } else {
      ElMessage.error(res.data.msg);
    }
  }).catch((err) => {
    console.log(err);
  });
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
onMounted(() => {
  getTableData();
  getSiteNameList();
});
</script>

<template>
  <div class="main-container">
    <div class="search-block">
      <el-input v-model="areaNameInput" style="width: 240px" placeholder="请输入要查找的区域名" />
      <el-button type="primary" @click="getTableData" style="margin-left: 3%;">搜索</el-button>
      <el-button type="primary" @click="showAddAreaDialog">新增区域</el-button>
    </div>

    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="aid" label="序号" />
      <el-table-column prop="siteId" label="工地 ID" />
      <el-table-column prop="areaName" label="区域名称" />
      <el-table-column prop="areaDescription" label="区域描述" />
      <el-table-column prop="cameraIp" label="摄像头 IP" />
      <el-table-column label="操作" width="150px">
        <template #default="scope">
          <el-button type="primary" size="small" @click="showModifyArea(scope.row)">修改</el-button>
          <el-button type="danger" size="small" @click="deleteArea(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="paginationClass">
      <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 20, 40]"
        layout="total, sizes, prev, pager, next, jumper" :total="totalSize" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
    </div>

    <!-- 修改区域对话框 -->
    <el-dialog v-model="modifyAreaVisible" title="修改区域" width="30%">
      <el-form :model="area" label-width="100px">
        <el-form-item label="工地">
          <el-select v-model="area.siteId" placeholder="请选择工地" style="width: 240px">
            <el-option v-for="item in siteNameList" :key="item.sid" :label="item.siteName" :value="item.sid" />
          </el-select>
        </el-form-item>
        <el-form-item label="区域名称">
          <el-input v-model="area.areaName" />
        </el-form-item>
        <el-form-item label="区域描述">
          <el-input v-model="area.areaDescription" />
        </el-form-item>
        <el-form-item label="摄像头 IP">
          <el-input v-model="area.cameraIp" />
        </el-form-item>
      </el-form>
      <div>
        <el-button @click="modifyAreaVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleModifyArea">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 新增区域对话框 -->
    <el-dialog v-model="addAreaVisible" title="新增区域" width="30%">
      <el-form :model="addArea" label-width="100px">
        <el-form-item label="工地">
          <el-select v-model="addArea.siteId" placeholder="请选择工地" style="width: 240px">
            <el-option v-for="item in siteNameList" :key="item.sid" :label="item.siteName" :value="item.sid" />
          </el-select>
        </el-form-item>
        <el-form-item label="区域名称">
          <el-input v-model="addArea.areaName" />
        </el-form-item>
        <el-form-item label="区域描述">
          <el-input v-model="addArea.areaDescription" />
        </el-form-item>
        <el-form-item label="摄像头 IP">
          <el-input v-model="addArea.cameraIp" />
        </el-form-item>
      </el-form>
      <div>
        <el-button @click="addAreaVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAddArea">确 定</el-button>
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
