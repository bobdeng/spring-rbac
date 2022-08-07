<template>
  <div>
    <Login @adminLogin="onAdminLogin" @userLogin="onUserLogin"/>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import Login from "./Login.vue";

const router = useRouter();
const onAdminLogin = () => {
  router.replace({
    name: "console",
  });
};
const onUserLogin = () => {
  router.replace({
    name: "userConsole",
  });
};
</script>

<style scoped></style>
