import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.example.api.AuthorizeCourierRequest;
import org.example.api.CreateCourierRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.example.api.CreateCourierRequest.getRandomCourierData;
import static org.example.config.Enviroment.baseURL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    CreateCourierRequest createRandomCourier;
    CreateCourierRequest createdSameСourier;
    CreateCourierRequest createdСourierWithoutLogin;
    CreateCourierRequest createdСourierWithoutPassword;
    AuthorizeCourierRequest successLoginCourier;
    boolean isCreateCourier;

    @Before
    public void CreateCourierRequest(){
        createRandomCourier = getRandomCourierData();

        createdSameСourier = new CreateCourierRequest(
                "1234abcd",
                getRandomCourierData().password,
                getRandomCourierData().firstName);

        createdСourierWithoutLogin = new CreateCourierRequest(
                null,
                getRandomCourierData().password,
                getRandomCourierData().firstName);

        createdСourierWithoutPassword = new CreateCourierRequest(
                getRandomCourierData().login,
                null,
                getRandomCourierData().firstName);

        successLoginCourier = new AuthorizeCourierRequest(
                createRandomCourier.login,
                createRandomCourier.password);
    }

        //курьера можно создать
        //запрос возвращает правильный код ответа
        //успешный запрос возвращает ok: true
    @Test
    public void successCreateCourierTest(){
        isCreateCourier = true;

        boolean isCreateCourier = given()
              //  .log().all()
                .contentType(ContentType.JSON)
                .body(createRandomCourier)
                .when()
                .post(baseURL + "/api/v1/courier")
                .then()
              //  .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .path("ok");

        assertTrue("Courier is not created", isCreateCourier);
        }

        //нельзя создать двух одинаковых курьеров
        //запрос возвращает правильный код ответа
        //если создать пользователя с логином, который уже есть, возвращается ошибка
    @Test
    public void createSameCourierTest(){
        isCreateCourier = false;

        String result = given()
              //  .log().all()
                .contentType(ContentType.JSON)
                .body(createdSameСourier)
                .when()
                .post(baseURL + "/api/v1/courier")
                .then()
              //  .log().all()
                .statusCode(HttpStatus.SC_CONFLICT)
                .extract()
                .path("message");

        String expectedMessage = "Этот логин уже используется";

        assertEquals("You create two same couriers", expectedMessage, result);
    }

        //чтобы создать курьера, нужно передать в ручку все обязательные поля
        //запрос возвращает правильный код ответа
        //если одного из полей нет, запрос возвращает ошибку
    @Test
    public void createCourierRequiredFieldsTest(){
        isCreateCourier = false;

        String resultWithoutLogin = given()
               // .log().all()
                .contentType(ContentType.JSON)
                .body(createdСourierWithoutLogin)
                .when()
                .post(baseURL + "/api/v1/courier")
                .then()
               // .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .path("message");

        String resultWithoutPassword = given()
              //  .log().all()
                .contentType(ContentType.JSON)
                .body(createdСourierWithoutPassword)
                .when()
                .post(baseURL + "/api/v1/courier")
                .then()
               // .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .path("message");

        String expectedMessage = "Недостаточно данных для создания учетной записи";

        assertEquals("Enough data to create account", expectedMessage, resultWithoutLogin);
        assertEquals("Enough data to create account", expectedMessage, resultWithoutPassword);
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