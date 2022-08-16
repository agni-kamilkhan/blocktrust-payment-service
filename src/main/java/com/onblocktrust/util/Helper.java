package com.onblocktrust.util;

import okhttp3.*;

import java.io.IOException;

public class Helper {

    private static OkHttpClient okHttpClient;

    private Helper() {}

    public static OkHttpClient createAuthenticatedClient(final String username, final String password) {
        if (okHttpClient != null) return okHttpClient;
        okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).build();
        return okHttpClient;
    }

}
