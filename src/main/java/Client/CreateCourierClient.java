package Client;

import DataProvider.CourierData;
import POJO.CreateCourierRequest;
import Specification.Specification;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;


public class CreateCourierClient extends CourierData {


    private static final String CREATE_URI = "/api/v1/courier";
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CreateCourierRequest createCourierRequest) {
       return given()
               .spec(Specification.requestSpec(BASE_URI))
                .body(createCourierRequest)
                .when()
                .post(CREATE_URI)
                .then().log().all();
    }
}
