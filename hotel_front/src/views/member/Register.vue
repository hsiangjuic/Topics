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
    <div class="register-container">
      <h2>註冊</h2>
      <form @submit.prevent="register">
        <table>
          <tbody>
            <!-- 第一行: 名字 和 姓氏 -->
            <tr>
              <td><label for="firstName">姓氏:<br><br></label></td>
              <td>
                <input type="text" v-model="fields.firstName.value" required @input="checkChineseInput('firstName')">
                <span
                  :class="{ 'error-message': true, 'active': fields.firstName.value && !fields.firstName.value.match(/[\u4E00-\u9FA5]/) }"><br>請輸入中文字</span>
              </td>
              <td><label for="lastName">名字:<br><br></label></td>
              <td>
                <input type="text" v-model="fields.lastName.value" required @input="checkChineseInput('lastName')">
                <span
                  :class="{ 'error-message': true, 'active': fields.lastName.value && !fields.lastName.value.match(/[\u4E00-\u9FA5]/) }">
                  <br>請輸入中文字
                </span>
              </td>
            </tr>
            <!-- 第二行: 使用者帳號 和 電子郵件 -->
            <tr>
              <td><label for="username">帳號:<br><br></label></td>
              <td><input type="text" v-model="fields.username.value" required>
                <span :class="{ 'error-message': true, 'active': !passwordMatch }"><br>
                  <!--  -->&ensp;
                </span>
              </td>
              <td><label for="email">電子郵件:<br><br></label></td>
              <td>
                <input type="email" v-model="fields.email.value" required @input="checkEmailFormat">
                <span :class="{ 'error-message': true, 'active': fields.email.value && !emailFormatValid }"><br>
                  郵件格式XXX@gmail.com
                </span><br>
              </td>
            </tr>
            <!-- 第三行: 密碼 和 密碼確認 -->
            <tr>
              <td><label for="password">密碼:<br><br></label></td>
              <td><input type="password" v-model="fields.password.value" required><span
                  :class="{ 'error-message': true, 'active': !passwordMatch }"><br>
                  <!-- 密碼不匹配 -->&ensp;
                </span></td>
              <!-- 這邊寫密碼確認 -->
              <td><label for="confirmPassword">確認密碼:<br><br></label></td>
              <td>
                <input type="password" v-model="passwordConfirm" required @input="checkPasswordMatch">

                <span :class="{ 'error-message': true, 'active': passwordConfirm && !passwordMatch }"><br>
                  密碼與確認密碼不同
                </span>
              </td>
            </tr>
            <!-- 第四行: 地址 和 生日 -->
            <tr>
              <td><label for="country">國家:<br><br></label></td>
              <td>
                <select v-model="fields.country.value" required @change="updateAddressOptions">
                  <option disabled value="">請選擇國家</option>
                  <option value="台灣">台灣</option>
                  <!-- 在此添加其他國家選項 -->
                </select>
                <span :class="{ 'error-message': true, 'active': !passwordMatch }"><br>&ensp;</span>
              </td>

              <!-- 選擇國家之前顯示選擇地址和選擇地區 -->
              <td>
                <label for="address">地址:<br><br></label>
              </td>

              <td v-if="fields.country.value === '台灣'">
                <select v-model="selectedCity" required>
                  <option disabled value="">選擇地址&nbsp;</option>
                  <option value="台北市">台北市</option>
                  <option value="新北市">新北市</option>
                </select>

                <select v-model="selectedDistrict" required>
                  <option disabled value="">選擇地區&nbsp;</option>
                  <option v-if="selectedCity === '台北市'" value="北投區">北投區</option>
                  <option v-if="selectedCity === '台北市'" value="士林區">士林區</option>
                  <option v-if="selectedCity === '台北市'" value="大安區">大安區</option>
                  <option v-if="selectedCity === '新北市'" value="新店區">新店區</option>
                  <option v-if="selectedCity === '新北市'" value="三峽區">三峽區</option>
                  <option v-if="selectedCity === '新北市'" value="烏來區">烏來區</option>
                </select>
                <span :class="{ 'error-message': true, 'active': !passwordMatch }"><br>&ensp;</span>
              </td>
            </tr>

            <!-- 第五行: 性別 和 國家 -->
            <tr>
              <td><label for="gender">性別:<br><br></label></td>
              <td>
                <select v-model="fields.gender.value" required>
                  <option disabled value="">請選擇性別</option>
                  <option value="male">男</option>
                  <option value="female">女</option>
                  <option value="none">不願透露</option>
                </select><span :class="{ 'error-message': true, 'active': !passwordMatch }"><br>
                  <!--  -->&ensp;
                </span>
              </td>
              <td><label for="birthday">生日:<br><br></label></td>
              <td><input type="text" id="birthdayPicker" v-model="fields.birthday.value" required
                  placeholder="請輸入出生日期"><span :class="{ 'error-message': true, 'active': !passwordMatch }"><br>
                  <!--  -->&ensp;
                </span></td>

            </tr>
            <!-- 第六行: 身份證號碼 和 護照號碼 -->
            <tr>
              <td><label for="identification">身份證號:<br><br></label></td>
              <td>
                <input type="text" v-model="fields.identification.value" :disabled="fields.identification.disabled"
                  @input="handleIDInput">
                <span
                  :class="{ 'error-message': true, 'active': fields.identification.value && !validateTWID(fields.identification.value) }"><br>身份證號格式不符</span>
              </td>


              <td><label for="passportNumber">護照號碼:<br><br></label></td>
              <td><input type="text" v-model="fields.passportNumber.value" :disabled="fields.passportNumber.disabled"
                  @input="disableIdentification"><span :class="{ 'error-message': true, 'active': !passwordMatch }"><br>
                  <!--  -->&ensp;
                </span></td>
            </tr>
            <!-- 第七行: 電話 和 允許推廣郵件 -->
            <tr>

              <td><label for="tel">電話:<br><br></label></td>
              <td>
                <input type="text" v-model="fields.tel.value" required @input="checkPhoneNumberFormat">
                <span :class="{ 'error-message': true, 'active': fields.tel.value && !validatePhoneNumber() }"><br>
                  <!-- 如果電話號碼格式不正確，顯示錯誤訊息 -->
                  電話號碼格式不正確
                </span>
              </td>
              <td><label for="allowPromotionMail">促銷訊息:<br><br></label></td>
              <td><input type="checkbox" v-model="fields.allowPromotionMail.value"><span
                  :class="{ 'error-message': true, 'active': !passwordMatch }"><br>
                  <!--  -->&ensp;
                </span></td>
            </tr>
            <!-- 第八行: 備註 -->

            <tr>
              <td>
                <label for="acceptTerms" style="width: 240%;">會員條款:</label>
              </td>
              <td>
                <input style="margin-left:1px;" type="checkbox" v-model="fields.acceptTerms.value"
                  @click="showTermsConfirmation">
                <span :class="{ 'error-message': true, 'active': !fields.acceptTerms.value }"><br></span>
              </td>
            </tr>


            <tr>
              <td><label for="remark">備註:</label></td>
              <td><textarea v-model="fields.remark.value" style="width: 240%;"></textarea></td>
            </tr>
          </tbody>
        </table>
        <div class="g-recaptcha-container" style="margin-left:-20px;">
          <div class="g-recaptcha" data-sitekey="6LdBZespAAAAAEq3e6B2DXeXMARyxn4WXoyHdE95"
            style="transform:scale(0.8);-webkit-transform:scale(0.8);transform-origin:0 0;-webkit-transform-origin:0 0; position: absolute;">
          </div>
        </div>
        <div class="register-button">
          <button type="submit" class="btn1">註冊<span></span>
            <span></span>
            <span></span>
            <span></span></button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from '@/plugins/axios.js';
