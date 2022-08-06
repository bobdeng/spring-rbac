import {Home, Tenants, Console, Domain} from "./views";
import {createRouter, createWebHashHistory} from "vue-router";

export const routes = [
    {path: "/", component: Home, name: "home"},
    {
        path: "/console", component: Console, name: "console",
        children: [
            {path: "/tenants", component: Tenants, name: "tenants"},
            {path: "/tenants/:id/domains", component: Domain, name: "tenantDomains"}
        ]
    }

];

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
