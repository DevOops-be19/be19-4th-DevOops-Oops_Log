<template>
  <div class="tab-page">
    <h2 class="tab-title">성장 그래프</h2>

    <!-- 연도/월 선택 -->
    <div class="filter-box">
      <select v-model="selectedYear" @change="fetchData">
        <option v-for="y in availableYears" :key="y">{{ y }}년</option>
      </select>
      <select v-model="selectedMonth" @change="fetchData">
        <option v-for="m in availableMonths" :key="m">{{ m }}월</option>
      </select>
    </div>

    <!-- 요약 카드 -->
    <div class="stat-cards">
      <div class="card">총 기록 <strong>{{ total }}</strong></div>
      <div class="card">Oops <strong>{{ oops }}</strong></div>
      <div class="card">Ooh <strong>{{ ooh }}</strong></div>
    </div>

    <!-- 일별 그래프 -->
    <div id="dailyChart" class="chart-box"></div>

    <!-- 태그별 그래프 -->
    <h3 class="chart-title">Top Oops Tags</h3>
    <div id="oopsTagChart" class="chart-box"></div>

    <h3 class="chart-title">Top Ooh Tags</h3>
    <div id="oohTagChart" class="chart-box"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ApexCharts from 'apexcharts'
import axios from 'axios'
import { useUserStore } from '@/stores/useUserInfo' // 피니아에서 로그인 유저 가져오기

// 상태
const userStore = useUserStore()
const userId = userStore.user?.id || 20 // 테스트용 기본값 20

const oops = ref(0)
const ooh = ref(0)
const total = ref(0)

const selectedYear = ref(2024)
const selectedMonth = ref(8)
const availableYears = ref([])
const availableMonths = ref([])

const oopsRecords = ref([])
const oohRecords = ref([])
const topOopsTags = ref([])
const topOohTags = ref([])

// 📊 데이터 요청
async function fetchData() {
  try {
    const res = await axios.get(
      `/api/achivement/${userId}/daily`,
      { params: { year: selectedYear.value, month: selectedMonth.value } }
    )

    const data = res.data
    oops.value = data.oopsCount
    ooh.value = data.oohCount
    total.value = data.oopsCount + data.oohCount

    // 연도/월 드롭다운
    const years = new Set([
      ...data.findYearOops.map((d) => d.year),
      ...data.findYearOoh.map((d) => d.year)
    ])
    availableYears.value = [...years]
    availableMonths.value = [
      ...new Set([
        ...data.findYearOops.map((d) => d.month),
        ...data.findYearOoh.map((d) => d.month)
      ])
    ]

    oopsRecords.value = data.oopsRecords || []
    oohRecords.value = data.oohRecords || []
    topOopsTags.value = data.topOopsTags || []
    topOohTags.value = data.topOohTags || []

    renderCharts()
  } catch (err) {
    console.error('데이터 로드 실패:', err)
  }
}

// 📈 그래프 렌더링
function renderCharts() {
  // 1️⃣ 일별 라인 차트
  const days = Array.from({ length: 31 }, (_, i) => i + 1)
  const oopsData = days.map(
    (d) => oopsRecords.value.find((r) => r.day === d)?.oops_count || 0
  )
  const oohData = days.map(
    (d) => oohRecords.value.find((r) => r.day === d)?.ooh_count || 0
  )

  const dailyOptions = {
    series: [
      { name: 'Oops', data: oopsData },
      { name: 'Ooh', data: oohData }
    ],
    chart: { height: 350, type: 'line', zoom: { enabled: false } },
    stroke: { width: [3, 3], curve: 'smooth' },
    xaxis: { categories: days.map((d) => `${d}일`) },
    dataLabels: { enabled: false },
    title: { text: `${selectedYear.value}년 ${selectedMonth.value}월 기록`, align: 'left' }
  }
  new ApexCharts(document.querySelector('#dailyChart'), dailyOptions).render()

  // 2️⃣ Top Oops Tags (막대그래프)
  const oopsTagOptions = {
    series: [{ data: topOopsTags.value.map((t) => t.tag_count) }],
    chart: { type: 'bar', height: 300 },
    xaxis: { categories: topOopsTags.value.map((t) => t.tag_name) },
    title: { text: 'Top Oops Tags', align: 'left' },
    colors: ['#f87171']
  }
  new ApexCharts(document.querySelector('#oopsTagChart'), oopsTagOptions).render()

  // 3️⃣ Top Ooh Tags (막대그래프)
  const oohTagOptions = {
    series: [{ data: topOohTags.value.map((t) => t.tag_count) }],
    chart: { type: 'bar', height: 300 },
    xaxis: { categories: topOohTags.value.map((t) => t.tag_name) },
    title: { text: 'Top Ooh Tags', align: 'left' },
    colors: ['#60a5fa']
  }
  new ApexCharts(document.querySelector('#oohTagChart'), oohTagOptions).render()
}

onMounted(fetchData)
</script>

<style scoped>
.tab-page {
  padding: 20px;
  background: #fafafa;
  border-radius: 12px;
}

.filter-box {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

select {
  padding: 6px 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
}

.stat-cards {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}
.card {
  flex: 1;
  padding: 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background: #fff;
  text-align: center;
}
.chart-box {
  height: 350px;
  border: 1px dashed #ccc;
  border-radius: 8px;
  margin-bottom: 30px;
}
.chart-title {
  margin: 10px 0;
  font-size: 1.1rem;
  font-weight: 600;
}
</style>
