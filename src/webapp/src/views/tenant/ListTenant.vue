<template>
  <div>
    <Empty id="noData" v-if="tenants.length===0"/>
  </div>
</template>

<script setup lang="ts">
import {Empty} from "ant-design-vue";
import 'ant-design-vue/dist/antd.css';
import {ref} from "vue";
import {server, TenantListItem} from "../../model/HttpServer";

const tenants = ref([] as TenantListItem[])

async function onLoad() {
  tenants.value = await server.listTenants();
}

onLoad();
</script>

<style scoped></style>
