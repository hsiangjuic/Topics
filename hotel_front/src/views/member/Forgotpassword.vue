<template>
  <div style="max-width: 100%;
  min-width: 100%;max-height:2500px;height:845px;
  margin: 0 auto;
  padding: 20px;
  background-color: #ddd;
  background-size: cover;">
    <div id="loadingIndicator" class="loading-indicator" style="display: none;">
      <!-- <img src="https://it-easy.tw/wp-content/uploads/preloaders/l2.gif" alt="Loading..." /> -->
      <img src="/loading0.gif" alt="Loading..." />
    </div>
    <div class="container">
      <input v-model="email" type="email" placeholder="輸入email" class="input-field">
      <button @click="checkEmail" class="button">檢查</button>
      <p v-if="emailExists !== null">
        Email {{ emailExists ? '存在' : '不存在' }}.
        <i :class="['fas', emailExists ? 'fa-check-circle' : 'fa-times-circle']"
          :style="{ color: emailExists ? 'green' : 'red' }"></i>
      </p>
      <br><br><input v-model="verificationCode" type="text" placeholder="輸入驗證碼" class="input-field">
      <button @click="verifyCode" class="button">驗證</button>
      <p v-if="verificationResult !== null">
        驗證碼 {{ verificationResult ? '驗證成功' : '驗證失敗' }}.
      </p>
      <!-- 更新用戶表單 -->
      <div v-if="showUpdateForm" class="update-form">
        <h2>密碼更換</h2>
        <p>用戶帳號: {{ username }}</p>
        <!-- 用戶訊息輸入框 -->
        <input v-model="password" type="password" placeholder="更新密碼" class="input-field">
        <button @click="updateCustomerInfo" class="button">Update</button>
        <p v-if="!password.trim()" style="color: red;">輸入不能為空</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '@/plugins/axios.js';
import Swal from 'sweetalert2';

export default {
  data() {
    return {
      email: '',
      emailExists: null,
      verificationCode: '',
      verificationResult: null,
      showUpdateForm: false, // 是否顯示更新
      username: '',  //用戶帳號
      password: '', // 用戶訊息

    };
  },
  methods: {
    checkEmail() {
      axios.get(`/api/customers/email/${this.email}`)
        .then(response => {
          this.emailExists = true; // Email 存在
          this.sendVerificationCode(); // 寄驗證碼 
          this.username = response.data.username;
        })
        .catch(error => {
          if (error.response.status === 404) {

            Swal.fire({
              icon: 'error',
              title: '找不到email',
              text: '此email未註冊'
            });
            this.emailExists = false;
          } else {
            console.error('Error checking email:', error);
          }
        });
    },
    sendVerificationCode() {
      document.getElementById('loadingIndicator').style.display = 'block';
      axios.post(`/api/customers/forgotpassword/send-verification-code`, { email: this.email })
        .then(response => {
          Swal.fire({
            icon: 'success',
            title: '驗證碼已寄出',
            text: '檢查輸入的郵件'
          }); document.getElementById('loadingIndicator').style.display = 'none';
        })
        .catch(error => {
          document.getElementById('loadingIndicator').style.display = 'none';
          console.error('錯誤', error);
        });
    },
    verifyCode() {
      axios.post(`/api/customers/verifycode`, {
        email: this.email,
        enteredCode: this.verificationCode// 驗證碼
      })
        .then(response => {
          if (response.data) {
            // 驗證成功調用端點
            this.showUpdateForm = true;
          } else {
            // 驗證碼驗證失敗
            console.error('Verification code is incorrect');
          }
        })
        .catch(error => {
          console.error('Error verifying code:', error);
        });
    },
    updateCustomerInfo() {
      // 構造新的值
      if (!this.password.trim()) {
        Swal.fire({
          icon: 'error',
          title: '需要填寫密碼',
          text: '請輸入密碼'
        });
        return;
      }

      const updatedInfo = {
        password: this.password,
      };

      // 調用個人訊息端點
      axios.put(`/api/customers/updatepassword/${this.email}`, updatedInfo)
        .then(response => {
          // 更新成功
          console.log('Customer information updated successfully');
          Swal.fire({
            icon: 'success',
            title: '更新成功',
            text: '密碼已成功更新！'
          });
        })
        .catch(error => {
          // 處理失敗
          console.error('Error updating customer information:', error);
          // 顯示SweetAlert錯誤消息
          Swal.fire({
            icon: 'error',
            title: '更新失敗',
            text: '密碼更新失敗，請稍後再試。'
          });
        });
    }
  }
};
</script>

<style scoped>
.container {
  text-align: center;
  border: solid 1px gray;
  max-width: 600px;
  margin: 85px auto;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.363);
}

.input-field {
  border: 1px solid #dd4f4f;
  padding: 10px;
  margin: 25px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.button {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.button:hover {
  background-color: #45a049;
}

.update-form {

  margin: 20px auto;
  border: 1px solid #ccc;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 500px;

}

.loading-indicator {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 0.8;
}

.loading-indicator img {
  max-width: 1500px;
  max-height: 1500px;
}
</style>
