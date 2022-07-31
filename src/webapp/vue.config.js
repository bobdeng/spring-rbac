const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    filenameHashing: false,
    outputDir: "../main/resources/static/admin",
    publicPath: "/admin/"
})
