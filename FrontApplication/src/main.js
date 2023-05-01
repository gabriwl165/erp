import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

// Import Bootstrap and BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
// import LoginPage from "@/views/LoginPage";
// import TelaPrincipal from "@/views/TelaPrincipal";

Vue.config.productionTip = true

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

new Vue({
  router,
  store,
  BootstrapVue,
  IconsPlugin,
  render: h => h(App)
}).$mount('#app')
