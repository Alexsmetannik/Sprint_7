package org.example.api;

import org.apache.commons.lang3.RandomStringUtils;

public class CreateCourierRequest {
    String login;
    String password;
    String firstName;
    public CreateCourierRequest (String login, String password, String firstName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static CreateCourierRequest getRandomCourierData() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(12);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        return new CreateCourierRequest(login, password,firstName);
    }
}
