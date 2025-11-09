import { createRouter, createWebHistory } from 'vue-router'
import adminRoutes from './modules/admin.routes';

const routes = [
  ...adminRoutes, 
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router
