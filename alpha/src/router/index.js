import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/components/login'
import home from '@/components/home'
import register from '@/components/register'
import homepage from '@/components/homepage'
import welcome from '@/components/welcome'
import introduct from '@/components/inroduct/introduct'
import aboutus from '@/components/aboutus/aboutus'
import fac1 from '@/components/factory/fac1'
import mainfac from '@/components/factory/mainfac'
import allchart from '@/components/factory/mainchart/allchart'
import linechart from '@/components/factory/mainchart/linechart'
import barchart from '@/components/factory/mainchart/barchart'
Vue.use(VueRouter)

const routes = [
  {path:'/', redirect:'/homepage'},
  {path:'/login', component: login },

  {path:'/home', 
  component: home,
  redirect:'/fac1',
  children:[
    // {path:'/welcome', component: welcome},
    {path:'/fac1', component: fac1},
    // {path:'/mainfac', component: mainfac,},
  ]},
  


  {path:'/register', component: register },

  {path:'/homepage', 
  component: homepage, 
  redirect:'/welcome',
  children:[
    {path:'/welcome', component: welcome},
    {path:'/intro', component: introduct},
    {path:'/aboutus', component: aboutus},
    {path:'/mainfac', component: mainfac,},
  ] },

  
]

const router = new VueRouter({
  // mode:'history',
  routes
})
//挂载路由导航守卫
router.beforeEach((to,from,next)=>{
  if(to.path === '/login') return next();
  if(to.path === '/welcome') return next();
  if(to.path === '/mainfac') return next();
  if(to.path === '/aboutus') return next();
  const tokenstr=window.sessionStorage.getItem('token');
  if(!tokenstr) return next('/login')
  next()
  
})
export default router
