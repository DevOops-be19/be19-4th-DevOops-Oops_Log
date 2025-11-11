<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from 'vue-router'

const router = useRouter()

const activeTab = ref("login");

const showModal = ref(false);

// 로그인 폼 데이터
const id = ref("");
const password = ref("");

// 로그인 함수
const login = async () => {
  try {
    const payload = {
      memberId: id.value,     // ← 네이밍 변경: memberId / memberPwd
      memberPwd: password.value
    };

    const res = await axios.post("/api/login", payload, {
      headers: { "Content-Type": "application/json" }
    });

    // 토큰 처리
    const token = res.data;
    if (token && token !== "존재하지 않는 회원" && token !== "비밀번호 불일치") {
      sessionStorage.setItem("token", token);
      router.push("/main");
    } else {
      showModal.value = true;
    }
  } catch (err) {
    // 서버가 에러 메시지를 보냈다면 콘솔에 그대로 찍어 원인 추적
    console.error("로그인 에러:",
      err.response?.status,
      err.response?.data || err.message
    );
    showModal.value = true;
  }
};

// const adminLogin = () => {
//   router.push('/adminlogin')
// }

// const registerMember = () => {
//   router.push('/registermember')
// }

// const findId = () => {
//   router.push('/findid')
// }

const closeModal = () => {
  showModal.value = false;
};
</script>

<template>
  <div class="screen">
    <div class="login-form">
      <div class="container">
        <div class="image-with-fallback" />

        <div class="div">
          <div class="div-wrapper">
            <div class="text-wrapper">FASHION</div>
          </div>

          <div class="div-wrapper">
            <div class="text-wrapper-2">MANAGER</div>
          </div>

          <div class="container-2" />

          <div class="paragraph">
            <div class="text-wrapper-3">THE VISIONARY PROJECT</div>
          </div>

          <div class="div-wrapper">
            <div class="text-wrapper-4">Style. Substance. Success.</div>
          </div>
        </div>

        <div class="container-3" />
      </div>

      <div class="container-4">
        <div class="container-5">
          <div class="div-2">
            <div class="text-wrapper-5">Welcome Back</div>
          </div>

          <div class="paragraph-2">
            <p class="p">패션의 세계로 오신 것을 환영합니다</p>
          </div>
        </div>

        <div class="form">
          <div class="container-6">
            <div class="primitive-label">
              <div class="text-wrapper-6">아이디</div>
            </div>

            <div class="div-2">
              <input class="input" type="text" v-model="id" />
            </div>
          </div>

          <div class="container-7">
            <div class="primitive-label">
              <div class="text-wrapper-6">비밀번호</div>
            </div>

            <div class="div-2">
              <input class="input-2" v-model="password" type="password" />
            </div>
          </div>

          <div class="container-8">
            <!--<div class="container-9">
              <input class="primitive-button" type="checkbox"/>

              <div class="primitive-label-2">
                <div class="text-wrapper-7">로그인 상태 유지</div>
              </div>
            </div>-->

            <!--<button class="button-2">
              <div class="text-wrapper-8">비밀번호 찾기</div>
            </button>-->
          </div>

          <button class="button-3" @click="login">
            <div class="text-wrapper-9">로그인</div>
          </button>

          <button class="button-4" @click="adminLogin">
            <div class="text-wrapper-10">관리자로 로그인</div>
          </button>
        </div>

        <div class="container-wrapper">
          <div class="container-10">
            <button class="button-5" @click="findId">
              <div class="text-wrapper-11">아이디 찾기</div>
            </button>

            <div class="text">
              <div class="text-wrapper-12">•</div>
            </div>

            <button class="button-6" @click="registerMember">
              <div class="text-wrapper-13">회원가입</div>
            </button>

            <div class="text-2">
              <div class="text-wrapper-12">•</div>
            </div>

            <button class="button-7">
              <div class="text-wrapper-13">비밀번호 찾기</div>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- ✅ 모달 창 -->
    <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal">
        <h3>메시지</h3>
        <p>로그인에 실패했습니다.</p>
        <button @click="closeModal" class="close-btn">확인</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Screen",
};
</script>

