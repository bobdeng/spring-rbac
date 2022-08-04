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

export async function ajax(fun: any) {
    const response = await fun();
    if (response.status !== 200) {
        return Promise.reject(response.data || response.status)
    }
    return Promise.resolve(response.data);
}

export const server = {
    login: async (loginForm: LoginForm) => {
        return await ajax(() => axios.post("/api/rbac/admin/sessions", {password: loginForm.password}, config));
    }
}