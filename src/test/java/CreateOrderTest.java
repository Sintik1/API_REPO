import Client.CreateOrderClient;
import POJO.CreateOrderRequest;
import POJO.CreateOrderSuccessResponse;
import Specification.Specification;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.time.LocalDate;
import java.util.*;

import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    private final CreateOrderRequest createOrderRequest;
    CreateOrderClient createOrderClient = new CreateOrderClient();

    public CreateOrderTest(CreateOrderRequest createOrderRequest) {
        this.createOrderRequest = createOrderRequest;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {new CreateOrderRequest("Влад", "Сентяков", "Проспект Ветеранов 132", 5, "+79234624214", 5, String.valueOf(LocalDate.now()), "с полным зарядом", List.of("GREY"))},
                {new CreateOrderRequest("Проект", "Спринтов", "Невский проспкт 1", 1, "+79789432345", 1, String.valueOf(LocalDate.now()), "хочу самокат", List.of("BLACK"))},
                {new CreateOrderRequest("Иван", "Иванов", "Арбат 13", 1, "+79124121124", 3, String.valueOf(LocalDate.now()), "желательно по быстрее", List.of("GREY", "BLACK"))},
                {new CreateOrderRequest("Тест", "Тестовый", "Рублёвское шоссе", 11, "+79456789323", 7, String.valueOf(LocalDate.now()), "по-быстрее",List.of())}
        };
    }

    @DisplayName("Сценарий создания успешного заказа")
    @Description("Должен вернуться статус код 201, и track заказа")
    @Test
    public void shouldBeSuccessCreateOrder() {
        Specification.installSpecification(Specification.requestSpec(BASE_URI),Specification.responseSpec(SC_CREATED));
        CreateOrderSuccessResponse createOrderSuccessResponse = createOrderClient.createOrder(createOrderRequest)
                .extract().as(CreateOrderSuccessResponse.class);
        Assert.assertNotNull(createOrderSuccessResponse.getTrack());
    }
}




