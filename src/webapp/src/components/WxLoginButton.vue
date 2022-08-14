<template>
  <div v-if="hasConfig">
    <Button type="primary" @click="wxLogin">微信登录</Button>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {Button} from "ant-design-vue";
import {server, WxConfig} from "../model/HttpServer";

const hasConfig = ref(false)
const loading = ref(false)
let wxConfig: WxConfig;

async function onLoad() {
  wxConfig = await server.getWxConfig();
  hasConfig.value = wxConfig !== null
}

function wxLogin() {
  console.log(encodeURIComponent(wxConfig.callback))
  document.location.href = `https://open.weixin.qq.com/connect/qrconnect?appid=${wxConfig.appId}&redirect_uri=${encodeURIComponent(wxConfig.callback)}&response_type=code&scope=snsapi_login&state=${wxConfig.code}#wechat_redirect`
}

onLoad()
</script>

<style scoped></style>