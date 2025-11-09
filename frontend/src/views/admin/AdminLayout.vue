<script>
import { RouterView, RouterLink } from 'vue-router'
import Sidebar from './components_1/Sidebar.vue';

export default {
  components: { Sidebar }
}
</script>

<template>
  <div class="admin-layout">
    <Sidebar /> <!-- 관리자 메뉴 -->
    <div class="admin-content">
      <RouterView />
    </div>
  </div>
</template>

<style>
.admin-layout {
  height: 100svh;       /* 모바일 뷰포트 대응 */
  position: relative;
  overflow: hidden;     /* 배경 이펙트가 삐져나가도 스크롤 안생김 */
}

.admin-content {
  position: absolute;
  inset: var(--header-h) 0 calc(var(--footer-h) + var(--safe-bottom)) 0;
  overflow: auto;       /* 여기서만 스크롤 */
  -webkit-overflow-scrolling: touch;
}
</style>

