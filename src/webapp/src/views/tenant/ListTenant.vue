<template>
  <div>
    <Spin :spinning="loading">
      <div style="display: flex;">
        <InputSearch placeholder="输入名称查询" style="width:200px;" id="search"
                     @search="onLoad"
                     v-model:value="keyword"/>
        <AddTenantDialog @success="onLoad"/>
      </div>
      <Table :dataSource="tenants" :columns="columns" :pagination="false" id="tableTenants">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key==='action'">
            <Button type="link" @click="()=>onDomainClick(record)">域名</Button>
            <Button type="link" @click="()=>onRoleClick(record)">角色</Button>
          </template>
        </template>
      </Table>
    </Spin>
  </div>
</template>

<script setup lang="ts">
import {Table, InputSearch, Button, Spin} from "ant-design-vue";
import 'ant-design-vue/dist/antd.css';
import {ref} from "vue";
import {server} from "../../model/HttpServer";
import {useRouter} from "vue-router";
import AddTenantDialog from "./AddTenantDialog.vue";

const keyword = ref("")
const columns = ref([
  {
    title: "租户名",
    dataIndex: ['description', 'name'],
    key: "description.name"
  },
  {
    title: '操作',
    key: 'action',
    width: "200px"
  }
])
const tenants = ref([])
const router = useRouter()
const loading = ref(false)

async function onLoad() {
  loading.value = true
  try {
    tenants.value = await server.listTenants(keyword.value);
  } finally {
    loading.value = false
  }
}

function onDomainClick(record: any) {
  router.push({
    path: `/tenants/${record.id}/domains`
  })
}

const onRoleClick = (record: any) => {
  router.push({
    path: `/tenants/${record.id}/roles`
  })
}

onLoad();
</script>

<style scoped></style>
