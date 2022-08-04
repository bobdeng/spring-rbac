import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    base: "admin",
    build: {
        outDir: "../main/resources/static/admin",
        rollupOptions: {
            output: {
                assetFileNames: "assets/[name][extname]",
                chunkFileNames: "assets/[name].js",
                entryFileNames: "assets/[name].js"
            }
        }
    }
})
