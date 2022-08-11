<template>
  <div>
    <Modal v-if="visible" id="dialog" v-model:visible="visible"
           title="用户登录名"
           :ok-button-props="{ loading:loading}"
           :maskClosable="false"
    >
      <div v-if="form">
        已使用登录名:{{ form.description.name }}
      </div>
      <Form v-else>
        <FormItem label="登录名">
          <Input v-model:value="loginName" id="inputLoginName"/>
        </FormItem>
      </Form>
      <template #footer>
        <Button @click="close">取消</Button>
        <Button @click="remove" danger id="buttonRemove" v-if="form" :loading="loading">解绑</Button>
        <Button @click="bind" type="primary" id="buttonAdd" v-else :loading="loading">绑定</Button>
      </template>
    </Modal>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {Modal, Button, notification, Form, FormItem, Input} from "ant-design-vue";
import 'ant-design-vue/dist/antd.css';
import {server} from "../../../model/HttpServer";
import {notifications} from "../../../model/notifications";

const visible = ref(false)
const loading = ref(false)
const loginName = ref("")
const form = ref()
let userId: string = ""
const show = (id: any) => {
  userId = id
  visible.value = true
  loading.value = true
  server.getUserLoginName(userId).then(resp => {
    form.value = resp
  })
      .finally(() => loading.value = false)
}

function close() {
  visible.value = false
}

function remove() {
  server.deleteLoginName(form.value.id).then(resp => {
    notification.success({message: "解绑成功"})
    form.value = undefined
    visible.value = false
  }).catch(notifications.error)
}

function bind() {
  server.newLoginName({userId: userId, loginName: loginName.value})
      .then(() => {
        visible.value = false
        notification.success({message: "绑定成功"})
      }).catch(notifications.error)
}

defineExpose({
  show
})
</script>

<style scoped></style>
