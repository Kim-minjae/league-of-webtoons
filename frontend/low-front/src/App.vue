<template>
  <div id="app">
    <div class="topnav">
      <div v-if="isAuthenticated">
      <a href="#" @click="_logout">Logout</a>
      </div>
      <div v-else>
        <a href="#">로그인 하세요</a>
      </div>
    </div>
    <div class="sidenav">
      <a href="#" v-if="!isAuthenticated"><router-link to="signin">로그인</router-link></a>
      <a href="#" v-if="!isAuthenticated"><router-link to="signup">가입</router-link></a>
      <a href="#" v-if="isAuthenticated"><router-link to="mypage">내정보</router-link></a>
      <a href="#" v-if="isAuthenticated"><router-link to="battle">배틀</router-link></a>
    </div>

    <div class="main">
      <router-view/>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'App',
  computed: {
    isAuthenticated () {
      return this.$store.getters.isAuthenticated
    }
  },
  created: function () {
    axios.interceptors.response.use(undefined, function (err) {
      return new Promise(function (resolve, reject) {
        if (err.status === 401 && err.config && !err.config.__isRetryRequest) {
        // if you ever get an unauthorized, logout the user
          this.$store.dispatch('authLogout')
        // you can also redirect to /login if needed !
        }
        throw err;
      })
    })
  },
  methods: {
    _logout: function () {
      this.$store.dispatch('authLogout')
      .then(() => {
        this.$router.push('/signIn')
      })
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.topnav {
  overflow: hidden;
  background-color: #333;
  
    position: -webkit-sticky; /* Safari */
    position: sticky;
  text-align: right;
}

.topnav a {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}

.topnav a.active {
  background-color: #4CAF50;
  color: white;
}
.sidenav {
    height: 100%;
    width: 200px;
    position: fixed;
    z-index: 1;
    left: 0;
    background-color: #111;
    overflow-x: hidden;
    padding-top: 20px;
}

.sidenav a {
    padding: 6px 6px 6px 32px;
    text-decoration: none;
    font-size: 25px;
    color: #818181;
    display: block;
}

.sidenav a:hover {
    color: #f1f1f1;
}

.main {
    margin-left: 200px; /* Same as the width of the sidenav */
    padding:20px;
}

@media screen and (max-height: 450px) {
  .sidenav {padding-top: 15px;}
  .sidenav a {font-size: 18px;}
}
</style>
