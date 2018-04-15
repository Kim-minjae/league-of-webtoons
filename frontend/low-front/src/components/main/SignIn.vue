<template>
  <div id="signin">
    <img src="@/assets/logo.png"/>
    <div id="signin-box">
      <div class="form-group">
        <label>email</label>
        <input class="form-control" v-model="email"/>
      </div>
      <div class="form-group">
        <label>password</label>
        <input type="password" class="form-control" v-model="password"/>
      </div>
      <div class="form-group">
        <button class="btn btn-default" @click="_signIn">로그인</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import store from '@/store'

const headers = {
  'Content-type': 'application/json'
}
export default {
  name: 'sign-in',
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    _signIn: function () {
      const { email, password } = this
      this.store.dispatch('authRequest', { email, password }).then(() => {
        this.$router.push('/')
      }).catch(error => {
        if (error.response.status === 401) {
          alert('회원 정보를 확인하세요.')
        }
      })
    }
    
    // _signIn: function () {
    //   axios.post("/api/auth/signin",{
    //     userEmail: this.email,
    //     password: this.password
    //   }).then(response => {
    //     if (response.status === 200) {
    //       alert('로그인 성공')
    //       this.$store.commit('setToken',response.data.accessToken)
    //       this.$store.dispatch('setUserInfo').then((response) => {
    //         console.log(response)
    //         this.$router.push('/')
    //       })
    //     }
    //   }).catch(error => {
    //     if (error.response.status === 401) {
    //       alert('회원 정보를 확인하세요.')
    //     }
    //   })
    // }
  }
}
</script>

<style scoped>
#signin{
  text-align: center
}
#signin-box{
  border: 1px solid;
  padding: 10px;
  box-shadow: 2px 3px #888888;
  margin: auto;
  width:300px;
}
</style>
