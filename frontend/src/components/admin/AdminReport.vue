<template>
  <main class="admin-canvas">
    <!-- 배경 -->
    <div class="ink-bg" aria-hidden="true">
      <svg class="ink ink-a" viewBox="0 0 200 200">
        <circle cx="100" cy="100" r="80" fill="#88AA82" opacity="0.25" />
      </svg>
      <svg class="ink ink-b" viewBox="0 0 200 200">
        <circle cx="100" cy="100" r="60" fill="#55433B" opacity="0.15" />
      </svg>
    </div>
    <div class="paper-noise" aria-hidden="true"></div>

    <!-- 메인 -->
    <section class="admin-hero">
      <div class="title-block">
        <h1 class="admin-title">신고 관리</h1>
        <p class="admin-subtitle">Oops Log 관리자 페이지</p>
      </div>

      <!-- 테이블 -->
      <div class="table-wrapper">
        <table class="member-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>신고일</th>
              <th>상태</th>
              <th>카테고리ID</th>
              <th>신고자ID</th>
              <th>OopsID</th>
              <th>OohID</th>
              <th>CommentID</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="report in reports"
              :key="report.id"
              @click="selectReport(report.id)"
              :class="{ active: selectedReport && selectedReport.id === report.id }"
            >
              <td>{{ report.id }}</td>
              <td>{{ formatDate(report.report_date) }}</td>
              <td>
                <span :class="getStatusClass(report.state)">
                  {{ getStateText(report.state) }}
                </span>
              </td>
              <td>{{ report.category_name }}</td>
              <td>{{ report.user_id }}</td>
              <td>{{ report.oops_id || '-' }}</td>
              <td>{{ report.ooh_id || '-' }}</td>
              <td>{{ report.comment_id || '-' }}</td>
            </tr>

            <tr v-if="reports.length === 0">
              <td colspan="8" class="no-data">신고 데이터가 없습니다.</td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- 페이지네이션 -->
      <div class="pagination">
        <button @click="prevPage" :disabled="page === 1">이전</button>
        <span>페이지 {{ page }}</span>
        <button @click="nextPage" :disabled="!hasNextPage">다음</button>
      </div>

      <!-- 상세보기 -->
      <div v-if="selectedReport" class="detail-box">
        <h2 class="detail-title">신고 상세 정보</h2>
        <div class="detail-row"><strong>ID:</strong> {{ selectedReport.id }}</div>
        <div class="detail-row"><strong>신고자 ID:</strong> {{ selectedReport.user_id }}</div>
        <div class="detail-row"><strong>대상 ID:</strong> {{ selectedReport.target_id }}</div>
        <div class="detail-row"><strong>신고 상태:</strong> {{ getStateText(selectedReport.state) }}</div>
        <div class="detail-row"><strong>대상 내용:</strong></div>
        <p class="content-box">{{ selectedReport.target_content }}</p>

        <div v-if="selectedReport.state === 'U'" class="button-group">
          <button class="approve-btn" @click="updateState('Y')">승인</button>
          <button class="reject-btn" @click="updateState('N')">반려</button>
        </div>

        <div v-else class="info-text">
          이미 "{{ getStateText(selectedReport.state) }}" 처리된 신고입니다.
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const reports = ref([])
const selectedReport = ref(null)
const page = ref(1)
const size = ref(10)
const hasNextPage = ref(true)

const fetchReports = async () => {
  try {
    const res = await axios.get(`/boot/report/all?page=${page.value}&size=${size.value}`)
    reports.value = res.data
    hasNextPage.value = res.data.length === size.value
  } catch (err) {
    console.error('신고 목록 불러오기 오류:', err)
  }
}

const selectReport = async (id) => {
  try {
    const res = await axios.get(`/boot/report/${id}`)
    selectedReport.value = res.data
  } catch (err) {
    console.error('신고 상세 조회 오류:', err)
  }
}

const updateState = async (state) => {
  if (!selectedReport.value) return
  try {
    await axios.patch(`/boot/report/${selectedReport.value.id}/state?state=${state}`)
    selectedReport.value.state = state
    alert(`신고 상태가 ${state === 'Y' ? '승인' : '반려'}되었습니다.`)
    fetchReports()
  } catch (err) {
    console.error('신고 상태 변경 오류:', err)
  }
}

const getStateText = (state) => {
  switch (state) {
    case 'Y': return '승인'
    case 'N': return '반려'
    case 'U': return '대기'
    default: return '-'
  }
}

const getStatusClass = (state) => {
  switch (state) {
    case 'Y': return 'status-approved'
    case 'N': return 'status-rejected'
    case 'U': return 'status-waiting'
    default: return ''
  }
}

