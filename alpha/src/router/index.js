import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/components/login'
import register from '@/components/register'
import homepage from '@/components/homepage'
import welcome from '@/components/welcome'
import introduct from '@/components/inroduct/introduct'
import aboutus from '@/components/aboutus/aboutus'
import mainfac from '@/components/factory/mainfac'
import coshome from '@/components/costom/coshome'
import dashboard from '@/components/costom/dashboard'
import griddemo from '@/components/costom/griddemo'
import createdb from '@/components/costom/createdb'
import createtb from '@/components/costom/createtb'
import createchart from '@/components/costom/formcreate/createchart'
import createtable from '@/components/costom/formcreate/createtable'
import createmqttline from '@/components/costom/formcreate/createmqttline'
import createmqttnum from '@/components/costom/formcreate/createmqttnum'
import viewdb from '@/components/costom/dbmanage/viewdb'
import editdb from '@/components/costom/dbmanage/editdb'
import viewtb from '@/components/costom/dbmanage/viewtb'
import edittb from '@/components/costom/dbmanage/edittb'
Vue.use(VueRouter)

const routes = [
  {path:'/', redirect:'/homepage'},
  {path:'/login', component: login },

  
  


  {path:'/register', component: register },

  {path:'/homepage', 
  component: homepage, 
  redirect:'/welcome',
  children:[
    {path:'/welcome', component: welcome},
    {path:'/intro', component: introduct},
    {path:'/aboutus', component: aboutus},
    {path:'/coshome', 
    component: coshome,
    name:"coshome",
    redirect:'/createdb',
    children:[
      {path:'/createdb', component: createdb,name:"createdb"},
      {path:'/createtb', component: createtb,name:"createtb"},
      {path:'/griddemo', component: griddemo,name:"griddemo"},
      {path:'/dashboard/:dbID', component: dashboard,name:"dashboard"},
      {path:'/createchart', component: createchart,name:"createchart"},
      {path:'/createtable', component: createtable,name:"createtable"},
      {path:'/createmqttline', component: createmqttline,name:"createmqttline"},
      {path:'/createmqttnum', component: createmqttnum,name:"createmqttnum"},
      {path:'/viewdb/:dbID', component: viewdb,name:"viewdb"},
      {path:'/editdb/:dbID', component: editdb,name:"editdb"},
      {path:'/viewtb/:tbID', component: viewtb,name:"viewtb"},
      {path:'/edittb/:tbID', component: edittb,name:"edittb"},
    ]},
    
    // {path:'/mainfac', component: mainfac,},
  ] },
  {path:'/mainfac', component: mainfac,},
  

  
]

const router = new VueRouter({
  // mode:'history',
  routes
})
//挂载路由导航守卫
router.beforeEach((to,from,next)=>{
  if(to.path === '/login') return next();
  if(to.path === '/register') return next();
  if(to.path === '/welcome') return next();
  if(to.path === '/mainfac') return next();
  if(to.path === '/aboutus') return next();
  const tokenstr=window.localStorage.getItem('token');
  if(!tokenstr) return next('/login')
  next()
  
})
export default router
