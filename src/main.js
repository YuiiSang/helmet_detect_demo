import { createApp } from 'vue'
import App from './App.vue'

import router from './routers/router';

import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
const app = createApp(App);
// 注册组件
app.use(ElementPlus);
app.use(router);
app.mount('#app');
