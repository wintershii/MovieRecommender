import Vue from 'vue'
import Router from 'vue-router'
import List from '@/components/List'
import Info from '@/components/Info'
import Login from '@/components/Login'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      meta:{
        keepalive:false
      }
    },
    // {
    //   path:'*',
    //   redirect:'/'
    // },
    {
      path: '/list',
      name: 'List',
      component: List
    },
    {
      path: '/info',
      name: 'Info',
      component: Info
    }
  ]
})

var app = new Vue(
  {
    data: {},
    el: '#app',
    render: h => h(App),
    router,
    store,
    created() {
      this.checkLogin();
    },
    methods:{
      checkLogin() {
        if(!this.getCookie('uid')) {
          this.$router.push('/login')
        } else {
          this.$router.push('list')
        }
      }
    }
  }
)