<template>
  <div>
    <Layout style="min-height: 400px;">
      <LayoutSider>
        <OrganizationTree @select="onOrganizationSelect"/>
      </LayoutSider>
      <LayoutContent>
        <OrganizationView :organization="organization"/>
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
const onOrganizationSelect = (selected: any) => {
  organization.value = selected;
}
</script>

<style scoped></style>
