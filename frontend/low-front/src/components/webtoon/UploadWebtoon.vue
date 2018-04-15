<template>
  <div>
    
      <h1>Upload images</h1>
      <div class="dropbox">
        <div class="form-control">
          <label>제목</label>
          <input type="text" v-model="webtoonTitle">
        </div>
        <div class="form-control">
          <label>설명</label>
          <input type="text" v-model="webtoonDescription">
        </div>
        <div class="form-control">
          <label>썸네일</label>
          <input type="file" accept="image/*" id="file-input-thumbnail" @change="_setThumbnail($event)">
        </div>
        <div class="form-control">
          <label>웹툰</label>
          <input type="file" accept="image/*" id="file-input-webtoon" @change="_setWebtoon($event)">
        </div>
        
      </div>
      <button class="btn btn-primary" @click="_upload">업로드</button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'upload-webtoon',
  data() {
    return {
      webtoonTitle: '',
      webtoonDescription: '',
      webtoonImage: File,
      webtoonThumbnail: File
    }
  },
  methods: {
    _setThumbnail (event) {
      this.webtoonThumbnail = event.target.files[0]
    },
    _setWebtoon (event) {
      this.webtoonImage = event.target.files[0]
    },
    _upload () {

      let data = new FormData();
      data.append('webtoonTitle', this.webtoonTitle)
      data.append('webtoonDescription', this.webtoonDescription)
      data.append('webtoonImage', this.webtoonImage)
      data.append('webtoonThumbnail', this.webtoonThumbnail)

    let config = {
      header : {
        'Content-Type' : 'image/png'
      }
    }

    axios.post(
      '/api/webtoons', 
      data,
      config
    ).then(
      response => {
        console.log('image upload response > ', response)
      }
    )
    }
  }
}
</script>
