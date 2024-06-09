package com.ispan.hotel.service.booking;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.BookingDetail;
import com.ispan.hotel.model.BookingPayment;
import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.Room;
import com.ispan.hotel.model.UseDiscount;

@Service
public class BookingProcessServiceYTH {
	
	@Autowired
	private CustomerServiceYTH customerService;
	
	@Autowired
	private BookingServiceYTH bookingService;
	
	@Autowired
	private RoomServiceYTH roomService;
	
	@Autowired
	private BookingDetailServiceYTH bookingDetailService;
	
	@Autowired
	private BookingPaymentServiceYTH bookingPaymentService;
	
	@Autowired
	private MemberRankServiceYTH memberRankServiceYTH;
	
	@Autowired
	private UseDiscountServiceYTH useDiscountService;
	
	@Transactional
	public Boolean removeBooking(Integer id) throws Exception{
		Booking booking = bookingService.findById(id);
		List<BookingPayment> bookingPayments = bookingPaymentService.findByBooking(booking);
		
		//砍掉bookingPayment和useDiscount
		if(bookingPayments != null) {
			for(BookingPayment bookingPayment : bookingPayments) {
//				if(bookingPayment.getAmountPaid() != 0) {
//					throw new Exception("cannot delete paid payment" + bookingPayment.getBpId()); 
//				}
				List<UseDiscount> useDiscounts = useDiscountService.findByBookingPayment(bookingPayment);
				if(useDiscounts != null) {
					for(UseDiscount useDiscount : useDiscounts) {
						Boolean result = useDiscountService.delete(useDiscount.getUdId());
						if(!result) {
							throw new Exception("useDiscount delete failed" + useDiscount.getUdId()); 
						}
					}
				}
				Boolean result = bookingPaymentService.delete(bookingPayment.getBpId());
				if(!result) {
					throw new Exception("bookingPayment delete failed" + bookingPayment.getBpId()); 
				}
			}
		}
		Boolean result = bookingService.delete(booking.getbId());
		if(!result) {
			throw new Exception("booking delete failed" + booking.getbId());
		}
		return true;
	}
	
	
	@Transactional
	public String createBooking(String json) throws Exception{
			JSONObject obj = new JSONObject(json);
			JSONObject responseJSON = new JSONObject();
			String username = obj.isNull("username") ? null : obj.getString("username");
			String payType  = obj.isNull("payType") ? null : obj.getString("payType");
			Integer mr = 1;

			
			Customer customer = customerService.findByUsername(username);

			if (customer == null) {
				customer = customerService.create(obj);
			} else {
				mr = memberRankServiceYTH.findById(customer.getMemberRank().getMrId()).getGainPoints();
			}
			
			if (customer == null) {
				throw new Exception("Customer creation failed");
			}

			BookingDetail bookingDetail = bookingDetailService.create(obj, customer);
			if (bookingDetail == null) {
				throw new Exception("BookingDetail creation failed");
			}
			
			JSONArray roomArray = obj.getJSONArray("rooms");
			for (int i = 0; i < roomArray.length(); i++) {
	            JSONObject roomObj = roomArray.getJSONObject(i);
	            String typeName = roomObj.isNull("typeName") ? null : roomObj.getString("typeName");
	            Integer amountPayable = roomObj.isNull("amountPayable") ? null : roomObj.getInt("amountPayable");
	            Integer deposit = roomObj.isNull("deposit") ? null : roomObj.getInt("deposit");
	            
	            
	            System.out.println(roomObj.toString());
	            List<Room> rooms = roomService.findEmptyRoom(roomObj);
	            Room room = rooms.get(0);
	            if (room == null) {
	            	throw new Exception("No empty room found");
	            }
	            
	            Booking booking = bookingService.create(roomObj, bookingDetail, room);
	            if (booking == null) {
	            	throw new Exception("Booking creation failed");
	            }
	            
	            BookingPayment bookingPayment = new BookingPayment();
	            typeName = "應付金額";
	            bookingPayment = bookingPaymentService.create(roomObj, booking, mr, typeName, amountPayable);
	            String typeNameDeposit = "訂金";
	            BookingPayment depositPayment = bookingPaymentService.create(roomObj, booking, mr, typeNameDeposit, deposit);
	            if(depositPayment == null) {
	            	throw new Exception("Deposit bookingPayment creation failed");
	            }
	            
	            if (bookingPayment == null) {
	            	throw new Exception("BookingPayment creation failed");
	            }
	            JSONArray ddIdsArray = roomObj.getJSONArray("ddIds");
	            for(int j = 0; j < ddIdsArray.length(); j++) {
	            	JSONObject ddIdObj = ddIdsArray.getJSONObject(j);
	            	Integer ddId = ddIdObj.isNull("ddId") ? null :ddIdObj.getInt("ddId");
	            	if(ddId != null) {
	            		UseDiscount useDiscount = useDiscountService.create(bookingPayment, ddId);
	            		if (useDiscount == null) {
	            			throw new Exception("UseDiscount creation failed");
	            		}
	            	}
	            }
			}
			responseJSON.put("orderNumber", bookingDetail.getOrderNumber())
						.put("paymentStatus", bookingDetail.getPaymentStatus())
						.put("titleOfCourtesy", customer.getTitleOfCourtesy())
						.put("payType", payType);
			if(customer.getCountry() != null) {
				if(customer.getCountry().equals("台灣")) {
					responseJSON.put("name", customer.getLastName()+customer.getFirstName());
				} else {
					responseJSON.put("name", customer.getFirstName()+" "+customer.getLastName());
				}
			}
			return responseJSON.toString();
	
	}
}
