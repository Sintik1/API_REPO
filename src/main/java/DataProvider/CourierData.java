package DataProvider;

import POJO.CreateCourierRequest;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierData {

    public static CreateCourierRequest generateData() {
        CreateCourierRequest createCourierRequest = new CreateCourierRequest();
        createCourierRequest.setLogin(RandomStringUtils.randomAlphabetic(8));
        createCourierRequest.setPassword(RandomStringUtils.randomAlphabetic(8));
        createCourierRequest.setFirstName(RandomStringUtils.randomAlphabetic(8));

        return createCourierRequest;
    }
}

