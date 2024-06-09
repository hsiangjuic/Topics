package com.ispan.hotel.service.restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.Restaurant;
import com.ispan.hotel.model.RestaurantPoint;
import com.ispan.hotel.repository.restaurant.RestaurantPointRepositoryCKY;
import com.ispan.hotel.service.booking.CustomerServiceYTH;

@Service
public class RestaurantPointServiceCKY {

    // @Autowired
    // private Restaurant restaurant;

    @Autowired
    private RestaurantPointRepositoryCKY restaurantPointRepository;
    
    @Autowired
    private RestaurantServiceCYJ restaurantService;
    
    @Autowired
    private CustomerServiceYTH customerService;

//    @PersistenceContext
//    private Session session;
//    public Session getSession(){
//        return this.session;
//    }

    public RestaurantPoint create(String json) {

        
            JSONObject obj = new JSONObject(json);

            Integer repId = obj.isNull("repId") ? null : obj.getInt("repId");
            Integer reIdint = obj.isNull("reId") ? null : obj.getInt("reId");
            Integer cIdint = obj.isNull("cId") ? null : obj.getInt("cId");
            String paymentDateStr = obj.isNull("paymentDate") ? null : obj.getString("paymentDate");
            Integer points = obj.isNull("points") ? null : obj.getInt("points");

            LocalDate paymentDate = LocalDate.parse(paymentDateStr);

            if (repId != null ) {

            	Restaurant reId = restaurantService.findById(reIdint);
                
                Customer cId = customerService.findById(cIdint);

                if (cId != null) {
                    RestaurantPoint insert = new RestaurantPoint();
                    insert.setRepId(repId);
                    insert.setRestaurant(reId);
                    insert.setCustomer(cId);
                    insert.setPaymentDate(paymentDate);
                    insert.setPoints(points);

                    return restaurantPointRepository.save(insert);

                }

            }

        
        return null;

    }

    public RestaurantPoint modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);

            String reId = obj.isNull("reId") ? null : obj.getString("reId");
            // String cId = obj.isNull("cId") ? null : obj.getString("cId");
            String paymentDateStr = obj.isNull("paymentDate") ? null : obj.getString("paymentDate");
            Integer points = obj.isNull("points") ? null : obj.getInt("points");

            LocalDate paymentDate = LocalDate.parse(paymentDateStr);

            if (reId != null ) {
                

                // Restaurant restaurant = restaurantRepository.findByRestaurant(reId);

                if (paymentDate == null) {
                    RestaurantPoint update = new RestaurantPoint();
                    // insert.setRestaurant(reId);
                    // insert.setCustomer(cId);
                    update.setPaymentDate(paymentDate);
                    update.setPoints(points);

                    return restaurantPointRepository.save(update);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
    
    public RestaurantPoint findById(Integer id) {
		if(id !=null) {
			Optional<RestaurantPoint> optional = restaurantPointRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
	
	public List<RestaurantPoint> find(){
		return restaurantPointRepository.findAll();
	}
	
	public Boolean delete(Integer id) {
		if(id != null) {
			Optional<RestaurantPoint> opt = restaurantPointRepository.findById(id);
			if(opt.isPresent()) {
				restaurantPointRepository.deleteById(id);
				return true;
			}
		}
		return false;
	}

}
