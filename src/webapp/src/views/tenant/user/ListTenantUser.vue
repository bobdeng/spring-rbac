<template>
  <div>
    <AddUser ref="newUserModal"/>
    <Spin :spinning="loading">
      <div style="display: flex">
        <Space>
          <InputSearch placeholder="输入名称查询" style="width:200px;" id="search"
                       @search="onLoad"
                       v-model:value="keyword"/>
          <Button type="primary" @click="newUser">新增</Button>
        </Space>
      </div>
      <Table :dataSource="users" :columns="columns" :pagination="false" id="tableUsers">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key==='action'">
            <Button type="link" @click="()=>configResetPassword(record)">重置密码</Button>
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
import AddUser from "./AddUser.vue";

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
const users = ref([])
const loading = ref(false)
const router = useRouter()
const newUserModal = ref()
const keyword = ref("")

async function onLoad() {
  loading.value = true
  try {
    users.value = await server.listUsers(keyword.value);
  } finally {
    loading.value = false
  }
}

const newUser = () => {
  newUserModal.value.show();
}
const configResetPassword = (user: any) => {
  Modal.confirm({
    title: "确认",
    content: "你确定要重置密码吗？",
    okText: "确定",
    cancelText: "取消",
    onOk: async () => {
      loading.value = true;
      try {
        let resp = await server.resetPassword({userId: user.id});
        notification.success({
          message: "重置成功，新密码为：" + resp.password
        })
        await onLoad();
      } catch (e) {
        notification.error({message: "错误", description: e as string})
      } finally {
        loading.value = false;
      }
    }
  })
}


onLoad();
</script>

<style scoped></style>
