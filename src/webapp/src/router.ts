import {Home, Console} from "./views";
import {createRouter, createWebHashHistory} from "vue-router";

const routes = [
    {path: "/", component: Home, name: "home"},
    {path: "/console", component: Console, name: "console"},
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
