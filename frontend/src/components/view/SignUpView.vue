<template>
  <div class="min-h-screen flex items-center justify-center px-6 py-12" :style="{ backgroundColor: '#F6F1E0' }">
    <div class="w-full max-w-md transition-all duration-700" :class="entered ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-5'">
      <!-- 뒤로가기 -->
      <button
        @click="emit('navigateToLogin')"
        class="flex items-center gap-2 mb-8 hover:opacity-60 transition-opacity"
        :style="{ color: '#55433B' }"
      >
        <ArrowLeft :size="20" />
        <span :style="{ fontFamily: `'Inter', sans-serif` }">로그인으로 돌아가기</span>
      </button>

      <!-- 로고 -->
      <div class="text-center mb-12 transition-opacity duration-700" :class="entered ? 'opacity-100' : 'opacity-0'">
        <div class="flex justify-center mb-6">
          <img :src="logoImage" alt="Oops_Log" class="h-16 w-auto" />
        </div>
        <h1
          class="mb-3"
          :style="{
            fontFamily: `'EB Garamond', 'Cormorant', serif`,
            fontSize: '2.5rem',
            color: '#55433B',
            letterSpacing: '0.02em'
          }"
        >
          시작하기
        </h1>
        <p class="opacity-70" :style="{ fontFamily: `'Inter', sans-serif`, color: '#55433B', lineHeight: '1.8' }">
          당신만의 서재를 만들어보세요
        </p>
      </div>

      <!-- 폼 -->
      <div
        class="border-2 rounded-lg p-8 transition-all duration-700"
        :class="entered ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-5'"
        :style="{ backgroundColor: 'rgba(255,255,255,0.5)', borderColor: 'rgba(136,170,130,0.3)' }"
      >
        <form @submit.prevent="handleSubmit" class="space-y-5">
          <!-- 이름 -->
          <div class="space-y-2">
            <label for="name" :style="{ fontFamily: `'Inter', sans-serif`, color: '#55433B' }">
              <div class="flex items-center gap-2">
                <User :size="16" :style="{ color: '#88AA82' }" />
                이름
              </div>
            </label>
            <input
              id="name"
              v-model="name"
              type="text"
              placeholder="이름을 입력하세요"
              class="w-full rounded-md px-3 py-2 outline-none"
              :style="{
                fontFamily: `'Inter', sans-serif`,
                backgroundColor: 'rgba(246,241,224,0.5)',
                border: '1px solid rgba(136,170,130,0.3)',
                color: '#55433B'
              }"
            />
          </div>

          <!-- 이메일 -->
          <div class="space-y-2">
            <label for="email" :style="{ fontFamily: `'Inter', sans-serif`, color: '#55433B' }">
              <div class="flex items-center gap-2">
                <Mail :size="16" :style="{ color: '#88AA82' }" />
                이메일
              </div>
            </label>
            <input
              id="email"
              v-model="email"
              type="email"
              placeholder="your@email.com"
              class="w-full rounded-md px-3 py-2 outline-none"
              :style="{
                fontFamily: `'Inter', sans-serif`,
                backgroundColor: 'rgba(246,241,224,0.5)',
                border: '1px solid rgba(136,170,130,0.3)',
                color: '#55433B'
              }"
            />
          </div>

          <!-- 비밀번호 -->
          <div class="space-y-2">
            <label for="password" :style="{ fontFamily: `'Inter', sans-serif`, color: '#55433B' }">
              <div class="flex items-center gap-2">
                <Lock :size="16" :style="{ color: '#88AA82' }" />
                비밀번호
              </div>
            </label>
            <input
              id="password"
              v-model="password"
              type="password"
              placeholder="최소 6자 이상"
              class="w-full rounded-md px-3 py-2 outline-none"
              :style="{
                fontFamily: `'Inter', sans-serif`,
                backgroundColor: 'rgba(246,241,224,0.5)',
                border: '1px solid rgba(136,170,130,0.3)',
                color: '#55433B'
              }"
            />
          </div>

          <!-- 비밀번호 확인 -->
          <div class="space-y-2">
            <label for="confirmPassword" :style="{ fontFamily: `'Inter', sans-serif`, color: '#55433B' }">
              <div class="flex items-center gap-2">
                <Lock :size="16" :style="{ color: '#88AA82' }" />
                비밀번호 확인
              </div>
            </label>
            <input
              id="confirmPassword"
              v-model="confirmPassword"
              type="password"
              placeholder="비밀번호를 다시 입력하세요"
              class="w-full rounded-md px-3 py-2 outline-none"
              :style="{
                fontFamily: `'Inter', sans-serif`,
                backgroundColor: 'rgba(246,241,224,0.5)',
                border: '1px solid rgba(136,170,130,0.3)',
                color: '#55433B'
              }"
            />
          </div>

          <!-- 회원가입 버튼 -->
          <button
            type="submit"
            class="w-full py-3 rounded-lg hover:opacity-90 transition-opacity mt-6"
            :style="{ backgroundColor: '#88AA82', color: 'white', fontFamily: `'Inter', sans-serif` }"
          >
            서재 만들기
          </button>
        </form>

        <!-- 약관 안내 -->
        <p
          class="mt-6 text-xs text-center opacity-60"
          :style="{ fontFamily: `'Inter', sans-serif`, color: '#55433B', lineHeight: '1.6' }"
        >
          회원가입을 진행하면 Oops_Log의 개인정보 보호 정책에 동의하는 것으로 간주됩니다.
          모든 기록은 브라우저에 안전하게 저장됩니다.
        </p>
      </div>

      <!-- 하단 문구 -->
      <div class="mt-8 text-center transition-opacity duration-700" :class="entered ? 'opacity-100' : 'opacity-0'">
        <p
          class="opacity-60"
          :style="{
            fontFamily: `'EB Garamond', serif`,
            fontSize: '1.125rem',
            color: '#55433B',
            fontStyle: 'italic',
            lineHeight: '1.8'
          }"
        >
          "당신의 하루는 기록될 가치가 있습니다."
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useToast } from '@/components/useToast.js';
import { Mail, Lock, User, ArrowLeft } from 'lucide-vue-next';
import logoImage from 'figma:asset/e8d617e704c5b0a9cf445de7b027458244d5c6ea.png';

const emit = defineEmits(['signup', 'navigateToLogin']);

const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const name = ref('');
const entered = ref(false);
const toast = useToast();

onMounted(() => {
  requestAnimationFrame(() => (entered.value = true));
});

function handleSubmit() {
  if (!email.value || !password.value || !confirmPassword.value || !name.value) {
    toast.error('모든 항목을 입력해주세요');
    return;
  }
  if (password.value !== confirmPassword.value) {
    toast.error('비밀번호가 일치하지 않습니다');
    return;
  }
  if (password.value.length < 6) {
    toast.error('비밀번호는 최소 6자 이상이어야 합니다');
    return;
  }

  const users = JSON.parse(localStorage.getItem('oopslog_users') || '[]');
  if (users.some(u => u.email === email.value)) {
    toast.error('이미 사용중인 이메일입니다');
    return;
  }

  users.push({ email: email.value, password: password.value, name: name.value });
  localStorage.setItem('oopslog_users', JSON.stringify(users));
  localStorage.setItem('oopslog_current_user', email.value);

  toast.success('환영합니다! 당신의 서재가 준비되었습니다', 3000);
  emit('signup');
}
</script>
