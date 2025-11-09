<template>
    <div class="page-container">
      <div class="tag-box">
        <h1 class="title">태그 관리</h1>
  
        <!-- 1️⃣ 태그 추가 -->
        <section class="section">
          <h2>태그 추가</h2>
          <div class="form-row">
            <input
              v-model="newTagName"
              type="text"
              placeholder="태그 이름 입력"
            />
            <select v-model="newTagType">
              <option disabled value="">태그 유형 선택</option>
              <option value="ooh">OOH</option>
              <option value="oops">OOPS</option>
              <option value="emo">EMO</option>
            </select>
            <button class="add-btn" @click="addTag">추가</button>
          </div>
        </section>
  
        <!-- 2️⃣ 태그 조회 -->
        <section class="section">
          <h2>태그 조회</h2>
          <div class="tag-columns">
            <div class="tag-column">
              <h3>OOH</h3>
              <ul>
                <li
                  v-for="tag in oohTags"
                  :key="tag.id"
                  @click="selectTag(tag)"
                  :class="{ active: selectedTag && selectedTag.id === tag.id }"
                >
                  {{ tag.tag_name }}
                </li>
              </ul>
            </div>
            <div class="tag-column">
              <h3>OOPS</h3>
              <ul>
                <li
                  v-for="tag in oopsTags"
                  :key="tag.id"
                  @click="selectTag(tag)"
                  :class="{ active: selectedTag && selectedTag.id === tag.id }"
                >
                  {{ tag.tag_name }}
                </li>
              </ul>
            </div>
            <div class="tag-column">
              <h3>EMO</h3>
              <ul>
                <li
                  v-for="tag in emoTags"
                  :key="tag.id"
                  @click="selectTag(tag)"
                  :class="{ active: selectedTag && selectedTag.id === tag.id }"
                >
                  {{ tag.tag_name }}
                </li>
              </ul>
            </div>
          </div>
        </section>
  
        <!-- 3️⃣ 태그 삭제 -->
        <section v-if="selectedTag" class="section">
          <h2>태그 삭제</h2>
          <div class="detail-box">
            <p><strong>ID:</strong> {{ selectedTag.id }}</p>
            <p><strong>이름:</strong> {{ selectedTag.tag_name }}</p>
            <p><strong>유형:</strong> {{ selectedTag.tag_type }}</p>
            <button class="delete-btn" @click="deleteTag(selectedTag.id)">
              삭제
            </button>
          </div>
        </section>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue'
  import axios from 'axios'
  
  // 전체 태그 목록
  const tags = ref([])
  
  // 입력 폼
  const newTagName = ref('')
  const newTagType = ref('')
  
  // 선택된 태그
  const selectedTag = ref(null)
  
  // 태그 목록 가져오기
  const fetchTags = async () => {
    try {
      const res = await axios.get('/api/admin/tag')
      tags.value = res.data
    } catch (err) {
      console.error('태그 목록 조회 오류:', err)
    }
  }
  
  // 태그 추가
  const addTag = async () => {
    if (!newTagName.value || !newTagType.value) {
      alert('태그 이름과 유형을 입력해주세요.')
      return
    }
    try {
      await axios.post('/api/admin/tag', {
        tagName: newTagName.value,
        tagType: newTagType.value,
      })
      alert('태그가 추가되었습니다.')
      newTagName.value = ''
      newTagType.value = ''
      fetchTags()
    } catch (err) {
      console.error('태그 추가 오류:', err)
    }
  }
  
  // 태그 선택
  const selectTag = (tag) => {
    selectedTag.value = tag
  }
  
  // 태그 삭제
  const deleteTag = async (id) => {
    if (!confirm('정말로 이 태그를 삭제하시겠습니까?')) return
    try {
      await axios.delete(`/api/admin/tag/${id}`)
      alert('태그가 삭제되었습니다.')
      selectedTag.value = null
      fetchTags()
    } catch (err) {
      console.error('태그 삭제 오류:', err)
    }
  }
  
  // 타입별 태그 분류
  const oohTags = computed(() => tags.value.filter(t => t.tag_type === 'ooh'))
  const oopsTags = computed(() => tags.value.filter(t => t.tag_type === 'oops'))
  const emoTags = computed(() => tags.value.filter(t => t.tag_type === 'emo'))
  
  onMounted(fetchTags)
  </script>
  
  <style scoped>
  .page-container {
    background-color: #f9fafb;
    min-height: 100vh;
    padding: 40px;
  }
  
  .tag-box {
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
    margin-bottom: 25px;
  }
  
  .section {
    margin-bottom: 30px;
  }
  
  .section h2 {
    color: #1e40af;
    font-size: 1.2rem;
    margin-bottom: 10px;
  }
  
  /* 폼 영역 */
  .form-row {
    display: flex;
    gap: 10px;
    align-items: center;
  }
  
  .form-row input,
  .form-row select {
    padding: 8px 12px;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 0.9rem;
  }
  
  .add-btn {
    background-color: #2563eb;
    color: white;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    cursor: pointer;
  }
  .add-btn:hover {
    background-color: #1d4ed8;
  }
  
  /* 태그 리스트 */
  .tag-columns {
    display: flex;
    justify-content: space-between;
    gap: 10px;
  }
  
  .tag-column {
    flex: 1;
    background-color: #f1f5f9;
    border-radius: 10px;
    padding: 10px;
    min-height: 200px;
  }
  
  .tag-column h3 {
    text-align: center;
    font-size: 1rem;
    color: #1e40af;
    margin-bottom: 10px;
  }
  
  .tag-column ul {
    list-style: none;
    padding: 0;
  }
  
  .tag-column li {
    padding: 8px;
    margin: 4px 0;
    border-radius: 6px;
    background-color: white;
    text-align: center;
    cursor: pointer;
    border: 1px solid #e2e8f0;
    transition: background-color 0.2s;
  }
  
  .tag-column li:hover {
    background-color: #e0f2fe;
  }
  
  .tag-column li.active {
    background-color: #bfdbfe;
    border-color: #60a5fa;
  }
  
  /* 상세 */
  .detail-box {
    border-top: 2px solid #93c5fd;
    padding-top: 10px;
  }
  
  .detail-box p {
    margin: 5px 0;
  }
  
  .delete-btn {
    background-color: #ef4444;
    color: white;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    margin-top: 10px;
    cursor: pointer;
  }
  .delete-btn:hover {
    background-color: #dc2626;
  }
  </style>
  