const formatDate = (dateStr) => {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return new Intl.DateTimeFormat('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  }).format(date)
}

const nextPage = () => {
  if (hasNextPage.value) {
    page.value++
    fetchReports()
  }
}
const prevPage = () => {
  if (page.value > 1) {
    page.value--
    fetchReports()
  }
}

onMounted(fetchReports)
</script>

<style scoped>
/* 🩶 감성 관리자 스타일 공통 */
.admin-canvas {
  position: relative;
  min-height: 100vh;
  background: var(--paper-bg, #fefdfb);
  color: var(--ink, #3b302b);
  overflow: hidden;
  display: grid;
  place-items: center;
  padding: 40px 16px;
}

.admin-hero {
  position: relative;
  z-index: 2;
  max-width: 1100px;
  width: 100%;
  background: rgba(255, 255, 255, 0.65);
  backdrop-filter: blur(6px);
  border-radius: 16px;
  box-shadow: 0 4px 18px rgba(0, 0, 0, 0.08);
  padding: 40px 32px 60px;
}

.title-block {
  text-align: center;
  margin-bottom: 28px;
}
.admin-title {
  font-family: 'EB Garamond', 'Noto Serif KR', serif;
  font-size: 1.9rem;
  color: rgba(85, 67, 59, 0.9);
  margin-bottom: 6px;
}
.admin-subtitle {
  color: rgba(85, 67, 59, 0.6);
  font-size: 0.9rem;
  letter-spacing: 0.04em;
}

/* 테이블 */
.table-wrapper {
  overflow-x: auto;
}
.member-table {
  width: 100%;
  border-collapse: collapse;
  background: rgba(255, 255, 255, 0.6);
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.04);
}
.member-table thead {
  background-color: rgba(136, 170, 130, 0.15);
  color: #3b332d;
}
.member-table th,
.member-table td {
  border: 1px solid rgba(0, 0, 0, 0.05);
  padding: 12px;
  text-align: center;
  font-size: 0.95rem;
}
.member-table tbody tr:hover {
  background-color: rgba(136, 170, 130, 0.06);
  transition: background-color 0.25s ease;
}
.member-table tr.active {
  background-color: rgba(136, 170, 130, 0.1);
}

/* 상태 */
.status-approved {
  background-color: rgba(136, 170, 130, 0.85);
  color: white;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
}
.status-rejected {
  background-color: rgba(199, 96, 77, 0.85);
  color: white;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
}
.status-waiting {
  background-color: rgba(247, 181, 62, 0.85);
  color: white;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
}

/* 상세 */
.detail-box {
  margin-top: 40px;
  border-top: 1px solid rgba(85, 67, 59, 0.2);
  padding-top: 20px;
}
.detail-title {
  font-family: 'EB Garamond', 'Noto Serif KR', serif;
  font-size: 1.3rem;
  color: rgba(85, 67, 59, 0.85);
  margin-bottom: 16px;
}
.detail-row {
  margin-bottom: 8px;
  font-size: 0.95rem;
}
.content-box {
  border: 1px solid rgba(0, 0, 0, 0.1);
  background-color: rgba(255, 255, 255, 0.6);
  padding: 12px;
  border-radius: 8px;
  white-space: pre-wrap;
  color: rgba(60, 60, 60, 0.85);
}

/* 버튼 */
.button-group {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}
.approve-btn,
.reject-btn {
  border: none;
  border-radius: 8px;
  padding: 8px 14px;
  color: white;
  cursor: pointer;
  font-weight: 500;
  transition: transform 0.2s ease, background 0.3s ease;
}
.approve-btn {
  background-color: rgba(136, 170, 130, 0.85);
}
.reject-btn {
  background-color: rgba(199, 96, 77, 0.85);
}
.approve-btn:hover { transform: translateY(-2px); background-color: rgba(136, 170, 130, 1); }
.reject-btn:hover { transform: translateY(-2px); background-color: rgba(199, 96, 77, 1); }

.info-text {
  margin-top: 15px;
  font-size: 0.9rem;
  color: rgba(60, 60, 60, 0.6);
}

/* 페이지네이션 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 14px;
  margin-top: 30px;
}
.pagination button {
  border: 1px solid rgba(85, 67, 59, 0.3);
  background: rgba(255, 255, 255, 0.4);
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.25s ease;
  font-size: 0.9rem;
  color: #3b302b;
}
.pagination button:hover {
  background: rgba(136, 170, 130, 0.15);
  transform: translateY(-1px);
}
.pagination button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* no data */
.no-data {
  text-align: center;
  color: rgba(60, 60, 60, 0.5);
  padding: 20px;
}

/* 배경 잉크 효과 */
.ink-bg {
  position: absolute;
  inset: 0;
  pointer-events: none;
  overflow: hidden;
  opacity: 0.04;
}
.ink {
  position: absolute;
  width: 22rem;
  height: 22rem;
}
.ink-a {
  top: 25%;
  left: 18%;
  animation: breatheA 9s ease-in-out infinite;
}
.ink-b {
  right: 22%;
  bottom: 28%;
  width: 16rem;
  height: 16rem;
  animation: breatheB 11s ease-in-out infinite;
}
.paper-noise {
  position: absolute;
  inset: 0;
  pointer-events: none;
  background-size: 400px 400px;
  opacity: 0.03;
}

/* 애니메이션 */
@keyframes breatheA {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.05); }
}
@keyframes breatheB {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.08); }
}
</style>
