<template>
  <div v-if="organization">
    <Spin :spinning="loading">
      <div id="organizationName">分公司</div>
    </Spin>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";

const props = defineProps(['organization'])
const loading = ref(false)
</script>

<style scoped></style>