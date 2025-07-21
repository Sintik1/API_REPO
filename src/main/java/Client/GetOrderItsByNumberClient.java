package Client;

import Specification.Specification;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class GetOrderItsByNumberClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru"; // Замените на ваш базовый URL
    private static final String TRACK_ORDER_URL = BASE_URL + "/api/v1/orders/track";

    @Step("Получение заказа по номеру трэка")
    public ValidatableResponse trackOrder(String trackingNumber){
        return given()
                .queryParam("t",trackingNumber)
                .when()
                .get(TRACK_ORDER_URL)
                .then().log().all();
    }
}
