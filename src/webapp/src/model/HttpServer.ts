import axios, {AxiosRequestConfig} from "axios";

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
    if (response.status !== 200) {
        return Promise.reject(response.data || response.status)
    }
    return Promise.resolve(response.data);
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
    async listRoles(tenantId: any) {
        return await ajax(() => axios.get(`/tenants/${tenantId}/roles`, config));
    },
    async listFunctions(): Promise<Function[]> {
        return await ajax(() => axios.get("/functions", config)) as Function[];
    }
}