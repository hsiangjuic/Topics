package com.ispan.hotel.service.restaurant;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantPointTestCKY {

    @Autowired
    private RestaurantPointServiceCKY restaurantPointService;

    @Test
    public void testCreate() {

        JSONObject obj = new JSONObject()
                .put("repId", "1")
                .put("reId", "1")
                .put("cId", "1")
                .put("paymentDate", "2024-05-14")
                .put("points", "1");
                // .put("lastModifiedDate", "2024-05-15")
                // .put("lastModifiedEmp", "kevin")
                // .put("lastModifiedText", "whatever");
        String json = obj.toString();

        System.out.println("result=" + restaurantPointService.create(json));

        

    }

}
