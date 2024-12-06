<script setup>
import { ref, onMounted} from 'vue'
import instance from '../../axios';
import { ElMessage } from 'element-plus';
import router from '../../routers/router.js'
let username = ref('admin')
let password = ref()
let vImgCode = ref('')
let vcode = ref('')
let inputVcode = ref('')
// 获取验证码
const getVcode = () => {
  instance.get('/user/getKaptcha')
   .then(result => {
      vcode.value = result.data.data.vcode;
      vImgCode.value = result.data.data.kaptchaImage;
  })
}
// 登录
const login = () => {
  if (inputVcode.value == '') {
    ElMessage.error('请输入验证码')
    return
  }
  if (inputVcode.value!= vcode.value.toUpperCase()) {
    ElMessage.error('验证码错误')
    return
  }
  if (username.value == '') {
    ElMessage.error('请输入账号')
    return
  }
  if (password.value == '') {
    ElMessage.error('请输入密码')
    return
  }
  instance.post('/user/login', {
    username: username.value,
    password: password.value
  })
 .then(result => {
    var data = result.data;
    if (data.code == 200) {
      ElMessage.success(data.msg)
      localStorage.setItem('token', data.data.token)
      router.push('/home')
    } else {
      ElMessage.error(data.msg)
    }
  })
}

onMounted(() => {
  getVcode()
})

</script>

<template>
    <div class="login-container">
    <el-card class="box_card">
      <div>
        <h1>欢迎登录<span style="font-size: 0.5em; color: #999;">&emsp;&emsp;安全帽检测系统&emsp;</span></h1>
      </div>
      <el-form>
        <el-form-item>
          <el-input placeholder="请输入账号" v-model="username"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input placeholder="请输入密码" v-model="password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <div style="display: flex; align-items: center;">
            <el-input v-model="inputVcode" placeholder="请输入验证码" style="width: 160px;"></el-input>
            <img :src="vImgCode" @click="getVcode" style="cursor: pointer; margin-left: 50px;" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%;" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-image: url('../../assets/loginBackground.gif');
    background-size: cover;
    background-position: center;
  }

  .box_card {
    width: 500px;
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
</style>