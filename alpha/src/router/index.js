import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/components/login'
import home from '@/components/home'
import register from '@/components/register'

Vue.use(VueRouter)

const routes = [
  {path:'/', redirect:'/login'},
  {path:'/login', component: login },
  {path:'/home', component: home },
  {path:'/register', component: register },
]

const router = new VueRouter({
  routes
})

export default router
