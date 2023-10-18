import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.api.AuthorizeCourierRequest;
import org.example.api.CreateCourierRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.example.api.CreateCourierRequest.getRandomCourierData;
import static org.example.config.Enviroment.baseURL;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;


public class LoginCourierTest {

    CreateCourierRequest createRandomCourier;
    AuthorizeCourierRequest successLoginCourier;
    AuthorizeCourierRequest loginCourierWithoutLogin;
    AuthorizeCourierRequest loginCourierWithoutPassword;
    AuthorizeCourierRequest loginCourierWrongLogin;
    AuthorizeCourierRequest loginCourierWrongPassword;
    boolean isCreateCourier;

    @Before
    public void AuthorizeCourierRequest() {
        createRandomCourier = getRandomCourierData();

        successLoginCourier = new AuthorizeCourierRequest(
                createRandomCourier.login,
                createRandomCourier.password);

        loginCourierWithoutLogin = new AuthorizeCourierRequest(
                null,
                createRandomCourier.password);

        loginCourierWithoutPassword = new AuthorizeCourierRequest(
                createRandomCourier.login,
                null);

        loginCourierWrongLogin = new AuthorizeCourierRequest(
                "1234",
                createRandomCourier.password);

        loginCourierWrongPassword = new AuthorizeCourierRequest(
                createRandomCourier.login,
                "1234");
    }

    @Before
    public void createCourier() {
        given()
                .contentType(ContentType.JSON)
                .body(createRandomCourier)
                .when()
                .post(baseURL + "/api/v1/courier");

        isCreateCourier = true;
    }

    //курьер может авторизоваться;
    //успешный запрос возвращает id
    @Test
    public void successLoginCourierTest(){
        Response isCanAuthorize = given()
               // .log().all()
                .contentType(ContentType.JSON)
                .body(successLoginCourier)
                .when()
                .post(baseURL + "/api/v1/courier/login");
        isCanAuthorize.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK);
    }

    //для авторизации нужно передать все обязательные поля
    //если какого-то поля нет, запрос возвращает ошибку
    @Test
    public void loginCourierRequiredFieldsTest(){
        String resultWithoutLogin = given()
                //.log().all()
                .contentType(ContentType.JSON)
                .body(loginCourierWithoutLogin)
                .when()
                .post(baseURL + "/api/v1/courier/login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .path("message");

        String resultWithoutPassword = given()
                //.log().all()
                .contentType(ContentType.JSON)
                .body(loginCourierWithoutPassword)
                .when()
                .post(baseURL + "/api/v1/courier/login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .path("message");

        String expectedMessage = "Недостаточно данных для входа";

        assertEquals("Enough login data", expectedMessage, resultWithoutLogin);
        assertEquals("Enough login data", expectedMessage, resultWithoutPassword);
    }

    //система вернёт ошибку, если неправильно указать логин или пароль
    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку
    @Test
    public void loginCourierWrongValuesTest(){
        String resultWrongLogin = given()
                //.log().all()
                .contentType(ContentType.JSON)
                .body(loginCourierWrongLogin)
                .when()
                .post(baseURL + "/api/v1/courier/login")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .path("message");

        String resultWrongPassword = given()
                //.log().all()
                .contentType(ContentType.JSON)
                .body(loginCourierWrongPassword)
                .when()
                .post(baseURL + "/api/v1/courier/login")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .path("message");

        String expectedMessage = "Учетная запись не найдена";

        assertEquals("Enough login data", expectedMessage, resultWrongLogin);
        assertEquals("Enough login data", expectedMessage, resultWrongPassword);
    }

    @After
    public void deleteCourier() {
        if(isCreateCourier) {
            int courierId = given()
                    //.log().all()
                    .contentType(ContentType.JSON)
                    .body(successLoginCourier)
                    .when()
                    .post(baseURL + "/api/v1/courier/login")
                    .then()
                    //.log().all()
                    .extract()
                    .path("id");

            given()
                    //.log().all()
                    .contentType(ContentType.JSON)
                    .body("{\"id\":\"\"" + courierId + "\"}")
                    .when()
                    .delete(baseURL + "/api/v1/courier/" + courierId);
        }
    }
}
