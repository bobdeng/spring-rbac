import axios, {AxiosRequestConfig} from "axios";
import {Ref, UnwrapRef} from "vue";

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
        return Promise.reject(response.data.map((error: any) => error.error).join("\n"))
    }
    return Promise.reject(response.data)
}

export const server = {
    login: async (loginForm: LoginForm) => {
        return await ajax(() => axios.post("/admin_sessions", {password: loginForm.password}, config));
    },
    listTenants: async (name: string) => {
        let requestConfig = getConfig();
        requestConfig.params = {
            name: name
        }
        return await ajax(() => axios.get(`/tenants`, requestConfig));
    },
    async newTenant(param: { name: string }) {
        return await ajax(() => axios.post("/tenants", param, config))
    },
    async listDomains(tenantId: string) {
        return await ajax(() => axios.get(`/tenants/${tenantId}/domains`, config))
    },
    async newTenantDomain(param: { name: string; tenant: any }) {
        return await ajax(() => axios.post("/domains", param, config))
    },
    async deleteDomain(tenantId: any) {
        return await ajax(() => axios.delete(`/domains/${tenantId}`, config))
    },
    async listRolesOfTenant(tenantId: any) {
        return await ajax(() => axios.get(`/tenants/${tenantId}/roles`, config));
    },
    async listRoles() {
        return await ajax(() => axios.get(`/roles`, config));
    },
    async listFunctions(): Promise<Function[]> {
        return await ajax(() => axios.get("/functions", config)) as Function[];
    },
    async newTenantRole(param: { allows: string[]; name: string; tenant: any }) {
        return await ajax(() => axios.post(`/tenants/${param.tenant}/roles`, param, config))
    },
    async getRole(param: { role: any; tenant: any }) {
        return await ajax(() => axios.get(`/tenants/${param.tenant}/roles/${param.role}`, config))
    },
    async saveTenantRole(param: { allows: string[], role: string; name: string; tenant: any }) {
        return await ajax(() => axios.patch(`/tenants/${param.tenant}/roles/${param.role}`, param, config))
    },
    async deleteRole(param: { role: any; tenant: any }) {
        return await ajax(() => axios.delete(`/tenants/${param.tenant}/roles/${param.role}`, config))
    },
    async userLogin(param: { password: string; loginName: string }) {
        return await ajax(() => axios.post("/user_sessions", param, config))
    },
    async listUsers(keyword: string) {
        return await ajax(() => axios.get(`/users?name=${keyword}`, config));
    },
    async newUser(form: { password: string; loginName: string; roles: any[]; name: string }) {
        return await ajax(() => axios.post("/users", form, config))
    },
    async getTenant() {
        return await ajax(() => axios.get("/tenant", config))
    },
    async resetPassword(param: { userId: any }) {
        return await ajax(() => axios.patch(`/users/${param.userId}/password`, {}, config))
    },
    async setPassword(param: { password: string; newPassword: string }) {
        return await ajax(() => axios.put("/password", param, config))
    },
    async lockUser(userId: any) {
        return await ajax(() => axios.post(`/users/${userId}/lock`, {}, config))
    },
    async unlockUser(userId: any) {
        return await ajax(() => axios.delete(`/users/${userId}/lock`, config))
    }
}