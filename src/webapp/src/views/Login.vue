<template>
  <div>
    <Card title="管理员登录">
      <Form>
        <Alert v-if="error" id="error" :message="error" type="error"/>
        <FormItem label="密码">
          <InputPassword v-model:value="password" id="inputPassword" :maxlength="20"/>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="login" id="buttonLogin">登录</Button>
        </FormItem>
      </Form>
    </Card>
  </div>
</template>

<script setup lang="ts">
import {Card, Form, FormItem, InputPassword, Button, Alert} from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import {ref} from "vue";
import {LoginForm, server} from "../HttpServer";

const emit = defineEmits(['login'])
const password = ref("")
const error = ref("")

async function login() {
  try {
    await server.login(new LoginForm(password.value))
    emit("login")
  } catch (e) {
    error.value = e as string;
  }
}
</script>

<style scoped></style>
