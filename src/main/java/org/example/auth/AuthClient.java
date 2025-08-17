package org.example.auth;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class AuthClient {
    private final String apiKey;
    private final OkHttpClient http = new OkHttpClient();
    private final Gson gson = new Gson();

    public AuthClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public AuthResponse signUp(String email, String password) throws IOException {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + apiKey;
        return postAuth(url, email, password);
    }

    public AuthResponse signIn(String email, String password) throws IOException {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey;
        return postAuth(url, email, password);
    }

    private AuthResponse postAuth(String url, String email, String password) throws IOException {
        String json = gson.toJson(new AuthPayload(email, password, true));
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = http.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String err = response.body() != null ? response.body().string() : ("HTTP " + response.code());
                throw new IOException("Falha Auth: " + err);
            }
            String resp = response.body().string();
            return gson.fromJson(resp, AuthResponse.class);
        }
    }

    private static class AuthPayload {
        final String email;
        final String password;
        final boolean returnSecureToken;

        AuthPayload(String email, String password, boolean returnSecureToken) {
            this.email = email;
            this.password = password;
            this.returnSecureToken = returnSecureToken;
        }
    }
}
