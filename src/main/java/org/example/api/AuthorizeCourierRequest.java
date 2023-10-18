package org.example.api;

public class AuthorizeCourierRequest {

    public String login;
    public String password;

    public AuthorizeCourierRequest (String login, String password){
        this.login = login;
        this.password = password;
    }

    public AuthorizeCourierRequest(){
    }
}
