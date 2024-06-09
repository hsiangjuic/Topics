package com.ispan.hotel.service.restaurant;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.print.DocFlavor.STRING;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.RestaurantCustomer;
import com.ispan.hotel.model.RestaurantMenu;
import com.ispan.hotel.repository.restaurant.RestaurantCustomerRepositoryCKY;


@Service
public class RestaurantCustomerServiceCKY {

    @Autowired
    private RestaurantCustomerRepositoryCKY restaurantCustomerRepository;

    @Autowired
    private RestaurantMenuServiceCKY restaurantMenuService;



    // @Autowired
    // private RestaurantCustomer restaurantCustomer;
    public static LocalDateTime parseLocalDateTime(String datetime, String format) {
		Date date = new Date();
		try {
			date = new SimpleDateFormat(format).parse(datetime);
		} catch (Exception e) {
			date = new Date();
			e.printStackTrace();
		}
		
        LocalDateTime result = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		return result;
	}


    public RestaurantCustomer create(String json) {

        try {
            JSONObject obj = new JSONObject(json);

            Integer recId = obj.isNull("recId") ? null : obj.getInt("recId");
            String reserveTimestr = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");
            String pickTimestr = obj.isNull("pickTime") ? null : obj.getString("pickTime");
            Integer restaurantMenuint = obj.isNull("restaurantMenu") ? null : obj.getInt("restaurantMenu");
            String menuName = obj.isNull("menuName") ? null : obj.getString("menuName");
            Integer price = obj.isNull("price") ? null : obj.getInt("price");
            String firstName = obj.isNull("firstName") ? null : obj.getString("firstName");
            String lastName = obj.isNull("lastName") ? null : obj.getString("lastName");
            String cellphone = obj.isNull("cellphone") ? null : obj.getString("cellphone");
            String email = obj.isNull("email") ? null : obj.getString("email");
            String remark = obj.isNull("remark") ? null : obj.getString("remark");
            String lastModifiedDatestr = obj.isNull("lastModifiedDate") ? null : obj.getString("lastModifiedDate");

            LocalDateTime reserveTime = parseLocalDateTime(reserveTimestr, "yyyy-MM-dd HH:mm:ss");
            LocalDateTime pickTime = parseLocalDateTime(pickTimestr, "yyyy-MM-dd HH:mm:ss");
            LocalDateTime lastModifiedDate = parseLocalDateTime(lastModifiedDatestr, "yyyy-MM-dd HH:mm:ss");
//思考菜單id的問題 無法解決就用固定id
            if (cellphone != null) {
                RestaurantMenu restaurantMenu = restaurantMenuService.findById(7);

                RestaurantCustomer insert = new RestaurantCustomer();

                insert.setReserveTime(reserveTime);
                insert.setPickTime(pickTime);
                insert.setRestaurantMenu(restaurantMenu);
                insert.setMenuName(menuName);
                insert.setPrice(price);
                insert.setFirstName(firstName);
                insert.setLastName(lastName);
                insert.setCellphone(cellphone);
                insert.setEmail(email);
                insert.setRemark(remark);
                insert.setLastModifiedDate(lastModifiedDate);

                return restaurantCustomerRepository.save(insert);

            }
            return null;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public RestaurantCustomer modify(String json) {

        try {
            JSONObject obj = new JSONObject(json);

            Integer recId = obj.isNull("recId") ? null : obj.getInt("recId");
            String reserveTimestr = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");
            String pickTimestr = obj.isNull("pickTime") ? null : obj.getString("pickTime");
            String restaurantMenu = obj.isNull("restaurantMenu") ? null : obj.getString("restaurantMenu");
            String menuName = obj.isNull("menuName") ? null : obj.getString("menuName");
            Integer price = obj.isNull("price") ? null : obj.getInt("price");
            String firstName = obj.isNull("firstName") ? null : obj.getString("firstName");
            String lastName = obj.isNull("lastName") ? null : obj.getString("lastName");
            String cellphone = obj.isNull("cellphone") ? null : obj.getString("cellphone");
            String email = obj.isNull("email") ? null : obj.getString("email");
            String remark = obj.isNull("remark") ? null : obj.getString("remark");
            String lastModifiedDatestr = obj.isNull("lastModifiedDate") ? null : obj.getString("lastModifiedDate");

            LocalDateTime reserveTime = LocalDateTime.parse(reserveTimestr);
            LocalDateTime pickTime = LocalDateTime.parse(pickTimestr);
            LocalDateTime lastModifiedDate = LocalDateTime.parse(lastModifiedDatestr);

            if (recId != null) {

                Optional<RestaurantCustomer> opt = restaurantCustomerRepository.findById(recId);
                if (opt.isPresent()) {
                    RestaurantCustomer update = opt.get();
                    update.setReserveTime(reserveTime);
                    update.setPickTime(pickTime);
                    update.setMenuName(menuName);
                    update.setPrice(price);
                    update.setFirstName(firstName);
                    update.setLastName(lastName);
                    update.setCellphone(cellphone);
                    update.setEmail(email);
                    update.setRemark(remark);

                    if (lastModifiedDate != null) {

                        update.setLastModifiedDate(LocalDateTime.now());

                    }
                    return restaurantCustomerRepository.save(update);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public RestaurantCustomer findById(Integer id) {
        if (id != null) {
            Optional<RestaurantCustomer> optional = restaurantCustomerRepository.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }



    public List<RestaurantCustomer> find() {
        return restaurantCustomerRepository.findAll();
    }
    
    public Boolean delete(Integer id) {
        if (id != null) {
            Optional<RestaurantCustomer> opt = restaurantCustomerRepository.findById(id);
            if (opt.isPresent()) {
                restaurantCustomerRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}