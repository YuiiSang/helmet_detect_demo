<script setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
  VideoCamera,
  User,
  DataLine,
} from '@element-plus/icons-vue'
import {ref, reactive, onMounted} from 'vue'
import router from '../../routers/router.js'
const handleUserManagement = () => {
  router.push('/userManagement')
}
const handleDetectManagement = () => {
  router.push('/detectManagement')
}
const handleSiteManagement = () => {
  router.push('/siteManagement')
}
const handleAreaManagement = () => {
  router.push('/areaManagement')
}
const handleViolationManagement = () => {
  router.push('/violationManagement')
}
const handleOpen = function(key, keyPath) {
  console.log(key, keyPath);
}

const handleClose = function(key, keyPath) {
  console.log(key, keyPath);
}
onMounted(() => {
  if (localStorage.getItem('siteId') == '0') {
    handleUserManagement();
  } else {
    handleAreaManagement();
  }
}
)
</script>

<template>
    <el-container class="container" >
      <el-aside width="200px" class="aside">
        <h5 class="mb-2">Custom colors</h5>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#545c64"
        class="el-menu-vertical"
        default-active="1"
        text-color="#fff"
        @open="handleOpen"
        @close="handleClose"
      >
        <el-sub-menu index="1" @click="handleUserManagement" >
          <template #title>
          <el-icon><User /></el-icon>
          <span>用户管理</span>
          </template>
        
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>
          <el-icon><location /></el-icon>
          <span>地点管理</span>
          </template>
            <el-menu-item index="2-1" @click="handleSiteManagement">工地管理</el-menu-item>
            <el-menu-item index="2-2" @click="handleAreaManagement">区域管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3" @click="handleViolationManagement">
          <template #title>
          <el-icon><document /></el-icon>
          <span>违规记录管理</span>
          </template>
        </el-sub-menu>
<!--         <el-sub-menu index="4">
          <template #title>
          <el-icon><DataLine /></el-icon>
          <span>查看统计报表</span>
          </template>
        </el-sub-menu> -->
        <el-sub-menu index="5" @click="handleDetectManagement">
          <template #title>
          <el-icon><VideoCamera /></el-icon>
          <span>监控管理</span>
          </template>
        </el-sub-menu>
      </el-menu>

      </el-aside>
      <el-container>
        <el-header class="header">Header</el-header>
        <el-main class="main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
</template>

<style scoped>
.el-menu-vertical{

  border-right: 0;/* 使边缘平整 */
}
.container{
  
}
.aside{

  overflow: hidden;/* 隐藏超出部分 */
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  background-color: #545c64;
  height:98vh; 
}
.header{
  overflow: hidden;/* 隐藏超出部分 */

  border-top-right-radius: 15px;
  background-color: #B3C0D1;
  height:8vh;
}
.main{
  overflow: hidden;/* 隐藏超出部分 */
  border-bottom-right-radius: 15px;
  background-color: #E5E9F2;
  height:90vh; 
}

</style>
