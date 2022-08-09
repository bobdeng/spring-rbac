<template>
  <div>
    <Card title="修改密码" style="width: 480px;">
      <Form :label-col="{style:{width:'150px'}}" :wrapper-col="{span:16}">
        <FormItem label="原密码">
          <InputPassword v-model:value="password" id="inputCurrentPassword"/>
        </FormItem>
        <FormItem label="新密码">
          <InputPassword v-model:value="newPassword" id="inputNewPassword"/>
        </FormItem>
        <FormItem label="重复密码">
          <InputPassword v-model:value="confirmation" id="inputConfirmation"/>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="save" id="buttonLogin" :loading="loading">确定</Button>
        </FormItem>
      </Form>
    </Card>
  </div>
</template>

<script setup lang="ts">
import {Card, Form, FormItem, Input, InputPassword, Button, Alert, notification} from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import {ref} from "vue";
import {server} from "../../../model/HttpServer";

const password = ref("")
const newPassword = ref("")
const confirmation = ref("")
const error = ref("")
const loading = ref(false)
const tenant = ref({id: 0, description: {name: ""}})

async function save() {
  loading.value = true;
  try {
    if (newPassword.value !== confirmation.value) {
      throw "两次密码输入不一致";
    }
    await server.setPassword({password: password.value, newPassword: newPassword.value})
    notification.success({message: "修改成功"})
    reset();
  } catch (e) {
    notification.error({message: "错误", description: e as string})
  } finally {
    loading.value = false;
  }
}

function reset() {
  password.value = ""
  newPassword.value = ""
  confirmation.value = ""
}


</script>

<style scoped>
</style>
