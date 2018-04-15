import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('user-token') || '',
    status: ''
  },
  getters: {
    isAuthenticated: state => !!state.token,
    authStatus: state => state.status
  },
  actions: {
    authRequest: ({commit, dispatch}, user) => {
      return new Promise((resolve, reject) => { // The Promise used for router redirect in login
        commit('authRequest')
        axios.post('/api/auth/signin', user)
          .then(resp => {
            const token = resp.data.accessToken
            localStorage.setItem('user-token', token)
            // Add the following line:
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
            commit('authSuccess', resp)
            // dispatch('userRequest')
            resolve(resp)
          })
          .catch(err => {
            commit('authError', err)
            localStorage.removeItem('user-token') // if the request fails, remove any possible user token if possible
            reject(err)
          })
      })
    },
    authLogout: ({commit, dispatch}) => {
      return new Promise((resolve, reject) => {
        commit('authLogout')
        localStorage.removeItem('user-token')
        // remove the axios default header
        delete axios.defaults.headers.common['Authorization']
        resolve()
      })
    }
  },
  mutations: {
    authLogout: (state) => {
      state.token = ''
    },
    authRequest: (state) => {
      state.status = 'loading'
    },
    authSuccess: (state, token) => {
      state.status = 'success'
      state.token = token
    },
    authError: (state) => {
      state.status = 'error'
    }
  }
})
