package org.example.api;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderGenerator {

    public static Faker faker = new Faker();
    public static Date now = new Date();

    public static CreateOrderRequest orderDefault(List<String> color) {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String metroStation = RandomStringUtils.randomAlphabetic(10);
        String phone = faker.phoneNumber().phoneNumber();
        int rentTime = ThreadLocalRandom.current().nextInt(1, 10);
        String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(faker.date().future(1, TimeUnit.SECONDS, now));
        String comment = RandomStringUtils.randomAlphabetic(10);
        return new CreateOrderRequest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate,
                comment, color);
    }
}
