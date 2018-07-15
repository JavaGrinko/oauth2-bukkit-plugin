package ru.javainside.oauth.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import ru.javainside.oauth.model.OAuthResponse;

import java.io.IOException;

public class OAuth2Service {

    private final OkHttpClient client = new OkHttpClient();
    private Gson gson = new GsonBuilder().create();

    public OAuthResponse auth(String username, String password, String clientId,
                              String clientSecret, String grantPathUrl) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(grantPathUrl)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        return gson.fromJson(response.body().string(), OAuthResponse.class);
    }
}
