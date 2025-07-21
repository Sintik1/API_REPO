package Client;

import POJO.CancelOrderRequest;
import Specification.Specification;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CancelOrderClient {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    private static final String CANCEL_ORDER="api/v1/orders/cancel";


    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(CancelOrderRequest cancelOrderRequest){
        return given()
                .spec(Specification.requestSpec(BASE_URI))
                .body(cancelOrderRequest)
                .when()
                .put(CANCEL_ORDER)
                .then().log().all();

    }
}
