<template>
  <div class="toast-wrapper">
    <transition-group name="toast" tag="div" class="toast-list">
      <div v-for="t in toasts" :key="t.id" class="toast-item" :class="t.type">
        {{ t.message }}
      </div>
    </transition-group>
  </div>
</template>

<script setup>
import { toasts } from './useToast.js';
</script>

<style scoped>
.toast-wrapper {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 9999;
}
.toast-list { display: flex; flex-direction: column; gap: 10px; }
.toast-item {
  min-width: 260px; padding: 12px 18px; border-radius: 10px;
  font-family: 'Inter', sans-serif; font-size: 0.875rem;
  color: #55433B; background: #F6F1E0; border: 1px solid #88AA82;
  box-shadow: 0 2px 6px rgba(0,0,0,0.15); opacity: 0.95;
}
.toast-item.error { border-color: #55433B; }
.toast-item.success { border-color: #88AA82; }

/* 애니메이션 */
.toast-enter-from { opacity: 0; transform: translateY(-10px); }
.toast-enter-active, .toast-leave-active { transition: all 0.35s ease; }
.toast-leave-to { opacity: 0; transform: translateY(-20px); }
</style>
