package org.example.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.config.Enviroment.baseURL;

public class CreateCourierRequest {

    private static final String pathCreate = "/api/v1/courier";
    private static final String pathDelete = "/api/v1/courier/login";
    public String login;
    public String password;
    public String firstName;

    public CreateCourierRequest (String login, String password, String firstName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CreateCourierRequest () {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Step("courier creation")
    public ValidatableResponse createCourier(CreateCourierRequest createCourierRequest) {
        return given()
                //  .log().all()
                .contentType(ContentType.JSON)
                .body(createCourierRequest)
                .when()
                .post(baseURL + pathCreate)
                .then();
                //  .log().all()
    }

    @Step("courier removal")
    public ValidatableResponse deleteCourier(int courierId) {
        return given()
                //  .log().all()
                .contentType(ContentType.JSON)
                .body("{\"id\":\"\"" + courierId + "\"}")
                .when()
                .delete(baseURL + pathDelete+ courierId)
                .then();
                //  .log().all()
    }
}