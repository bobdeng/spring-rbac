<template>
  <div>
    <Spin :spinning="loading">
      <Table :dataSource="domains" :columns="columns" :pagination="false" id="tableDomains">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key==='action'">
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
import {server} from "../../../model/HttpServer";
import {useRoute} from "vue-router";

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

async function onLoad() {
  loading.value = true
  try {
    domains.value = await server.listDomains(route.params.id as string);
  } finally {
    loading.value = false
  }
}


onLoad();
</script>

<style scoped></style>
