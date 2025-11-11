<script setup>
import axios from "axios";
import { ref } from "vue";
import { useToastStore } from "@/stores/userToast";

const username = ref("");
const password = ref("");
const toastStore = useToastStore();

const handleLogin = async () => {
  if (!username.value || !password.value) {
    toastStore.showToast("아이디와 비밀번호를 입력해주세요.");
    return;
  }
  console.log("로그인 시도:", username.value, password.value);
  // 로그인 요청
  try {
  const response = await axios.post('/api/login', {
    member_id: username.value,
    member_pw: password.value
  });
  const data = response.data;
  console.log(data);
  toastStore.showToast(response.data)

  } catch (error) {
    console.log(error);
    toastStore.showToast(error.response.data.error)
  }
};

</script>

<template>
  <div class="login-container">
    <h1 class="login-title">로그인</h1>

    <form class="login-form" @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">아이디</label>
        <input
          id="username"
          type="text"
          v-model="username"
          placeholder="아이디를 입력하세요"
        />
      </div>

      <div class="form-group">
        <label for="password">비밀번호</label>
        <input
          id="password"
          type="password"
          v-model="password"
          placeholder="비밀번호를 입력하세요"
        />
      </div>

      <div class="form-group">
        <button type="submit" class="login-btn">로그인</button>
      </div>
    </form>
  </div>
</template>


<style scoped>
/* 전체 배경 */
.login-container {
  background-color: #f7f1df;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

/* 제목 */
.login-title {
  font-size: 2rem;
  font-weight: 600;
  color: #4b3a2f;
  margin-bottom: 2rem;
}

/* 폼 */
.login-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 300px;
  padding-bottom: 10%;
}

/* 라벨 */
.form-group label {
  display: block;
  font-size: 0.9rem;
  color: #5a4636;
  margin-bottom: 4px;
}

/* 입력창 */
.form-group input {
  width: 91.5%;
  padding: 10px 12px;
  border-radius: 8px;
  border: 1px solid #d6c8b2;
  background-color: #fcf8ef;
  box-shadow: 1px 2px 3px rgba(0, 0, 0, 0.08);
  font-size: 0.95rem;
  outline: none;
}

.form-group input:focus {
  border-color: #b8a483;
}

/* 버튼 */
.login-btn {
  width: 100%;
  background-color: #f3eddd;
  color: #4b3a2f;
  border: none;
  border-radius: 10px;
  padding: 12px 0;
  font-size: 1rem;
  cursor: pointer;
  box-shadow: 1px 2px 3px rgba(0, 0, 0, 0.08);
  transition: all 0.2s;
  
}

.login-btn:hover {
  background-color: #e5ddc7;
}

</style>