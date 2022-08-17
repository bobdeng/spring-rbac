import {defineConfig} from "cypress";

export default defineConfig({
    chromeWebSecurity: false,
    e2e: {
        setupNodeEvents(on, config) {
            // implement node event listeners here
        },
    },

    component: {
        devServer: {
            framework: "vue",
            bundler: "vite",
        },
    },
    viewportWidth: 1000,
    viewportHeight: 660
});