<style scoped>
.screen {
  background-color: #ffffff;
  display: flex;
  min-height: 931px;
  min-width: 1440px;
  width: 100%;
}

.screen .login-form {
  background: linear-gradient(
    117deg,
    rgba(238, 242, 255, 1) 0%,
    rgba(255, 255, 255, 1) 50%,
    rgba(250, 245, 255, 1) 100%
  );
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  grid-template-rows: repeat(1, minmax(0, 1fr));
  height: 931px;
  position: relative;
  width: 1440px;
}

.screen .container {
  background-color: #000000;
  grid-column: 1 / 2;
  grid-row: 1 / 2;
  height: 931px;
  justify-self: start;
  overflow: hidden;
  position: relative;
  width: 100%;
}

.screen .image-with-fallback {
  background-image: url('/images/login-background.jpg');
  background-position: 50% 50%;
  background-size: cover;
  height: 931px;
  left: 0;
  position: absolute;
  top: 1px;
  width: 720px;
}

.screen .div {
  display: flex;
  flex-direction: column;
  gap: 12px;
  height: 210px;
  left: 49px;
  position: absolute;
  top: 95px;
  width: 244px;
}

.screen .div-wrapper {
  display: flex;
  width: 243.62px;
}

.screen .text-wrapper {
  color: #ffffff;
  font-family: "Arial-Regular", Helvetica;
  font-size: 48px;
  font-weight: 400;
  height: 48px;
  letter-spacing: 1.80px;
  line-height: 48px;
  margin-top: -5px;
  white-space: nowrap;
  width: 222px;
}

.screen .text-wrapper-2 {
  color: #ffffff;
  font-family: "Arial-Regular", Helvetica;
  font-size: 48px;
  font-weight: 400;
  height: 48px;
  letter-spacing: 1.80px;
  line-height: 48px;
  margin-top: -5px;
  white-space: nowrap;
  width: 254px;
}

.screen .container-2 {
  background: linear-gradient(
    180deg,
    rgba(225, 113, 0, 1) 0%,
    rgba(255, 185, 0, 1) 100%
  );
  height: 2px;
  width: 128px;
}

.screen .paragraph {
  display: flex;
  height: 36px;
  opacity: 0.9;
  width: 243.62px;
}

.screen .text-wrapper-3 {
  color: #ffffff;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  height: 20px;
  letter-spacing: 1.40px;
  line-height: 20px;
  margin-top: 14.7px;
  white-space: nowrap;
  width: 205px;
}

.screen .text-wrapper-4 {
  color: #fe9a00;
  font-family: "Arial-Italic", Helvetica;
  font-size: 18px;
  font-style: italic;
  font-weight: 400;
  height: 28px;
  letter-spacing: 0;
  line-height: 28px;
  margin-top: -1px;
  white-space: nowrap;
  width: 219px;
}

.screen .container-3 {
  background: linear-gradient(
    90deg,
    rgba(0, 0, 0, 0.6) 0%,
    rgba(0, 0, 0, 0.3) 50%,
    rgba(0, 0, 0, 0) 100%
  );
  height: 931px;
  left: 0;
  position: absolute;
  top: 0;
  width: 720px;
}

.screen .container-4 {
  align-items: flex-start;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  gap: 32px;
  grid-column: 2 / 3;
  grid-row: 1 / 2;
  height: 931px;
  justify-self: end;
  padding: 215.33px 122px 0px;
  position: relative;
  width: 100%;
}

.screen .container-5 {
  align-items: flex-start;
  display: flex;
  flex-direction: column;
  gap: 8px;
  height: 68px;
  position: relative;
  width: 402px;
}

.screen .div-2 {
  align-self: stretch;
  height: 36px;
  position: relative;
  width: 100%;
}

