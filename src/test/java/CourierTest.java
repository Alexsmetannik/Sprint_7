import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.example.api.CreateCourierRequest;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.example.api.CreateCourierRequest.getRandomCourierData;
import static org.example.config.Enviroment.baseURL;

public class CourierTest {
    CreateCourierRequest createCourierRequest;
    @Before
    public void CreateCourierRequest(){
        /*
        File body = new File("src/test/resources/newCourier.json");
        String login = "abcde12347";
        String password = "password1234";
        String firstName = "test001";
         */
        createCourierRequest = getRandomCourierData();
    }

    @Test
    public void createCourierTest(){
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(createCourierRequest)
                .when()
                .post(baseURL + "/api/v1/courier")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED);
        }
}
