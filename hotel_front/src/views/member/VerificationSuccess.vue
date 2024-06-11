<template>
    <div class="verification-success">
        <h2 v-if="showSuccessMessage">驗證成功</h2>
        <p v-if="showSuccessMessage">您的電子郵件已經成功驗證。</p>

        <router-link to="/pages/login">前往登入</router-link>
    </div>
</template>

<script>
import axios from '@/plugins/axios.js';
import Swal from 'sweetalert2';

export default {
    data() {
        return {
            showSuccessMessage: true // 初始化為true，驗證失敗為false
        };
    },
    mounted() {
        const email = new URLSearchParams(window.location.search).get('email');
        const token = new URLSearchParams(window.location.search).get('token');

        if (email && token) {
            axios.get(`/api/customers/verify?email=${email}&token=${token}`)
                .then(response => {
                    console.log("Email verified successfully");
                    // 更新 true
                    this.updateMemberStatus(email, 'true');
                })
                .catch(error => {
                    console.error("An error occurred while verifying email:", error);

                    Swal.fire({
                        icon: 'error',
                        title: '驗證時間過期',
                        text: '您的驗證連結已經過期，請重新註冊會員。',
                        confirmButtonText: '確定'
                    });

                    this.showSuccessMessage = false;
                });
        }
    },
    methods: {
        updateMemberStatus(email, status) {
            axios.post('/api/customers/updateMemberStatus', {
                email: email,
                status: status
            })
                .then(response => {
                    console.log("Member status updated successfully");


                })
                .catch(error => {
                    console.error("An error occurred while updating member status:", error);

                });
        }
    }
}
</script>

<style scoped>
.verification-success {
    max-width: 600px;
    margin: 0 auto;
    padding: 2em;
    text-align: center;
    background-color: #e0ffe0;
    border-radius: 8px;
}
</style>
