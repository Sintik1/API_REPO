package Client;

import Specification.Specification;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class GetListOrderClient {

    private static final String LIST_ORDER_URI="api/v1/orders";
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";


    @Step ("Получение списка заказов")
    public ValidatableResponse getListOrder(){
        return given()
                .when().get(LIST_ORDER_URI)
                .then().log().all();
    }
}
