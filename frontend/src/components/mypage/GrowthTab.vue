<template>
  <div class="tab-page">
    <h2 class="tab-title">성장 그래프</h2>

    <!-- 연도/월 선택 -->
    <div class="filter-box">
      <select v-model="selectedYear">
        <option v-for="y in availableYears" :key="y" :value="y">{{ y }}년</option>
      </select>

      <!-- ✅ 월 선택 시 차트 자동 변경 -->
      <select v-model="selectedMonth" @change="fetchDailyData">
        <option v-for="m in availableMonths" :key="m" :value="m">{{ m }}월</option>
      </select>
    </div>

    <!-- 요약 카드 -->
    <div class="stat-cards">
      <div class="card">총 기록 <strong>{{ total }}</strong></div>
      <div class="card">Oops <strong>{{ oops }}</strong></div>
      <div class="card">Ooh <strong>{{ ooh }}</strong></div>
    </div>

    <!-- 일별 그래프 -->
    <div v-if="total > 0">
      <div id="dailyChart" class="chart-box"></div>

      <!-- 태그별 그래프 -->
       <div class="tag-charts">
        <div class="tag-chart-box">
         <h3 class="chart-title">Oops Tags</h3>
         <div id="oopsTagChart" class="chart-box"></div>
        </div>
        <div class="tag-chart-box">  
         <h3 class="chart-title">Ooh Tags</h3>
         <div id="oohTagChart" class="chart-box"></div>
        </div> 
      </div>
    </div>

    <div v-else class="no-data-message">
      <p>해당 내역이 없습니다</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import ApexCharts from 'apexcharts'
import axios from 'axios'
import { useUserStore } from '@/stores/useUserInfo'

const userStore = useUserStore()
const userId = userStore.id

const oops = ref(0)
const ooh = ref(0)
const total = ref(0)
const selectedYear = ref(null)
const selectedMonth = ref(null)
const availableYears = ref([])
const availableMonths = ref([])

const oopsRecords = ref([])
const oohRecords = ref([])
const topOopsTags = ref([])
const topOohTags = ref([])

let dailyChart = null
let oopsTagChart = null
let oohTagChart = null

// 1️⃣ 최초 로드: 연/월/총기록
async function fetchInitialData() {
  try {
    const res = await axios.get(`/boot/achivement/${userId}`)
    const data = res.data

    // ✅ 총 기록 (상단 카드용)
    oops.value = data.oopsCount || 0
    ooh.value = data.oohCount || 0
    total.value = (data.oopsCount || 0) + (data.oohCount || 0)

    // ✅ 연/월 데이터 구성
    const allRecords = [...data.findYearOops, ...data.findYearOoh]

    const years = [...new Set(allRecords.map(r => r.year))].sort((a, b) => b - a)
    availableYears.value = years

    // 최신 연도 계산
    const latestYear = Math.max(...years)

    // 해당 연도에 포함된 월 정리
    const monthsForLatestYear = allRecords
      .filter(r => r.year === latestYear)
      .map(r => r.month)

    availableMonths.value = [...new Set(monthsForLatestYear)].sort((a, b) => b - a)

    // ✅ 자동 선택: 최신 연/월
    selectedYear.value = latestYear
    selectedMonth.value = availableMonths.value[0]

    // ✅ 첫 차트 로드
    await fetchDailyData()
  } catch (err) {
    console.error('초기 데이터 로드 실패:', err)
  }
}

// 2️⃣ 일별 데이터 (월 변경 시 자동 호출)
async function fetchDailyData() {
  if (!selectedYear.value || !selectedMonth.value) return
  if (total.value === 0) return

  try {
    const res = await axios.get(`/boot/achivement/${userId}/daily`, {
      params: { year: selectedYear.value, month: selectedMonth.value }
    })
    const data = res.data

    oopsRecords.value = data.oopsRecords || []
    oohRecords.value = data.oohRecords || []
    topOopsTags.value = data.topOopsTags || []
    topOohTags.value = data.topOohTags || []

    renderCharts()
  } catch (err) {
    console.error('일별 데이터 로드 실패:', err)
  }
}

