package com.project.Citas;

public class TokenResponse {
    private String Token;
    public TokenResponse(String token){
        this.Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
    
}