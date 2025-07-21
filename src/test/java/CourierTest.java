import Client.CreateCourierClient;
import DataProvider.CourierData;
import POJO.CreateCourierRequest;
import POJO.CreateCourierSuccessResponse;
import POJO.CreateCourierUnSuccessResponse;
import Specification.Specification;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierTest {
CreateCourierClient courierClient = new CreateCourierClient();


    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";


   /* @DisplayName("Сценарий создания двух одинаковых курьеров")
    @Description("Должен вернуться статус код 409 и сообщение Этот логин уже используется ")
    @Test
    public void repeatCreateCourier(){
        CreateCourierRequest repeatCreateCourier =CourierData.generateData();
        courierClient.createCourier(repeatCreateCourier)
                .statusCode(SC_CREATED);
        CreateCourierUnSuccessResponse unSuccessResponse =
                courierClient.createCourier(repeatCreateCourier)
                        .statusCode(SC_CONFLICT)
                        .extract().as(CreateCourierUnSuccessResponse.class);
        Assert.assertEquals("Этот логин уже используется. Попробуйте другой.",unSuccessResponse.getMessage());
    }
    */
   @DisplayName("Кейс проверки, что нельзя создать двух одинаковых курьеров")
   @Description("Должна вернуться ошибка 409 Этот логин уже используется.Попробуйте другой")
   @Test
   public void repeatedCourierShouldBeCreated() {
       CreateCourierRequest createCourierRequest = CourierData.generateData();
       courierClient.createCourier(createCourierRequest);
       courierClient.createCourier(createCourierRequest)
               .statusCode(SC_CONFLICT)
               .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
   }
    @DisplayName("Сценарий создания курьера")
    @Description("Статус код должен быть 201 , в ответе должно быть true")
    @Test
    public void shouldBeCreateCourier(){
        CreateCourierRequest courierRequest = CourierData.generateData();
        Specification.installSpecification(Specification.requestSpec(BASE_URI), Specification.responseSpec(SC_CREATED));
        CreateCourierSuccessResponse courierResponse =
              courierClient.createCourier(courierRequest)
               .extract().as(CreateCourierSuccessResponse.class);
        Assert.assertTrue(courierResponse.getOk());
    }

    @DisplayName("Сценарий создания курьера без логина")
    @Description("Должен вернуться статус код 400 и сообщение Недостаточно данных для создания учетной записи")
    @Test
    public void shouldBeCreateCourierWithoutLogin(){
        CreateCourierRequest courierRequest = CourierData.generateData();
        Specification.installSpecification(Specification.requestSpec(BASE_URI), Specification.responseSpec(SC_BAD_REQUEST));
        courierRequest.setLogin("");
        CreateCourierUnSuccessResponse courierResponse =
                courierClient.createCourier(courierRequest)
                .extract().as(CreateCourierUnSuccessResponse.class);
        Assert.assertEquals("Недостаточно данных для создания учетной записи",courierResponse.getMessage());
    }

    @DisplayName("Сценарий создания курьера без пароля")
    @Description("Должен вернуться статус код 400 и сообщение Недостаточно данных для создания учетной записи")
    @Test
    public void shouldBeCreateCourierWithoutPassword(){
        CreateCourierRequest courierRequest = CourierData.generateData();
        Specification.installSpecification(Specification.requestSpec(BASE_URI), Specification.responseSpec(SC_BAD_REQUEST));
        courierRequest.setPassword("");
        CreateCourierUnSuccessResponse courierResponse =
                courierClient.createCourier(courierRequest)
                .extract().as(CreateCourierUnSuccessResponse.class);
        Assert.assertEquals("Недостаточно данных для создания учетной записи",courierResponse.getMessage());
    }
}
