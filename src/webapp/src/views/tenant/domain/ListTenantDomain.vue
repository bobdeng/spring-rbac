<template>
  <div>
    <Spin :spinning="loading">
      <AddTenantDomain @success="onLoad" :tenant="tenant"/>
      <Table :dataSource="domains" :columns="columns" :pagination="false" id="tableDomains">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key==='action'">
            <Button type="link" @click="()=>confirmDeleteDomain(record)">删除</Button>
          </template>
        </template>
      </Table>
    </Spin>
  </div>
</template>

<script setup lang="ts">
import {Table, InputSearch, Button, Spin, Modal} from "ant-design-vue";
import 'ant-design-vue/dist/antd.css';
import {ref} from "vue";
import {server} from "../../../model/HttpServer";
import {useRoute} from "vue-router";
import AddTenantDomain from "./AddTenantDomain.vue";

const columns = ref([
  {
    title: "域名",
    dataIndex: ['description', 'domain'],
    key: "description.name"
  },
  {
    title: '操作',
    key: 'action',
    width: "200px"
  }
])
const domains = ref([])
const route = useRoute()
const loading = ref(false)
const tenant = ref('')

async function onLoad() {
  loading.value = true
  try {
    domains.value = await server.listDomains(tenant.value);
  } finally {
    loading.value = false
  }

}

const confirmDeleteDomain = (domain: any) => {
  Modal.confirm({
    title: "确认",
    content: "你确定要删除吗？",
    onOk: async () => {
      loading.value = true;
      try {
        await server.deleteDomain(domain.id);
        await onLoad();
      } catch (e) {

      } finally {
        loading.value = false;
      }
    }
  })
}


tenant.value = route.params.id as string;
onLoad();
</script>

<style scoped></style>
