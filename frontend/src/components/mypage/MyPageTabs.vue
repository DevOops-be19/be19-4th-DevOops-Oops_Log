<template>
  <!-- 마이페이지 전용 탭바: 부모의 v-model과 동기화 -->
  <div
    class="mypage-tabs"
    role="tablist"
    aria-label="마이페이지 탭"
  >
    <button
      v-for="t in tabs"
      :key="t.value"
      class="tab-btn"
      :class="{ active: modelValue === t.value }"
      role="tab"
      :aria-selected="modelValue === t.value"
      :tabindex="modelValue === t.value ? 0 : -1"
      @click="$emit('update:modelValue', t.value)"
      @keydown.enter.space="$emit('update:modelValue', t.value)"
    >
      {{ t.label }}
    </button>
  </div>
</template>

<script>
export default {
  name: 'MyPageTabs',
  props: {
    modelValue: { type: String, required: true }, // 활성 탭 값
    // 탭 목록 주입 가능(미전달 시 기본 구성 사용)
    items: {
      type: Array,
      default: () => ([
        { value: 'profile',     label: '개인정보 수정' },
        { value: 'oops',        label: 'Oops 기록' },
        { value: 'ooh',         label: 'Ooh 기록' },
        { value: 'graph',       label: '성장 그래프' },
        { value: 'recommended', label: '추천게시글' },
        { value: 'bookmarks',   label: '북마크기록' },
        { value: 'following',   label: '팔로우기록' },
        { value: 'settings',    label: '설정' }
      ])
    }
  },
  computed: {
    tabs() { return this.items; }
  }
}
</script>

<style scoped>
.mypage-tabs {
  display: grid;
  grid-template-columns: repeat(4, minmax(0,1fr));
  gap: 8px;
  padding: 8px;
  background: rgba(136,170,130,0.10); /* 연녹 배경 */
  border-radius: 12px;
}
@media (min-width: 1024px) {
  .mypage-tabs { grid-template-columns: repeat(8, minmax(0,1fr)); }
}
.tab-btn {
  appearance: none;
  border: none;
  padding: 10px 12px;
  border-radius: 10px;
  background: transparent;
  color: #55433B;                 /* 갈색 텍스트 */
  font-family: 'Inter', system-ui, sans-serif;
  font-size: 14px;
  cursor: pointer;
  line-height: 1;
  transition: background .18s ease, transform .06s ease;
}
.tab-btn:hover { background: rgba(136,170,130,0.18); }
.tab-btn:active { transform: translateY(1px); }
.tab-btn.active {
  background: #F6F1E0;            /* 크림색 */
  outline: 1px solid rgba(136,170,130,0.35);
}
.tab-btn:focus-visible {
  outline: 2px solid #88AA82;     /* 포커스 링 */
  outline-offset: 2px;
  border-radius: 10px;
}
</style>
