import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import './plugins/element.js'
import "./router/permission"
import "./assets/css/index.scss"
import moment from 'moment'

/* 富文本*/
import tinymce from 'tinymce'
import VueTinymce from '@packy-tang/vue-tinymce'
Vue.prototype.$tinymce = tinymce;
Vue.use(VueTinymce);
/* 二次封装  */
import vueTinymceEditor from '@/components/vue-tinymce/index';
Vue.component('TinymceEditor', vueTinymceEditor);
import vueTinymceNoEdit from '@/components/tinymcenoedit/index';
Vue.component('TinymceNoEdit', vueTinymceNoEdit);
import vueTinymceView from '@/components/tinymceview/index';
Vue.component('TinymceView', vueTinymceView);

import jquery from 'jquery'
Vue.prototype.$ = jquery;

import child from '@/components/child/index';
Vue.component('Child', child);
import filter from './utils/filter'
for (const key in filter) {
  Vue.filter(key,filter[key]);
}

// 引入
import VueHtml2pdf from 'vue-html2pdf'
// 注册
Vue.component('VueHtml2pdf',VueHtml2pdf);

Vue.filter('dateYMDHMSFormat', function (dateStr, formatString) {
  formatString = formatString || 'YYYY-MM-DD hh:mm:ss';
  return moment(dateStr).format(formatString);
});

Vue.filter('dateYMDFormat', function (dateStr, formatString) {
  formatString = formatString || 'YYYY-MM-DD';
  return moment(dateStr).format(formatString);
});



Vue.use(require('vue-moment'));
Vue.prototype.moment = moment;

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