import Swal from 'sweetalert2';
import router from '@/router/router.js';
import flatpickr from 'flatpickr';
import 'flatpickr/dist/flatpickr.min.css';
import { watch } from 'vue';
export default {
  data() {
    return {
      selectedCity: '', // 定義選擇的城市
      selectedDistrict: '', // 定義選擇的區域
      emailVerified: false,
      chineseInput: false,
      passwordConfirm: '',
      passwordMatch: true,
      emailFormatValid: false,
      fields: {
        firstName: { name: 'firstName', label: '名字', type: 'text', value: '', required: true },
        lastName: { name: 'lastName', label: '姓氏', type: 'text', value: '', required: true },
        username: { name: 'username', label: '使用者帳號', type: 'text', value: '', required: true },
        identification: { name: 'identification', label: '身份證號碼', type: 'text', value: '', required: false, disabled: false },
        passportNumber: { name: 'passportNumber', label: '護照號碼', type: 'text', value: '', required: false, disabled: false },
        tel: { name: 'tel', label: '電話', type: 'text', value: '', required: true },
        address: { name: 'address', label: '地址', type: 'text', value: '', required: true },
        birthday: { name: 'birthday', label: '生日', type: 'date', value: '', required: true },
        gender: { name: 'gender', label: '性別', type: 'text', value: '', required: true },
        country: { name: 'country', label: '國家', type: 'text', value: '', required: true },
        email: { name: 'email', label: '電子郵件', type: 'email', value: '', required: true },
        password: { name: 'password', label: '密碼', type: 'password', value: '', required: true },
        remark: { name: 'remark', label: '備註', type: 'text', value: '', required: false },
        allowPromotionMail: { name: 'allowPromotionMail', label: '允許推廣郵件', type: 'checkbox', value: false, required: false },
        mrId: { name: 'mrId', lable: '會員等級', type: 'hidden', value: 1, required: false },
        memberStatus: { name: 'memberStatus', label: '會員狀態', type: 'hidden', value: 'none', required: false },
        totalPoints: { name: 'totalPoints', label: '總積分', type: 'hidden', value: 0, required: false },
        acceptTerms: { name: 'acceptTerms', label: '需要接受會員規範', type: 'checkbox', value: false, required: true }
      }
    };
  },
  watch: {
    'fields.password.value': function (newVal, oldVal) {
      // 每次密碼字段發生變化時，將清空確認密碼字段
      this.passwordConfirm = '';
      // 同時重新檢查密碼匹配
      this.checkPasswordMatch();
    }
  },
  mounted() {
    const script = document.createElement('script');
    script.src = 'https://www.google.com/recaptcha/api.js';
    script.async = true;
    document.head.appendChild(script);




    const today = new Date();
    const maxDate = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate());

    flatpickr('#birthdayPicker', {
      dateFormat: 'Y-m-d',//因為訂房必須是成年人
      maxDate: maxDate, // 最大日期為今年減去18年日期
      allowInput: true
    });
  },






  methods: {
    submitForm() {
      const response = grecaptcha.getResponse();
      if (!response) {

        alert('請完成機器人驗證');
        return;
      }
    },

    validatePhoneNumber() {
      const phoneNumberPattern = /^0\d{9}$/;
      return phoneNumberPattern.test(this.fields.tel.value);
    },

    showTermsConfirmation() {
      Swal.fire({
        title: "閱讀並同意會員規範",
        text: "我已閱讀並同意芳山旅館會員條款與細則",

        showCancelButton: true,
        confirmButtonText: "已閱讀並同意",
        cancelButtonText: "不同意"
      }).then((result) => {
        if (result.value) {

          this.fields.acceptTerms.value = true;
        } else {

          this.fields.acceptTerms.value = false;
        }
      });
    },




    async register() {



      const response = grecaptcha.getResponse();

      if (!response) {
        // 如果未驗證成功，阻止表單提交並提示用戶完成驗證
        alert('請完成機器人驗證');
        return;
      }
      document.getElementById('loadingIndicator').style.display = 'block';

      try {



        if (!this.fields.firstName.value.match(/[\u4E00-\u9FA5]/) || !this.fields.lastName.value.match(/[\u4E00-\u9FA5]/)) {
          document.getElementById('loadingIndicator').style.display = 'none';
          return; // 如果不是中文，阻止表單提交
        }
        this.checkEmailFormat();
        this.checkPasswordMatch();
        // 如果 email 格式不正確，阻止提交
        if (!this.emailFormatValid) {
          document.getElementById('loadingIndicator').style.display = 'none';
          return;
        }
        // 如果密碼不匹配，不執行後續邏輯
        if (!this.passwordMatch) {
          document.getElementById('loadingIndicator').style.display = 'none';
          return;
        }


        this.handleIDInput();
        // 如果身分證不正確，阻止提交
        if (!this.fields.identification.value || !this.validateTWID(this.fields.identification.value)) {
          document.getElementById('loadingIndicator').style.display = 'none';
          return;
        }

        if (!this.validatePhoneNumber()) {
          document.getElementById('loadingIndicator').style.display = 'none';
          // 如果電話號碼格式不正確，阻止表單提交
          return;
        }

        const customer = {
          ...Object.fromEntries(Object.entries(this.fields).map(([key, field]) => [key, field.value])),
          address: ` ${this.selectedCity}${this.selectedDistrict}`
        };
        const response = await axios.post('/api/customers/register', customer);
        if (response.status === 200) {
          await this.sendVerificationEmail(customer.email);
          Swal.fire('註冊成功', '請檢查您的電子郵件以驗證您的帳戶', 'success');
          router.push('/pages/login');
        } else {
          Swal.fire('註冊失敗', '請再試一次', 'error');
        }

        document.getElementById('loadingIndicator').style.display = 'none';

      } catch (error) {
        document.getElementById('loadingIndicator').style.display = 'none';
        Swal.fire('註冊失敗', '請再試一次', 'error');
      }
    },
    async sendVerificationEmail(email) {
      try {
        if (!this.emailVerified) {
          await axios.post('/api/customers/sendVerificationEmail', { email });
          this.emailVerified = true;
          Swal.fire('驗證郵件已發送', '請檢查您的電子郵件', 'success');
        }
      } catch (error) {
        Swal.fire('發送驗證郵件失敗', '請再試一次', 'error');
      }
    }, disableIdentification() {
      if (this.fields.passportNumber.value) {
        this.fields.identification.disabled = true;
      } else {
        this.fields.identification.disabled = false;
      }
    },

    checkChineseInput(fieldName) {
      // 檢查是否中文輸入
      if (this.fields[fieldName].value && this.fields[fieldName].value.match(/[\u4E00-\u9FA5]/)) {
        this.chineseInput = true;
      } else {
        this.chineseInput = false;
      }
    },
    checkPasswordMatch() {
      if (this.fields.password.value !== this.passwordConfirm) {
        this.passwordMatch = false;
      } else {
        this.passwordMatch = true;
      }
    },

    checkEmailFormat() {
      // 使用正則表達式來檢查電子郵件格式是否正確
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
      const gmailPattern = /@gmail\.com$/;
      if (this.fields.email.value.match(emailPattern) && this.fields.email.value.match(gmailPattern)) {
        this.emailFormatValid = true;
      } else {
        this.emailFormatValid = false;
      }
    },
    handleIDInput() {
      if (this.fields.identification.value) {
        this.fields.passportNumber.disabled = true;
        const id = this.fields.identification.value.toUpperCase(); // 將字母轉換為大寫
        const isValid = this.validateTWID(id); // 調用驗證函數
        // 更新錯誤狀態
        this.fields.identification.error = !isValid;
      } else {
        this.fields.passportNumber.disabled = false;
      }
    },

    validateTWID(id) {
      const regExp = /^[A-Z][0-9]{9}$/;
      if (!regExp.test(id)) return false;

      const alphabet = 'ABCDEFGHJKLMNPQRSTUVXYWZIO';
      const idLetter = id.charAt(0);
      const index = alphabet.indexOf(idLetter) + 10;
      const X1 = Math.floor(index / 10);
      const X2 = index % 10;
      const D1 = Number(id.charAt(1));
      const D2 = Number(id.charAt(2));
      const D3 = Number(id.charAt(3));
      const D4 = Number(id.charAt(4));
      const D5 = Number(id.charAt(5));
      const D6 = Number(id.charAt(6));
      const D7 = Number(id.charAt(7));
      const D8 = Number(id.charAt(8));
      const Y = X1 + 9 * X2 + 8 * D1 + 7 * D2 + 6 * D3 + 5 * D4 + 4 * D5 + 3 * D6 + 2 * D7 + D8;
      const checkCode = (10 - (Y % 10)) % 10;

      // 檢查字母部分是否在特定的範圍內
      if (alphabet.indexOf(idLetter) === -1) return false;

      // 檢查檢查碼是否正確
      return checkCode.toString() === id.charAt(9);
    },

    checkPhoneNumberFormat() {
      // 電話號碼的正則表達式，開頭是0，總共10個數字
      const phoneNumberPattern = /^0\d{9}$/;
      // 使用正則表達式來檢查輸入的電話號碼格式是否正確
      return phoneNumberPattern.test(this.fields.tel.value);

    },


  }
};
</script>

