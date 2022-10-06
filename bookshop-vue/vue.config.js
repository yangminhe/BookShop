module.exports = {
    devServer: {
      proxy: {
        '/api': {
          // target: 'http://192.168.1.100:8443',
          target: 'http://localhost:8443',
          changeOrigin: true,
          ws: true,
          pathRewrite: {
            '^/api': ''
          }
        }
      }
    },
    lintOnSave: false
  }