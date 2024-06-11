<template>
  <!-- 上傳圖片測試 -->
  <div>
    <h1>上傳圖片</h1>
    <input type="number" v-model="id" placeholder="Photo Id" />
    <input type="file" @change="onFileChange" />
    <button @click="uploadPhoto">Upload</button>
    <p v-if="message">{{ message }}</p>

    <div v-if="photos.length">
      <div v-for="photo in photos" :key="photo.id" class="photo-item">
        <h2>{{ photo.id }}</h2>
        <img :src="`http://localhost:8080/photos/${photo.id}`" alt="Photo" class="photo-img" />
      </div>
    </div>
    <p v-else>No photos available</p>
  </div>
</template>

<script>
import axios from '@/plugins/axios.js';
import Swal from "sweetalert2";
import '@fortawesome/fontawesome-free/css/all.css';

export default {
  data() {
    return {
      id: '',
      photoFile: null,
      message: '',
      photos: []  // 新增 photos 資料屬性
    };
  },
  created() {
    this.fetchPhotos();  // 在組件創建時獲取所有圖片
  },
  methods: {
    async fetchPhotos() {
      try {
        const response = await axios.get('/photos');
        this.photos = response.data;
      } catch (error) {
        console.error('Error fetching photos:', error);
      }
    },
    onFileChange(event) {
      this.photoFile = event.target.files[0];
    },
    async uploadPhoto() {
      if (!this.id || !this.photoFile) {
        this.message = '提供id和檔案';
        return;
      }

      const formData = new FormData();
      formData.append('id', this.id);
      formData.append('photoFile', this.photoFile);

      try {
        const response = await axios.post('/photos/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        this.message = '圖片上傳成功!';

        // 使用 SweetAlert2 提示成功信息
        Swal.fire({
          icon: 'success',
          title: 'Success',
          text: 'Photo uploaded successfully!',
        });

        // 上傳成功後刷新圖片列表
        this.fetchPhotos();
      } catch (error) {
        console.error('There was an error uploading the photo:', error);
        this.message = 'Failed to upload photo';

        // 使用 SweetAlert2 提示錯誤信息
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Failed to upload photo',
        });
      }
    }
  }
};
</script>

<style scoped>
.photo-item {
  margin-bottom: 20px;
}

.photo-img {
  max-width: 100%;
  height: auto;
}
</style>