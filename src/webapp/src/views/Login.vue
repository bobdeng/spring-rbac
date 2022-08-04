<template>
  <div>
    <Card title="管理员登录">
      <Form>
        <FormItem label="密码">
          <InputPassword v-model:value="password" id="inputPassword" maxlength="20"/>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="login" id="buttonLogin">登录</Button>
        </FormItem>
      </Form>
    </Card>
  </div>
</template>

<script setup lang="ts">
import {Card, Form, FormItem, InputPassword, Button} from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import {ref} from "vue";

const password = ref("")

function login() {
  password.value = ""
}
</script>

<style scoped></style>
