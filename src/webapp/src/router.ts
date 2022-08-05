import {Home, Tenants, Console} from "./views";
import {createRouter, createWebHashHistory} from "vue-router";

export const routes = [
    {path: "/", component: Home, name: "home"},
    {
        path: "/console", component: Console, name: "console",
        children: [
            {path: "/tenants", component: Tenants, name: "tenants"}
        ]
    }

];

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
