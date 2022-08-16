const razorpayView = {
    name: 'Razor Pay',
    template: '#app-razorpay-view',
    data() {
        let o = {
            keyId: 'rzp_test_cwpsi4iy7TJC8y',
            orderId: null,
            amount: null,
            businessName: 'Some Corp',
            description: 'Some Description',
            prefillName: 'Some Name',
            prefillContact: '9123456780',
            prefillEmail: 'somename@somedomain.com',
            prefillAddress: '#1, Some Street, Some City - 600000',
            imageUrl: 'https://cdn.razorpay.com/logos/BUVwvgaqVByGp2_large.jpg',
            callbackUrl: 'http://localhost:9003/index#/success',
            cancelUrl: 'http://localhost:9003/index#/failure'
        };
        return { item: o };
    },
    mounted() {
        http.get('/razorpay/current-order').then(res => {
            console.log(res);
            this.item.orderId = res.data.id;
            this.item.amount = res.data.amount;
        });
    },
    methods: {
        async makePayment() {
            razorpayService.successResponse = null;
            razorpayService.failureResponse = null;
            let routerRef = this.$router;
            let options = {
                'key': this.item.keyId,
                'order_id': this.item.orderId,
                'amount': this.item.amount,
                'name': this.item.businessName,
                'description': this.item.description,
                'prefill': {
                    'name': this.item.prefillName,
                    'contact': this.item.prefillContact,
                    'email': this.item.prefillEmail,
                },
                'notes': {
                    'address': this.prefillAddress
                },
                'image': this.item.imageUrl,
                'handler': function (res) {
                    console.log(res)
                    razorpayService.successResponse = res;
                    routerRef.push('/razorpay/success');
                },
            }
            console.log(options);
            let razorpay = new Razorpay(options);
            razorpay.on('payment.failed', function (res){
                console.log(res);
                razorpayService.failureResponse = res;
                routerRef.push('/razorpay/failure');
            });
            razorpay.open();
        }
    }
};

const razorpaySuccessView = {
    name: 'Razor Pay Success',
    template: '#app-razorpay-success-view',
    data() {
        razorpayService.successResponse = {
            razorpay_payment_id: 'pay_K6NOHhkiQ0VLYy',
            razorpay_order_id: 'order_K6NO4L5VqdghHS',
            razorpay_signature: 'bde3a3c9ca54475e624a3c2e9b363241c2e3eabf4a69d97fa119c42fbfbfe8bb"'
        };
        return { item: razorpayService.successResponse, itemS: JSON.stringify(razorpayService.successResponse) };
    },
}

const razorpayFailureView = {
    name: 'Razor Pay Failure',
    template: '#app-razorpay-failure-view',
    data() {
        return { item: razorpayService.failureResponse, itemS: JSON.stringify(razorpayService.failureResponse)  }
    }
}