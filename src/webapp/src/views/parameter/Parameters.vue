<template>
  <div>
    <Spin :spinning="loading">
      <Form :label-col="{span:16}" :wrapper-col="{span:8}"
            label-align="left">
        <FormItem :label="parameter.description.name" v-for="parameter in parameters">
          <Input v-model:value="parameter.description.value"/>
        </FormItem>
      </Form>
      <Button type="primary" :loading="loading" @click="save" id="buttonSave">保存</Button>
    </Spin>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {Parameter, server} from "../../model/HttpServer";
import {Form, FormItem, Input, Button, Spin, notification} from "ant-design-vue";

const loading = ref(false)
const parameters = ref([] as Parameter[])

async function load() {
  loading.value = true
  try {
    parameters.value = await server.listParameters();
  } finally {
    loading.value = false
  }
}

async function save() {
  loading.value = true
  try {
    await server.setParameters(parameters.value.map(it => {
      return {
        name: it.description.key,
        value: it.description.value
      }
    }))
    notification.success({message: "保存成功"})
  } finally {
    loading.value = false
  }
}

load()
</script>

<style scoped></style>