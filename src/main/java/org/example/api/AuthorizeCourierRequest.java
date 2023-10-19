package org.example.api;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.config.Enviroment.baseURL;

public class AuthorizeCourierRequest {

    private static final String pathLogin = "/api/v1/courier/login";
    public String login;
    public String password;

    public AuthorizeCourierRequest (String login, String password){
        this.login = login;
        this.password = password;
    }

    public AuthorizeCourierRequest() {
    }

    public static AuthorizeCourierRequest from (CreateCourierRequest createCourierRequest) {
        return new AuthorizeCourierRequest(createCourierRequest.getLogin(), createCourierRequest.getPassword());
    }

    public ValidatableResponse authorizeCourier(AuthorizeCourierRequest authorizeCourierRequest) {
        return given()
                //  .log().all()
                .contentType(ContentType.JSON)
                .body(authorizeCourierRequest)
                .when()
                .post(baseURL + pathLogin)
                .then();
                //  .log().all()
    }
}
