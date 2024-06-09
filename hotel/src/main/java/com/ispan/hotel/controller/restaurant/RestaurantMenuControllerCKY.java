package com.ispan.hotel.controller.restaurant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ispan.hotel.model.RestaurantMenu;
import com.ispan.hotel.service.restaurant.RestaurantMenuServiceCKY;

import java.util.List;

@RestController

@RequestMapping("/restaurant/RestaurantMenu")
@CrossOrigin
public class RestaurantMenuControllerCKY {

    @Autowired
    private RestaurantMenuServiceCKY restaurantMenuService;

    // 新增餐單
    @PostMapping("/create")
    public ResponseEntity<RestaurantMenu> createMenu(@RequestBody RestaurantMenu json) {
        RestaurantMenu newMenu = restaurantMenuService.create(json);
        if (newMenu != null) {
            return ResponseEntity.ok(newMenu);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 查詢一筆餐單
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantMenu> getMenuById(@PathVariable Integer id) {
        RestaurantMenu menu = restaurantMenuService.findById(id);
        if (menu != null) {
            return ResponseEntity.ok(menu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 查詢全部餐單
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantMenu>> getAllMenus() {
        List<RestaurantMenu> menus = restaurantMenuService.find();
        return ResponseEntity.ok(menus);
    }

    // 修改餐單
    @PutMapping("/update")
    public ResponseEntity<RestaurantMenu> updateMenu(@RequestBody String json) {
        RestaurantMenu updatedMenu = restaurantMenuService.modify(json);
        if (updatedMenu != null) {
            return ResponseEntity.ok(updatedMenu);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 刪除餐單
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Integer id) {
        Boolean isDeleted = restaurantMenuService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
