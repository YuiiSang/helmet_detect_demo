import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/views/login.vue';
import Home from '../components/views/home.vue';
import UserManagement from '../components/views/userManagement.vue';
const routes = [
  {
    path: '/',
    name: 'login',
    component: Login,
  },
  {
    path: '/home',
    name: 'home',
    component: Home,
    /* 子路由 */
    children:[
      {
        path:'/userManagement',
        name:'userManagement',
        component:UserManagement,    
      },
    ]
  },


];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;