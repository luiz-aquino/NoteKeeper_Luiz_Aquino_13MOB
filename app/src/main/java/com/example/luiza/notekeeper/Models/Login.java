package com.example.luiza.notekeeper.Models;


public class Login {
    private String Login;
    private String AcessToken;
    private String SocialAccessToken;

    public long getSocialType() {
        return SocialType;
    }

    public void setSocialType(long socialType) {
        SocialType = socialType;
    }

    private long SocialType;

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getAcessToken() {
        return AcessToken;
    }

    public void setAcessToken(String acessToken) {
        AcessToken = acessToken;
    }

    public String getSocialAccessToken() {
        return SocialAccessToken;
    }

    public void setSocialAccessToken(String socialAccessToken) {
        SocialAccessToken = socialAccessToken;
    }

}
