
import Client.GetOrderItsByNumberClient;
import POJO.GetOrderItsByNumberResponseSuccess;
import POJO.GetOrderItsByNumberResponseUnSuccess;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;


import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class GetOrderItsNumberTest {
    GetOrderItsByNumberClient client = new GetOrderItsByNumberClient();
    private Integer id;

    @DisplayName("Получение заказа по его номеру")
    @Description("Должен вернуться заказ с Id,и статус код 200 ")
    @Test
    public void testTrack() {
        GetOrderItsByNumberResponseSuccess response = client.trackOrder("712665")
                .statusCode(SC_OK)
                .extract().body().jsonPath().getObject("order", GetOrderItsByNumberResponseSuccess.class);
        Assert.assertNotNull(response.getId());
        id = response.getId();
        System.out.println(id);
    }
    @DisplayName("Сценарий получения заказа по его номер , без указания номера")
    @Description("Должен вернуться статус код 400, и сообщение об ошибке")
    @Test
    public void testGetOrderItsTrackWithoutNumber(){
        GetOrderItsByNumberResponseUnSuccess response = client.trackOrder(null)
                .statusCode(SC_BAD_REQUEST)
                .extract().as(GetOrderItsByNumberResponseUnSuccess.class);
        Assert.assertEquals("Недостаточно данных для поиска",response.getMessage());
    }
    @DisplayName("Сценарий получения заказа по его номер , c несуществующим номером заказа")
    @Description("Должен вернуться статус код 404, и сообщение об ошибке")
    @Test
    public void testGetOrderItsTrackInvalidNumber(){
        GetOrderItsByNumberResponseUnSuccess response = client.trackOrder("012132312")
                .statusCode(SC_NOT_FOUND)
                .extract().as(GetOrderItsByNumberResponseUnSuccess.class);
        Assert.assertEquals("Заказ не найден",response.getMessage());
    }
}

