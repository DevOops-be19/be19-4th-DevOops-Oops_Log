// stores/user.js
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore(
  'userInfo',()=>{
    const userId= ref(0)
    const token=ref('')
    const memberId=ref('')
    const email=ref('')
    const name=ref('')
    const birth=ref('')
    const gender=ref('')
    const signUpDate = ref('')
    const auth = ref([])

    const setUserInfo = (getId,getMemberId,getEmail,getName,getBirth,getGender,getSignUpDate,getToken,getAuth)=>{
      userId.value = getId
      memberId.value = getMemberId
      email.value = getEmail
      name.value = getName
      birth.value = getBirth
      gender.value = getGender
      signUpDate.value = getSignUpDate
      token.value = getToken
      auth.value = getAuth
    }

    return {userId,token,memberId,email,name,birth,gender,signUpDate,auth,setUserInfo}
  })