<style>
.register-container {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  min-width: 590px;
}

.register-button {
  text-align: center;
  margin-top: 20px;
}

.error-message {
  color: red;
  position: relative;
  bottom: -2px;
  left: 0;
  visibility: hidden;

}

.error-message.active {
  visibility: visible;


}


/* 樣式特效https://blog.tarswork.com/post/a-selection-of-cool-css-button-effects */
.btn1,
.btn1:focus,
.btn1:hover {
  position: relative;
  min-width: 80px;
  border: 1px solid #5107fc;
  color: #130101;
  font-size: 1rem;
  font-weight: bold;
  text-align: center;
  text-decoration: none;
  text-transform: uppercase;
  -webkit-font-smoothing: antialiased;
  padding: 10px 20px;
  background-color: #2df59142;
}

.btn1 span:nth-child(1),
.btn1 span:nth-child(2),
.btn1 span:nth-child(3),
.btn1 span:nth-child(4) {
  content: "";
  display: block;
  position: absolute;
  background-color: #3d09f8;

}

.btn1 span:nth-child(1) {
  width: 1px;
  left: 0;
  bottom: 0;
}

.btn1 span:nth-child(2) {
  height: 1px;
  left: 0;
  top: 0;
}

.btn1 span:nth-child(3) {
  width: 1px;
  right: 0;
  top: 0;
}

