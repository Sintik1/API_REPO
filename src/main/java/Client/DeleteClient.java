package Client;

import Specification.Specification;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class DeleteClient {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    private static final String DELETE_URI ="api/v1/courier/";
    private Integer id;

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(Integer id){
        return given()
                .spec(Specification.requestSpec(BASE_URI))
                .when()
                .delete(DELETE_URI+id)
                .then().log().all();
    }

}
