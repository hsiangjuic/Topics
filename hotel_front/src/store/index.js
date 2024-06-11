// store/index.js
import { createStore } from 'vuex';

export default createStore({
    state: {
        username: '',
        isAuthenticated: false,
    },
    mutations: {
        setUsername(state, username) {
            state.username = username;
            state.isAuthenticated = true;
        },
        logout(state) {
            state.username = '';
            state.isAuthenticated = false;
        }
    },
    getters: {
        getUsername: state => state.username,
        isAuthenticated: state => state.isAuthenticated
    }
});
