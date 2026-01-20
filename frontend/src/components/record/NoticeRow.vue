<template>
  <div class="row">
    <div class="card-wrap" :class="{ open: openState }">
      <!-- 헤더 -->
      <button class="row-head" type="button" @click="toggle">
        <div class="left">
          <span class="dot-icon" aria-hidden="true"></span>
          <span class="pill" :class="pillClass">{{ badge }}</span>
          <span class="title">{{ item.noticeTitle || item.title }}</span>
        </div>
        <div class="right">
          <time class="date">{{ dateLabel }}</time>
          <span class="chev" :class="{ open: openState }"></span>
        </div>
      </button>

      <!-- 바디 -->
      <transition name="accordion">
        <section v-show="openState" class="card-body">
          <div class="divider" aria-hidden="true"></div>

          <div class="content-box">
            <div class="content" v-html="item.noticeContent || item.content"></div>
          </div>

          <!-- ✅ 지금은 모든 사용자에게 노출 -->
          <!-- 권한 적용하려면: v-if="canWrite" 로 변경 -->
          <div v-if="role === 'ROLE_ADMIN'" class="action-bar">
            <button class="btn ghost" type="button" @click.stop="onEdit">✎ 수정</button>
            <button class="btn danger" type="button" @click.stop="onDelete">🗑 삭제</button>
          </div>

          <div class="sub-divider" aria-hidden="true"></div>

          <NoticeComments
            v-if="noticeIdNum"
            class="comments-box"
            :notice-id="noticeIdNum"
          />
        </section>
      </transition>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watchEffect } from 'vue'
import { useRouter } from 'vue-router'
import NoticeComments from './NoticeComments.vue'
import { softDeleteNotice, hardDeleteNotice } from '../api/notice'
import { useUserStore } from '@/stores/useUserInfo'

const userStore = useUserStore()
const role = userStore.auth[0];

const props = defineProps({
  item: { type: Object, required: true },
  defaultOpen: { type: Boolean, default: false },
  badge: { type: String, default: '공지' },
})
const emit = defineEmits(['deleted', 'updated'])
const router = useRouter()

/* ──────────────────────────────────────────────
   권한 체크
   - 현재는 모두 보이도록 true 반환
   - 나중에 관리자만 보이게 하려면 주석 해제 후
     아래 canWrite를 템플릿의 v-if에 연결하세요.
────────────────────────────────────────────── */
// const canWrite = computed(() => {
//   const role = (localStorage.getItem('role') || '').toUpperCase()
//   const isAdmin = (localStorage.getItem('isAdmin') || '').toLowerCase()
//   return role === 'ADMIN' || isAdmin === 'true'
// })
const canWrite = computed(() => true) // ← 임시: 모두 노출

/* 아코디언 */
const openState = ref(props.defaultOpen)
function toggle() { openState.value = !openState.value }
watchEffect(() => { if (props.defaultOpen) openState.value = true })

/* 날짜 포맷 */
const dateLabel = computed(() => {
  const iso = props.item.noticeCreateDate || props.item.date || props.item.createDate
  if (!iso) return ''
  const d = new Date(iso)
  const y = d.getFullYear()
  const m = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  return `${y}.${m}.${day}`
})

/* 뱃지 색상 */
const pillClass = computed(() => {
  switch (props.badge) {
    case '고정': return 'pin'
    case '중요': return 'warn'
    default:     return 'info'
  }
})

/* 댓글 로드용 숫자 ID */
const noticeIdNum = computed(() => {
  const raw = props.item.noticeId ?? props.item.id
  const n = Number(raw)
  return Number.isFinite(n) ? n : null
})

