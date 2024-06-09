package com.ispan.hotel.service.booking;

import java.util.List;

import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.BookingDetail;
import com.ispan.hotel.model.BookingPayment;
import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.Hotel;
import com.ispan.hotel.model.UseDiscount;

@Service
public class BookingFindServiceYTH {
	
	@Autowired
	private BookingServiceYTH bookingServiceYTH;
	
	@Autowired
	private BookingPaymentServiceYTH bookingPaymentServiceYTH;
	
	
	@Autowired
	private UseDiscountServiceYTH useDiscountServiceYTH;
	
	
	
	@Transactional
	public String findBookingData(BookingDetail bookingDetail) {
        JSONObject responseJSON = new JSONObject();
        try {
        	JSONObject bookingFindObj = new JSONObject();
            if (bookingDetail == null) {
                return createErrorResponse("查無訂單，請確認輸入無誤");
            } else {
            	createBookingDetailJSONObject(bookingDetail, bookingFindObj);
            }
            Hotel hotel = bookingDetail.getHotel();
            if (hotel == null) {
                return createErrorResponse("查無訂單，請確認輸入無誤");
            } else {
            	createHotelJSONObject(hotel, bookingFindObj);
            }
            Customer customer = bookingDetail.getCustomer();
            if (customer == null) {
                return createErrorResponse("查無訂單，請確認輸入無誤");
            } else {
            	createCustomerJSONObject(customer, bookingFindObj);
            }
            
            
            List<Booking> bookings = bookingServiceYTH.findByBookingDetail(bookingDetail);
            if (bookings == null || bookings.isEmpty()) {
                return createErrorResponse("查無訂單，請確認輸入無誤");
            }

            JSONArray bookingArray = new JSONArray();
            for (Booking booking : bookings) {
            	// 手動初始化懶加載的屬性
                Hibernate.initialize(booking.getRoom().getRoomType());
                JSONObject bookingObj = createBookingJSONObject(booking);
                bookingArray.put(bookingObj);
            }
            bookingFindObj.put("rooms", bookingArray);
            
            responseJSON.put("booking", bookingFindObj);
            responseJSON.put("message", "查詢成功");
            responseJSON.put("success", true);

        } catch (Exception e) {
            e.printStackTrace();
            return createErrorResponse("查詢出錯");
        }
        return responseJSON.toString();
    }
	
	
	@Transactional
	public String findBookingData(Booking booking) {
		JSONObject responseJSON = new JSONObject();
        try {
            if (booking == null) {
                return createErrorResponse("查無訂單，請確認輸入無誤");
            } else {
            	responseJSON = createBookingJSONObject(booking);
            	
            	BookingDetail bookingDetail = booking.getBookingDetail();
            	createBookingDetailJSONObject(bookingDetail, responseJSON);
            	
	            Hotel hotel = bookingDetail.getHotel();
	            if (hotel == null) {
	                return createErrorResponse("查無訂單，請確認輸入無誤");
	            } else {
	            	createHotelJSONObject(hotel, responseJSON);
	            }
	            Customer customer = bookingDetail.getCustomer();
	            if (customer == null) {
	                return createErrorResponse("查無訂單，請確認輸入無誤");
	            } else {
	            	createCustomerJSONObject(customer, responseJSON);
	            }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return createErrorResponse("查詢出錯");
        }
        return responseJSON.toString();
	}
	
	
	private JSONObject createBookingDetailJSONObject(BookingDetail bookingDetail, JSONObject bookingFindObj) {
		bookingFindObj.put("arrivalTime", bookingDetail.getArrivalTime())
						.put("infantUtility", bookingDetail.getInfantUtility())
						.put("remark", bookingDetail.getCustomerRemark())
						.put("orderNumber", bookingDetail.getOrderNumber())
						.put("totalRoom", bookingDetail.getTotalRoom())
						.put("paymentStatus", bookingDetail.getPaymentStatus())
						.put("bdId", bookingDetail.getBdId());
		
		return bookingFindObj;
	}
	
	private JSONObject createCustomerJSONObject(Customer customer, JSONObject bookingFindObj) {
		bookingFindObj.put("firstName", customer.getFirstName())
						.put("lastName", customer.getLastName())
						.put("identification", customer.getIdentification())
						.put("passportNumber", customer.getPassportNumber())
						.put("country", customer.getCountry())
						.put("address", customer.getAddress())
						.put("gender", customer.getGender())
						.put("birthday", customer.getBirthday())
						.put("allowPromotionMail", customer.getAllowPromotionMail())
						.put("username", customer.getUsername())
						.put("email", customer.getEmail())
						.put("titleOfCourtesy", customer.getTitleOfCourtesy())
						.put("tel", customer.getTel())
						.put("cId", customer.getcId());
		
		return bookingFindObj;
	}
	
	private JSONObject createHotelJSONObject(Hotel hotel, JSONObject bookingFindObj) {
		bookingFindObj.put("hotelName", hotel.getName())
						.put("hotelAddress", hotel.getAddress())
						.put("hotelTel", hotel.getTel())
						.put("hId", hotel.gethId());
		
		return bookingFindObj;
	}

    private JSONObject createBookingJSONObject(Booking booking) {
        JSONObject bookingObj = new JSONObject();
        bookingObj.put("rtId", booking.getRoom().getRoomType().getRtId())
        	.put("rId", booking.getRoom().getrId())
        	.put("roomNumber", booking.getRoom().getRoomNumber())
	        .put("roomType", booking.getRoom().getRoomType().getRoomName())
	        .put("beginDate", booking.getBeginDate())
	        .put("lastDate", booking.getLastDate())
	        .put("breakfast", booking.getBreakfast())
	        .put("bookingStatus", booking.getBookingStatus())
	        .put("bId", booking.getbId());

        List<BookingPayment> bookingPayments = bookingPaymentServiceYTH.findByBooking(booking);
        if (bookingPayments != null) {
            for (BookingPayment payment : bookingPayments) {
                addPaymentToBookingObj(payment, bookingObj);
            }
        }
        return bookingObj;
    }

    private void addPaymentToBookingObj(BookingPayment payment, JSONObject bookingObj) {
        String typeName = payment.getTypeName();
        if ("應付金額".equals(typeName)) {
        	bookingObj.put("amountPayable", payment.getAmountPayable())
        	.put("amountPaid", payment.getAmountPaid())
        	.put("bookingDate", payment.getCreateDate())
        	.put("amountPaidDate", payment.getPaymentDate())
        	.put("amountMethod", payment.getPaymentMethod())
        	.put("amountId", payment.getBpId());
        }
        if ("訂金".equals(typeName)) {
        	bookingObj.put("deposit", payment.getAmountPayable())
        	.put("depositPaid", payment.getAmountPaid())
        	.put("depositExpire", payment.getExpireDate())
        	.put("depositPaidDate", payment.getPaymentDate())
        	.put("depositMethod", payment.getPaymentMethod())
        	.put("depositId", payment.getBpId());
        }

        List<UseDiscount> useDiscounts = useDiscountServiceYTH.findByBookingPayment(payment);
        if (useDiscounts != null && !useDiscounts.isEmpty()) {
            for (UseDiscount discount : useDiscounts) {
            	if(discount.getDiscountDetail().getDiscountType().equals("專案") ) {
            		bookingObj.put("packageDdName", discount.getDiscountDetail().getName())
            				  .put("packageDdId", discount.getDiscountDetail().getDdId())
            				  .put("packageUdId", discount.getUdId());
            	}else {
            		bookingObj.put("promoDdName", discount.getDiscountDetail().getName())
            				  .put("promoDdId", discount.getDiscountDetail().getDdId())
            				  .put("promoUdId", discount.getUdId());
            	}
            }
        }
    }

    private String createErrorResponse(String message) {
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("message", message);
        responseJSON.put("success", false);
        return responseJSON.toString();
    }
	
}
