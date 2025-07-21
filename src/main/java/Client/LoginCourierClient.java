package Client;

import DataProvider.CourierData;
import POJO.LoginCourierRequest;
import Specification.Specification;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class LoginCourierClient extends CourierData {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    private static final String LOGIN_URI ="api/v1/courier/login";


    @Step("Логин курьера")
    public ValidatableResponse loginCourier(LoginCourierRequest loginCourierRequest){

        return given()
                .spec(Specification.requestSpec(BASE_URI))
                .body(loginCourierRequest)
                .when()
                .post(LOGIN_URI)
                .then().log().all();

    }
}
