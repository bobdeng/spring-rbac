<template>
  <div>
    <Login @adminLogin="onAdminLogin" @userLogin="onUserLogin"/>
  </div>
</template>

<script setup lang="ts">
import {useRouter, useRoute} from "vue-router";
import Login from "./Login.vue";

const router = useRouter();
const route = useRoute()
const onAdminLogin = () => {
  router.replace({
    name: "console",
  });
};
const onUserLogin = () => {
  if (route.query.from) {
    document.location.href = route.query.from as string
    return;
  }
  router.replace({
    name: "userConsole",
  });
};
</script>

<style scoped></style>
