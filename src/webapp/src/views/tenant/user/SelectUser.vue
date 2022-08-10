<template>
  <div>

    <Modal v-if="visible" id="dialog" v-model:visible="visible"
           title="选择用户"
           cancelText="取消"
           okText="确定"
           :maskClosable="false">
      <InputSearch placeholder="输入名称查询" style="width:200px;" id="search"
                   @search="reload"
                   v-model:value="keyword"/>
      <Table :dataSource="users" :columns="columns" :pagination="false" id="tableUsers">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key==='action'">
            <Button type="link" @click="()=>selectUser(record)">{{ props.actionName }}</Button>
          </template>
        </template>
      </Table>
      <template #footer>
        <Button key="back" @click="close">关闭</Button>
      </template>
    </Modal>

  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {Table, Button, Modal, InputSearch} from "ant-design-vue";
import {server} from "../../../model/HttpServer";

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
const loading = ref(false)
const users = ref([])
const props = defineProps(['actionName', 'loading'])
const emit = defineEmits(['select'])
const visible = ref(false)
const keyword = ref("")

function show() {
  visible.value = true
}

function close() {
  visible.value = false
}

function reload() {
  loading.value = true
  server.listUsers(keyword.value)
      .then(resp => users.value = resp)
      .finally(() => loading.value = false)
}

const selectUser = (record: any) => {
  emit('select', record.id)
  visible.value = false
}
defineExpose({
  show
})
reload();
</script>

<style scoped></style>