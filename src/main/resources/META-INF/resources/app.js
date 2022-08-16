const notFoundView = {
    name: 'Not Found',
    template: '<p>Page not found...</p>'
};

const http = axios.create({
    baseURL: "http://localhost:9003/api",
    headers: {
        "Content-type": "application/json",
    },
});

const razorpayService = {

    successResponse: null,

    failureResponse: null

}

const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes: [
        { path: '/:pathMatch(.*)*', name: 'NotFound', component: notFoundView },
        { path: '/', redirect: '/home' },
        { path: '/home', name: homeView.name, component: homeView },
        { path: '/success', name: successView.name, component: successView },
        { path: '/failure', name: failureView.name, component: failureView },
        { path: '/payu/pay', name: payuView.name, component: payuView },
        { path: '/razorpay/pay', name: razorpayView.name, component: razorpayView },
        { path: '/razorpay/success', name: razorpaySuccessView.name, component: razorpaySuccessView },
        { path: '/razorpay/failure', name: razorpayFailureView.name, component: razorpayFailureView },
    ]
});

const app = Vue.createApp({
    name: 'app',
    template: '#app-root-view'
});

app.use(router);

app.mount('#app');