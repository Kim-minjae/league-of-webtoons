import Vue from 'vue'
import Router from 'vue-router'
import SignUp from '@/components/main/SignUp'
import SignIn from '@/components/main/SignIn'
import BattleMain from '@/components/battle/BattleMain'
Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/signup',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/signin',
      name: 'SignIn',
      component: SignIn
    },
    {
      path: '/battle',
      name: 'Battle',
      component: BattleMain
    }
  ]
})
