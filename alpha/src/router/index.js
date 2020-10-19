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
  redirect:'/mainfac',
  children:[
    // {path:'/welcome', component: welcome},
    {path:'/fac1', component: fac1},
    {path:'/mainfac', component: mainfac,},
  ]},
  


  {path:'/register', component: register },

  {path:'/homepage', 
  component: homepage, 
  redirect:'/welcome',
  children:[
    {path:'/welcome', component: welcome},
    {path:'/intro', component: introduct},
    {path:'/aboutus', component: aboutus},
  ] },

  
]

const router = new VueRouter({
  // mode:'history',
  routes
})

export default router
