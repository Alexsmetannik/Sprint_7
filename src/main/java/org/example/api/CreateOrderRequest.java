package org.example.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.example.config.Enviroment.baseURL;

public class CreateOrderRequest {

    private static final String pathCreate = "/api/v1/orders";
    private static final String pathList = "/api/v1/orders";
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public CreateOrderRequest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public CreateOrderRequest() {
    }

    @Step("order creation")
    public ValidatableResponse createOrder(CreateOrderRequest createOrderRequest) {
        return given()
                //.log().all()
                .contentType(ContentType.JSON)
                .body(createOrderRequest)
                .when()
                .post(baseURL + pathCreate)
                .then();
                //.log().all();
    }

    @Step("get list orders")
    public static ValidatableResponse getOrderList(int courierId) {
        return given()
                //  .log().all()
                .when()
                .get(baseURL + pathList + "?courierId=" + courierId)
                .then();
        //  .log().all()
    }
}
