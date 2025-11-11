<template>
  <article class="card">
    <div class="top">
      <div class="avatar">{{ post.userName || '익명' }}</div>
      <span class="time">{{ timeAgo(post.createdAt) }}</span>
      <span class="pill">Oops</span>
    </div>

    <h3 class="title">{{ post.title }}</h3>
    <p class="content">{{ post.content }}</p>

    <div class="tags" v-if="post.tags?.length">
      <span v-for="(t,i) in post.tags" :key="i" class="tag">#{{ t }}</span>
    </div>

    <div class="actions">
      <button @click="onLike" title="좋아요">좋아요 {{ likeCount }}</button>
      <button @click="$emit('bookmark', post)">북마크</button>
    </div>
  </article>
</template>


<script setup>
import { ref, onMounted, watchEffect } from 'vue'
import {
  checkOopsLikesCount, toggleOopsLike, getOopsLikesCount,
  checkOohLikesCount,  toggleOohLike,  getOohLikesCount
} from '../api/likes'

const props = defineProps({
  post: { type: Object, required: true },
  fetchLikes: { type: Boolean, default: true },
  // ✅ 카드 타입 지정: 'oops' | 'ooh'
  recordType: { type: String, default: 'oops' }
})
const emit = defineEmits(['update:likes', 'bookmark'])

const likeCount = ref(props.post.likes ?? 0)

// 타입별 함수 맵핑
const likeApis = {
  oops: {
    check: (id) => checkOopsLikesCount(id),
    toggle: (id) => toggleOopsLike(id),
    count: (id) => getOopsLikesCount(id),
  },
  ooh: {
    check: (id) => checkOohLikesCount(id),
    toggle: (id) => toggleOohLike(id),
    count: (id) => getOohLikesCount(id),
  }
}


onMounted(async () => {
  if (!props.fetchLikes || !props.post?.id) return
  const apis = likeApis[props.recordType] || likeApis.oops
  try {
    // check* 함수가 숫자 인자 하나를 받도록 likes.js 시그니처 확인/통일!
    const { data } = await apis.check(props.post.id)
    likeCount.value = data?.count ?? data ?? likeCount.value
    emit('update:likes', likeCount.value)
  } catch (e) {
    console.error(`[RecordCard:${props.recordType}] 좋아요 수 불러오기 실패`, {
      postId: props.post?.id,
      status: e?.response?.status,
      error: e?.response?.data || e.message,
    })
  }
})

async function onLike() {
  if (!props.post?.id) return
  const apis = likeApis[props.recordType] || likeApis.oops
  try {
    await apis.toggle(props.post.id)
    const { data } = await apis.count(props.post.id)
    likeCount.value = data?.count ?? data ?? likeCount.value
    emit('update:likes', likeCount.value)
  } catch (e) {
    console.error(`[RecordCard:${props.recordType}] 좋아요 토글 실패`, {
      postId: props.post?.id,
      status: e?.response?.status,
      error: e?.response?.data || e.message,
    })
  }
}

function timeAgo(iso) {
  if (!iso) return ''
  const d = new Date(iso)
  const diff = (Date.now() - d.getTime()) / 1000
  if (diff < 60) return '방금 전'
  if (diff < 3600) return `${Math.floor(diff/60)}분 전`
  if (diff < 86400) return `${Math.floor(diff/3600)}시간 전`
  return `${Math.floor(diff/86400)}일 전`
}
</script>

<style scoped>
.card{
  background:white; border:1px solid #e9e2cf; border-radius:16px;
  padding:16px; margin:12px 0; box-shadow: 0 1px 0 rgba(0,0,0,0.03);
}
.top{ display:flex; align-items:center; gap:8px; }
.avatar{
  width:36px;height:36px;border-radius:50%;background:#7a8d56;color:white;
  display:flex;align-items:center;justify-content:center;font-size:12px;
}
.time{ margin-left:8px; color:#8d907e; font-size:13px; }
.pill{
  margin-left:auto; font-size:12px; color:#5f6b48; background:#edf1e5;
  border:1px solid #dfe7cf; border-radius:999px; padding:3px 8px;
}
.title{ margin:10px 0 6px; font-size:16px; color:#343530; }
.content{ color:#595b50; white-space:pre-wrap; }
.tags{ margin-top:10px; display:flex; flex-wrap:wrap; gap:6px; }
.tag{
  font-size:12px; color:#546;
  border:1px solid #e3dcc8; background:#f8f3e6; border-radius:999px; padding:3px 8px;
}
.actions{ margin-top:12px; display:flex; gap:8px; }
.actions button{
  border:1px solid #e3dcc8; background:#fff; border-radius:12px; padding:6px 10px; cursor:pointer;
}
</style>
