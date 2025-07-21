package Client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class AcceptOrderClient {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/api/v1/orders/accept/";
    private static final String ACCEPT_ORDER = "api/v1/orders/accept/";





    @Step("Принять заказ")
    public ValidatableResponse acceptOrder(Integer id,Integer param){
        System.out.println("Accepting order with id: " + id + " and courierId: " + param);
        return given()

                .queryParam("courierId",param)
                .when()
                .put(BASE_URI + id)
                .then().log().all();
    }
}
