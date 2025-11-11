<template>
  <div class="page-wrap">
    <!-- 헤더 -->
    <button class="back" @click="onBack">
      ← 돌아가기
    </button>
    <h1 class="title">마이페이지</h1>
    <p class="subtitle">당신의 성장 여정을 돌아봅니다</p>

    <!-- Tabs 상태는 부모가 가짐 -->
    <MyPageTabs v-model="tab" />

    <!-- 탭별 컨텐츠: 가진 코드 섹션들을 그대로 옮겨오면 됨 -->
    <section v-if="tab === 'profile'">
      <!-- 개인정보 수정 섹션 -->
      <!-- (너가 이미 만든 입력/버튼 UI 붙여넣기) -->
      <div class="card">
        <h2>개인정보</h2>
        <!-- 폼… -->
      </div>
    </section>

    <section v-else-if="tab === 'oops'">
      <!-- Oops 기록 리스트 -->
    </section>

    <section v-else-if="tab === 'ooh'">
      <!-- Ooh 기록 리스트 -->
    </section>

    <section v-else-if="tab === 'graph'">
      <!-- 성장 그래프(차트 라이브러리 안 쓰면 캔버스/테이블 등으로 대체) -->
    </section>

    <section v-else-if="tab === 'recommended'">
      <!-- 추천게시글 -->
    </section>

    <section v-else-if="tab === 'bookmarks'">
      <!-- 북마크기록 -->
    </section>

    <section v-else-if="tab === 'following'">
      <!-- 팔로우기록 -->
    </section>

    <section v-else-if="tab === 'settings'">
      <!-- 설정 -->
    </section>
  </div>
</template>

<script>
import MyPageTabs from '../mypage/MyPageTabs.vue';

export default {
  name: 'MyPage',
  components: { MyPageTabs },
  props: {
    // 필요하면 onBack은 emit으로 바꿔도 됨
  },
  data() {
    return {
      tab: 'profile',   // 활성 탭
    };
  },
  methods: {
    onBack() {
      // 라우터 사용 시: this.$router.back()
      history.back();
    }
  }
};
</script>

<style scoped>
.page-wrap {
  max-width: 1040px;
  margin: 0 auto;
  padding: 32px 24px;
}
.back {
  border: none;
  background: transparent;
  color: #55433B;
  cursor: pointer;
  margin-bottom: 16px;
}
.title {
  font-family: 'EB Garamond', serif;
  font-size: 40px;
  color: #55433B;
  letter-spacing: .02em;
  margin: 0 0 8px;
}
.subtitle {
  color: #55433B;
  opacity: .8;
  margin-bottom: 28px;
}
.card {
  background: rgba(246,241,224,0.5);
  border: 1px solid rgba(136,170,130,0.3);
  border-radius: 12px;
  padding: 24px;
}
</style>
