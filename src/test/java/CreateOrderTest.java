import io.restassured.response.ValidatableResponse;

import org.example.api.CreateCourierRequest;
import org.example.api.CreateOrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//@RunWith(Parameterized.class)
public class CreateOrderTest {

    private CreateOrderRequest createOrderRequest;
    private CreateCourierRequest createCourierRequest;
    private int courierId;

    @Before
    public void CreateOrderTest() {
      //  createOrderRequest = new CreateOrderRequest();
    }

    @After
    public void deleteCourier() {
        ValidatableResponse deleteCourier = createCourierRequest.deleteCourier(courierId);
    }


    @Test
    public void successCreateOrderTest(){
       /*
                .post(baseURL + "/api/v1/orders")
                .then()
                //  .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .path("ok");

        assertTrue("Courier is not created", isCreateCourier);

        */
    }
}
