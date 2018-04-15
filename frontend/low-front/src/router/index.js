import Vue from 'vue'
import Router from 'vue-router'
import SignUp from '@/components/main/SignUp'
import SignIn from '@/components/main/SignIn'
import BattleMain from '@/components/battle/BattleMain'
import MyPage from '@/components/main/MyPage'
import UploadWebtoon from '@/components/webtoon/UploadWebtoon'
import store from '@/store' // your vuex store 
Vue.use(Router)

const ifNotAuthenticated = (to, from, next) => {
  if (!store.getters.isAuthenticated) {
    next()
    return
  }
  next('/')
}

const ifAuthenticated = (to, from, next) => {
  if (store.getters.isAuthenticated) {
    next()
    return
  }
  next('/signin')
}
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
      component: SignIn,
      beforeEnter: ifNotAuthenticated
    },
    {
      path: '/mypage',
      name: 'MyPage',
      component: MyPage,
      beforeEnter: ifAuthenticated
    },
    {
      path: '/webtoon',
      name: 'Webtoon',
      component: UploadWebtoon,
      beforeEnter: ifAuthenticated
    },
    {
      path: '/battle',
      name: 'Battle',
      component: BattleMain
    }
  ]
})
