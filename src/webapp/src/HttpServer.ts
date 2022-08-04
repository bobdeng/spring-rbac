export class LoginForm {
    password: string;

    constructor(password: string) {
        this.password = password;
    }
}

export const server = {
    login: (loginForm: LoginForm) => {
        console.log(loginForm.password)
    }
}