.screen .text-wrapper-5 {
  color: #0a0a0a;
  font-family: "Arial-Regular", Helvetica;
  font-size: 30px;
  font-weight: 400;
  left: 124px;
  letter-spacing: 0;
  line-height: 36px;
  position: absolute;
  text-align: center;
  top: -3px;
  white-space: nowrap;
}

.screen .paragraph-2 {
  align-self: stretch;
  height: 24px;
  position: relative;
  width: 100%;
}

.screen .p {
  color: #6a7282;
  font-family: "Arial-Regular", Helvetica;
  font-size: 16px;
  font-weight: 400;
  left: 105px;
  letter-spacing: 0;
  line-height: 24px;
  position: absolute;
  text-align: center;
  top: -2px;
  white-space: nowrap;
}

.screen .form {
  height: 324px;
  position: relative;
  width: 402px;
}

.screen .container-6 {
  align-items: flex-start;
  display: flex;
  flex-direction: column;
  gap: 8px;
  height: 64px;
  left: 0;
  position: absolute;
  top: 0;
  width: 448px;
}

.screen .primitive-label {
  align-items: center;
  align-self: stretch;
  display: flex;
  gap: 8px;
  height: 20px;
  position: relative;
  width: 100%;
}

.screen .text-wrapper-6 {
  color: #0a0a0a;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  letter-spacing: 0;
  line-height: 20px;
  margin-top: -1.00px;
  position: relative;
  white-space: nowrap;
  width: fit-content;
}

.screen .input {
  align-self: stretch;
  background-color: #f3f3f5;
  border: 0.67px solid;
  border-color: #d1d5dc;
  border-radius: 8px;
  color: #717182;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  height: 36px;
  letter-spacing: 0;
  line-height: normal;
  overflow: hidden;
  padding: 4px 12px;
  position: relative;
  white-space: nowrap;
  width: 100%;
}

.screen .container-7 {
  align-items: flex-start;
  display: flex;
  flex-direction: column;
  gap: 8px;
  height: 64px;
  left: 0;
  position: absolute;
  top: 88px;
  width: 448px;
}

.screen .input-2 {
  background-color: #f3f3f5;
  border: 0.67px solid;
  border-color: #d1d5dc;
  border-radius: 8px;
  color: #717182;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  height: 36px;
  left: 0;
  letter-spacing: 0;
  line-height: normal;
  overflow: hidden;
  padding: 4px 12px;
  position: absolute;
  top: 0;
  white-space: nowrap;
  width: 448px;
}

.screen .button {
  align-items: flex-start;
  display: flex;
  flex-direction: column;
  height: 16px;
  left: 420px;
  position: absolute;
  top: 10px;
  width: 16px;
}

.screen .icon {
  align-self: stretch;
  height: 16px;
  position: relative;
  width: 100%;
}

.screen .vector {
  height: 170.33%;
  left: 4.16%;
  position: absolute;
  top: 16.67%;
  width: 83.34%;
}

.screen .img {
  height: 25.00%;
  left: 33.33%;
  position: absolute;
  top: 33.33%;
  width: 25.00%;
}

.screen .container-8 {
  align-items: center;
  display: flex;
  height: 20px;
  justify-content: flex-end;
  left: 0;
  padding: 0px -8.39e-05px 0px 0px;
  position: absolute;
  top: 176px;
  width: 448px;
}

.screen .container-9 {
  align-items: center;
  display: flex;
  gap: 8px;
  height: 20px;
  position: relative;
  width: 131.84px;
}

.screen .primitive-button {
  background-color: #f3f3f5;
  border: 0.67px solid;
  border-color: #d1d5dc;
  border-radius: 4px;
  box-shadow: 0px 1px 2px #0000000d;
  height: 16px;
  position: relative;
  width: 16px;
}

.screen .primitive-label-2 {
  align-items: center;
  display: flex;
  flex: 1;
  flex-grow: 1;
  gap: 8px;
  height: 20px;
  position: relative;
}

