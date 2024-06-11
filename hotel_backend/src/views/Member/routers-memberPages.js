//引入SFC元件
import MemberRank from "./MemberRank.vue";
import Customer from "./Customer.vue";


//設定路由網址
export default [
    { name: "memberRank-link", path: "/pages/memberRank", component: MemberRank },
    { name: "customer-link", path: "/pages/customer", component: Customer },

]