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
    <AddSubOrganization :parent="organization" ref="newOrganizationModal"/>
  </div>
</template>

<script setup lang="ts">
import {computed, ref} from "vue";
import {Button, PageHeader, Divider} from "ant-design-vue";
import AddSubOrganization from "./AddSubOrganization.vue";

const name = computed(() => {
  if (!props.organization) {
    return ""
  }
  return props.organization.description.name;
})
const props = defineProps(['organization'])
const loading = ref(false)
const newOrganizationModal = ref()
const addOrganization = () => {
  newOrganizationModal.value.show()
}
</script>

<style scoped></style>