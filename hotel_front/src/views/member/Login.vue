<template ><div class="photo">
  <div class="login-container">
    <h2>會員登入</h2>
    <form @submit.prevent="login" class="login-box">
      <div class="avatar"></div>
      <div class="input-container">
        <p>使用者</p>
        <div class="icon-input-wrapper">
          <label for="username"></label> &ensp; <i class="fa-solid fa-user"></i>
          <input type="text" v-model="username" placeholder="用戶帳號" required>
        </div>
      </div>
      <div class="input-container">
        <p>密碼</p>
        <div class="icon-input-wrapper">
          <label for="password"></label> &ensp;&thinsp;<i class="fa-solid fa-lock"></i>
          <input type="password" v-model="password" placeholder="用戶密碼" required>
        </div>
        <div class="register">
        <router-link to="/pages/register">註冊</router-link>
      </div>
         <div class="forgot-password">
      <router-link to="/pages/forgotpassword">忘記密碼？</router-link>
    </div> 
      </div>
      <button class="button1" type="submit">登入</button>
    
    
    </form>
    
        


</div> </div>
</template>

<script>
import axios from '@/plugins/axios.js';
import Swal from 'sweetalert2';
import router from '@/router/router.js';
import { mapMutations } from 'vuex';

export default {
  data() {
    return {
      username: '',
      password: '',
     
    };
  },
  methods: {
    ...mapMutations(['setUsername']),
    login() {
      const loginData = {
        username: this.username,
        password: this.password
      };

      axios.post('/api/customers/login', loginData)
        .then(response => {
          const message = response.data.message;
          if (message === '登入成功') {
            this.setUsername(this.username);
            sessionStorage.setItem("username", this.username);
            router.push({ name: 'home-link' });
          } else {
            Swal.fire({
              icon: 'error',
              title: message
            });
          }
        })
        .catch(error => {
          console.error("There was an error logging in!", error);
          Swal.fire({
            icon: 'error',
            title: '登入失敗'
          });
        });
    },
   
  }
};
</script>

<style scoped>
/* @import使用CSS樣式 */
@import url('../style/button.css');
/* 登陸樣式 */
@import url('../style/styles.css');

/* @import底下全刪了 */




</style>
