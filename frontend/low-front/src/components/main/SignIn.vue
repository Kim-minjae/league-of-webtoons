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
        <input class="form-control" v-model="password"/>
      </div>
      <div class="form-group">
        <button class="btn btn-default" @click="_signIn">로그인</button>
      </div>
    </div>
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
    _signIn: function () {
      let params = new URLSearchParams()
      params.append('userEmail', this.email)
      params.append('password', this.password)
      console.log(params.toString())


      axios.post("http://localhost:8080/api/auth/signin",headers,params).then(response => {
        console.log(response)
      }).catch(error => {
        console.log(error)
      })
    }
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
