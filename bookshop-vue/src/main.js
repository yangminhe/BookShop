/*eslint-disable */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Axios from 'axios'
import VueAxios from "vue-axios";

Vue.use(ElementUI)
Vue.use(VueAxios,Axios)
// Axios.defaults.baseURL = 'http://192.168.1.100:8443/api'
Axios.defaults.baseURL = 'http://localhost:8443/api'
Axios.defaults.withCredentials=true;
Vue.config.productionTip = false


router.afterEach((to, from, next) => {
  document.querySelector("body").setAttribute("style", "overflow: auto !important;")
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
