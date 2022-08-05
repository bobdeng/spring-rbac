<template>
  <div>
    <Login @login="onLogin"/>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import Login from "./Login.vue";

const router = useRouter();
const onLogin = () => {
  router.push({
    name: "console",
  });
};
</script>

<style scoped></style>
