const { defineConfig } = require('@vue/cli-service')
// const webpack = require('webpack')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  // devServer:{
  //   port:"8080",
  //   proxy: {
  //     '/api': {
  //       target: 'http://127.0.0.1:5050',//要访问的后端接口
  //       changeOrigin: true,
  //       ws: true,
  //       pathRewrite: {
  //         '^/api': '/'
  //       }
  //     }
  //   }
  // }
})
