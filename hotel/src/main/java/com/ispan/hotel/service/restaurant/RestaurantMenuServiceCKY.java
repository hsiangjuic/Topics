package com.ispan.hotel.service.restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.RestaurantBooking;
import com.ispan.hotel.model.RestaurantCustomer;
import com.ispan.hotel.model.RestaurantMenu;
import com.ispan.hotel.repository.restaurant.RestaurantMenuRepositoryCKY;

@Service
public class RestaurantMenuServiceCKY {

    @Autowired
    private RestaurantMenuRepositoryCKY restaurantMenuRepository;

    // @Autowired
    // private RestaurantMenu restaurantMenu;

    // @PersistenceContext
    // private Session session;
    // public Session getSession() {
    // return this.session;
    // }

    public RestaurantMenu create(RestaurantMenu json) {

        JSONObject obj = new JSONObject(json);

        Integer remId = obj.isNull("remId") ? null : obj.getInt("remId");
        // String restaurantCustomer = obj.isNull("restaurantCustomer") ? null :
        // obj.getString("restaurantCustomer");
        String menuName = obj.isNull("menuName") ? null : obj.getString("menuName");
        Integer price = obj.isNull("price") ? null : obj.getInt("price");
        // String startDatestr = obj.isNull("startDate") ? null : obj.getString("startDate");
        // String endDatestr = obj.isNull("endDate") ? null : obj.getString("endDate");

        // LocalDate startDate = LocalDate.parse(startDatestr);
        // LocalDate endDate = LocalDate.parse(endDatestr);

        if (remId != null) {

            RestaurantMenu insert = new RestaurantMenu();
            insert.setRemId(remId);
            // insert.setRestaurantCustomer(null);
            insert.setMenuName(menuName);
            insert.setPrice(price);
            // insert.setStartDate(startDate);
            // insert.setEndDate(endDate);

            return restaurantMenuRepository.save(insert);

        }
        return null;
    }

    public RestaurantMenu modify(String json) {

        JSONObject obj = new JSONObject(json);

        Integer remId = obj.isNull("remId") ? null : obj.getInt("remId");
        // String restaurantCustomer = obj.isNull("restaurantCustomer") ? null :
        // obj.getString("restaurantCustomer");
        String menuName = obj.isNull("menuName") ? null : obj.getString("menuName");
        Integer price = obj.isNull("price") ? null : obj.getInt("price");
        String startDatestr = obj.isNull("startDate") ? null : obj.getString("startDate");
        String endDatestr = obj.isNull("endDate") ? null : obj.getString("endDate");

        LocalDate startDate = LocalDate.parse(startDatestr);
        LocalDate endDate = LocalDate.parse(endDatestr);

        if (remId != null) {

            Optional<RestaurantMenu> opt = restaurantMenuRepository.findById(remId);
            if (opt.isPresent()) {
                RestaurantMenu update = opt.get();
                update.setRemId(remId);
                // update.setRestaurantCustomer(null);
                update.setMenuName(menuName);
                update.setPrice(price);
                update.setStartDate(startDate);
                update.setEndDate(endDate);

                return restaurantMenuRepository.save(update);
            }

        }
        return null;
    }

    public RestaurantMenu findById(Integer id) {
        if (id != null) {
            Optional<RestaurantMenu> optional = restaurantMenuRepository.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    public List<RestaurantMenu> find() {
        return restaurantMenuRepository.findAll();
    }

    public Boolean delete(Integer id) {
        if (id != null) {
            Optional<RestaurantMenu> opt = restaurantMenuRepository.findById(id);
            if (opt.isPresent()) {
                restaurantMenuRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}
