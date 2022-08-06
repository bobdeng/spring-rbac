import {Home, Tenants, Console, Domain, Role} from "./views";
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
    }

];

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
