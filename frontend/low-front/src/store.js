import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    token: 0,
    id: 0,
    username: '',
    useremail: ''
  },
  mutations: {
    setToken: function (state, payload) {
      state.token = payload
    },
    setUser: function (state, payload) {
      state.id = payload.id
      state.username = payload.username
      state.useremail = payload.useremail
    }
  },
  actions: {
    getUserInfo: function ({commit, state}) {
      axios.get('/api/users/me', {
        headers: {
          Authorization: 'Bearer ' + state.token
        }
      }).then(response => {
        console.log(response)
        commit('setUser', response.data)
      })
    }
  }

})
