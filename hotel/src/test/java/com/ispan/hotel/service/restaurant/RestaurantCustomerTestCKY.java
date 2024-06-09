package com.ispan.hotel.service.restaurant;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantCustomerTestCKY {

    @Autowired
    private RestaurantCustomerServiceCKY restaurantCustomerService;

    @Test

    public void testCreate(){

        JSONObject obj = new JSONObject()
                .put("recId", "1")
                .put("reserveTime", "2024-05-16 18:00")
                .put("pickTime", "2024-05-16 19:00")
                .put("restaurantMenu", "1")//?
                .put("menuName", "test")
                .put("price", "123")
                .put("firstName", "ko")
                .put("lastName", "chu")
                .put("cellphone", "0970571688")
                .put("email", "ke@gmail.com")
                .put("remark", "奧客")
                .put("lastModifiedDate", "2024-05-13 19:00");
                String json = obj.toString();

                System.out.println("result = "+ restaurantCustomerService.create(json));





    }
    
}
