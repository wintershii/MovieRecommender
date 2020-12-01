import Vue from 'vue'
import Router from 'vue-router'
import log from '@/components/log'
import login from '@/components/login'
import logon from '@/components/logon'
import list from '@/components/list'
import info from '@/components/info'


Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    redirect: '/log'
  }, 
  {
    path: '/log',
    component: log,
    children: [{
      path: '',
      component: login
    }, {
      path: 'login',
      component: login
    },
    {
      path: 'logon',
      component: logon
    }
    ]
  }, 
  {
    path: '/list',
    component: list
  },
  {
    path: '/info',
    component: info
  }
  ]
})
