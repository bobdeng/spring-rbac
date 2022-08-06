<template>
  <div>
    <Button type="primary" @click="show" id="buttonShow">新增</Button>
    <Modal v-if="visible" id="dialog" v-model:visible="visible"
           cancelText="取消"
           okText="确定"
           :ok-button-props="{ loading:loading}"
           :maskClosable="false"
           @ok="save"
    >
      <Form v-model:value="form">
        <FormItem label="租户名">
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

const visible = ref(false)
const loading = ref(false)
const props = defineProps(['tenant'])
const form = ref({
  name: "",
  allows: []
})
const emit = defineEmits(['success'])
const functions = ref([] as Function[])
const expandedKeys = ref([] as string[])
const show = () => {
  visible.value = true
  form.value.name = ''
}
const save = () => {
  loading.value = true
  server.newTenantRole({name: form.value.name, allows: form.value.allows, tenant: props.tenant})
      .then(() => {
        visible.value = false
        emit("success")
        notification.success({message:"新增成功"})
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
  const modules = [] as string[]
  readModules(modules, functions.value)
  expandedKeys.value = modules
}
const readModules = (result: string[], functions: Function[]) => {
  functions.forEach((fun: Function) => {
    if (fun.children) {
      result.push(fun.key)
      readModules(result, fun.children)
    }
  })
}
onLoad();
</script>

<style scoped></style>
