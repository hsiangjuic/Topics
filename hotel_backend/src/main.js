import { createApp } from 'vue'
import App from './App.vue'
import router from "./router/router.js"
import VueCookies from 'vue-cookies';

createApp(App)
    .use(router)
    .use(VueCookies, { expires: '2h' })
    .mount('#app')
