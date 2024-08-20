import Vue from "vue"
import Router from 'vue-router'
import ListProduct from "./components/ListProduct.vue"
import CartPage from "./components/CartPage.vue"
Vue.use(Router)

export default new Router({ 
    routes: [
        { path: '/', component: ListProduct },
        { path: '/CartPage', component: CartPage }
    ]
})