<template>
  <div class="info-box">
    <div class="info-content">
      <img src="@/assets/logo.png"/>

      <h5>{{info.username}}</h5>
      
      <span>{{info.info}}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'webtoonist-info',
  props: ['info']
}
</script>

<style scoped>

.info-box {
    border: 1px solid;
    padding: 10px;
    box-shadow: 2px 3px #888888;
    margin: 10px;
}
.info-box img {
  width: 100%
}
.info-content{
  width:100%;
}
</style>
