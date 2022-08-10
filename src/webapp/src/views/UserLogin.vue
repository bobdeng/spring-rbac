<template>
  <div>
    <Login @userLogin="onUserLogin"/>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import Login from "./Login.vue";

const router = useRouter();

const onUserLogin = () => {
  document.location = document.referrer
};
</script>

<style scoped></style>
