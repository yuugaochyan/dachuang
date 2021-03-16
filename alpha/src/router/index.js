import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/components/login'
import register from '@/components/register'
import homepage from '@/components/homepage'
import welcome from '@/components/welcome'
import introduct from '@/components/inroduct/introduct'
import aboutus from '@/components/aboutus/aboutus'
import mainfac from '@/components/factory/mainfac'
import createdb from '@/components/costom/createdb'
import createtb from '@/components/costom/createtb'
import createchart from '@/components/costom/formcreate/createchart'
import createtable from '@/components/costom/formcreate/createtable'
import createmqttline from '@/components/costom/formcreate/createmqttline'
import createmqttnum from '@/components/costom/formcreate/createmqttnum'
import viewdb from '@/components/costom/dbmanage/viewdb'
import editdb from '@/components/costom/dbmanage/editdb'
import viewtb from '@/components/costom/dbmanage/viewtb'
import editchart from '@/components/costom/dbmanage/editchart'
import edittable from '@/components/costom/dbmanage/edittable'
import editmqttline from '@/components/costom/dbmanage/editmqttline'
import editmqttnum from '@/components/costom/dbmanage/editmqttnum'
import tbmanage from '@/components/costom/tbmanage'
import staticdata from '@/components/costom/datamanage/staticdata'
import dynamicdata from '@/components/costom/datamanage/dynamicdata'
Vue.use(VueRouter)

const routes = [
  {path:'/', redirect:'/login'},
  {path:'/login', component: login },

  
  


  {path:'/register', component: register },

  {path:'/homepage', 
  component: homepage, 
  redirect:'/welcome',
  children:[
    {path:'/welcome', component: welcome},
    {path:'/intro', component: introduct},
    {path:'/aboutus', component: aboutus},
    
    
      {path:'/createdb', component: createdb,name:"createdb"},
      {path:'/tbmanage', component: tbmanage,name:"tbmanage"},
      {path:'/createtb', component: createtb,name:"createtb"},
      {path:'/createchart', component: createchart,name:"createchart"},
      {path:'/createtable', component: createtable,name:"createtable"},
      {path:'/createmqttline', component: createmqttline,name:"createmqttline"},
      {path:'/createmqttnum', component: createmqttnum,name:"createmqttnum"},
      {path:'/viewdb/:dbID', component: viewdb,name:"viewdb"},
      {path:'/editdb/:dbID', component: editdb,name:"editdb"},
      {path:'/viewtb/:tbID', component: viewtb,name:"viewtb"},
      {path:'/editchart/:tbID', component: editchart,name:"editchart"},
      {path:'/edittable/:tbID', component: edittable,name:"edittable"},
      {path:'/editmqttline/:tbID', component: editmqttline,name:"editmqttline"},
      {path:'/editmqttnum/:tbID', component: editmqttnum,name:"editmqttnum"},
      {path:'/datamanage/:tableID', component: staticdata,name:"staticdata"},
      {path:'/dynamicdata', component: dynamicdata,name:"dynamicdata"},
    ,
    
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
  // if(to.path === '/welcome') return next();
  // if(to.path === '/mainfac') return next();
  // if(to.path === '/aboutus') return next();
  const userID=window.localStorage.getItem('userID');
  if(!userID) return next('/login')
  next()
  
})
export default router