// 3️⃣ 차트 렌더링
function renderCharts() {
  const days = Array.from({ length: 31 }, (_, i) => i + 1)
  const oopsData = days.map(
    d => oopsRecords.value.find(r => r.day === d)?.oops_count || 0
  )
  const oohData = days.map(
    d => oohRecords.value.find(r => r.day === d)?.ooh_count || 0
  )

  if (dailyChart) dailyChart.destroy()
  if (oopsTagChart) oopsTagChart.destroy()
  if (oohTagChart) oohTagChart.destroy()

  // 🌈 감정 흐름 (부드러운 영역형 그래프)
  const dailyOptions = {
    series: [
      { name: 'Oops', data: oopsData },
      { name: 'Ooh', data: oohData }
    ],
    chart: { type: 'area', height: 350, toolbar: { show: false } },
    stroke: { curve: 'smooth', width: 3 },
    fill: {
      type: 'gradient',
      gradient: {
        shadeIntensity: 1,
        opacityFrom: 0.5,
        opacityTo: 0.1,
        stops: [0, 100]
      }
    },
    colors: ['#f87171', '#60a5fa'],
    dataLabels: { enabled: false },
    grid: { show: false },
    xaxis: { categories: days.map(d => `${d}일`) },
    yaxis: { labels: { show: false } },
    legend: { position: 'top', horizontalAlign: 'right' },
  }
  dailyChart = new ApexCharts(document.querySelector('#dailyChart'), dailyOptions)
  dailyChart.render()

  const hasOopsData = topOopsTags.value.length > 0

  const oopsTagOptions = {
  series: [{ data: topOopsTags.value.map(t => t.tag_count) }],
  chart: { type: 'bar', height: 300, toolbar: { show: false } },
  xaxis: {
    categories: hasOopsData ? topOopsTags.value.map(t => t.tag_name) : [],
    axisBorder: { show: hasOopsData },
    axisTicks: { show: hasOopsData },
  },
  yaxis: {
    labels: { show: false },
    axisBorder: { show: false },
    axisTicks: { show: false },
  },
  colors: ['#f87171'],
  grid: {
    show: false,
    xaxis: { lines: { show: false } },
    yaxis: { lines: { show: false } },
  },
  noData: { // ✅ “데이터 없음” 표시도 커스텀 가능
    text: '데이터가 없습니다.',
    align: 'center',
    verticalAlign: 'middle',
    style: { color: '#888', fontSize: '14px' }
  }
}
oopsTagChart = new ApexCharts(document.querySelector('#oopsTagChart'), oopsTagOptions)
oopsTagChart.render()

  const hasOohData = topOohTags.value.length > 0

const oohTagOptions = {
  series: [{ data: topOohTags.value.map(t => t.tag_count) }],
  chart: { type: 'bar', height: 300, toolbar: { show: false } },
  xaxis: {
    categories: hasOohData ? topOohTags.value.map(t => t.tag_name) : [],
    axisBorder: { show: hasOohData },
    axisTicks: { show: hasOohData },
  },
  yaxis: {
    labels: { show: false },
    axisBorder: { show: false },
    axisTicks: { show: false },
  },
  colors: ['#60a5fa'],
  grid: {
    show: false,
    xaxis: { lines: { show: false } },
    yaxis: { lines: { show: false } },
  },
  noData: { // ✅ “데이터 없음” 표시도 커스텀 가능
    text: '데이터가 없습니다.',
    align: 'center',
    verticalAlign: 'middle',
    style: { color: '#888', fontSize: '14px' }
  }
}
oohTagChart = new ApexCharts(document.querySelector('#oohTagChart'), oohTagOptions)
oohTagChart.render()

}

onMounted(fetchInitialData)
</script>

<style scoped>
.tab-page {
  padding: 20px;
  background: #F6F1E0;
  border-radius: 12px;
}

.filter-box {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  align-items: center;
}

select {
  padding: 6px 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  background: #fff;
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


.tag-charts {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  margin-top: 10px;
}

.tag-chart-box {
  flex: 1;
  border-radius: 8px;
  padding: 10px;
  border: 1px solid #ddd;
}

.tag-chart-box h3 {
  text-align: center;
  font-weight: 600;
  margin-bottom: 8px;
}

.chart-box {
  height: 300px; /* 살짝 줄여도 좋음 */
  border: 1px dashed #ccc;
  border-radius: 8px;
}

.no-data-message {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 400px;
  text-align: center;
  font-size: 1.2rem;
  color: #888;
  background: #fff;
  border: 1px dashed #ccc;
  border-radius: 8px;
}

</style>
