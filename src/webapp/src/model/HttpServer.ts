import axios from "axios";

export class LoginForm {
    password: string;

    constructor(password: string) {
        this.password = password;
    }
}

export const server = {
    login: async (loginForm: LoginForm) => {
        const {data, status} = await axios.post("/api/rbac/admin/sessions", {password: loginForm.password})
        if (status != null) {
            return Promise.reject(data)
        }
        return data;
    }
}