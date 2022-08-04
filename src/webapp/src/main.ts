import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import Antd from 'ant-design-vue';
import { pinia } from "./store";
import { router } from "./router";
import 'ant-design-vue/dist/antd.css';

const app = createApp(App);

app.use(router);
app.use(pinia);
app.use(Antd)

app.mount("#app");
