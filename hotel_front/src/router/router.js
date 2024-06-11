//引入vue-router函式庫
import { createRouter, createWebHistory } from 'vue-router'

//引入SFC元件
import Home from '../views/Home.vue'
import NotFound from '../views/NotFound.vue';
// import Login from "../views/secure/Login.vue"
import routersBookingPages from "@/views/bookingPages/routers-bookingPages.js"

import routersRestaurantPages from '@/views/restaurant/routers-restaurantPages';
//back
import routersMemberPages from '@/views/member/routers-memberPages';

//front
import routersNewsFront from '@/views/frontLatestNews/routers-front-news';

import routersRoom from '@/views/Room/routers-room';

import Login from "../views/member/Login.vue";
import Customer from "../views/member/Customer.vue";
import Logout from '../views/member/Logout.vue';
import Register from '../views/member/Register.vue';
import Customerupdate from '../views/member/Customerupdate.vue';
import VerificationSuccess from '@/views/member/VerificationSuccess.vue';
import SendVerificationEmail from '@/views/member/SendVerificationEmail.vue';
// import SendVerificationEmail from '@/views/pages/SendVerificationEmail.vue';
import Forgotpassword from '@/views/member/Forgotpassword.vue';
import store from '@/store';

//設定路由網址
const routes = [
    { name: "home-link", path: "/", component: Home },
    { name: "notfound-link", path: "/:pathMatch(.*)*", component: NotFound },
    { name: "login-link", path: "/pages/login", component: Login },
    { name: "customer-link", path: "/pages/customer", component: Customer, meta: { requiresAuth: true } },
    { name: "logout-link", path: "/pages/logout", component: Logout },
    { name: "register-link", path: "/pages/register", component: Register },
    { name: "verificationSuccess-link", path: "/pages/verificationSuccess", component: VerificationSuccess },
    { name: "sendVerificationEmail-link", path: "/pages/sendVerificationEmail", component: SendVerificationEmail },
    // { name: "sendVerificationEmail-link", path: "/pages/sendVerificationEmail", component: SendVerificationEmail },
    //meta: { requiresAuth: true }這段是登錄綁定
    { name: "forgotpassword-link", path: "/pages/forgotpassword", component: Forgotpassword },
    { name: "customerupdate-link", path: "/pages/customerupdate", component: Customerupdate, meta: { requiresAuth: true } },
    ...routersBookingPages,
    ...routersRestaurantPages,
    ...routersMemberPages,
    ...routersNewsFront,
    ...routersRoom
];

//產生router物件
const router = createRouter({
    routes,
    history: createWebHistory()
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!store.getters.isAuthenticated) {
            next({ name: 'login-link' });
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router;
