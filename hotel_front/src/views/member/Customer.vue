<template>
    <div class="member-list-container">
      <h3>訂房客戶編輯</h3>
      <div class="table-responsive">
        <table class="table table-bordered table-hover table-striped member-list">
          <thead>
            <tr>
              <th>會員等級</th>
              <th>客戶姓名</th>
              <th>身分證號</th>
              <th>護照號碼</th>
              <th>電話號碼</th>
              <th>客戶生日</th>
              <th>客戶性別</th>
              <th>電子郵件</th>
              <th>住家地址</th>
              <th>所在國家</th>
              <th>客戶密碼</th>
              <th>註冊日期</th>
              <th>會員狀態</th>
              <th>所得點數</th>
              <th>允許促銷</th>
              <th>客戶備註</th>
              <th>修改時間</th>
              <th>會員規定發布日期</th>
              <th>
                <button @click="showAddForm" class="btn btn-primary">新增成員</button>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="customer in customers" :key="customer.cId">
              <td>{{ customer.memberRank ? customer.memberRank.mrId : '非會員' }}</td>
              <td>{{ customer.firstName }} {{ customer.lastName }}</td>
              <td>{{ customer.identification }}</td>
              <td>{{ customer.passportNumber }}</td>
              <td>{{ customer.tel }}</td>
              <td>{{ customer.birthday }}</td>
              <td>{{ customer.gender }}</td>
              <td>{{ customer.email }}</td>
              <td>{{ customer.address }}</td>
              <td>{{ customer.country }}</td>
              <td>{{ customer.password }}</td>
              <td>{{ customer.beginDate }}</td>
              <td>{{ customer.memberStatus }}</td>
              <td>{{ customer.totalPoints }}</td>
              <td>{{ customer.allowPromotionMail }}</td>
              <td>{{ customer.remark }}</td>
              <td>{{ customer.lastModifiedDate }}</td>
              <td>{{ customer.memberRank ? customer.memberRank.lastModifiedDate : '非會員'  }}</td>
              <td>
                <button @click="editCustomer(customer)" class="btn btn-secondary"><i class="fa fa-pencil"></i></button>&nbsp;
                <button @click="deleteCustomer(customer.cId)" class="btn btn-danger">
                  <i class="fas fa-times"></i>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </template>
  
  <script>
  import axios from '@/plugins/axios.js';
  import Swal from "sweetalert2";
  import '@fortawesome/fontawesome-free/css/all.css';
  
  export default {
    data() {
      return {
        customers: [],
      };
    },
    methods: {
      async showAddForm() {
        const { value: formValues } = await Swal.fire({
          title: '新增客戶',
          html: `
            <form id="addCustomerForm">
              <label for="firstName">名字：</label>
              <input type="text" id="firstName" class="swal2-input" required>
              <label for="lastName">姓氏：</label>
              <input type="text" id="lastName" class="swal2-input" required>
              <label for="identification">身分證號：</label>
              <input type="text" id="identification" class="swal2-input">
              <label for="passportNumber">護照號碼：</label>
              <input type="text" id="passportNumber" class="swal2-input">
              <label for="tel">電話號碼：</label>
              <input type="text" id="tel" class="swal2-input" required>
              <label for="address">住家地址：</label>
              <input type="text" id="address" class="swal2-input" required>
              <label for="birthday">生日：</label>
              <input type="date" id="birthday" class="swal2-input" required>
              <label for="gender">性別：</label>
              <input type="text" id="gender" class="swal2-input" required>
              <label for="country">國家：</label>
              <input type="text" id="country" class="swal2-input" required>
              <label for="email">電子郵件：</label>
              <input type="email" id="email" class="swal2-input" required>
              <label for="remark">備註：</label>
              <input type="text" id="remark" class="swal2-input">
              <label for="titleOfCourtesy">稱謂：</label>
              <input type="text" id="titleOfCourtesy" class="swal2-input">
              <label for="allowPromotionMail">允許促銷：</label>
              <input type="checkbox" id="allowPromotionMail" class="swal2-input">
              <label for="username">用戶名：</label>
              <input type="text" id="username" class="swal2-input" required>
              <label for="password">密碼：</label>
              <input type="password" id="password" class="swal2-input" required>
              
              <label for="memberStatus">會員狀態：</label>
              <input type="text" id="memberStatus" class="swal2-input" required>
              <label for="totalPoints">總點數：</label>
              <input type="number" id="totalPoints" class="swal2-input" required>
              <label for="mrId">會員等級ID：</label>
              <input type="number" id="mrId" class="swal2-input">
            </form>`,
          focusConfirm: false,
          showCancelButton: true,
          confirmButtonText: '提交',
          preConfirm: () => {
            const form = document.getElementById('addCustomerForm');
            const customer = {
              firstName: form.querySelector('#firstName').value,
              lastName: form.querySelector('#lastName').value,
              identification: form.querySelector('#identification').value,
              passportNumber: form.querySelector('#passportNumber').value,
              tel: form.querySelector('#tel').value,
              address: form.querySelector('#address').value,
              birthday: form.querySelector('#birthday').value,
              gender: form.querySelector('#gender').value,
              country: form.querySelector('#country').value,
              email: form.querySelector('#email').value,
              remark: form.querySelector('#remark').value,
              titleOfCourtesy: form.querySelector('#titleOfCourtesy').value,
              allowPromotionMail: form.querySelector('#allowPromotionMail').checked,
              username: form.querySelector('#username').value,
              password: form.querySelector('#password').value,
              
              memberStatus: form.querySelector('#memberStatus').value,
              totalPoints: form.querySelector('#totalPoints').value,
              mrId: form.querySelector('#mrId').value,
            };
            return customer;
          }
        });
  
        if (formValues) {
          try {
            const response = await axios.post('/customer/pages/customer', formValues);
            Swal.fire({
              icon: 'success',
              title: '新增成功!',
            }).then(() => { this.fetchCustomers(); });
          } catch (error) {
            Swal.fire({
              icon: 'error',
              title: '錯誤',
              text: '新增出現問題',
            });
          }
        }
      },
      async fetchCustomers() {
        try {
          const requestData = {};
          const response = await axios.post('/customer/pages/customer/find', requestData);
          this.customers = response.data.list;
        } catch (error) {
          console.error('獲取客戶列表失敗：', error);
          alert('網路異常：' + error.message);
        }
      },
      async deleteCustomer(cId) {
        try {
          const response = await axios.delete(`/customer/pages/customer/${cId}`);
          if (response.data.status === 'success') {
            Swal.fire({
              text: '刪除成功!',
              icon: 'success',
              allowOutsideClick: false,
              confirmButtonText: '確認'
            }).then(() => { this.fetchCustomers(); });
          } else {
            Swal.fire({
              text: '刪除失敗!',
              icon: 'error',
              allowOutsideClick: false,
              confirmButtonText: '確認'
            });
          }
        } catch (error) {
          Swal.fire({
            text: '刪除客戶失敗',
            icon: 'error',
            allowOutsideClick: false,
            confirmButtonText: '確認'
          });
          console.error('刪除客戶失敗：', error);
        }
      },
      async editCustomer(customer) {
        const { value: formValues } = await Swal.fire({
          title: '編輯客戶',
          html: `
            <form id="editCustomerForm">
              <label for="firstName">名字：</label>
              <input type="text" id="firstName" class="swal2-input" value="${customer.firstName}" >
              <label for="lastName">姓氏：</label>
              <input type="text" id="lastName" class="swal2-input" value="${customer.lastName}" >
              
              
              <label for="tel">電話號碼：</label>
              <input type="text" id="tel" class="swal2-input" value="${customer.tel}" >
              <label for="address">住家地址：</label>
              <input type="text" id="address" class="swal2-input" value="${customer.address}" >
              <label for="birthday">生日：</label>
              <input type="date" id="birthday" class="swal2-input" value="${customer.birthday}" >
              <label for="gender">性別：</label>
              <input type="text" id="gender" class="swal2-input" value="${customer.gender}" >
              <label for="country">國家：</label>
              <input type="text" id="country" class="swal2-input" value="${customer.country}" >
              
              
              <label for="titleOfCourtesy">稱謂：</label>
              <input type="text" id="titleOfCourtesy" class="swal2-input" value="${customer.titleOfCourtesy}">
              <label for="allowPromotionMail">允許促銷：</label>
              <input type="checkbox" id="allowPromotionMail" class="swal2-input" ${customer.allowPromotionMail ? 'checked' : ''}>
             
              
            
              <label for="memberStatus">會員狀態：</label>
              <input type="text" id="memberStatus" class="swal2-input" value="${customer.memberStatus}" >
              
              <label for="mrId">會員等級ID：</label>
              <input type="number" id="mrId" class="swal2-input" value="${customer.mrId}">
            </form>`,
          focusConfirm: false,
          showCancelButton: true,
          confirmButtonText: '提交',
          preConfirm: () => {
            const form = document.getElementById('editCustomerForm');
            const editedCustomer = {
              cId: customer.cId,
              firstName: form.querySelector('#firstName').value,
              lastName: form.querySelector('#lastName').value,
              
              
              tel: form.querySelector('#tel').value,
              address: form.querySelector('#address').value,
              birthday: form.querySelector('#birthday').value,
              gender: form.querySelector('#gender').value,
              country: form.querySelector('#country').value,
              
              
              titleOfCourtesy: form.querySelector('#titleOfCourtesy').value,
              allowPromotionMail: form.querySelector('#allowPromotionMail').checked,
            
              
            
              memberStatus: form.querySelector('#memberStatus').value,
             
              mrId: form.querySelector('#mrId').value,
            };
            return editedCustomer;
          }
        });
  
        if (formValues) {
          try {
            const response = await axios.put(`/customer/pages/customer/${customer.cId}`, formValues);
            Swal.fire({
              icon: 'success',
              title: '修改成功!',
            }).then(() => { this.fetchCustomers(); });
          } catch (error) {
            Swal.fire({
              icon: 'error',
              title: '錯誤',
              text: '修改出現問題',
            });
          }
        }
      },
    },
    mounted() {
      this.fetchCustomers();
    },
  };
  </script>
  
  <style>
 
  </style>
  