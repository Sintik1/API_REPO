import Client.*;
import DataProvider.CourierData;
import POJO.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.apache.http.HttpStatus.*;

public class AcceptOrderTest {
    AcceptOrderClient acceptOrderClient = new AcceptOrderClient();
    CreateCourierClient createCourierClient = new CreateCourierClient();
    LoginCourierClient loginCourierClient = new LoginCourierClient();
    GetOrderItsByNumberClient client = new GetOrderItsByNumberClient();
    DeleteClient delete = new DeleteClient();


    private Integer id;
    private Integer orderId;
    private String track;

@DisplayName("Сценарий принятия заказа курьером")
@Description("Должен вернуться статус код 200, и ответ true")
@Test
public void acceptOrder(){
    getIdCourierAndIdOrder();
        AcceptOrderResponseSuccess  response = acceptOrderClient.acceptOrder(orderId, id)
                .statusCode(SC_OK)
                .extract().as(AcceptOrderResponseSuccess.class);
        Assert.assertTrue(response.getOk());
    }

    @DisplayName("Сценарий повторного принятия заказа курьером")
    @Description("Должен вернуться статус код 409, и ответ Этот заказ уже в работе")
    @Test
    public void repeatAcceptOrder(){
        getIdCourierAndIdOrder();
        acceptOrderClient.acceptOrder(orderId, id);
        AcceptOrderResponseUnSuccess  response = acceptOrderClient.acceptOrder(orderId, id)
                .statusCode(SC_CONFLICT)
                .extract().as(AcceptOrderResponseUnSuccess.class);
        Assert.assertEquals("Этот заказ уже в работе",response.getMessage());
    }


    @DisplayName("Сценарий  принятия заказа курьером без указания Id курьера")
    @Description("Должен вернуться статус код 400, и ответ Недостаточно данных для поиска")
    @Test
    public void acceptOrderWithoutCourierId(){
        getTrackOrder();
        getIdOrder();
        AcceptOrderResponseUnSuccess  response = acceptOrderClient.acceptOrder(orderId, null)
                .statusCode(SC_BAD_REQUEST)
                .extract().as(AcceptOrderResponseUnSuccess.class);
        Assert.assertEquals("Недостаточно данных для поиска",response.getMessage());
    }

    @DisplayName("Сценарий  принятия заказа курьером c несуществующим  Id курьера")
    @Description("Должен вернуться статус код 404, и ответ Курьера с таким id не существует")
    @Test
    public void acceptOrderWithInvalidCourierId(){
        getTrackOrder();
        getIdOrder();
        AcceptOrderResponseUnSuccess  response = acceptOrderClient.acceptOrder(orderId, 0000)
                .statusCode(SC_NOT_FOUND)
                .extract().as(AcceptOrderResponseUnSuccess.class);
        Assert.assertEquals("Курьера с таким id не существует",response.getMessage());
    }
    @DisplayName("Сценарий  принятия заказа курьером c несуществующим id заказа")
    @Description("Должен вернуться статус код 404, и ответ Заказа с таким id не существует")
    @Test
    public void acceptOrderWithInvalidOrderId(){
        getIdCourier();
        AcceptOrderResponseUnSuccess  response = acceptOrderClient.acceptOrder(0000, id)
                .statusCode(SC_NOT_FOUND)
                .extract().as(AcceptOrderResponseUnSuccess.class);
        Assert.assertEquals("Заказа с таким id не существует",response.getMessage());
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
    //Вспомогательный метод получения трэка заказа
    public void getTrackOrder(){
        CreateOrderClient createOrderClient = new CreateOrderClient();
        CreateOrderRequest createOrderRequest = new CreateOrderRequest("Иван", "Иванов", "Арбат 13", 1, "+79124121124", 3, String.valueOf(LocalDate.now()), "желательно по быстрее", List.of("GREY", "BLACK"));
        CreateOrderSuccessResponse response = createOrderClient.createOrder(createOrderRequest)
                .extract().as(CreateOrderSuccessResponse.class);
        track=response.getTrack();
    }
//Вспомогательный метод получения id заказа
    public void getIdOrder(){
        GetOrderItsByNumberResponseSuccess response = client.trackOrder(track)
                .statusCode(SC_OK)
                .extract().body().jsonPath().getObject("order", GetOrderItsByNumberResponseSuccess.class);
        Assert.assertNotNull(response.getId());
        orderId = response.getId();
        System.out.println(id);
    }
    //Вспомогательный метод получения id курьера и id заказа
    public void getIdCourierAndIdOrder(){
    getIdCourier();
    getTrackOrder();
    getIdOrder();
    }
    @After
    public void tearDown() {
        if (id != null) {
            DeleteCourierResponse response = delete.deleteCourier(id)
                    .statusCode(SC_OK)
                    .extract().as(DeleteCourierResponse.class);
            Assert.assertTrue(response.getOk());
        }
    }
       }


