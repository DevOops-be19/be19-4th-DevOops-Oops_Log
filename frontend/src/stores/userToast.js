import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useToastStore = defineStore('toast', ()=>{
  
    const message= ref('')
    const show= ref(false)
  
  const showToast = (msg) =>{
      message.value = msg
      show.value = true
      setTimeout(() => (show.value = false), 2500)
    }
  return {message,show,showToast}
})