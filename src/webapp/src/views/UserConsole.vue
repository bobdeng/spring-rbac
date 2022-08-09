<template>
  <div>
    <div>
      <Menu mode="horizontal" theme="dark" @select="onMenuSelect">
        <MenuItem disabled>
          {{ tenant.description.name }}
        </MenuItem>
        <MenuItem key="users">
          用户管理
        </MenuItem>
        <MenuItem key="organizations">
          组织管理
        </MenuItem>
        <MenuItem key="set_password">
          修改密码
        </MenuItem>
      </Menu>
    </div>
    <div style="padding: 5px;">
      <RouterView></RouterView>
    </div>
  </div>
</template>

<script setup lang="ts">
import {Menu, MenuItem, PageHeader} from "ant-design-vue";
import {SelectInfo} from "ant-design-vue/es/menu/src/interface";
import {useRouter} from "vue-router";
import {server} from "../model/HttpServer";
import {ref} from "vue";

const router = useRouter()
const tenant = ref({id: 0, description: {name: ""}})
const onMenuSelect = (info: SelectInfo) => {
  router.push({name: info.key.toString()})
}
const onLoad = () => {
  server.getTenant().then(resp => tenant.value = resp)
}
onLoad();
</script>

<style scoped></style>
