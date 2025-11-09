<template>
    <div class="page-container">
      <div class="member-box">
        <h1 class="title">회원 관리</h1>
  
        <!-- 테이블 -->
        <div class="table-wrapper">
          <table class="member-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>아이디</th>
                <th>이름</th>
                <th>이메일</th>
                <th>성별</th>
                <th>생년월일</th>
                <th>가입일</th>
                <th>상태</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="member in members" :key="member.id">
                <td>{{ member.id }}</td>
                <td>{{ member.member_id }}</td>
                <td>{{ member.name }}</td>
                <td>{{ member.email }}</td>
  
                <!-- 성별 -->
                <td>{{ member.gender?.toLowerCase() === 'm' ? '남성' : '여성' }}</td>
  
                <!-- 날짜 -->
                <td>{{ formatDate(member.birth) }}</td>
                <td>{{ formatDate(member.sign_up_date) }}</td>
  
                <!-- 상태 -->
                <td>
                  <span
                    :class="member.user_state?.toUpperCase() === 'A' ? 'status-active' : 'status-inactive'"
                  >
                    {{ member.user_state?.toUpperCase() === 'A' ? '활성' : '비활성' }}
                  </span>
                </td>
              </tr>
  
              <tr v-if="members.length === 0">
                <td colspan="8" class="no-data">조회된 회원이 없습니다.</td>
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
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  const members = ref([])
  const page = ref(1)
  const size = ref(10)
  const hasNextPage = ref(true)
  
  const fetchMembers = async () => {
    try {
      const res = await axios.get(`/api/admin/member?page=${page.value}&size=${size.value}`)
      members.value = res.data
      hasNextPage.value = res.data.length === size.value
    } catch (err) {
      console.error('회원 데이터를 불러오는 중 오류 발생:', err)
    }
  }
  
  const formatDate = (dateStr) => {
    if (!dateStr) return '-'
    try {
      const date = new Date(dateStr)
      return new Intl.DateTimeFormat('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
      }).format(date)
    } catch (e) {
      return '-'
    }
  }
  
  const nextPage = () => {
    if (hasNextPage.value) {
      page.value++
      fetchMembers()
    }
  }
  
  const prevPage = () => {
    if (page.value > 1) {
      page.value--
      fetchMembers()
    }
  }
  
  onMounted(fetchMembers)
  </script>
  
  <style scoped>
  /* 전체 페이지 레이아웃 */
  .page-container {
    background-color: #f9fafb;
    min-height: 100vh;
    padding: 40px;
  }
  
  /* 메인 박스 */
  .member-box {
    max-width: 1100px;
    margin: 0 auto;
    background: white;
    border-radius: 16px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
    padding: 30px;
  }
  
  /* 제목 */
  .title {
    font-size: 1.6rem;
    font-weight: bold;
    color: #1d4ed8;
    margin-bottom: 20px;
    text-align: left;
  }
  
  /* 테이블 기본 */
  .table-wrapper {
    overflow-x: auto;
  }
  
  .member-table {
    width: 100%;
    border-collapse: collapse;
    border: 1px solid #ddd;
  }
  
  .member-table thead {
    background-color: #e0f2fe;
    color: #374151;
  }
  
  .member-table th,
  .member-table td {
    border: 1px solid #ddd;
    padding: 10px 12px;
    text-align: center;
    font-size: 0.95rem;
  }
  
  .member-table tr:hover {
    background-color: #f0f9ff;
  }
  
  /* 상태 스타일 */
  .status-active {
    display: inline-block;
    background-color: #22c55e;
    color: white;
    padding: 4px 10px;
    border-radius: 12px;
    font-size: 0.8rem;
  }
  
  .status-inactive {
    display: inline-block;
    background-color: #ef4444;
    color: white;
    padding: 4px 10px;
    border-radius: 12px;
    font-size: 0.8rem;
  }
  
  /* 데이터 없음 */
  .no-data {
    text-align: center;
    color: #6b7280;
    padding: 20px;
  }
  
  /* 페이지네이션 */
  .pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 12px;
    margin-top: 25px;
  }
  
  .pagination button {
    background-color: #e5e7eb;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    font-size: 0.9rem;
    cursor: pointer;
    transition: background-color 0.2s;
  }
  
  .pagination button:hover {
    background-color: #d1d5db;
  }
  
  .pagination button:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
  
  .pagination span {
    color: #374151;
    font-weight: 500;
  }
  </style>
  