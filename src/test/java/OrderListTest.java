import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.api.AuthorizeCourierRequest;
import org.example.api.CourierGenerator;
import org.example.api.CreateCourierRequest;
import org.example.api.CreateOrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class OrderListTest {

    private CreateCourierRequest successCreatedCourier;
    private AuthorizeCourierRequest authorizeCourierRequest;
    private CreateOrderRequest createOrderRequest;
    private int courierId;

    @Before
    public void beforeOrderListTest() {
        successCreatedCourier = CourierGenerator.getRandomCourierData();
        authorizeCourierRequest = new AuthorizeCourierRequest();
        createOrderRequest = new CreateOrderRequest();
    }

    @After
    public void deleteCourier() {
        ValidatableResponse responseDelete = successCreatedCourier.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Check StatusCode and success get list orders")
    @Description("в тело ответа возвращается список заказов")
    public void orderListTest(){
        successCreatedCourier.createCourier(successCreatedCourier);
        ValidatableResponse responseAuthorize = authorizeCourierRequest.authorizeCourier(AuthorizeCourierRequest.from(successCreatedCourier));
        courierId = responseAuthorize.extract().path("id");
        createOrderRequest.createOrder(createOrderRequest);
        ValidatableResponse responseGetOrderList = CreateOrderRequest.getOrderList(courierId);
        int actualStatusCode = responseGetOrderList.extract().statusCode();
        ArrayList<String> listOrders = responseGetOrderList.extract().path("orders");
        assertEquals("StatusCode is not 200", SC_OK, actualStatusCode);
        assertThat(listOrders, notNullValue());
    }
}
