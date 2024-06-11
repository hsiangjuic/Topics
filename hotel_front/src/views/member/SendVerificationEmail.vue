<template>
  <div>
    <input type="email" v-model="email" placeholder="輸入您的郵件地址">
    <button @click="sendVerificationEmail" :disabled="isSending">發送驗證郵件</button>
  </div>
</template>

<script>
import axios from '@/plugins/axios.js';
import router from '@/router/router.js';
export default {
  data() {
    return {
      email: '',
      isSending: false // 新增一個狀態來標記是否正在發送郵件
    };
  },
  methods: {
    sendVerificationEmail() {
      // 前端驗證
      if (!this.email) {
        alert('請輸入有效的郵件地址');
        return;
      }

      // 防止重複點擊
      if (this.isSending) return;

      // 開始發送郵件，將 isSending 設置為 true
      this.isSending = true;

      // 發送請求至後端
      axios.post('/api/customers/sendVerificationEmail', { email: this.email })
        .then(response => {
          alert('驗證郵件已發送');
        })
        .catch(error => {
          console.error('發送郵件時出錯：', error);
          alert('發送驗證郵件時出錯');
        })
        .finally(() => {
          // 無論成功或失敗，都將 isSending 設置為 false
          this.isSending = false;
        });
    }
  }
}
</script>