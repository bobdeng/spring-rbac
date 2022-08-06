<template>
  <div>
    <Modal v-if="visible" id="dialog" v-model:visible="visible"
           cancelText="取消"
           okText="确定"
           :ok-button-props="{ loading:loading}"
           :maskClosable="false"
           @ok="save"
    >
      <Form v-model:value="form">
        <FormItem label="角色名">
          <Input v-model:value="form.name" id="inputName"/>
        </FormItem>
        <FormItem label="功能">
          <Tree
              :fieldNames="{title:'name',}"
              checkable
              :selectable="false"
              autoExpandParent
              :expandedKeys="expandedKeys"
              v-model:checkedKeys="form.allows"
              :treeData="functions"
          >
          </Tree>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script setup lang="ts">
import {Modal, Button, Form, FormItem, Input, notification, Tree} from "ant-design-vue";
import {ref} from "vue";
import 'ant-design-vue/dist/antd.css';
import {Function, server} from "../../../model/HttpServer";
import {Functions} from "./Functions";

const visible = ref(false)
const loading = ref(false)
const props = defineProps(['tenant'])
const roleId = ref("")
const form = ref({
  name: "",
  allows: []
})
const emit = defineEmits(['success'])
const functions = ref([] as Function[])
const expandedKeys = ref([] as string[])
const show = (role: any) => {
  roleId.value = role;
  visible.value = true
  server.getRole({tenant: props.tenant, role: role})
      .then(resp => {
        form.value.name = resp.description.name;
        form.value.allows = resp.description.allows;
      })
}
const save = () => {
  loading.value = true
  server.saveTenantRole({name: form.value.name, allows: form.value.allows, tenant: props.tenant, role: roleId.value})
      .then(() => {
        visible.value = false
        emit("success")
        notification.success({message: "保存成功"})
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
  functions.value = await server.listFunctions();
  expandedKeys.value = new Functions(functions.value).allModules()
}
onLoad();
defineExpose({
  show
})
</script>

<style scoped></style>
