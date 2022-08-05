<template>
  <div>
    <div>
      <Menu mode="horizontal" theme="dark" @select="onMenuSelect">
        <MenuItem key="tenants">
          租户管理
        </MenuItem>
      </Menu>
    </div>
    <RouterView></RouterView>
  </div>
</template>

<script setup lang="ts">
import {Menu, MenuItem} from "ant-design-vue";
import {SelectInfo} from "ant-design-vue/es/menu/src/interface";
import {useRouter} from "vue-router";

const router = useRouter()
const onMenuSelect = (info: SelectInfo) => {
  router.push({name: info.key.toString()})
}
</script>

<style scoped></style>
