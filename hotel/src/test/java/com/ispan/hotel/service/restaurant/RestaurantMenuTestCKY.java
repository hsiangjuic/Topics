package com.ispan.hotel.service.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.json.JSONObject;

@SpringBootTest
public class RestaurantMenuTestCKY {

    @Autowired
    private RestaurantMenuServiceCKY restaurantMenuService;

    @Test
    public void testCreate(){

        JSONObject obj = new JSONObject()
                .put("remId", "1")
                .put("restaurantCustomer", "aaa") 
                .put("menuName", "test")
                .put("price", "123")
                .put("startDate", "2024-05-14")
                .put("endDate", "2024-05-14");      

        String json = obj.toString();
        

//        System.out.println("result = "+ restaurantMenuService.create(json));






    }

}