.btn1 span:nth-child(4) {
  height: 1px;
  right: 0;
  bottom: 0;
}

.btn1:hover {
  border: none;
}

.btn1:hover span:nth-child(1) {
  animation: move1 1500ms infinite ease;
}

.btn1:hover span:nth-child(2) {
  animation: move2 1500ms infinite ease;
}

.btn1:hover span:nth-child(3) {
  animation: move3 1500ms infinite ease;
}

.btn1:hover span:nth-child(4) {
  animation: move4 1500ms infinite ease;
}

@keyframes move1 {
  0% {
    height: 100%;
    bottom: 0;
  }

  54% {
    height: 0;
    bottom: 100%;
  }

  55% {
    height: 0;
    bottom: 0;
  }

  100% {
    height: 100%;
    bottom: 0;
  }
}

@keyframes move2 {
  0% {
    width: 0;
    left: 0;
  }

  50% {
    width: 100%;
    left: 0;
  }

  100% {
    width: 0;
    left: 100%;
  }
}

@keyframes move3 {
  0% {
    height: 100%;
    top: 0;
  }

  54% {
    height: 0;
    top: 100%;
  }

  55% {
    height: 0;
    top: 0;
  }

  100% {
    height: 100%;
    top: 0;
  }
}

@keyframes move4 {
  0% {
    width: 0;
    right: 0;
  }

  55% {
    width: 100%;
    right: 0;
  }

  100% {
    width: 0;
    right: 100%;
  }
}

/* Flatpickr 輸入樣式 */
.flatpickr-input {
  width: 95%;
  height: 30px;
  padding: 8px;
  border: 1px solid #0a0000;
  /* border-radius: 5px; */
  font-size: 1rem;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

.flatpickr-input:focus {
  outline: none;
  border-color: #4CAF50;
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

input[disabled] {
  cursor: not-allowed;
}

input.error {
  border-color: #e74c3c;
  /* 錯誤時的邊框顏色 */
  box-shadow: inset 0 1px 3px rgba(231, 76, 60, 0.12), 0 0 8px rgba(231, 76, 60, 0.5);
  /* 錯誤時的陰影效果 */
}
</style>