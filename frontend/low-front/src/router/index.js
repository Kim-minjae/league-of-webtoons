import Vue from 'vue'
import Router from 'vue-router'
import SignUp from '@/components/main/SignUp'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'SignUp',
      component: SignUp
    }
  ]
})
