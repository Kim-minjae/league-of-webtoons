<template>
  <div>
      
      <label>username</label>
      <input class="form-control" v-model="username"/>
      
      <label>email</label>
      <input class="form-control" v-model="email"/>
      
      <label>password</label>
      <input class="form-control" v-model="password"/>

      
      <label>user/webtoonist</label>
      <input type="radio" id="one" value="true" v-model="picked">
      <label for="one">유저</label>
      <br>
      <input type="radio" id="two" value="false" v-model="picked">
      <label for="two">작가</label>

      <button class="btn btn-default" @click="_signUp">가입</button>
  </div>
</template>

<script>
import axios from 'axios'
const headers = {
  'Content-type': 'application/json'
}
export default {
  name: 'sign-up',
  data() {
    return {
      picked: '',
      username: '',
      email: '',
      password: ''
    }
  },
  methods: {
    _signUp: function () {
      let params = new URLSearchParams()
      params.append('username', this.username)
      params.append('email', this.email)
      params.append('password', this.password)
      params.append('userorwebtoonist', this.picked)
      console.log(params.toString())


      axios.post("/api/auth/signup",headers,params).then(response => {
        console.log(response)
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>

<style>

</style>
