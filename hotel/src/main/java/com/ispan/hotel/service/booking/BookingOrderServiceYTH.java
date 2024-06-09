package com.ispan.hotel.service.booking;

import java.time.LocalDate;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.BookingDetail;
import com.ispan.hotel.model.BookingPayment;
import com.ispan.hotel.model.Customer;

@Service
public class BookingOrderServiceYTH {

	@Autowired
	BookingPaymentServiceYTH bookingPaymentServiceYTH;
	
	@Autowired
	BookingServiceYTH bookingServiceYTH;
	
	@Autowired
	BookingDetailServiceYTH bookingDetailServiceYTH;
	
	@Autowired
	CustomerServiceYTH customerServiceYTH;
	
	@PostMapping("booking/book/paymentend")
	@Transactional
	public void ecpayProcess(String payType, String pd, LocalDate paymentDate, Integer paid, String paymentMethod, String orderNumber) {
			BookingDetail bd = bookingDetailServiceYTH.findByOrderNumber(orderNumber);
			if(bd != null) {
				Integer totalDeposit = 0, totalAmount = 0;
				
				JSONObject bpModifyObj = new JSONObject();
				bpModifyObj.put("paymentDate", paymentDate);
				bpModifyObj.put("paymentMethod", paymentMethod);
				
				List<Booking> bs = bookingServiceYTH.findByBookingDetail(bd); 
				if(bs != null) {
					
					// 先計算出totalDeposit和totalAmount
					for(Booking b : bs) {
						List<BookingPayment> bps = bookingPaymentServiceYTH.findByBooking(b);
						for(BookingPayment bp : bps) {
							if(bp.getTypeName().equals("訂金")) {
								totalDeposit += bp.getAmountPayable();
							}
							if(bp.getTypeName().equals("應付金額")) {
								totalAmount += bp.getAmountPayable();
							}
						}
					}
					
					//===================================================
					System.out.println("totalDeposit = " + totalDeposit);
					System.out.println("totalAmount = " + totalAmount);
					System.out.println("payType = " + payType);
					System.out.println("paid = " + paid);
					//===================================================
					
					if (payType.equals("deposit")) {
					    System.out.println("payType is deposit");
					} else {
					    System.out.println("payType is not deposit: " + payType);
					}

					if (paid == totalDeposit) {
					    System.out.println("paid is equal to totalDeposit: " + paid);
					} else {
					    System.out.println("paid is not equal to totalDeposit: " + paid + " vs " + totalDeposit);
					}
					
					//	判斷和比對付款類型
					//	先付訂金
					if(payType.equals("deposit") && paid.equals(totalDeposit)) {
						setDepositPayment(bs, bpModifyObj, "訂金");
					} else if(payType.equals("remain") && paid.equals(totalAmount)) {
						setDepositPayment(bs, bpModifyObj, "應付金額");
					} else if(payType.equals("full") && paid.equals(totalDeposit + totalAmount)){
						setDepositPayment(bs, bpModifyObj, "訂金");
						setDepositPayment(bs, bpModifyObj, "應付金額");
					} else {
						throw new RuntimeException("Unknown payment");
					}
				}
				
				Customer customer = bd.getCustomer();
				Customer result = customerServiceYTH.setPoint(customer, paid);
				if (result == null) {
                    throw new RuntimeException("Customer processing failed");
                }
				BookingDetail bm = bookingDetailServiceYTH.modifyPaymentStatus(bd, payType);
				if(bm == null) {
					throw new RuntimeException("bd finding failed");
				}
			} else {
				throw new RuntimeException("bd finding failed");
			}
	}
	
	private void setDepositPayment(List<Booking> bs, JSONObject obj, String method) {
		for(Booking b : bs) {
			List<BookingPayment> bps = bookingPaymentServiceYTH.findByBooking(b);
			for(BookingPayment bp : bps) {
				if(bp.getTypeName().equals(method)) {
					obj.put("bpId", bp.getBpId());
					obj.put("bId", b.getbId());
					obj.put("amountPaid", bp.getAmountPayable());
					bookingPaymentServiceYTH.modify(obj.toString());
				}
			}
		}
	}
	
	
		
		
	
	
//	private Integer setPayment(List<BookingPayment> bps, LocalDate date, Integer paid, String paymentMethod) {
//	    JSONObject obj = new JSONObject();
//	    obj.put("paymentDate", date);
//	    obj.put("paymentMethod", paymentMethod);
//	    Integer totalCost = 0;
//	    Integer deposit = 0;
//	    for (BookingPayment bp : bps) {
//	        if (bp.getTypeName().equals("訂金")) {
//	            deposit = bp.getAmountPayable() - bp.getAmountPaid();
//	        }
//	    }
//	    
//	    System.out.println("deposit: " + deposit);
//	    System.out.println("paid: " + paid);
//	    Integer remainingPaid = Math.subtractExact(paid, deposit);
//	    System.out.println("remainingPaid: " + remainingPaid);
//
//	    for (BookingPayment bp : bps) {
//	        obj.put("bpId", bp.getBpId())
//	           .put("bId", bp.getBooking().getbId());
//
//	        if (bp.getTypeName().equals("訂金")) {
//	            if (deposit > 0) {
//	                int paymentForDeposit = Math.min(paid, deposit);
//	                obj.put("amountPaid", paymentForDeposit);
//	                BookingPayment result = bookingPaymentServiceYTH.modify(obj.toString());
//	                if(result == null) {
//	                	throw new RuntimeException("updatebookingPayment deposit failed");
//	                }
//	                paid -= paymentForDeposit; // reduce the paid amount by the deposit amount
//	                totalCost += paymentForDeposit;
//	            }
//	        } else {
//	            if (remainingPaid > 0) {
//	                obj.put("amountPaid", remainingPaid);
//	                BookingPayment result = bookingPaymentServiceYTH.modify(obj.toString());
//	                if(result == null) {
//	                	throw new RuntimeException("updatebookingPayment amountPaid failed");
//	                }
//	                totalCost += remainingPaid;
//	                remainingPaid = 0; // ensure we do not double pay
//	            }
//	        }
//	    }
//	    return totalCost;
//	}
}
