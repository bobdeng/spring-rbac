import {Home, Tenants, Console, Domain, Role, UserConsole, Users} from "./views";
import {createRouter, createWebHashHistory} from "vue-router";

export const routes = [
    {path: "/", component: Home, name: "home"},
    {
        path: "/console", component: Console, name: "console",
        children: [
            {path: "/tenants", component: Tenants, name: "tenants"},
            {path: "/tenants/:id/domains", component: Domain, name: "tenantDomains"},
            {path: "/tenants/:id/roles", component: Role, name: "tenantRoles"}
        ]
    },
    {
        path: '/user/console', component: UserConsole, name: "userConsole",
        children: [
            {path: "/users", component: Users, name: "users"}
        ]
    }

];

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
