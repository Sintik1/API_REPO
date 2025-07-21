import Client.GetListOrderClient;
import POJO.GetListOrderResponse;
import Specification.Specification;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class ListOrderTest {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    GetListOrderClient getListOrderClient = new GetListOrderClient();

    @DisplayName("Получение списка заказов")
    @Description("Должен вернуться статус код 200 и список заказов")
    @Test
    public void getListOrderTest(){
        Specification.installSpecification(Specification.requestSpec(BASE_URI),Specification.responseSpec(SC_OK));
        List<GetListOrderResponse> listOrder= getListOrderClient.getListOrder()
                .extract().body().jsonPath().getList("orders",GetListOrderResponse.class);
                listOrder.stream().forEach(x-> Assert.assertNotNull("Id заказа не должен быть null",x.getId()));
                List<Integer>track = listOrder.stream().map(GetListOrderResponse::getTrack).collect(Collectors.toList());
                Assert.assertNotNull("Трэк-номер не должен быть пустой",track);
                Assert.assertFalse("Список должен быть не пустой",listOrder.isEmpty());
                System.out.println(listOrder.size());
    }
}
