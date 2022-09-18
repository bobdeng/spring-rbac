import axios, {AxiosRequestConfig} from "axios";

const apiPrefix = "/api/1.0/"

export class LoginForm {
  password: string;

  constructor(password: string) {
    this.password = password;
  }
}

export class Function {
  name: string;
  key: string;
  children: Function[];

  constructor(name: string, key: string, children: Function[]) {
    this.name = name;
    this.key = key;
    this.children = children;
  }
}

export class Parameter {
  id: string
  description: {
    name: string,
    value: string
  }

  constructor(id: string, description: { name: string; value: string }) {
    this.id = id;
    this.description = description;
  }
}

export class Organization {
  id: number
  description: { name: string, parent: number }

  constructor(id: number, description: { name: string; parent: number }) {
    this.id = id;
    this.description = description;
  }
}

export class WxConfig {
  appId: string
  callback: string
  code: string

  constructor(appId: string, callback: string, code: string) {
    this.appId = appId;
    this.callback = callback;
    this.code = code;
  }
}

const config: AxiosRequestConfig = {
  validateStatus: () => true
}

function getConfig(): AxiosRequestConfig {
  return {
    validateStatus: () => true
  }
}

export async function ajax(fun: any) {
  const response = await fun();
  if (response.status === 200) {
    return Promise.resolve(response.data);
  }
  if (!response.data) {
    return Promise.reject(response.status)
  }
  if (Array.isArray(response.data)) {
    return Promise.reject(response.data.map((error: any) => error.error).join("；"))
  }
  return Promise.reject(response.data)
}

export const server = {
  login: async (loginForm: LoginForm) => {
    return await ajax(() => axios.post(`${apiPrefix}admin_sessions`, {password: loginForm.password}, config));
  },
  listTenants: async (name: string) => {
    let requestConfig = getConfig();
    requestConfig.params = {
      name: name
    }
    return await ajax(() => axios.get(`${apiPrefix}tenants`, requestConfig));
  },
  async newTenant(param: { name: string }) {
    return await ajax(() => axios.post("${apiPrefix}tenants", param, config))
  },
  async listDomains(tenantId: string) {
    return await ajax(() => axios.get(`${apiPrefix}tenants/${tenantId}/domains`, config))
  },
  async newTenantDomain(param: { name: string; tenant: any }) {
    return await ajax(() => axios.post(`${apiPrefix}domains`, param, config))
  },
  async deleteDomain(tenantId: any) {
    return await ajax(() => axios.delete(`${apiPrefix}domains/${tenantId}`, config))
  },
  async listRolesOfTenant(tenantId: any) {
    return await ajax(() => axios.get(`${apiPrefix}tenants/${tenantId}/roles`, config));
  },
  async listRoles() {
    return await ajax(() => axios.get(`${apiPrefix}roles`, config));
  },
  async listFunctions(): Promise<Function[]> {
    return await ajax(() => axios.get("${apiPrefix}functions", config)) as Function[];
  },
  async newTenantRole(param: { allows: string[]; name: string; tenant: any }) {
    return await ajax(() => axios.post(`${apiPrefix}tenants/${param.tenant}/roles`, param, config))
  },
  async getRole(param: { role: any; tenant: any }) {
    return await ajax(() => axios.get(`${apiPrefix}tenants/${param.tenant}/roles/${param.role}`, config))
  },
  async saveTenantRole(param: { allows: string[], role: string; name: string; tenant: any }) {
    return await ajax(() => axios.patch(`${apiPrefix}tenants/${param.tenant}/roles/${param.role}`, param, config))
  },
  async deleteRole(param: { role: any; tenant: any }) {
    return await ajax(() => axios.delete(`${apiPrefix}tenants/${param.tenant}/roles/${param.role}`, config))
  },
  async userLogin(param: { password: string; loginName: string }) {
    return await ajax(() => axios.post(`${apiPrefix}user_sessions`, param, config))
  },
  async listUsers(keyword: string) {
    return await ajax(() => axios.get(`${apiPrefix}users?name=${keyword}`, config));
  },
  async newUser(form: { password: string; loginName: string; roles: any[]; name: string }) {
    return await ajax(() => axios.post("${apiPrefix}users", form, config))
  },
  async getTenant() {
    return await ajax(() => axios.get("${apiPrefix}tenant", config))
  },
  async resetPassword(param: { userId: any }) {
    return await ajax(() => axios.patch(`${apiPrefix}users/${param.userId}/password`, {}, config))
  },
  async setPassword(param: { password: string; newPassword: string }) {
    return await ajax(() => axios.put(`${apiPrefix}password`, param, config))
  },
  async lockUser(userId: any) {
    return await ajax(() => axios.post(`${apiPrefix}users/${userId}/lock`, {}, config))
  },
  async unlockUser(userId: any) {
    return await ajax(() => axios.delete(`${apiPrefix}users/${userId}/lock`, config))
  },
  async getOrganizations(): Promise<Organization[]> {
    return await ajax(() => axios.get(`${apiPrefix}organizations`, config)) as Organization[]
  },
  async newOrganization(param: { name: string, parent: number }) {
    return await ajax(() => axios.post(`${apiPrefix}organizations`, param, config))
  },
  async listEmployees(organization: any) {
    return await ajax(() => axios.get(`${apiPrefix}organizations/${organization.id}/employees`, config))
  },
  async putUserToOrganization(param: { organizationId: any; userId: any }) {
    return await ajax(() => axios.put(`${apiPrefix}organizations/${param.organizationId}/employees`, param, config))
  },
  async deleteEmployee(param: { organizationId: any; userId: any }) {
    return await ajax(() => axios.delete(`${apiPrefix}organizations/${param.organizationId}/employees/${param.userId}`, config))

  },
  async getUserLoginName(userId: any) {
    return await ajax(() => axios.get(`${apiPrefix}users/${userId}/login_name`, config))
  },
  async deleteLoginName(id: any) {
    return await ajax(() => axios.delete(`${apiPrefix}login_names/${id}`, config))
  },
  async newLoginName(param: { loginName: string; userId: string }) {
    return await ajax(() => axios.post(`${apiPrefix}login_names`, param, config))
  },
  async getWxConfig(): Promise<WxConfig> {
    return await ajax(() => axios.get(`${apiPrefix}wx_config`, config))
  },
  async listParameters(): Promise<Parameter[]> {
    return await ajax(() => axios.get(`${apiPrefix}parameters`, config));
  },
  async setParameters(values: any) {
    return await ajax(() => axios.put(`${apiPrefix}parameters`, values, config))
  }
}