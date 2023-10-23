import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.example.api.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private CreateCourierRequest successCreatedCourier;
    private AuthorizeCourierRequest authorizeCourierRequest;
    private CreateOrderRequest createOrderRequest;
    private final int resultCode;
    private int courierId;

    @Before
    public void beforeCreateOrderTest() {
        successCreatedCourier = CourierGenerator.getRandomCourierData();
        authorizeCourierRequest = new AuthorizeCourierRequest();
    }

    @After
    public void deleteCourier() {
        ValidatableResponse responseDelete = successCreatedCourier.deleteCourier(courierId);
    }

    public CreateOrderTest(CreateOrderRequest createOrderRequest, int resultCode) {
        this.createOrderRequest = createOrderRequest;
        this.resultCode = resultCode;
    }
    @Parameterized.Parameters()
    public static Object[][] getOptionColor() {
        return new Object[][] {
                {OrderGenerator.orderDefault(List.of("BLACK")), SC_CREATED},
                {OrderGenerator.orderDefault(List.of("GREY")) , SC_CREATED},
                {OrderGenerator.orderDefault(List.of("BLACK", "GREY")) , SC_CREATED},
                {OrderGenerator.orderDefault(List.of("")), SC_CREATED}
        };
    }

    @Test
    @DisplayName("Check StatusCode and get 'track' after create order")
    @Description("можно указать один из цветов — BLACK или GREY, " +
            "можно указать оба цвета, " +
            "можно совсем не указывать цвет, тело ответа содержит track")
    public void createOrderOptionsColorTest(){
        successCreatedCourier.createCourier(successCreatedCourier);
        ValidatableResponse responseAuthorize = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        courierId = responseAuthorize.extract().path("id");
        ValidatableResponse responseCreate = createOrderRequest.createOrder(createOrderRequest);
        int actualStatusCode = responseCreate.extract().statusCode();
        int track = responseCreate.extract().path("track");
        assertEquals("StatusCode is not 201", resultCode, actualStatusCode);
        assertThat(track, notNullValue());
        }
}
