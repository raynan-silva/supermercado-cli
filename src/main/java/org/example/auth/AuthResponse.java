package org.example.auth;

public class AuthResponse {
    private String idToken;   // JWT do usuário logado
    private String email;
    private String refreshToken;
    private String expiresIn;
    private String localId;   // UID do usuário

    public String getIdToken() { return idToken; }
    public String getEmail() { return email; }
    public String getRefreshToken() { return refreshToken; }
    public String getExpiresIn() { return expiresIn; }
    public String getLocalId() { return localId; }
}