.screen .text-wrapper-7 {
  color: #4a5565;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  letter-spacing: 0;
  line-height: 20px;
  margin-top: -1.00px;
  position: relative;
  white-space: nowrap;
  width: fit-content;
}

.screen .button-2 {
  all: unset;
  box-sizing: border-box;
  height: 20px;
  position: relative;
  width: 88.93px;
  cursor: pointer;
}

.screen .text-wrapper-8 {
  color: #e17100;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  left: 0;
  letter-spacing: 0;
  line-height: 20px;
  position: absolute;
  top: -1px;
  white-space: nowrap;
}

.screen .button-3 {
  all: unset;
  background-color: #000000;
  border-radius: 8px;
  box-sizing: border-box;
  display: flex;
  height: 44px;
  left: 0;
  position: absolute;
  top: 220px;
  width: 448px;
  cursor:pointer;
}

.screen .text-wrapper-9 {
  color: #ffffff;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  height: 20px;
  letter-spacing: 0;
  line-height: 20px;
  margin-left: 203px;
  margin-top: 10.7px;
  white-space: nowrap;
  width: 39px;
  cursor: pointer;
}

.screen .button-4 {
  all: unset;
  background-color: #ffffff;
  border: 0.67px solid;
  border-color: #d1d5dc;
  border-radius: 8px;
  box-sizing: border-box;
  display: flex;
  height: 36px;
  left: 0;
  position: absolute;
  top: 288px;
  width: 448px;
  cursor: pointer;
}

.screen .text-wrapper-10 {
  color: #0a0a0a;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  height: 20px;
  letter-spacing: 0;
  line-height: 20px;
  margin-left: 179.6px;
  margin-top: 6.7px;
  white-space: nowrap;
  width: 95px;
}

.screen .container-wrapper {
  align-items: flex-start;
  border-color: #f2f4f6;
  border-top-style: solid;
  border-top-width: 0.67px;
  display: flex;
  flex-direction: column;
  height: 44.67px;
  padding: 24.67px 0px 0px;
  position: relative;
  width: 402px;
}

.screen .container-10 {
  align-self: stretch;
  height: 20px;
  position: relative;
  width: 100%;
}

.screen .button-5 {
  all: unset;
  box-sizing: border-box;
  display: flex;
  height: 20px;
  left: 93px;
  position: absolute;
  top: 0;
  width: 75px;
  cursor: pointer;
}

.screen .text-wrapper-11 {
  color: #697282;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  height: 20px;
  letter-spacing: 0;
  line-height: 20px;
  margin-top: -1.3px;
  white-space: nowrap;
  width: 69px;
}

.screen .text {
  display: flex;
  height: 20px;
  left: 184px;
  position: absolute;
  top: 0;
  width: 6px;
}

.screen .text-wrapper-12 {
  color: #697282;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  height: 20px;
  letter-spacing: 0;
  line-height: 20px;
  margin-top: -1.3px;
  white-space: nowrap;
  width: 5px;
}

.screen .button-6 {
  all: unset;
  box-sizing: border-box;
  display: flex;
  height: 20px;
  left: 205px;
  position: absolute;
  top: 0;
  width: 56px;
  cursor: pointer;
}

.screen .text-wrapper-13 {
  color: #697282;
  font-family: "Arial-Regular", Helvetica;
  font-size: 14px;
  font-weight: 400;
  height: 20px;
  letter-spacing: 0;
  line-height: 20px;
  margin-top: -1.3px;
  white-space: nowrap;
  width: 52px;
}

.screen .text-2 {
  display: flex;
  height: 20px;
  left: 277px;
  position: absolute;
  top: 0;
  width: 6px;
}

.screen .button-7 {
  all: unset;
  box-sizing: border-box;
  display: flex;
  height: 20px;
  left: 299px;
  position: absolute;
  top: 0;
  width: 56px;
  cursor: pointer;
}
</style>