<template>
  <div>
    <Button type="primary" @click="show" id="buttonShow">新增</Button>
    <Modal v-if="visible" id="dialog" v-model:visible="visible">
    </Modal>
  </div>
</template>

<script setup lang="ts">
import {Modal, Button} from "ant-design-vue";
import {ref} from "vue";
import 'ant-design-vue/dist/antd.css';

const visible = ref(false)
const show = () => {
  visible.value = true
}
defineExpose({
  show
})
</script>

<style scoped></style>
