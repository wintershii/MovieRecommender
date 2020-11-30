import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import TAction from '@/components/TAction'
import TShow from '@/components/TShow'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '*',
      redirect: '/taction'
    },
    {
      path: '/taction',
      name: 'TAction',
      component: TAction
    },
    {
      path: '/tshow',
      name: 'TShow',
      component: TShow
    }
  ]
})
