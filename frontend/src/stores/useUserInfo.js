// stores/user.js
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore(
  'userInfo',()=>{
    const userId= ref(0)
    const token = ref('')

    const setUserInfo = (getId,getToken)=>{
      userId.value = getId
      token.value = getToken

    }
    return {userId,token,setUserInfo}
  })
