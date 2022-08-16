const razorpayView = {
    name: 'Razor Pay',
    template: '#app-razorpay-view',
    data() {
        let o = {
            id: null,
            currency: null,
            amount: null
        };
        return { item: o };
    },
    mounted() {
    },
    methods: {
        createOrder() {
            http.get('/razorpay/current-order').then(res => {
                console.log(res);
            });
        }
    }
};
