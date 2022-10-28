const { defineConfig } = require('@vue/cli-service');
//跨域访问
module.exports = {
  devServer: {
    port: 8082,
    host:'autoimpactanalysisvue.com.cn',
    proxy: {
      '/api': {
        target: 'http://localhost:3300',
        changeOrigin: true,
        pathRewrite: {
          '^/api': 'autoimpactanalysis'
        }
      }
    }
  }
};
