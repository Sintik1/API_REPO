package Client;

import POJO.CreateOrderRequest;
import Specification.Specification;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CreateOrderClient {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    private static final String ORDER_URI ="api/v1/orders";


    @Step("Создание заказа")
    public ValidatableResponse createOrder(CreateOrderRequest createOrderRequest){
        return given()
                .spec(Specification.requestSpec(BASE_URI))
                .body(createOrderRequest)
                .when()
                .post(ORDER_URI)
                .then().log().all();
    }
}
