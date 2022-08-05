import axios, {AxiosRequestConfig} from "axios";

export class LoginForm {
    password: string;

    constructor(password: string) {
        this.password = password;
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
    async listDomains(id: string) {
        return await ajax(() => axios.get(`/tenants/${id}/domains`, config))
    },
    async newTenantDomain(param: { name: string; tenant: any }) {
        return await ajax(() => axios.post("/domains", param, config))
    }
}