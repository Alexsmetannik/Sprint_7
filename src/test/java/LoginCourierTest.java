import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.api.AuthorizeCourierRequest;
import org.example.api.CourierGenerator;
import org.example.api.CreateCourierRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;


public class LoginCourierTest {

    private CreateCourierRequest successCreatedCourier;
    private AuthorizeCourierRequest authorizeCourierRequest;
    private int courierId;

    @Before
    public void BeforeLoginCourierTest() {
        successCreatedCourier = CourierGenerator.getRandomCourierData();
        authorizeCourierRequest = new AuthorizeCourierRequest();
    }

    @After
    public void deleteCourier() {
        ValidatableResponse responseDelete = successCreatedCourier.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Check StatusCode and get 'id' after success authorize courier")
    @Description("курьер может авторизоваться, успешный запрос возвращает id")
    public void successAuthorizeCourierTest(){
        successCreatedCourier.createCourier(successCreatedCourier);
        ValidatableResponse responseAuthorize = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        courierId = responseAuthorize.extract().path("id");
        int actualStatusCode = responseAuthorize.extract().statusCode();
        assertEquals("StatusCode is not 200", SC_OK, actualStatusCode);
        assertThat(courierId, notNullValue());
    }

    @Test
    @DisplayName("Check StatusCode and get 'message' after authorize courier without login")
    @Description("для авторизации нужно передать все обязательные поля, " +
            "если какого-то поля нет, запрос возвращает ошибку")
    public void authorizeCourierWithoutLoginTest() {
        successCreatedCourier.createCourier(successCreatedCourier);
        ValidatableResponse responseAuthorize = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        courierId = responseAuthorize.extract().path("id");
        successCreatedCourier.setLogin(null);
        ValidatableResponse responseWithoutLogin = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        int actualStatusCode = responseWithoutLogin.extract().statusCode();
        String actualMessage = responseWithoutLogin.extract().path("message");
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals("StatusCode is not 400", SC_BAD_REQUEST, actualStatusCode);
        assertEquals("Enough login data", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check StatusCode and get 'message' after authorize courier without password")
    @Description("для авторизации нужно передать все обязательные поля, " +
            "если какого-то поля нет, запрос возвращает ошибку")
    public void authorizeCourierWithoutPasswordTest() {
        successCreatedCourier.createCourier(successCreatedCourier);
        ValidatableResponse responseAuthorize = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        courierId = responseAuthorize.extract().path("id");
        successCreatedCourier.setPassword(null);
        ValidatableResponse responseWithoutPassword = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        int actualStatusCode = responseWithoutPassword.extract().statusCode();
        String actualMessage = responseWithoutPassword.extract().path("message");
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals("StatusCode is not 400", SC_BAD_REQUEST, actualStatusCode);
        assertEquals("Enough login data", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check StatusCode and get 'message' after authorize courier with wrong login")
    @Description("система вернёт ошибку, если неправильно указать логин или пароль, " +
            "если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void authorizeCourierWithWrongLoginTest() {
        successCreatedCourier.createCourier(successCreatedCourier);
        ValidatableResponse responseAuthorize = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        courierId = responseAuthorize.extract().path("id");
        successCreatedCourier.setLogin("1234");
        ValidatableResponse responseWrongLogin = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        int actualStatusCode = responseWrongLogin.extract().statusCode();
        String actualMessage = responseWrongLogin.extract().path("message");
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("StatusCode is not 404", SC_NOT_FOUND, actualStatusCode);
        assertEquals("Account is found", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check StatusCode and get 'message' after authorize courier with wrong password")
    @Description("система вернёт ошибку, если неправильно указать логин или пароль, " +
            "если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void authorizeCourierWithWrongPasswordTest() {
        successCreatedCourier.createCourier(successCreatedCourier);
        ValidatableResponse responseAuthorize = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        courierId = responseAuthorize.extract().path("id");
        successCreatedCourier.setPassword("1234");
        ValidatableResponse responseWrongPassword = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        int actualStatusCode = responseWrongPassword.extract().statusCode();
        String actualMessage = responseWrongPassword.extract().path("message");
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("StatusCode is not 404", SC_NOT_FOUND, actualStatusCode);
        assertEquals("Account is found", expectedMessage, actualMessage);
    }
}
