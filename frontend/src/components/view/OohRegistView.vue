<template>
  <main class="wrap">
    <h1 class="title">Ooh 기록 작성</h1>

    <!-- 공개/비공개 -->
    <section class="field">
      <label class="label">공개 설정</label>
      <div class="row">
        <label class="radio-css">
          <input type="radio" value="N" v-model="form.oohIsPrivate" />공개
        </label>
        <label class="radio-css">
          <input type="radio" value="Y" v-model="form.oohIsPrivate" />비공개
        </label>
      </div>
    </section>

    <!-- 제목 -->
    <section class="field">
      <label class="label" for="title">제목</label>
      <input id="title" class="input" v-model.trim="form.oohTitle" placeholder="제목을 입력하세요" />
    </section>

    <!-- 태그 (ooh용, 최대 3개) -->
    <section class="field">
      <label class="label">태그 (최대 3개)</label>
      <div class="tags">
        <button
          v-for="t in tagOptions"
          :key="t.id"
          type="button"
          class="tag"
          :class="{ active: selectedTagIds.includes(Number(t.id)) }"
          @click="toggleTag(t.id)"
        >
          #{{ t.name }}
        </button>
      </div>
      <p class="hint">선택: {{ selectedTagIds.length }} / 3</p>
    </section>

    <!--내용: 입력은 부모에서만 -->
    <section class="field">
      <label class="label" for="content">내용</label>
      <textarea
        id="content"
        class="textarea"
        rows="8"
        v-model.trim="form.oohContent"
        placeholder="오늘의 빛남을 적어보세요…"
      />
      <div class="count">{{ (form.oohContent || '').length }}자</div>
    </section>

    <!-- AiAnalyze는 부모의 내용을 props로 받아 분석만 담당 -->
    <section class="field">
      <AiAnalyze :text="form.oohContent" @aiResult="onAIResult" />
    </section>

    <div class="actions">
      <button class="btn ghost" @click="onCancel">취소</button>
      <button class="btn primary" :disabled="busy" @click="submit">
        {{ busy ? '저장 중…' : '등록' }}
      </button>
    </div>
  </main>
</template>

<script setup>
import axios from 'axios'
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/useUserInfo'
import { useToastStore } from "@/stores/useToast";

import { createOoh } from '../api/ooh'
import { fetchOohTagOptions } from '../api/tag'
import AiAnalyze from '../common/AiAnalyze.vue';


const router = useRouter()
const userStore = useUserStore()
const toastStore = useToastStore()
const token = userStore.token;  

// 사용자 식별
const currentUserId = computed(() => Number(userStore.id || 0))

/* 폼 데이터: 내용/제목/공개여부/AI답변 */
const form = reactive({
  oohIsPrivate: 'N',
  oohTitle: '',
  oohContent: '',
  aiAnswer: '',          // 🔸 AiAnalyze 결과를 저장
})

/* 상태 */
const tagOptions = ref([])
const selectedTagIds = ref([])
const aiEmoTagNames = ref([])      // 🔸 AiAnalyze가 추천한 감정 태그 이름들
const busy = ref(false)

// 태그 로드
onMounted(async () => {
  try {
    tagOptions.value = await fetchOohTagOptions()
  } catch {
    toastStore.showToast("태그를 불러오지 못했습니다.")
  }
})

// 태그 옵션 로드
onMounted(async () => {
  try {
    tagOptions.value = await fetchOohTagOptions()
  } catch {
    toastStore.showToast("태그를 불러오지 못했습니다.")
  }
})

// 버튼 토글
function toggleTag(tagId) {
  const n = Number(tagId)
  const i = selectedTagIds.value.indexOf(n)
  if 
  (i >= 0) selectedTagIds.value.splice(i, 1)
  else if 
  (selectedTagIds.value.length < 3) selectedTagIds.value.push(n)
  else 
  toastStore.showToast('태그는 최대 3개까지만 선택 가능합니다.')
}

// 유효성
function validate() {
  if 
  (!form.oohTitle) 
  return toastStore.showToast('제목은 필수입니다.')
  if 
  (!form.oohContent) 
  return toastStore.showToast('내용은 필수입니다.')
  if 
  (selectedTagIds.value.length > 3) 
  return toastStore.showToast('태그는 최대 3개까지만 선택 가능합니다.')
  if 
  (form.oohIsPrivate !== 'Y' && form.oohIsPrivate !== 'N') 
  return toastStore.showToast('공개 설정 값이 올바르지 않습니다.')
  return ''
}

/* ✅ AiAnalyze 결과 수신: 부모의 form에 반영 */
function onAIResult({ feedback, relatedTags }){
  form.aiAnswer = feedback || ''                         // 🔸 DB 필드 oohAIAnswer로 저장됨
  aiEmoTagNames.value = Array.isArray(relatedTags) ? relatedTags : []
  toastStore.showToast('AI 피드백이 반영되었습니다.')
}

// (A) 부모에서 직접 AI 호출(자식 컴포넌트 호출 없이도 동작)
async function requestAI() {
  try {
    const { data } = await axios.post('/boot/ai/analyze', { content: form.oohContent })
    form.aiAnswer = data?.feedback || ''
    aiEmoTagNames.value = Array.isArray(data?.relatedTags) ? data.relatedTags : []
  } catch (e) {
    console.error('AI 분석 오류:', e)
    // 실패해도 흐름 막지 않음 (aiAnswer 빈값으로 저장 가능)
    form.aiAnswer = ''
    aiEmoTagNames.value = []
  }
}

