import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/CSS/global.css'
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
import axios from 'axios'
Vue.prototype.$axios= axios
// import VueAxios from 'vue-axios'
// import {post,fetch,patch,put} from "../static/http.js";
// Vue.prototype.$axios=axios;
// Vue.prototype.$post=post;
// Vue.prototype.$fetch=fetch;
// Vue.prototype.$patch=patch;
// Vue.prototype.$put=put;   
//axios.defaults.baseURL = '请求根路径：接口基准地址'
//问后端
// Vue.prototype.axios = axios

// Vue.config.productionTip = false

// Vue.use(VueAxios,axios)
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
