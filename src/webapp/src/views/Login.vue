<template>
  <div style="display: flex;justify-content: center;">
    <Card :title="tenant.description.name+'  登录'" style="width: 480px;margin-top: 100px;">
      <Alert v-if="error" id="error" :message="error" type="error" style="margin-bottom: 10px;"/>
      <Form :label-col="{span:8}" :wrapper-col="{span:16}">
        <FormItem label="登录名">
          <Input v-model:value="loginName" id="inputLoginName" :maxlength="20"/>
        </FormItem>
        <FormItem label="密码">
          <InputPassword v-model:value="password" id="inputPassword"/>
        </FormItem>
        <FormItem :wrapper-col="{ offset: 8, span: 16 }">
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

const emit = defineEmits(['adminLogin', 'userLogin'])
const password = ref("")
const loginName = ref("")
const error = ref("")
const loading = ref(false)
const tenant = ref({id: 0, description: {name: ""}})

async function login() {
  loading.value = true;
  try {
    if (loginName.value === "sysadmin") {
      await server.login(new LoginForm(password.value))
      emit("adminLogin")
      return;
    }
    await server.userLogin({loginName: loginName.value, password: password.value});
    emit("userLogin")
  } catch (e) {
    error.value = e + "";
  } finally {
    loading.value = false;
  }
}

const onLoad = () => {
  server.getTenant().then(resp => tenant.value = resp);
}

onLoad();
</script>

<style scoped>
</style>
