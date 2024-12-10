import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/views/login.vue';
import Home from '../components/views/home.vue';
import UserManagement from '../components/views/userManagement.vue';
import DetectManagement from '../components/views/detectManagement.vue'; // 注意这里首字母大写
import SiteManagement from '../components/views/siteManagement.vue'; // 注意这里首字母大写
import AreaManagement from '../components/views/areaManagement.vue'; // 注意这里首字母大写
import ViolationManagement from '../components/views/violationManagement.vue'; // 注意这里首字母大写
import NoPermission from '../components/views/noPermission.vue'; // 注意这里首字母大写
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
    // 子路由
    children: [
      {
        path: '/userManagement',
        name: 'userManagement',
        component: UserManagement,
        meta: { requiresAuth: true, requiresAdmin: true } // 需要登录且是管理员
      },
      {
        path: '/detectManagement',
        name: 'detectManagement',
        component: DetectManagement,
        meta: { requiresAuth: true } // 需要登录
      },
      {
        path: '/siteManagement',
        name: 'siteManagement',
        component: SiteManagement,
        meta: { requiresAuth: true, requiresAdmin: true } // 需要登录且是管理员
      },
      {
        path: '/areaManagement',
        name: 'areaManagement',
        component: AreaManagement,
        meta: { requiresAuth: true } // 需要登录
      },
      {
        path: '/violationManagement',
        name: 'violationManagement',
        component: ViolationManagement,
        meta: { requiresAuth: true } // 需要登录
      },
    ]
  },
  {
    path: '/no-permission',
    name: 'noPermission',
    component: NoPermission,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 全局前置守卫
router.beforeEach((to, from, next) => {
  // 检查用户是否登录
  const isLogged = localStorage.getItem('siteId') !== null;
  // 检查是否需要管理员权限
  const requiresAdmin = to.matched.some(record => record.meta.requiresAdmin);
  const isSiteIdZero = localStorage.getItem('siteId') === '0';

  if (to.matched.some(record => record.meta.requiresAuth) && !isLogged) {
    // 未登录，重定向到登录页面
    next('/');
  } else if (requiresAdmin && !isSiteIdZero) {
    // 不是管理员，重定向到无权限页面
    next('/no-permission'); //
  } else {
    next(); // 继续路由跳转
  }
});

export default router;