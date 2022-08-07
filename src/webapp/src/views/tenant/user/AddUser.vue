<template>
  <div>
    <Modal v-if="visible" id="dialog" v-model:visible="visible"
           title="新增用户"
           :maskClosable="false"
    >
      <Form v-model:value="form">
        <FormItem label="姓名">
          <Input v-model:value="form.name" id="inputName" :maxlength="20"/>
        </FormItem>
        <FormItem label="登录名">
          <Input v-model:value="form.loginName" id="inputLoginName" :maxlength="20"/>
        </FormItem>
        <FormItem label="密码">
          <Input v-model:value="form.password" id="inputPassword" :maxlength="20"/>
        </FormItem>
        <FormItem label="角色">
          <CheckboxGroup v-model:value="form.roles" style="width:100%;">
            <Row>
              <Col :span="6" v-for="role in roles">
                <Checkbox :value="role.id">{{ role.description.name }}</Checkbox>
              </Col>
            </Row>
          </CheckboxGroup>
        </FormItem>
      </Form>
      <template #footer>
        <Button key="back" @click="close()">关闭</Button>
        <Button key="submit" type="primary" :loading="loading" @click="save">保存</Button>
      </template>
    </Modal>
  </div>
</template>

<script setup lang="ts">
import {Modal, Button, Form, FormItem, Input, notification, Row, CheckboxGroup, Checkbox, Col} from "ant-design-vue";
import {ref} from "vue";
import 'ant-design-vue/dist/antd.css';
import {server} from "../../../model/HttpServer";

const visible = ref(false)
const loading = ref(false)
const roles = ref([] as {id:number,description:{name:string}}[])
const form = ref({
  name: "",
  password: "",
  loginName: "",
  roles: [],

})
const emit = defineEmits(['success'])

function clearForm() {
  form.value.name = ''
  form.value.password = ''
  form.value.loginName = ''
}

const show = () => {
  visible.value = true
  clearForm();
}
const close = () => {
  visible.value = false
}
const save = () => {
  loading.value = true
  server.newUser(form.value)
      .then(() => {
        emit("success")
        notification.success({message: "新增成功"})
        clearForm()
      })
      .catch((e) => {
        notification.error({
          message: '错误',
          description: e
        });
      })
      .finally(() => {
        loading.value = false
      })
}
const onLoad = async () => {
  roles.value = await server.listRoles()
}
onLoad();
defineExpose({
  show
})
</script>

<style scoped></style>
