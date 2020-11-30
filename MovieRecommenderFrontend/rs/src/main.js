// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import {getInfo,batchSend} from '../static/analytics.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false

Vue.use(ElementUI)

Vue.directive("click-stat",{
  bind(el,binding){
    el.addEventListener("click",()=>{
      var clickData = binding.value;
      // console.log(clickData)
      var info;
      info = getInfo(clickData);
      batchSend(info);
    })
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
