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
import {ref, reactive} from 'vue'
import router from '../../routers/router.js'
const handleUserManagement = () => {
  router.push('/userManagement')
}
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
        <el-menu-item index="1" @click="handleUserManagement">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="2">
          <el-icon><location /></el-icon>
          <span>地点管理</span>
        </el-menu-item>
        <el-menu-item index="3">
          <el-icon><document /></el-icon>
          <span>违规记录管理</span>
        </el-menu-item>
        <el-menu-item index="4">
          <el-icon><DataLine /></el-icon>
          <span>查看统计报表</span>
        </el-menu-item>
        <el-menu-item index="5">
          <el-icon><VideoCamera /></el-icon>
          <span>监控管理</span>
        </el-menu-item>
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
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  background-color: #545c64;
  height:98vh; 
}
.header{
  overflow: hidden;/* 隐藏超出部分 */

  border-top-right-radius: 10px;
  background-color: #B3C0D1;
}
.main{
  overflow: hidden;/* 隐藏超出部分 */
  border-bottom-right-radius: 10px;
  background-color: #E5E9F2;
}

</style>