// 감정 태그 이름 → id 매핑
function emotionNamesToIds(names) {
  if (!Array.isArray(names) || !names.length) return []
  const isEmo = (t) => String(t || '').toLowerCase() === 'emotion' || String(t || '').toLowerCase() === 'emo'
  // 옵션에서 감정 카테고리만 대상으로 이름 매칭
  const emoMap = new Map(
    tagOptions.value
      .filter(o => isEmo(o.tagType || o.tag_type))
      .map(o => [String(o.name).trim(), Number(o.id)])
  )
  return names
    .map(nm => emoMap.get(String(nm).trim()))
    .filter(v => typeof v === 'number' && !Number.isNaN(v))
    .slice(0, 3) // 안전하게 3개 제한
}



/* 저장 */
async function submit(){
  const msg = validate()
  if (msg) return toastStore.showToast(msg)
  if (!currentUserId.value) return toastStore.showToast('로그인이 필요합니다.')

  busy.value = true
  try {
    // ✅ 백엔드 계약에 맞춰 payload 구성
    const saved = await createOoh({
      oohUserId: currentUserId.value,
      oohTitle: form.oohTitle,
      oohContent: form.oohContent,
      oohIsPrivate: form.oohIsPrivate,
      tagIds: selectedTagIds.value.map(Number),  // 일반 태그 id[]
      emoTagIds: aiEmoTagNames.value,            // 감정 태그 "이름" 배열 (백엔드가 id로 매핑)
      oohAIAnswer: form.aiAnswer || null         // ✅ AI 피드백 본문
    }, token)

    toastStore.showToast('등록 완료!')
    if (saved?.oohId) router.push({ path: `/ooh/${saved.oohId}/detail` })
    else router.push({ name: 'Ooh' })
  } catch (e) {
    console.error(e)
    toastStore.showToast(e?.response?.data?.message || e?.response?.data?.error || '등록에 실패했습니다.')
  } finally {
    busy.value = false
  }
}

function onCancel(){ router.back() }
</script>

<style scoped>
:root { --ink:#55433B; --green:#88AA82; }
:root { --light-green: #D8F1D5; }

.wrap{ 
  max-width:720px; 
  margin:0 auto; 
  padding:0px }
.title{ font-size:20px; color:var(--ink); margin-bottom:14px }
.field{ margin-bottom:16px }
.label{ display:block; margin-bottom:8px; color:var(--ink); font-weight:600 }
.row{ display:flex; gap:10px; align-items:center }
.radio{ display:flex; gap:6px; align-items:center; color:var(--ink) }
.input,.textarea{
  width:100%; border:1px solid rgba(136,170,130,.45); border-radius:10px; padding:10px 12px; color:var(--ink);
}
.input:focus,.textarea:focus{ box-shadow:0 0 0 3px rgba(136,170,130,.18) }
.textarea{ resize:vertical }
.count{ text-align:right; color:var(--ink); opacity:.55; font-size:12px; margin-top:6px }
.tags{ display:flex; flex-wrap:wrap; gap:8px }
.tag{
  border:1px solid var(--green); padding:6px 12px; border-radius:9999px;
  background:#fff; color:var(--ink); font-size:13px; cursor:pointer;
}
.tag.active{ background:rgba(136,170,130,.15) }

.btn{
  border:1px solid var(--green);
  padding:8px 12px; border-radius:10px;
  color:#fff; background:var(--green);
}
.btn:hover { background:#77a373; }
.btn.ghost{
  background:#fff; color:var(--ink);
  border-color:rgba(136,170,130,.45)
}
.btn.primary{
  background: #7a8d56;
  color:rgb(255, 255, 255);
  border-color: var(--light-green);
}
.btn:disabled{ opacity:.4; cursor:not-allowed }
.actions{ 
  display:flex; 
  gap:8px; 
  justify-content:flex-end; 
  margin-top:18px; 
}
.hint{ font-size:12px; color:var(--ink); opacity:.6; margin-top:6px }
.error{ color:#b00020; margin-top:8px }

/* 성별 라디오 버튼 */
.radio-css {
  display: flex;
  justify-content: flex-start;
  gap: 10px;
  color: #55433b;
  margin-top: 4px;
}

/* 라벨 전체 클릭 가능 */
.radio-css label {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  font-size: 0.95rem;
  position: relative;
  user-select: none;
}

/* 기본 라디오 숨기기 */
.radio-css input[type="radio"] {
  appearance: none;
  -webkit-appearance: none;
  width: 18px;
  height: 18px;
  border: 2px solid rgba(136, 170, 130, 0.5);
  border-radius: 50%;
  background-color: rgba(246, 241, 224, 0.9);
  cursor: pointer;
  position: relative;
  transition: all 0.3s ease;
}

/* 마우스 오버 */
.radio-css input[type="radio"]:hover {
  border-color: #88aa82;
  box-shadow: 0 0 5px rgba(136, 170, 130, 0.3);
}

/* 선택 시 */
.radio-css input[type="radio"]:checked {
  background-color: #88aa82;
  border-color: #88aa82;
  box-shadow: 0 0 6px rgba(136, 170, 130, 0.4);
}

/* 선택된 원 안의 점 (checked 표시) */
.radio-css input[type="radio"]:checked::after {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background-color: white;
  transform: translate(-50%, -50%);
}

</style>
