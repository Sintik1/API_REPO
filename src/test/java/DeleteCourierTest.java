import Client.CreateCourierClient;
import Client.DeleteClient;
import Client.LoginCourierClient;
import DataProvider.CourierData;
import POJO.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class DeleteCourierTest {
    DeleteClient deleteClient = new DeleteClient();
    CreateCourierClient createCourierClient = new CreateCourierClient();
    LoginCourierClient loginCourierClient = new LoginCourierClient();
    private Integer id;

    @DisplayName("Удаление курьера")
    @Description("Должен вернуться статус код 200 и ответ true")
    @Test
    public void deleteCourier(){
        getIdCourier();
        DeleteCourierResponse response= deleteClient.deleteCourier(478777)
                .statusCode(SC_OK)
                .extract().as(DeleteCourierResponse.class);
        Assert.assertTrue(response.getOk());
    }

    @DisplayName("Удаление курьера c неправильным Id")
    @Description("Должен вернуться статус код 404 и ответ Курьера с таким id нет")
    @Test
    public void deleteCourierWithInvalidId(){
        DeleteCourierUnSuccessResponse response= deleteClient.deleteCourier(00000000)
                .statusCode(SC_NOT_FOUND)
                .extract().as(DeleteCourierUnSuccessResponse.class);
        Assert.assertEquals("Курьера с таким id нет.",response.getMessage());
    }




    //Вспомогательный метод получения id курьера
    public void getIdCourier(){
        CreateCourierRequest courier = CourierData.generateData();
        createCourierClient.createCourier(courier);
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(courier);
        LoginCourierResponse loginCourierResponse=loginCourierClient.loginCourier(loginCourierRequest)
                .extract().as(LoginCourierResponse.class);
        id = loginCourierResponse.getId();
    }
}
