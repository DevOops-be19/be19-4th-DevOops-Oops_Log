import { reactive } from 'vue';

export const toasts = reactive([]);

export function useToast() {
  function push(message, type = 'success', duration = 2500) {
    const id = Date.now() + Math.random();
    toasts.push({ id, message, type });
    setTimeout(() => {
      const i = toasts.findIndex(t => t.id === id);
      if (i !== -1) toasts.splice(i, 1);
    }, duration);
  }
  return {
    success: (msg, ms) => push(msg, 'success', ms),
    error: (msg, ms) => push(msg, 'error', ms),
  };
}
