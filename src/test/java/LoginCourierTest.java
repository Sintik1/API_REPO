import Client.CreateCourierClient;
import Client.DeleteClient;
import Client.LoginCourierClient;
import DataProvider.CourierData;
import POJO.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class LoginCourierTest {
    CreateCourierClient createCourierClient=new CreateCourierClient();
    LoginCourierClient loginCourierClient = new LoginCourierClient();
    DeleteClient delete = new DeleteClient();

    private int id;

@Test
    public void loginCourier(){
    CreateCourierRequest courierRequest = CourierData.generateData();
    createCourierClient.createCourier(courierRequest);
    LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(courierRequest);
     LoginCourierResponse loginCourierResponse= loginCourierClient.loginCourier(loginCourierRequest)
             .statusCode(SC_OK)
             .extract().as(LoginCourierResponse.class);
    Assert.assertNotNull(loginCourierResponse.getId());
    id = loginCourierResponse.getId();
}

@DisplayName("Сценарий логина курьера без указания логина")
@Description("Должен прийти статус код 400 и сообщение Недостаточно данных для входа")
@Test
public void shouldBeLoginCourierWithoutLogin(){
    CreateCourierRequest courierRequest = CourierData.generateData();
    createCourierClient.createCourier(courierRequest);
    LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(courierRequest);
    loginCourierRequest.setLogin("");
   LoginCourierUnSuccessResponse loginCourierUnSuccessResponse= loginCourierClient.loginCourier(loginCourierRequest)
           .statusCode(SC_BAD_REQUEST)
            .extract().as(LoginCourierUnSuccessResponse.class);
    Assert.assertEquals("Недостаточно данных для входа",loginCourierUnSuccessResponse.getMessage());
}

    @DisplayName("Сценарий логина курьера без указания Пароля")
    @Description("Должен прийти статус код 400 и сообщение Недостаточно данных для входа")
    @Test
    public void shouldBeLoginCourierWithoutPassword(){
        CreateCourierRequest courierRequest = CourierData.generateData();
        createCourierClient.createCourier(courierRequest);
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(courierRequest);
        loginCourierRequest.setLogin("");
        LoginCourierUnSuccessResponse loginCourierUnSuccessResponse= loginCourierClient.loginCourier(loginCourierRequest)
                .statusCode(SC_BAD_REQUEST)
                .extract().as(LoginCourierUnSuccessResponse.class);
        Assert.assertEquals("Недостаточно данных для входа",loginCourierUnSuccessResponse.getMessage());
    }


    @DisplayName("Сценарий логина курьера c несуществующими данными")
    @Description("Должен прийти статус код 400 и сообщение Недостаточно данных для входа")
    @Test
    public void shouldBeLoginCourierNonExistentCourier(){
        LoginCourierRequest loginCourierRequest = new LoginCourierRequest("Несуществующий логин","Несуществующий пароль");
        LoginCourierUnSuccessResponse loginCourierUnSuccessResponse= loginCourierClient.loginCourier(loginCourierRequest)
                .statusCode(SC_NOT_FOUND)
                .extract().as(LoginCourierUnSuccessResponse.class);
        Assert.assertEquals("Учетная запись не найдена",loginCourierUnSuccessResponse.getMessage());
    }

@After
    public void tearDown(){
    if (id!=0){
       DeleteCourierResponse response= delete.deleteCourier(id)
               .statusCode(SC_OK)
               .extract().as(DeleteCourierResponse.class);
       Assert.assertTrue(response.getOk());
    }
}
}
