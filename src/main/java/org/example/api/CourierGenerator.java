package org.example.api;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static Faker faker = new Faker();

    public static CreateCourierRequest getRandomCourierData() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(12);
        String firstName = faker.name().firstName();
        return new CreateCourierRequest(login, password,firstName);
    }

    public static CreateCourierRequest getSameСourier() {
        String login = "1234abcd";
        String password = RandomStringUtils.randomAlphabetic(12);
        String firstName = faker.name().firstName();
        return new CreateCourierRequest(login, password,firstName);
    }

    public static CreateCourierRequest getСourierWithoutLogin() {
        String password = RandomStringUtils.randomAlphabetic(12);
        String firstName = faker.name().firstName();
        return new CreateCourierRequest(null, password,firstName);
    }

    public static CreateCourierRequest getСourierWithoutPassword() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String firstName = faker.name().firstName();
        return new CreateCourierRequest(login, null,firstName);
    }
}