/* 액션 */
function onEdit() {
  if (!noticeIdNum.value) return
  router.push({
    name: 'UpdateNotice',
    params: { id: noticeIdNum.value },
    state: { item: props.item }, // 이 state가 매우 중요!
  })
}
async function onDelete() {
  if (!noticeIdNum.value) return
  if (!confirm('이 공지를 영구 삭제하시겠어요? 복구가 불가능합니다.')) return

  try {
    await hardDeleteNotice(noticeIdNum.value)
    emit('deleted', noticeIdNum.value)
  } catch (e) {
    console.error(e)
    alert('삭제에 실패했습니다. 잠시 후 다시 시도해주세요.')
  }
}
</script>

<style scoped>
:root{
  --bg: #f6f1e0;
  --ink:#55433b;
  --green:#88aa82;
  --green10: rgba(136,170,130,.08);
  --border:#88aa82;
  --borderSoft: rgba(136,170,130,.30);
}
.row { width:100%; }
.card-wrap{
  border:1px solid rgba(136,170,130,.22);
  background:#f5f2e7;
  border-radius:10px;
  transition: background .15s ease, border-color .15s ease, box-shadow .15s ease;
}
.card-wrap + .card-wrap{ margin-top:12px; }
.card-wrap.open{
  border-color: var(--border);
  background: rgba(136,170,130,.15);
  box-shadow: 0 1px 0 rgba(0,0,0,.02) inset;
}
.row-head{
  width:100%; background:transparent; border:0; border-radius:10px;
  padding:18px 20px; display:flex; align-items:center; justify-content:space-between;
  cursor:pointer; color:var(--ink);
}
.left{ display:flex; align-items:center; gap:10px; min-width:0; }
.title{
  font-size:20px; line-height:28px; font-weight:600; letter-spacing:.2px;
  white-space:nowrap; overflow:hidden; text-overflow:ellipsis;
}
.right{ display:flex; align-items:center; gap:12px; }
.date{ font-size:12px; opacity:.6; }

.dot-icon{
  width:14px; height:14px; border-radius:50%; border:1.6px solid var(--green);
  position:relative; flex:0 0 auto;
}
.dot-icon::after{
  content:""; position:absolute; width:6px; height:6px; border-radius:50%;
  background:var(--green); top:50%; left:50%; transform:translate(-50%,-50%);
}
.pill{
  border-radius:999px; padding:2px 8px; font-size:12px; line-height:16px;
  border:1px solid var(--green); color:var(--green); background:transparent;
}
.pill.pin{ background:var(--green); color:#000000; }
.pill.warn{ background:rgba(136,170,130,.18); color:#2f4a2a; }

.chev{
  width:10px; height:10px; opacity:.6;
  border-right:2px solid var(--ink); border-bottom:2px solid var(--ink);
  transform: rotate(-45deg); transition: transform .18s ease;
}
.chev.open{ transform: rotate(45deg); }

.card-body{ padding: 0 20px 20px 20px; overflow: visible; }
.divider{ height:1px; background:var(--borderSoft); margin: 10px 0 16px; }

.content-box{
  border:1px solid rgba(122,129,120,.22);
  background: rgba(237,240,237,.6);
  border-radius:10px;
  padding:14px;
}
.content{ color:var(--ink); font-size:14.4px; line-height:24.48px; }

.action-bar{
  display:flex; gap:8px; margin-top:10px;
}
.btn{
  padding:8px 12px; border-radius:8px; border:1px solid var(--borderSoft);
  background:#f5f7f3; cursor:pointer; font-weight:600;
}
.btn.ghost{ background:#eef4ea; }
.btn.danger{ background:#f7ecec; border-color:rgba(200,0,0,.25); }
.btn:hover{ filter:brightness(.98); }

.sub-divider{ height:1px; background: var(--borderSoft); margin: 12px 0; }
.comments-box{ margin-top:8px; }

.accordion-enter-from, .accordion-leave-to { max-height:0; opacity:0; }
.accordion-enter-to, .accordion-leave-from { max-height:900px; opacity:1; }
.accordion-enter-active, .accordion-leave-active {
  overflow:hidden; transition: max-height .24s ease, opacity .24s ease;
}
</style>
