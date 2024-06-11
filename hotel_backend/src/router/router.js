//引入vue-router函式庫
import { createRouter, createWebHistory } from 'vue-router'

//引入SFC元件
import Home from '../views/Home.vue'
import NotFound from '../views/NotFound.vue';
import routersMemberPages from "@/views/Member/routers-memberPages.js"

//設定路由網址
const routes = [
    { name: "home-link", path: "/", component: Home },
    { name: "notfound-link", path: "/:pathMatch(.*)*", component: NotFound },

    ...routersMemberPages,

];


//產生router物件
const router = createRouter({
    routes,
    history: createWebHistory()
});

export default router;
