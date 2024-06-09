package com.ispan.hotel.controller.restaurant;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ispan.hotel.model.RestaurantCustomer;
import com.ispan.hotel.service.restaurant.RestaurantCustomerServiceCKY;

import java.util.List;

@RestController

@RequestMapping("/restaurant/RestaurantCustomerCKY")
@CrossOrigin

public class RestaurantCustomerCKY {

    @Autowired
    private RestaurantCustomerServiceCKY restaurantCustomerService;

    // 新增餐廳顧客
    @PostMapping("/create")
    public ResponseEntity<RestaurantCustomer> createCustomer(@RequestBody String json) {
        RestaurantCustomer newCustomer = restaurantCustomerService.create(json);
        if (newCustomer != null) {
            return ResponseEntity.ok(newCustomer);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 查詢一筆餐廳顧客
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantCustomer> getCustomerById(@PathVariable Integer id) {
        RestaurantCustomer customer = restaurantCustomerService.findById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 查詢全部餐廳顧客
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantCustomer>> getAllCustomers() {
        List<RestaurantCustomer> customers = restaurantCustomerService.find();
        return ResponseEntity.ok(customers);
    }

    // 修改餐廳顧客
    @PutMapping("/update")
    public ResponseEntity<RestaurantCustomer> updateCustomer(@RequestBody String json) {
        RestaurantCustomer updatedCustomer = restaurantCustomerService.modify(json);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 刪除餐廳顧客
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        Boolean isDeleted = restaurantCustomerService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
