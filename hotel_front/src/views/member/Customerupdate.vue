<template>
  <div class="container">

    <header class="header">
      <h1>會員個人資訊</h1>
    </header>

    <div>
      <h2>用戶會員卡</h2>
      <div class="cardbackground">
        <div>
          <!-- 後端將資料庫轉成圖片放到後端網址，前端抓網址後顯示 -->
          <img src="http://localhost:8080/member/pages/1" alt="會員卡" class="photo-img" style="z-index: 1;" />
          <img src="/public/stars.gif" alt="會員卡" class="photo-img" style="z-index: 2;opacity: 0.2;" />
        </div>
      </div>
    </div>
    <main class="main">

      <section class="update-container">
        <h2 class="info">更新資料</h2>
        <div class="membername">會員名稱:</div>
        <div class="memberborder">
          <tr v-for="member in members" :key="member.mrId" class="members">
            <td class="membername" v-if="member.mrId === 1">{{ member.name }}</td>
          </tr>
        </div>

        <form @submit.prevent="updateCustomer" class="update-form">

          <div v-for="field in fields" :key="field.name" class="form-group1">
            <label :for="field.name" class="form-name">{{ field.label }}</label>
            <input v-if="field.type !== 'textarea' && field.type !== 'checkbox' && field.type !== 'select'"
              :type="field.type" v-model="field.value" :required="field.required" :disabled="field.disabled" />
            <textarea v-else-if="field.type === 'textarea'" :rows="4" :cols="field.cols" v-model="field.value"
              :required="field.required" :disabled="field.disabled"
              style="width: 50%; max-width: 50%;max-height: 200px;margin:0% 0% 0% 25%;"></textarea>
            <!-- 樣式 checkbox-->
            <input v-else-if="field.type === 'checkbox'" type="checkbox" :id="field.id" v-model="field.value"
              :class="field.class"
              style="width: 20px; height: 20px; background-color:white; border: 1px solid #000000; border-radius: 3px; cursor: pointer;">
            <!-- 性別 option-->

            <select v-else-if="field.type === 'select'" :id="field.id" v-model="field.value" :class="field.class"
              style="min-width:50%; height: 40px; background-color:white; border: 2px solid #ccc; border-radius: 5px; cursor: pointer;  margin:auto 25%; box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24); ">
              <option disabled value="">請選擇性別</option>
              <option v-for="(option, index) in field.options" :key="index" :value="option">{{ option }}</option>
            </select>


          </div>

          <button type="submit" style="max-width: 50%;background-color:darkorange;margin: 0%  0%  10% 25%;">更新</button>
        </form>
      </section>
    </main>

    <!-- 底部 -->
    <footer class="footer">
      <p>隱私權訊息 © 2024</p>
    </footer>
  </div>
</template>

<script>
import axios from '@/plugins/axios.js';
import Swal from 'sweetalert2';
import { mapState } from 'vuex';
export default {
  data() {
    return {
      fields: [
        { name: 'mrId', label: '會員等級:', type: 'text', value: '', required: true, disabled: true },
        { name: 'firstName', label: '名字:', type: 'text', value: '', required: true, disabled: false },
        { name: 'lastName', label: '姓氏:', type: 'text', value: '', required: true, disabled: false },
        { name: 'username', label: '使用者帳號:', type: 'text', value: '', required: true, disabled: true },
        { name: 'identification', label: '身份證號碼:', type: 'text', value: '', required: false, disabled: false },
        { name: 'passportNumber', label: '護照號碼:', type: 'text', value: '', required: false, disabled: true },
        { name: 'tel', label: '電話:', type: 'text', value: '', required: true, disabled: false },
        { name: 'address', label: '地址:', type: 'text', value: '', required: true, disabled: false },
        { name: 'birthday', label: '生日:', type: 'date', value: '', required: true, disabled: false },
        { name: 'gender', label: '性別:', id: "gender", type: 'select', value: '', required: true, disabled: false, options: ['男', '女'] },
        { name: 'country', label: '國家:', type: 'text', value: '', required: true, disabled: false },
        { name: 'email', label: '電子郵件:', type: 'email', value: '', required: true, disabled: false },
        { name: 'memberStatus', label: '會員狀態:', type: 'text', value: '', required: true, disabled: true },
        { name: 'totalPoints', label: '總積分:', type: 'number', value: '', required: false, disabled: true },
        { name: 'beginDate', id: "beginDate", label: '註冊時間:', type: 'text', value: '', required: true, disabled: true },
        { name: 'allowPromotionMail', id: "allowPromotionMail", label: '允許推廣郵件:', type: 'checkbox', value: false, required: false, disabled: false },
        { name: 'remark', label: '備註:', type: 'textarea', value: '', required: false, disabled: false },

      ], members: [],
    };
  },
  //vuex套件管理登入狀態較簡單
  computed: {
    ...mapState(['username'])
  },
  methods: {
    getUserData() {
      axios.get(`/api/customers/${this.username}`)
        .then(response => {
          const customerData = response.data;

          this.fields.forEach(field => {
            if (field.type === 'checkbox') {
              field.value = customerData[field.name] === 'true';
            } else {
              field.value = customerData[field.name] !== undefined ? customerData[field.name] : '';
            }
          });
          // 取 mrId
          return axios.get(`/api/customers/mrId/${this.username}`);
        })
        .then(response => {
          const mrId = response.data;
          this.fields.find(field => field.name === 'mrId').value = mrId;
        })
        .catch(error => {
          console.error("There was an error fetching the user data!", error);
          Swal.fire({
            icon: 'error',
            title: '獲取用戶資料失敗'
          });
        });
    },
    async fetchMembers() {
      try {
        const requestData = {};
        const response = await axios.post('/member/pages/member/find', requestData);
        console.log(response.data);
        this.members = response.data.list;
      } catch (error) {
        console.error('獲取會員列表失敗：', error);
        alert('網路異常：' + error.message);
      }
    },
    updateCustomer() {
      const updateData = this.fields.reduce((data, field) => {
        if (field.type === 'checkbox') {
          data[field.name] = field.value ? 'true' : 'false';
        } else {
          data[field.name] = field.value;
        }
        return data;
      }, {});
      updateData.username = this.username;

      // 如果 mrId 為零，設置為 null
      if (updateData.mrId === '0') {
        updateData.mrId = null;
      }

      axios.put('/api/customers/update', updateData)
        .then(response => {
          const message = response.data.message;
          if (message === '更新成功') {
            Swal.fire({
              icon: 'success',
              title: message
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: message
            });
          }
        })
        .catch(error => {
          console.error("There was an error updating!", error);
          Swal.fire({
            icon: 'error',
            title: '更新失敗'
          });
        });
    }
  },
  created() {
    this.getUserData();
  },
  mounted() {
    this.fetchMembers();
  },
};
</script>

<style scoped>
@import url('../style/customerupdate.css');
</style>
