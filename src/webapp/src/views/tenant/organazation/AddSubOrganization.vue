<template>
  <div>
    <Modal v-if="visible" id="dialog" v-model:visible="visible"
           cancelText="取消"
           okText="确定"
           title="新增组织"
           :ok-button-props="{ loading:loading}"
           :maskClosable="false"
           @ok="save"
    >
      <Form v-model:value="form">
        <FormItem label="名称">
          <Input :maxlength="20" v-model:value="form.name" id="inputName"/>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {Modal, Form, FormItem, Input, notification} from "ant-design-vue";
import 'ant-design-vue/dist/antd.css';
import {server} from "../../../model/HttpServer";

const visible = ref(false)
const loading = ref(false)
const props = defineProps(['parent'])
const emit = defineEmits(['success'])
const form = ref({
  name: ""
})
const show = () => {
  visible.value = true
}
const parentId = (): number => {
  if (props.parent) {
    return props.parent.id as number;
  }
  return 0;
}
const save = async () => {
  try {
    await server.newOrganization({name: form.value.name, parent: parentId()})
    notification.success({message: '新增成功'})
    emit('success')
    visible.value = false;
  } catch (e) {
    notification.error({message: "错误", description: e as string})
  }
}

defineExpose({
  show
})
</script>

<style scoped></style>
