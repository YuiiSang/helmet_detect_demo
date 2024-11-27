import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/views/login.vue';
import Home from '../components/views/home.vue';
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
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;