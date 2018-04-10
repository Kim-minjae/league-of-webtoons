<template>
  <div>
    <div class="rank-dashboard">
      <!-- <CountDown /> -->
    </div>
    <div class="webtoonist-list">
      <div class="col-12 row">

        <div v-for="webtoonist in webtoonists" :key="webtoonist.id" class="col-2">
        <webtoonist-info
          :info="webtoonist">
        </webtoonist-info>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import WebtoonistInfo from '@/components/common/WebtoonistInfo'
import CountDown from '@/components/common/CountDown'
export default {
  name: 'battle-main',
  components: {
    WebtoonistInfo,
    CountDown
  },
  data () {
    return {
      dday: 'Wed Apr 11 2018 12:00:00 GMT+0900',
      webtoonists: [
        {
          'id': 1,
          'username': 'webtoonist1',
          'info': 'Webtoonist Info'
        },
        {
          'id': 2,
          'username': 'webtoonist2',
          'info': 'Webtoonist Info'
        },
        {
          'id': 3,
          'username': 'webtoonist3',
          'info': 'Webtoonist Info'
        },
        {
          'id': 4,
          'username': 'webtoonist4',
          'info': 'Webtoonist Info'
        },
        {
          'id': 5,
          'username': 'webtoonist5',
          'info': 'Webtoonist Info'
        }
      ]
    }
  },
  created () {
  }
}
</script>

<style>

</style>
