<template>
  <div>
    <Spin :spinning="loading">
      <PageHeader :title="name">
        <template #extra>
          <Button type="primary" id="buttonAdd" @click="addOrganization">添加下级单位</Button>
        </template>
      </PageHeader>
      <Divider/>
    </Spin>
    <Employees :organization="organization" v-if="organization"/>
    <AddSubOrganization :parent="organization" ref="newOrganizationModal" @success="onSuccess"/>
  </div>
</template>

<script setup lang="ts">
import {computed, ref} from "vue";
import {Button, PageHeader, Divider} from "ant-design-vue";
import AddSubOrganization from "./AddSubOrganization.vue";
import Employees from "./Employees.vue";

const name = computed(() => {
  if (!props.organization) {
    return ""
  }
  return props.organization.description.name;
})
const props = defineProps(['organization'])
const loading = ref(false)
const newOrganizationModal = ref()
const emit = defineEmits(['add'])
const addOrganization = () => {
  newOrganizationModal.value.show()
}
const onSuccess = () => {
  emit('add')
}
</script>

<style scoped></style>