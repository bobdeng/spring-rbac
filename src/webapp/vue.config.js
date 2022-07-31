const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    outputDir: "../main/webapp/WEB-INF/templates/admin",
    publicPath: "/admin/"
})
