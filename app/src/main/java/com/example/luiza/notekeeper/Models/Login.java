package com.example.luiza.notekeeper.Models;


public class Login {

    public Login() {

    }

    public Login(String login, String password){
        this.Login = login;
        this.Password = password;
    }

    private String Login;
    private String Password;
    private String AcessToken;
    private String SocialAccessToken;
    private long SocialType;

    public long getSocialType() {
        return SocialType;
    }

    public void setSocialType(long socialType) {
        SocialType = socialType;
    }

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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
