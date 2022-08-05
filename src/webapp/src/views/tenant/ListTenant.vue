<template>
  <div>
    <InputSearch placeholder="输入名称查询" style="width:200px;" id="search"
                 @search="onLoad"
                 v-model:value="keyword"/>
    <Table :dataSource="tenants" :columns="columns" :pagination="false" id="tableTenants">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key==='action'">
          <Button type="link" @click="()=>onDomainClick(record)">域名</Button>
        </template>
      </template>
    </Table>
  </div>
</template>

<script setup lang="ts">
import {Table, InputSearch, Button} from "ant-design-vue";
import 'ant-design-vue/dist/antd.css';
import {ref} from "vue";
import {server, TenantListItem} from "../../model/HttpServer";
import {useRouter} from "vue-router";

const keyword = ref("")
const columns = ref([
  {
    title: "租户名",
    dataIndex: "name",
    key: "name"
  },
  {
    title: '操作',
    key: 'action',
    width: "200px"
  }
])
const tenants = ref([] as TenantListItem[])
const router = useRouter()

async function onLoad() {
  tenants.value = await server.listTenants(keyword.value);
}

function onDomainClick(record: TenantListItem) {
  router.replace({
    path: `/tenants/${record.id}/domains`
  })
}

onLoad();
</script>

<style scoped></style>
