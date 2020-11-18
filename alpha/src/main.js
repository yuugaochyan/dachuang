import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/CSS/global.css'
import 'lib-flexible/flexible'
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
import axios from 'axios'
Vue.prototype.$axios= axios
import qs from 'qs';
Vue.prototype.$qs = qs;
import dataV from '@jiaminghi/data-view';
Vue.config.productionTip = false;
Vue.use(dataV);
import Router from 'vue-router'
const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
return routerPush.call(this, location).catch(error=> error)
}
// import mqtt from 'mqtt';
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
