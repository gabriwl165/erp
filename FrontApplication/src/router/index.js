import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import LoginPage from "@/views/LoginPage";
import TelaPrincipal from "@/views/TelaPrincipal/TelaPrincipal";

Vue.use(VueRouter)

const params = {
    url: process.env.NODE_ENV === 'production' ? '187.22.223.105' : 'localhost',
}

const routes = [
    {path: '/', name: 'Login', component: LoginPage, props: {params}},
    {path: '/index', name: 'index', component: TelaPrincipal, props: {params}},
    {path: '/home', name: 'Home', component: Home, props: {params}},
    {
        path: '/about', name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
