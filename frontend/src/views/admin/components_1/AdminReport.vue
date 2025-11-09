<template>
    <div class="page-container">
      <div class="report-box">
        <h1 class="title">신고 관리</h1>
  
        <!-- 신고 목록 -->
        <div class="table-wrapper">
          <table class="report-table">
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
                <td>{{ report.category_id }}</td>
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
  
        <!-- 선택된 신고 상세 -->
        <div v-if="selectedReport" class="detail-box">
          <h2>신고 상세 정보</h2>
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
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  const reports = ref([])
  const selectedReport = ref(null)
  const page = ref(1)
  const size = ref(10)
  const hasNextPage = ref(true)
  
  // 전체 신고 목록 조회
  const fetchReports = async () => {
    try {
      const res = await axios.get(`/api/report/all?page=${page.value}&size=${size.value}`)
      reports.value = res.data
      hasNextPage.value = res.data.length === size.value
    } catch (err) {
      console.error('신고 목록 불러오기 오류:', err)
    }
  }
  
  // 특정 신고 상세 조회
  const selectReport = async (id) => {
    try {
      const res = await axios.get(`/api/report/${id}`)
      selectedReport.value = res.data
    } catch (err) {
      console.error('신고 상세 조회 오류:', err)
    }
  }
  
  // 상태 변경 (U → Y/N)
  const updateState = async (state) => {
    if (!selectedReport.value) return
    try {
      await axios.patch(`/api/report/${selectedReport.value.id}/state?state=${state}`)
      selectedReport.value.state = state
      alert(`신고 상태가 ${state === 'Y' ? '승인' : '반려'}되었습니다.`)
      fetchReports() // 목록 갱신
    } catch (err) {
      console.error('신고 상태 변경 오류:', err)
    }
  }
  
  // 상태 텍스트 변환
  const getStateText = (state) => {
    switch (state) {
      case 'Y': return '승인'
      case 'N': return '반려'
      case 'U': return '대기'
      default: return '-'
    }
  }
  
  // 상태별 스타일 클래스
  const getStatusClass = (state) => {
    switch (state) {
      case 'Y': return 'status-approved'
      case 'N': return 'status-rejected'
      case 'U': return 'status-waiting'
      default: return ''
    }
  }
  
  // 날짜 포맷
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
  
  // 페이지 이동
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
  .page-container {
    background-color: #f9fafb;
    min-height: 100vh;
    padding: 40px;
  }
  
  .report-box {
    max-width: 1100px;
    margin: 0 auto;
    background: white;
    border-radius: 16px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
    padding: 30px;
  }
  
  .title {
    font-size: 1.6rem;
    font-weight: bold;
    color: #1d4ed8;
    margin-bottom: 20px;
  }
  
  /* 테이블 */
  .table-wrapper {
    overflow-x: auto;
  }
  .report-table {
    width: 100%;
    border-collapse: collapse;
    border: 1px solid #ddd;
  }
  .report-table th, .report-table td {
    border: 1px solid #ddd;
    padding: 10px 12px;
    text-align: center;
    font-size: 0.95rem;
  }
  .report-table thead {
    background-color: #e0f2fe;
    color: #374151;
  }
  .report-table tr:hover {
    background-color: #f0f9ff;
    cursor: pointer;
  }
  .report-table tr.active {
    background-color: #dbeafe;
  }
  
  /* 상태 표시 */
  .status-approved {
    background: #22c55e;
    color: white;
    padding: 4px 10px;
    border-radius: 12px;
    font-size: 0.8rem;
  }
  .status-rejected {
    background: #ef4444;
    color: white;
    padding: 4px 10px;
    border-radius: 12px;
    font-size: 0.8rem;
  }
  .status-waiting {
    background: #f59e0b;
    color: white;
    padding: 4px 10px;
    border-radius: 12px;
    font-size: 0.8rem;
  }
  
  /* 페이지네이션 */
  .pagination {
    display: flex;
    justify-content: center;
    gap: 12px;
    margin: 20px 0;
  }
  .pagination button {
    background-color: #e5e7eb;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    cursor: pointer;
  }
  .pagination button:hover {
    background-color: #d1d5db;
  }
  .pagination button:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
  
  /* 상세 정보 */
  .detail-box {
    margin-top: 30px;
    border-top: 2px solid #93c5fd;
    padding-top: 20px;
  }
  .detail-box h2 {
    font-size: 1.2rem;
    color: #1d4ed8;
    margin-bottom: 15px;
  }
  .detail-row {
    margin-bottom: 8px;
    font-size: 0.95rem;
  }
  .content-box {
    border: 1px solid #ddd;
    background-color: #f9fafb;
    padding: 10px;
    border-radius: 8px;
    margin-top: 5px;
    white-space: pre-wrap;
  }
  
  /* 버튼 */
  .button-group {
    margin-top: 15px;
    display: flex;
    gap: 10px;
  }
  .approve-btn {
    background-color: #22c55e;
    color: white;
    border: none;
    padding: 8px 14px;
    border-radius: 6px;
    cursor: pointer;
  }
  .reject-btn {
    background-color: #ef4444;
    color: white;
    border: none;
    padding: 8px 14px;
    border-radius: 6px;
    cursor: pointer;
  }
  .approve-btn:hover { background-color: #16a34a; }
  .reject-btn:hover { background-color: #dc2626; }
  
  .info-text {
    margin-top: 15px;
    font-size: 0.9rem;
    color: #6b7280;
  }
  
  .no-data {
    text-align: center;
    color: #6b7280;
    padding: 20px;
  }
  </style>
  