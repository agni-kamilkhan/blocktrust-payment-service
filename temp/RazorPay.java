///usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS org.projectlombok:lombok:1.18.24
//DEPS org.slf4j:slf4j-api:1.7.36
//DEPS org.slf4j:slf4j-simple:1.7.36
//DEPS com.google.code.gson:gson:2.9.1
//DEPS com.squareup.okhttp3:okhttp:4.10.0
//DEPS com.squareup.okhttp3:logging-interceptor:4.10.0

import static java.lang.System.*;

import java.io.IOException;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import okhttp3.*;

@Slf4j
public class RazorPay {

    public static void main(String... args) {
        RazorPay object = new RazorPay();
        try {
            object.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    String razorPayKey = "rzp_test_cwpsi4iy7TJC8y";
    String razorPaySecret = "G7j69HDTNPC004qCtXp2oztq";

    OkHttpClient okHttpClient = createAuthenticatedClient(this.razorPayKey, this.razorPaySecret);

    void run() throws Exception {
        log.info("------------------------------------");

        log.info("RazorPay Key : {}", this.razorPayKey);
        log.info("RazorPay Sec : {}", this.razorPaySecret);

        Gson gson = new Gson();

        RazorOrder order = RazorOrder.builder()
                .currency("INR")
                .amount(500)
                .build();

        Request request = new Request.Builder()
                .post(RequestBody.create(gson.toJson(order), JSON))
                .header("accept", "application/json")
                .url("https://api.razorpay.com/v1/orders")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseText = null;
        if (response.isSuccessful()) {
            responseText = response.body().string();
        } else {
            responseText = "Unexpected code " + response;
        }
        out.println(responseText);

        log.info("------------------------------------");
    }

    private static OkHttpClient createAuthenticatedClient(final String username, final String password) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).build();
        return okHttpClient;
    }
}

@Builder
@Data
class RazorOrder {

    private String currency;

    private float amount;
}