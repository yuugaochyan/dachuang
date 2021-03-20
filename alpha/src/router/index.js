import Vue from 'vue'
import VueRouter from 'vue-router'






Vue.use(VueRouter)

const routes = [
  {path:'/', redirect:'/login'},
  {path:'/login', component:resolve => require(['@/components/login'],resolve)},
  {path:'/register', component: resolve => require(['@/components/register'],resolve) },

  {path:'/homepage', 
  component: resolve => require(['@/components/homepage'],resolve), 
  redirect:'/welcome',
  children:[
    {path:'/welcome', component: resolve => require(['@/components/welcome'],resolve)},
    {path:'/intro', component: resolve => require(['@/components/inroduct/introduct'],resolve)},
    {path:'/aboutus', component: resolve => require(['@/components/aboutus/aboutus'],resolve)},
    
    
      {path:'/createdb', component: resolve => require(['@/components/costom/createdb'],resolve),name:"createdb"},
      {path:'/tbmanage', component: resolve => require(['@/components/costom/tbmanage'],resolve),name:"tbmanage"},
      {path:'/createtb', component: resolve => require(['@/components/costom/createtb'],resolve),name:"createtb"},
      {path:'/createchart', component: resolve => require(['@/components/costom/formcreate/createchart'],resolve),name:"createchart"},
      {path:'/createtable', component: resolve => require(['@/components/costom/formcreate/createtable'],resolve),name:"createtable"},
      {path:'/createmqttline', component: resolve => require(['@/components/costom/formcreate/createmqttline'],resolve),name:"createmqttline"},
      {path:'/createmqttnum', component: resolve => require(['@/components/costom/formcreate/createmqttnum'],resolve),name:"createmqttnum"},
      {path:'/viewdb/:dbID', component: resolve => require(['@/components/costom/dbmanage/viewdb'],resolve),name:"viewdb"},
      {path:'/editdb/:dbID', component: resolve => require(['@/components/costom/dbmanage/editdb'],resolve),name:"editdb"},
      {path:'/viewtb/:tbID', component: resolve => require(['@/components/costom/dbmanage/viewtb'],resolve),name:"viewtb"},
      {path:'/editchart/:tbID', component:resolve => require(['@/components/costom/dbmanage/editchart'],resolve),name:"editchart"},
      {path:'/edittable/:tbID', component: resolve => require(['@/components/costom/dbmanage/edittable'],resolve),name:"edittable"},
      {path:'/editmqttline/:tbID', component: resolve => require(['@/components/costom/dbmanage/editmqttline'],resolve),name:"editmqttline"},
      {path:'/editmqttnum/:tbID', component: resolve => require(['@/components/costom/dbmanage/editmqttnum'],resolve),name:"editmqttnum"},
      {path:'/datamanage/:tableID', component: resolve => require(['@/components/costom/datamanage/staticdata'],resolve),name:"staticdata"},
      {path:'/dynamicdata', component: resolve => require(['@/components/costom/datamanage/dynamicdata'],resolve),name:"dynamicdata"},
    ,
    
    // {path:'/mainfac', component: mainfac,},
  ] },
  // {path:'/mainfac', component: mainfac,},
  

  
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
