<template>
  <div id="signup">
    <img src="@/assets/logo.png"/>
    <div id="signup-box">
      <div class="form-group">
        <label>username</label>
        <input class="form-control" v-model="username"/>
      </div>
      <div class="form-group">
        <label>email</label>
        <input class="form-control" v-model="email"/>
      </div>
      <div class="form-group">
        <label>password</label>
        <input class="form-control" v-model="password"/>
      </div>
      <div class="form-group">
        <label>user/webtoonist</label>
        <input type="radio" id="one" value="1" v-model="picked">
         <label for="one">유저</label>
        <br>
        <input type="radio" id="two" value="2" v-model="picked">
        <label for="two">작가</label>
      </div>
      <div class="form-group">
        <button class="btn btn-default" @click="_signUp">가입</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
const headers = {
  'Content-type': 'application/x-www-form-urlencoded'
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
      params.append('role', this.picked)
      console.log(params.toString())


      axios.post("/api/auth/signup",headers,{
        username: this.username,
        email: this.email,
        password: this.password,
        role: this.picked
      }).then(response => {
        console.log(response)
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>

<style scoped>
#signup{
  text-align: center
}
#signup-box{
  border: 1px solid;
  padding: 10px;
  box-shadow: 2px 3px #888888;
  margin: auto;
  width:300px;
}
</style>
