<template>
  <!-- <div class="logout-container">
    <h2>確定登出?</h2>
    <button @click="performLogout">登出</button>
  </div> -->
</template>

<script>
import { mapMutations } from 'vuex';
import router from '@/router/router.js';

export default {
  methods: {
    ...mapMutations(['logout']),
    performLogout() {
      this.logout(); // 調用 Vuex 的登出方法
      sessionStorage.removeItem("username");//刪除session
      router.push({ name: 'home-link' }); // 連到登入面
    }
  },
  created() {
   
    if (this.$route.name === 'logout-link') {
      this.performLogout();
    }
  }
};
</script>

<style scoped>
.logout-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 1em;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.logout-container h2 {
  text-align: center;
  margin-bottom: 1em;
}

.logout-container button {
  width: 100%;
  padding: 0.75em;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  font-size: 1em;
  cursor: pointer;
}

.logout-container button:hover {
  background-color: #0056b3;
}
</style>