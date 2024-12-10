import { createApp } from 'vue'
import App from './App.vue'

import router from './routers/router';

import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
const app = createApp(App);
// 注册组件
app.use(ElementPlus, {
    locale: zhCn,
});
app.use(router);
app.mount('#app');
