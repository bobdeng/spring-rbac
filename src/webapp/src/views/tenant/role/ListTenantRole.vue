<template>
  <div>
    <Spin :spinning="loading">
      <div style="display: flex">
        <Space>
          <AddTenantRole @success="onLoad" :tenant="tenant"/>
          <Button id="buttonBack" @click="router.go(-1)">返回</Button>
        </Space>
      </div>
      <EditTenantRole ref="edit" :tenant="tenant"/>
      <Table :dataSource="roles" :columns="columns" :pagination="false" id="tableDomains">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key==='action'">
            <Button type="link" @click="()=>confirmDeleteRole(record)">删除</Button>
            <Button type="link" @click="()=>showEdit(record)">修改</Button>
          </template>
        </template>
      </Table>
    </Spin>
  </div>
</template>

<script setup lang="ts">
import {Table, InputSearch, Button, Spin, Modal, Space, notification} from "ant-design-vue";
import 'ant-design-vue/dist/antd.css';
import {ref} from "vue";
import {server} from "../../../model/HttpServer";
import {useRoute, useRouter} from "vue-router";
import AddTenantRole from "./AddTenantRole.vue";
import EditTenantRole from "./EditTenantRole.vue";

const columns = ref([
  {
    title: "角色",
    dataIndex: ['description', 'name'],
    key: "description.name"
  },
  {
    title: '操作',
    key: 'action',
    width: "200px"
  }
])
const roles = ref([])
const route = useRoute()
const loading = ref(false)
const tenant = ref('')
const edit = ref({} as any)
const router = useRouter()

async function onLoad() {
  loading.value = true
  try {
    roles.value = await server.listRolesOfTenant(tenant.value);
  } finally {
    loading.value = false
  }
}

const showEdit = (role: any) => {
  edit.value.show(role.id)
}

const confirmDeleteRole = (role: any) => {
  Modal.confirm({
    title: "确认",
    content: "你确定要删除吗？",
    onOk: async () => {
      loading.value = true;
      try {
        await server.deleteRole({tenant: tenant.value, role: role.id});
        notification.success({message: "删除成功"})
        await onLoad();
      } catch (e) {
        notification.error({message: "错误", description: e as string})
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
