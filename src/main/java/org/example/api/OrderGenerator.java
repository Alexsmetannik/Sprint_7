package org.example.api;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.concurrent.ThreadLocalRandom;

import java.util.List;

public class OrderGenerator {
    public static CreateOrderRequest orderDefault(List<String> color) {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        String metroStation = RandomStringUtils.randomAlphabetic(10);
        String phone = RandomStringUtils.randomNumeric(10);
        int rentTime = ThreadLocalRandom.current().nextInt(1, 10);
        String deliveryDate = "2023-10-20";
        String comment = RandomStringUtils.randomAlphabetic(10);
        return new CreateOrderRequest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate,
                comment, color);
    }
}
