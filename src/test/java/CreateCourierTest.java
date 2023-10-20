import io.restassured.response.ValidatableResponse;
import org.example.api.AuthorizeCourierRequest;
import org.example.api.CourierGenerator;
import org.example.api.CreateCourierRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    private CreateCourierRequest createCourierRequest;
    private CreateCourierRequest successCreatedCourier;
    private CreateCourierRequest sameCreatedCourier;
    private CreateCourierRequest courierWithoutLogin;
    private CreateCourierRequest courierWithoutPassword;
    private AuthorizeCourierRequest authorizeCourierRequest;
    private int courierId;

    @Before
    public void BeforeCreateCourierTest(){
        createCourierRequest = new CreateCourierRequest();
        authorizeCourierRequest = new AuthorizeCourierRequest();
        successCreatedCourier = CourierGenerator.getRandomCourierData();
        sameCreatedCourier = CourierGenerator.getSameСourier();
        courierWithoutLogin = CourierGenerator.getСourierWithoutLogin();
        courierWithoutPassword = CourierGenerator.getСourierWithoutPassword();
    }

    @After
    public void deleteCourier() {
        ValidatableResponse responseDelete = createCourierRequest.deleteCourier(courierId);
    }

        //курьера можно создать
        //запрос возвращает правильный код ответа
        //успешный запрос возвращает ok: true
    @Test
    public void successCreateCourierTest(){
        ValidatableResponse responseCreate = createCourierRequest.createCourier(successCreatedCourier);
        int actualStatusCode = responseCreate.extract().statusCode();
        Boolean isCourierCreated = responseCreate.extract().path("ok");
        ValidatableResponse responseLogin = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        courierId = responseLogin.extract().path("id");
        assertEquals("StatusCode is not 201", SC_CREATED, actualStatusCode);
        assertTrue("Courier is not created", isCourierCreated);
        }

        //нельзя создать двух одинаковых курьеров
        //запрос возвращает правильный код ответа
        //если создать пользователя с логином, который уже есть, возвращается ошибка
    @Test
    public void createSameCourierTest(){
        ValidatableResponse responseCreate = createCourierRequest.createCourier(sameCreatedCourier);
        int actualStatusCode = responseCreate.extract().statusCode();
        String actualMessage = responseCreate.extract().path("message");
        String expectedMessage = "Этот логин уже используется";
        assertEquals("StatusCode is not 409", SC_CONFLICT, actualStatusCode);
        assertEquals("You create two same couriers", expectedMessage, actualMessage);
    }

        //чтобы создать курьера, нужно передать в ручку все обязательные поля
        //запрос возвращает правильный код ответа
        //если одного из полей нет, запрос возвращает ошибку
    @Test
    public void createCourierWithoutLoginTest() {
        ValidatableResponse responseCreate = createCourierRequest.createCourier(courierWithoutLogin);
        int actualStatusCode = responseCreate.extract().statusCode();
        String actualMessage = responseCreate.extract().path("message");
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("StatusCode is not 400", SC_BAD_REQUEST, actualStatusCode);
        assertEquals("Enough data to create account", expectedMessage, actualMessage);
    }

    //чтобы создать курьера, нужно передать в ручку все обязательные поля
    //запрос возвращает правильный код ответа
    //если одного из полей нет, запрос возвращает ошибку
    @Test
    public void createCourierWithoutPasswordTest() {
        ValidatableResponse responseCreate = createCourierRequest.createCourier(courierWithoutPassword);
        int actualStatusCode = responseCreate.extract().statusCode();
        String actualMessage = responseCreate.extract().path("message");
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("StatusCode is not 400", SC_BAD_REQUEST, actualStatusCode);
        assertEquals("Enough data to create account", expectedMessage, actualMessage);
    }
}