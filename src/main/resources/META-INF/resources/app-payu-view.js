
const payuView = {
    name: 'PayU',
    template: '#app-payu-view',
    data() {
        var o = {
            key: null,
            txnid: null,
            productinfo: null,
            amount: null,
            email: null,
            phone: null,
            firstname: null,
            lastname: null,
            surl: 'https://localhost:9003/success',
            furl: 'https://localhost:9003/failure',
            hash: null
        }
        return { item: o, hashSalt: null, hashInput: null, message: null }
    },
    mounted() {
        http.get('/payments/payment-detail').then(res => {
            this.item.key = res.data.key;
            this.item.txnid = res.data.txnId;
            this.item.productinfo = res.data.productInfo;
            this.item.amount = res.data.amount;
            this.item.email = res.data.email;
            this.item.phone = res.data.phone;
            this.item.firstname = res.data.firstName;
            this.item.lastname = res.data.lastName;
            this.item.surl = res.data.successUrl;
            this.item.furl = res.data.failureUrl;
            this.item.hash = res.data.hash;
            this.hashSalt = res.data.hashSalt;
            this.hashInput = res.data.hashInput;
            console.log(res);
        });
    },
    methods: {
        reComputeHashInputAndValue() {
//            sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||SALT)
            let value = ''
            //value += 'sha512(';
            value += this.item.key;
            value += 'JP***g';
            value += '|';
            value += this.item.txnid;
            value += '|';
            value += this.item.amount;
            value += '|';
            value += this.item.productinfo;
            value += '|';
            value += this.item.firstname;
            value += '|';
            value += this.item.email;
            value += '|';

            value += '|';   // udf1
            value += '|';   // udf2
            value += '|';   // udf3
            value += '|';   // udf4
            value += '|';   // udf5

            value += '|';   // 1
            value += '|';   // 2
            value += '|';   // 3
            value += '|';   // 4
            value += '|';   // 5

            value += this.hashSalt;
            //value += ')';
            this.hashInput = value;
            this.item.hash = CryptoJS.SHA512(this.hashInput);
            //this.item.hash = JsCrypto.SHA256.hash(this.hashInput).toString();
        }
    }
};
