<template>
  <div>
    <Spin :spinning="loading">
      <Tree
          :fieldNames="{title:'name'}"
          :treeData="organizations"
          v-model:expandedKeys="expandedKeys"
          @select="onSelect"
      >
      </Tree>
    </Spin>
  </div>
</template>

<script setup lang="ts">
import {Tree,Spin} from "ant-design-vue";
import {ref} from "vue";
import {server} from "../../../model/HttpServer";

const organizations = ref([] as OrganizationTreeItem[])
const expandedKeys = ref<number[]>([])
const loading = ref(true)
const emit = defineEmits(['select'])
let organizationsData: OrganizationsData;
const reload = () => {
  loading.value = true
  server.getOrganizations().then(resp => {
    organizationsData = new OrganizationsData(resp);
    organizations.value = organizationsData.tree
    expandedKeys.value = organizationsData.all
    loading.value = false
  })
}
const onSelect = (selectedKeys: any) => {
  emit("select", organizationsData.findById(selectedKeys[0]))
}
reload();
defineExpose({
  reload
})
</script>
<script lang="ts">
import {Organization} from "../../../model/HttpServer";

class OrganizationTreeItem {
  key: number
  name: string
  children: OrganizationTreeItem[]


  constructor(key: number, name: string, children: OrganizationTreeItem[]) {
    this.key = key;
    this.name = name;
    this.children = children;
  }
}

class OrganizationsData {
  private readonly _organizations: Organization[];

  constructor(organizations: any) {
    this._organizations = organizations;
  }

  get tree() {
    const root = this._organizations.filter((organization: Organization) => !organization.description.parent)
        .map(it => new OrganizationTreeItem(it.id, it.description.name, []));
    this.readSubOrganizations(root)
    return root;
  }

  findById(id: any) {
    return this._organizations.filter(it => it.id == id)[0]
  }

  get all() {
    return this._organizations.map(it => it.id)
  }

  private readSubOrganizations(organizations: OrganizationTreeItem[]) {
    organizations.forEach(organization => {
      organization.children = this._organizations.filter(it => it.description.parent === organization.key)
          .map(it => new OrganizationTreeItem(it.id, it.description.name, []))
      this.readSubOrganizations(organization.children)
    })
  }
}
</script>
<style scoped></style>
