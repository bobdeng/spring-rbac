<template>
  <div>
    <Card title="管理员登录" style="width: 480px;">
      <Form :label-col="{style:{width:'150px'}}" :wrapper-col="{span:16}">
        <FormItem>
          <Alert v-if="error" id="error" :message="error" type="error"/>
        </FormItem>
        <FormItem label="登录名">
          <Input v-model:value="loginName" id="inputLoginName" maxlength="20"/>
        </FormItem>
        <FormItem label="密码">
          <InputPassword v-model:value="password" id="inputPassword" maxlength="20"/>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="login" id="buttonLogin" :loading="loading">登录</Button>
        </FormItem>
      </Form>
    </Card>
  </div>
</template>

<script setup lang="ts">
import {Card, Form, FormItem, Input, InputPassword, Button, Alert} from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import {ref} from "vue";
import {LoginForm, server} from "../model/HttpServer";

const emit = defineEmits(['login'])
const password = ref("")
const loginName = ref("")
const error = ref("")
const loading = ref(false)

async function login() {
  loading.value = true;
  try {
    if (loginName.value === "sysadmin") {
      await server.login(new LoginForm(password.value))
    } else {
      await server.userLogin({loginName: loginName.value, password: password.value});
    }
    emit("login")
  } catch (e) {
    error.value = e + "";
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
</style>
