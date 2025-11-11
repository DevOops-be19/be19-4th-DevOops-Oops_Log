<!-- src/components/SearchBar.vue -->
<template>
  <form class="oops-search dark-pill" @submit.prevent="submit">
    <div class="bar">
      <span class="icon" aria-hidden>🔎</span>

      <input
        v-model="innerValue"
        type="search"
        :placeholder="placeholder"
        autocomplete="off"
      />

      <button
        v-if="innerValue"
        type="button"
        class="clear"
        @click="innerValue = ''"
        aria-label="입력 지우기"
        title="지우기"
      >×</button>

      <button type="submit" class="submit" :disabled="busy">검색</button>
    </div>
  </form>
</template>

<script>
export default {
  name: 'SearchBar',
  props: {
    modelValue: { type: String, default: '' },
    placeholder: { type: String, default: '제목 또는 내용 검색...' },
    busy: { type: Boolean, default: false }
  },
  emits: ['update:modelValue', 'search'],
  data() {
    return { innerValue: this.modelValue }
  },
  watch: {
    innerValue(v) { this.$emit('update:modelValue', v) },
    modelValue(v) { this.innerValue = v }
  },
  methods: {
    submit() { this.$emit('search', this.innerValue) }
  }
}
</script>

<style scoped>
/* ---------- 공통 레이아웃 ---------- */
.oops-search { width: 100%; }
.bar {
 display: grid;
  grid-template-columns: auto 1fr auto auto;
  align-items: center;
  gap: 12px;

  background: #ffffff;                /* ← 흰색 */
  border: 1px solid #e6e0d6;          /* 부드러운 베이지 테두리 */
  border-radius: 18px;
  padding: 14px 16px;
  box-shadow:
    0 6px 18px rgba(0,0,0,0.08),      /* 부드러운 바깥 그림자 */
    inset 0 1px 0 rgba(255,255,255,.8);
}

/* 아이콘은 살짝 투명 */
.icon { opacity: .6; font-size: 18px; color: #7d6f63; }  /* 아이콘 연한 회색브라운 */

/* 입력창: 테두리 제거 + 라이트한 텍스트 + 넉넉한 크기 */

input {
  border: none;
  outline: none;
  background: transparent;
  height: 28px;
  font-size: 16px;
  color: #3b2f2b;                     /* 진한 텍스트 */
}

input::placeholder { color: #a09286; }


/* 지우기 버튼: 테두리 최소, 배경 투명 */
.clear {
  height: 34px; min-width: 34px;
  border-radius: 10px;
  border: 1px solid #e9e1d7;
  background: #fff;                   /* 흰색 유지 */
  color: #6d6056;
  cursor: pointer;
}

.submit {
  height: 36px; padding: 0 16px;
  border: none; border-radius: 12px;
  background: #f49a59;                /* 오렌지 버튼 */
  color: #2a221d; font-weight: 700;
  cursor: pointer;
  box-shadow: 0 2px 0 rgba(0,0,0,.08);
}
.submit:disabled { opacity: .6; cursor: not-allowed; }

/* 다크모드에선 거의 동일 톤 유지 */
@media (prefers-color-scheme: dark) {
  .bar { background: #ffffff; border-color: #dcd4ca; }
  input { color: #2f2722; }
  .clear { background: #fff; border-color: #e2d9cf; color: #5b5148; }
  .submit { background: #ff9a5c; color: #1b1614; }
}

/* 컨테이너가 좁아져도 자연스럽게 줄도록 */
@media (max-width: 640px) {
  .bar { border-radius: 16px; padding: 12px 14px; }
  .submit { height: 34px; padding: 0 14px; }
}

input[type="search"]::-webkit-search-cancel-button,
input[type="search"]::-webkit-search-decoration {
  -webkit-appearance: none;
  appearance: none;
  display: none;
}
input[type="search"]::-ms-clear { display: none; width: 0; height: 0; }
input[type="search"]::-ms-reveal { display: none; width: 0; height: 0; }
</style>
