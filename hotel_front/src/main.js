import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import VueCookies from 'vue-cookies';
import store from './store';

// 要加上store
createApp(App)
    .use(store)
    .use(router)
    .use(VueCookies, { expires: '2h' })
    .mount('#app')
