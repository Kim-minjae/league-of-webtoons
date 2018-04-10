import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    token: 0
  },
  mutations: {
    setToken: function (state, payload) {
      state.counter = payload
    }
  }
})
