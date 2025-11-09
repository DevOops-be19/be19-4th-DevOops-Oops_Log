<script setup>
  import {ref} from 'vue';
  import {RouterView, useRoute} from 'vue-router';

const route = useRoute();
</script>

<template>
  <h1>oops_log</h1>
  <RouterView/>
</template>

<style scoped></style>
