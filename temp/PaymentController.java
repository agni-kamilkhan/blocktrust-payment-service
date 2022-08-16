package com.onblocktrust.controllers;

import com.onblocktrust.dtos.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Kamil Khan
 */

@Slf4j
@Path("/payments")
@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
public class PaymentController {

    @ConfigProperty(name = "app.payu.merchant-key")
    String merchantKey;

    @ConfigProperty(name = "app.payu.merchant-salt1")
    String merchantSalt1;

    @ConfigProperty(name = "app.payu.merchant-salt2")
    String merchantSalt2;

    private final OkHttpClient client = new OkHttpClient();

    @GET
    @Path("verify-payment")
    public ResponseDto verifyPayment() throws Exception {
        ResponseDto responseDto = ResponseDto.builder().build();

        List<String> pkeys = new ArrayList<>();
        pkeys.add("merchantKey");
        pkeys.add("transactionId");
        pkeys.add("paymentAmount");
        pkeys.add("productInfo");
        pkeys.add("firstName");
        pkeys.add("email");
        pkeys.add("udf1");
        pkeys.add("udf2");
        pkeys.add("udf3");
        pkeys.add("udf4");
        pkeys.add("udf5");

        Map<String, String> params = new HashMap<>();
        params.put("merchantKey", this.merchantKey);
        params.put("transactionId", UUID.randomUUID().toString());
        params.put("paymentAmount", "1000");
        params.put("productInfo", "Shopping");
        params.put("firstName", "Demo Name");
        params.put("email", "demo@demo.com");
        params.put("udf1", "");
        params.put("udf2", "");
        params.put("udf3", "");
        params.put("udf4", "");
        params.put("udf5", "");
        params.put("salt", this.merchantSalt2);

        //  const text = key+'|'+txnid+'|'+amount+'|'+productinfo+'|'+firstname+'|'+email+'|'+ udf1+'|'+udf2+'|'+udf3+'|'+udf4+'|'+udf5+'||||||'+salt;
        StringBuilder stringBuilder = new StringBuilder();
        for (String pkey : pkeys) {
            stringBuilder.append(params.get(pkey));
            stringBuilder.append("|");
        }
        stringBuilder.append("|||||");
        stringBuilder.append(params.get("salt2"));

        params.put("hashKey", stringBuilder.toString());
        params.put("hashValue", hashIt(params.get("hashKey")));

        RequestBody formBody = new FormBody.Builder()
                .add("key", params.get("merchantKey"))
                .add("command", "verify_payment")
                .add("var1", params.get("transactionId"))
                .add("hash", params.get("hashValue"))
                .build();
        Request request = new Request.Builder()
                .header("accept", "application/json")
//                .header("Content-Type", "application/x-www-form-urlencoded")
                .url("https://test.payu.in/merchant/postservice?form=2")
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseText = null;
            if (response.isSuccessful()) {
                params.put("responseCode", "" + response.code());
                responseText = response.body().string();
            } else {
                responseText = "Unexpected code " + response;
            }
            params.put("responseText", responseText);
            System.out.println(responseText);
        }
        responseDto.setData(params);

        responseDto.setType(ResponseDto.SUCCESS);
        responseDto.setMessage("verifyPayment success...");

        return responseDto;
    }

    static String hashIt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
