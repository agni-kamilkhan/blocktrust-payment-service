package com.onblocktrust.service.impl;

import com.onblocktrust.dtos.RazorPayOrder;
import com.onblocktrust.service.RazorPayService;
import com.onblocktrust.util.Helper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

@Slf4j
@ApplicationScoped
public class DefaultRazorPayService implements RazorPayService {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @ConfigProperty(name = "app.razorpay.key")
    String razorPayKey;

    @ConfigProperty(name = "app.razorpay.secret")
    String razorPaySecret;

    RazorPayOrder currentOrder;

    @Override
    public RazorPayOrder getCurrentOrder() throws IOException {
        if (this.currentOrder != null) {
            return this.currentOrder;
        }

        OkHttpClient okHttpClient = Helper.createAuthenticatedClient(this.razorPayKey, this.razorPaySecret);
        RazorPayOrder order = RazorPayOrder.builder().amount(50000).currency("INR").build();
//        Request request = new Request.Builder()
//                .post(RequestBody.create(gson.toJson(order), JSON))
//                .header("accept", "application/json")
//                .url("https://api.razorpay.com/v1/orders")
//                .build();
//
//        Response response = okHttpClient.newCall(request).execute();
//        if (response.isSuccessful()) {
//            String responseText = response.body().string();
//            log.info("ResponseText : {}", responseText)
//        } else {
//            log.error("Unexpected code : {} ", response);
//        }

        this.currentOrder = order;
        return this.currentOrder;
    }

}
