<template>
  <div>
    <Modal v-if="visible" id="dialog" v-model:visible="visible"
           cancelText="取消"
           title="设置角色"
           okText="确定"
           :ok-button-props="{ loading:loading}"
           :maskClosable="false"
    >
      <Form v-model:value="form">
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
        <Button key="submit" type="primary" :loading="loading" @click="setRoles">保存</Button>
      </template>
    </Modal>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {Modal, Form, FormItem, CheckboxGroup, Row, Col, Checkbox, Button, notification} from "ant-design-vue";
import 'ant-design-vue/dist/antd.css';
import {server} from "../../../model/HttpServer";

const visible = ref(false)
const loading = ref(false)
const roles = ref([] as { id: number, description: { name: string } }[])
const user = ref<number | undefined>()
const form = ref({
  roles: [] as number[]
})
const setRoles = () => {
  loading.value = true;
  server.setUserRole(user.value, form.value.roles)
      .then(() => {
        notification.success({message: "保存成功"})
        visible.value = false
      })
      .finally(() => loading.value = false)
}
const show = async (userId: number) => {
  user.value = userId;
  visible.value = true
  form.value.roles = (await server.listUserRoles(userId)).map(it => it.id)
}
const onLoad = async () => {
  roles.value = await server.listRoles()

}
const close = () => {
  visible.value = false
}
onLoad();
defineExpose({
  show
})
</script>

<style scoped></style>
