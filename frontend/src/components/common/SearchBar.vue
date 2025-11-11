<!-- src/components/SearchBar.vue -->
<template>
  <form class="oops-search" @submit.prevent="submit">
    <div class="bar">
      <span class="icon">🔍</span>

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
      >
        ×
      </button>

      <button type="submit" class="submit" :disabled="busy">검색</button>
    </div>
  </form>
</template>

<script>
export default {
  name: 'SearchBar',
  props: {
    modelValue: { type: String, default: '' },   // 검색어만 관리
    placeholder: { type: String, default: '검색어를 입력하세요' },
    busy: { type: Boolean, default: false }
  },
  emits: ['update:modelValue', 'search'],
  data() {
    return {
      innerValue: this.modelValue
    }
  },
  watch: {
    innerValue(v) { this.$emit('update:modelValue', v) },
    modelValue(v) { this.innerValue = v }
  },
  methods: {
    submit() {
      this.$emit('search', this.innerValue);
    }
  }
}
</script>

<style scoped>
.oops-search { display: grid; }
.bar {
  display: grid;
  grid-template-columns: auto 1fr auto auto;
  align-items: center;
  gap: 8px;

  background: #fff7ee;
  border: 1px solid #ffe3c7;
  border-radius: 14px;
  padding: 8px 12px;
  box-shadow: 0 2px 10px rgba(255, 170, 120, 0.15);
}
.icon { opacity: .8; }
input {
  border: none;
  outline: none;
  background: transparent;
  font-size: 15px;
  color: #55433B;
}
.clear {
  border: 1px solid #ffd7b0;
  background: #fff;
  border-radius: 10px;
  height: 32px;
  width: 32px;
  cursor: pointer;
  color: #55433B;
}
.submit {
  padding: 0 14px;
  height: 34px;
  background: #ffb078;
  border-radius: 10px;
  border: none;
  font-weight: 600;
  cursor: pointer;
  color: #222;
}
.submit:disabled { opacity: .6; cursor: not-allowed; }

@media (prefers-color-scheme: dark) {
  .bar { background: #2a2220; border-color: #3b2f2b; }
  input { color: #f1e7de; }
  .clear { background: #2f2724; border-color: #3b2f2b; color: #f1e7de; }
  .submit { background: #ff9a5c; color: #1b1614; }
}
</style>
<!-- 
<template>
<SearchBar 
  v-model="keyword"
  @search="searchNow"
  placeholder="제목 또는 내용 검색..."
/>

<p>검색어: {{ keyword }}</p>
</template>


<script>
import SearchBar from '@/components/SearchBar.vue'

export default {
  components: { SearchBar },
  data() {
    return {
      keyword: '',
    }
  },
  methods: {
    searchNow(q) {
      console.log("검색 실행:", q)
      // API 호출 가능
    }
  }
}
</script>

-->