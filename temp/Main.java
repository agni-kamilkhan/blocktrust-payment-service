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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

import com.google.gson.Gson;

import okhttp3.*;

@Slf4j
public class PayU {

    public static void main(String... args) {
        PayU object = new PayU();
        try {
            object.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient okHttpClient = new OkHttpClient();

    void run() throws Exception {
        log.info("------------------------------------");

        log.info("------------------------------------");
    }
}

