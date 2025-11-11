<template>
  <div class="notice-view">
    <!-- 상단 -->
    <div class="top">
      <button class="back" type="button" @click="router.back()">
        <span class="arrow"></span><span class="back-text">돌아가기</span>
      </button>
      <h1 class="title">공지사항</h1>
      <p class="subtitle">Oops_Log의 소식과 안내를 전해드립니다</p>

      <!-- ✅ 작성 버튼 (우측 상단) -->
      <button
        v-if="canWrite"
        type="button"
        class="write-btn"
        @click="goWrite"
        aria-label="공지사항 작성"
        >
        + 공지 작성
      </button>

    </div>

    <!-- 고정 공지(기본 펼침 + 카드스타일) -->
    <section class="list">
      <NoticeRow
        v-if="pinned"
        :item="pinned"
        badge="고정"
        :defaultOpen="true"
        @deleted="onRowDeleted"   
      />

      <!-- 목록 (드롭다운, 펼치면 카드 모양) -->
      <NoticeRow
        v-for="n in items"
        :key="n.noticeId || n.id"
        :item="n"
        :badge="badgeOf(n)"
      />

      <div v-if="loading" class="skeleton"></div>
      <div v-if="error" class="error">
        {{ error }} <button class="retry" @click="loadNext">다시 시도</button>
      </div>
      <div ref="sentinel" class="sentinel"></div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import NoticeRow from '../record/NoticeRow.vue'
import { fetchNotices } from '../api/notice'

/* ✅ 임시: 테스트용으로 항상 보이게 */
const canWrite = ref(true)

/* 작성 페이지로 이동 (라우트 이름/경로는 프로젝트에 맞게) */
function goWrite() {
  // 이름 라우팅이 있으면:
  // router.push({ name: 'notice-write' })
  // 경로 라우팅이면:
  router.push('/notice/insertNotice')
}
/* delete */
function onRowDeleted(id) {
  items.value = items.value.filter(n => (n.noticeId ?? n.id) !== id)
}

const router = useRouter()

// 상단 고정 – 서버에 없으니 프론트 상수로
const pinned = {
  noticeId: 'pinned',
  noticeTitle: '새로운 감정 태그를 제안해주세요',
  noticeContent: `
    현재 Oops와 Ooh 기록에는 각각 세 가지 감정 태그가 있습니다. 더 다양한 감정을 표현하고 싶으신가요?<br /><br />
    【 현재 태그 】<br />
    • Oops: 불안, 후회, 피로<br />
    • Ooh: 감사, 만족, 희망<br /><br />
    필요한 감정 태그가 있다면 제안해주세요. 여러분의 의견을 바탕으로 새로운 태그를 추가할 예정입니다. <br />
    추가 및 문의는 ******@******.com으로 문의 부탁드립니다.
  `,
  noticeCreateDate: '2025-11-09T00:00:00',
  noticeIsDeleted: 'N',
  name: '관리자'
}

const items = ref([])
const page = ref(1)
const size = ref(10)
const hasNext = ref(true)
const loading = ref(false)
const error = ref('')
const sentinel = ref(null)
let observer = null

function badgeOf(n) {
  const title = n.noticeTitle || ''
  if (/점검|약관|가이드라인|정책/.test(title)) return '중요'
  return '공지'
}

async function loadNext() {
  if (loading.value || !hasNext.value) return
  loading.value = true
  error.value = ''

  try {
    const { list, hasNextPage } = await fetchNotices({ page: page.value, size: size.value })
    // pinned와 제목 같으면 중복 제거 + 삭제 숨김
    const filtered = list.filter(n => (n.noticeTitle !== pinned.noticeTitle) && (n.noticeIsDeleted !== 'Y'))
    items.value.push(...filtered)
    hasNext.value = hasNextPage
    page.value += 1
  } catch (e) {
    error.value = '공지 불러오기에 실패했어요.'
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await loadNext()
  observer = new IntersectionObserver(async ([entry]) => {
    if (entry.isIntersecting) await loadNext()
  }, { root: null, rootMargin: '400px 0px', threshold: 0 })

  await nextTick()
  if (sentinel.value) observer.observe(sentinel.value)
})

onBeforeUnmount(() => {
  if (observer && sentinel.value) observer.unobserve(sentinel.value)
})
</script>

<style scoped>
:root{
  --bg:#f6f1e0; --ink:#55433b; --soft:#eae4cf; --borderSoft: rgba(136,170,130,.25);
}
.notice-view{
  width: 896px;
  margin: 0 auto;
  padding-top: 88px;
  color: var(--ink);
  background: var(--bg);
}

.top{ margin-bottom: 18px; }

.back{
  display:flex; align-items:center; gap:8px; margin-bottom:10px;
  background:none; border:0; cursor:pointer; color:var(--ink);
}
.arrow{ width:8px; height:8px; border-left:2px solid var(--ink); border-bottom:2px solid var(--ink); transform:rotate(45deg); }
.back-text{ font-size:16px; line-height:24px; }

.title{
  font-size:44px; line-height:62px; letter-spacing:.6px; font-weight:700;
}
.subtitle{ margin-top:4px; opacity:.7; font-size:16px; letter-spacing:.1px; }

/* 리스트 간격 */
.list{ display:flex; flex-direction:column; gap:14px; }

.skeleton{
  height:120px; border:1px solid var(--borderSoft); border-radius:10px;
  background:linear-gradient(90deg, rgba(136,170,130,.08), rgba(136,170,130,.12), rgba(136,170,130,.08));
  background-size:200% 100%; animation:shimmer 1.2s infinite linear;
}
@keyframes shimmer{ 0%{background-position:200% 0;} 100%{background-position:-200% 0;} }
.error{ padding:12px; border:1px solid rgba(200,0,0,.25); border-radius:8px; background:rgba(200,0,0,.05); }
.retry{ margin-left:8px; background:none; border:0; text-decoration:underline; cursor:pointer; color:var(--ink); }
.sentinel{ height:1px; }

.top{ margin-bottom: 18px; position: relative; } /* ← position 추가 */

.write-btn{
  position: absolute;
  right: 0;
  top: 18px;
  padding: 10px 14px;
  border-radius: 8px;
  border: 1px solid var(--borderSoft);
  background: #f0f4ec;
  color: var(--ink);
  font-weight: 600;
  letter-spacing: .2px;
  cursor: pointer;
  box-shadow: 0 1px 0 rgba(0,0,0,.02);
}
.write-btn:hover{ filter: brightness(0.98); }
.write-btn:active{ transform: translateY(1px); }
</style>
