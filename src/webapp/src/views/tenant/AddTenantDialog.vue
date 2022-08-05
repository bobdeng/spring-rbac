<template>
  <div>
    <Button type="primary" @click="show" id="buttonShow">新增</Button>
    <Modal v-if="visible" id="dialog" v-model:visible="visible"
           cancelText="取消"
           okText="确定"
           :maskClosable="false"
           @ok="newTenant"
    >
      <Form v-model:value="form">
        <FormItem label="租户名">
          <Input v-model:value="form.name" id="inputName"/>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script setup lang="ts">
import {Modal, Button, Form, FormItem, Input, notification} from "ant-design-vue";
import {ref} from "vue";
import 'ant-design-vue/dist/antd.css';
import {server} from "../../model/HttpServer";

const visible = ref(false)
const form = ref({
  name: ""
})
const show = () => {
  visible.value = true
  form.value.name = ''
}
const newTenant = () => {
  server.newTenant({name: form.value.name})
      .then(() => {
        visible.value = false
      })
      .catch((e) => {
        notification.error({
          message: '错误',
          description: e
        });
      })
}
defineExpose({
  show
})
</script>

<style scoped></style>
