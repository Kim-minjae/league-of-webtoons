<template>
  <div>
    <div class="form-control">
      <label>유저이름</label>
      <h5>{{userInfo.username}}</h5>
    </div>
    <div class="form-control">
      <label>유저이메일</label>
      <h5>{{userInfo.useremail}}</h5>
    </div>
    <div class="form-control">
      <button type="button" class="btn btn-danger" @click="_deleteUser">계정 삭제</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'my-page',
  data() {
    return {
      userInfo : {}
    }
  },
  created() {
    axios.get('/api/users/me').then(response => {
      this.userInfo = response.data
    })
  },
  methods: {
    _deleteUser () {
      axios.delete('/api/users/me').then(response => {
        this.$store.dispatch('authLogout').then(x => {
          this.$router.push('/signin')
        })
      })
    }
  }

}
</script>

<style>

</style>
