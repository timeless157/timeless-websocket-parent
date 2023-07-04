const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 关闭eslint
  lintOnSave: false,

  devServer: {
    port: 8081,
    // proxy: {
    //   '/api': {
    //     target: 'http://127.0.0.1:8082',
    //     changeOrigin: true, //是否跨域
    //     pathRewrite: { '^/api': '' },
    //   }
    // }
  }

})
