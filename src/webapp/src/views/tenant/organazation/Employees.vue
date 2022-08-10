<template>
  <div>
    <Spin :spinning="loading">
      <SelectUser ref="selectUser" @select="onUserSelect" action-name="加入" :loading="loading"/>
      <Button type="primary" @click="showAdd" id="buttonAdd">添加</Button>
      <Table :dataSource="employees" :columns="columns" :pagination="false" id="tableUsers">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key==='action'">
            <Button type="link" @click="remove(record)">删除</Button>
          </template>
        </template>
      </Table>
    </Spin>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {Table, Button, notification} from "ant-design-vue";
import {server} from "../../../model/HttpServer";
import SelectUser from "../user/SelectUser.vue";

const columns = ref([
  {
    title: "姓名",
    dataIndex: ['description', 'name'],
    key: "description.name"
  },
  {
    title: '操作',
    key: 'action',
    width: "200px"
  }
])
const employees = ref([])
const loading = ref(false)
const props = defineProps(['organization'])
const selectUser = ref()

function reload() {
  loading.value = true;
  server.listEmployees(props.organization).then(resp => {
    employees.value = resp;
  }).finally(() => loading.value = false)
}

function showAdd() {
  selectUser.value.show()
}

function onUserSelect(user: any) {
  loading.value = true
  server.putUserToOrganization({userId: user.id, organizationId: props.organization.id})
      .then(() => {
        notification.success({message: "操作成功"});
        reload()
      })
      .catch((e) => notification.error({message: "错误", description: e as string}))
      .finally(() => loading.value = false)
}

function remove(user: any) {
  loading.value = true;
  server.deleteEmployee({userId: user.id, organizationId: props.organization.id})
      .then(reload)
      .finally(() => loading.value = false)
}

reload()
</script>

<style scoped></style>