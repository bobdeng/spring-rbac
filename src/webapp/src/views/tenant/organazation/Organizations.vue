<template>
  <div>
    <Layout style="min-height: 400px;">
      <LayoutSider>
        <OrganizationTree @select="onOrganizationSelect" ref="tree"/>
      </LayoutSider>
      <LayoutContent>
        <OrganizationView :organization="organization" @add="onAdd"/>
      </LayoutContent>
    </Layout>
  </div>
</template>

<script setup lang="ts">
import {Layout, LayoutSider, LayoutContent} from "ant-design-vue";
import OrganizationTree from "./OrganizationTree.vue";
import OrganizationView from "./Organization.vue";
import {Organization} from "../../../model/HttpServer";
import {ref} from "vue";

const organization = ref<Organization>()
const tree = ref()
const onOrganizationSelect = (selected: any) => {
  organization.value = selected;
}
const onAdd = () => {
  tree.value.reload()
}
</script>

<style scoped></style>
