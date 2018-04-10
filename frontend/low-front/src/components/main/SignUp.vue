<template>
  <div id="signup">
    <img src="@/assets/logo.png"/>
    <div id="signup-box">
      <div class="form-group">
        <label>username</label>
        <input class="form-control" v-model="username"/>
        <span v-show="errorMessage.username!==''" class="validation_warn">{{errorMessage.username}}</span>
      </div>
      <div class="form-group">
        <label>email</label>
        <input class="form-control" v-model="email"/>
        <span v-show="errorMessage.email!==''" class="validation_warn">{{errorMessage.email}}</span>
      </div>
      <div class="form-group">
        <label>password</label>
        <input type="password" class="form-control" v-model="password"/>
        <span v-show="errorMessage.password!==''" class="validation_warn">{{errorMessage.password}}</span>
      </div>
      <div class="form-group">
        <label>user/webtoonist</label>
        <br>
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
      password: '',
      errorMessage: Object
    }
  },
  watch: {
    username: function () {
      if (this.username.length < 3 || this.username.length > 15) {
        this.errorMessage.username = '반드시 최소값 3과(와) 최대값 15 사이의 크기이어야 합니다.'
      } else {
        this.errorMessage.username = ''
      }
    },
    email: function () {
      const EMAIL_REGEX = RegExp('[^@ ]+@[^@ ]+\\.[^@ ]+');
      if(!EMAIL_REGEX.test(this.email)) {
        this.errorMessage.email = '이메일 형식이 아닙니다.'
        return
      } else {
        this.errorMessage.email = ''
      }
      if (this.email.length < 3 || this.email.length > 40) {
        this.errorMessage.email = '반드시 최소값 3과(와) 최대값 40 사이의 크기이어야 합니다.'
        return
      } else {
        this.errorMessage.email = ''
      }
    },
    password: function () {
      if(this.password.length < 6 || this.password.length > 20) {
        this.errorMessage.password = '반드시 최소값 6과(와) 최대값 20 사이의 크기이어야 합니다.'
      } else {
        this.errorMessage.password = ''
      }
    }
  },
  methods: {
    _validation: function () {
      let validation = Object.keys(this.errorMessage).every(x => {
        return this.errorMessage[x] === '' 
      })
      if (!validation) {
        alert('유효성 검사를 진행하세요.')
        return false
      }
      if (this.username.length === 0 || this.password.length === 0 || this.email.length === 0) {
        alert('form을 채워주세요.')
        return false
      }
      if (this.picked === '') {
        alert('회원 유형을 체크하세요.')
        return false
      }
      return true
    },
    _signUp: function () {
      if(!this._validation()) {
        return
      }
      axios.post("/api/auth/signup",{
        username: this.username,
        email: this.email,
        password: this.password,
        role: this.picked
      }).then(response => {
        if(response.status === 201) {
          alert('회원가입 성공')
          this.$router.push('signin')
        }
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
.validation_warn{
  color:red;
  font-size: bold;
}
</style>
