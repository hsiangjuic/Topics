package com.ispan.hotel.service.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.BookingPayment;
import com.ispan.hotel.repository.booking.BookingPaymentRepositoryYTH;
import com.ispan.hotel.util.DatetimeConverter;

@Service
public class BookingPaymentServiceYTH {

	@Autowired
	private BookingPaymentRepositoryYTH bookingPaymentRepository;
	
	@Autowired
	private BookingServiceYTH bookingService;
	
	public BookingPayment create(JSONObject obj, Booking booking, Integer mr,String typeName, Integer amountPayable) {
			Integer amountPaid = obj.isNull("amountPaid") ? null : obj.getInt("amountPaid");
			String expireDate = obj.isNull("expireDate") ? null : obj.getString("expireDate");
			String paymentDate = obj.isNull("paymentDate") ? null : obj.getString("paymentDate");
			String paymentMethod = obj.isNull("paymentMethod") ? null : obj.getString("paymentMethod");
			String remark = obj.isNull("paymentRemark") ? null : obj.getString("paymentRemark");
			Integer points = obj.isNull("points") ? null : obj.getInt("points");
			String createDate = obj.isNull("createDate") ? null : obj.getString("createDate");
			String invoiceNumber = obj.isNull("invoiceNumber") ? null : obj.getString("invoiceNumber");
			String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
			
			if(booking != null) {
				BookingPayment insert = new BookingPayment();
				insert.setBooking(booking);
				
				if(expireDate != null && expireDate.length() != 0) {
					LocalDate temp = DatetimeConverter.parseLocalDate(expireDate, "yyyy-MM-dd");
					insert.setExpireDate(temp);
				} else {
					if(typeName.equals("訂金")) {
						LocalDate temp = DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd");
						insert.setExpireDate(temp.minusDays(7));
					} else {
						LocalDate temp = DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd");
						insert.setExpireDate(temp);
					}
				}
				
				if(paymentDate != null && paymentDate.length() != 0) {
					LocalDate temp = DatetimeConverter.parseLocalDate(paymentDate, "yyyy-MM-dd");
					insert.setPaymentDate(temp);
				} else {
					insert.setPaymentDate(null);
				}
				if(typeName.equals("應付金額")) {
					insert.setPaymentMethod(null);
				} else {
					insert.setPaymentMethod(paymentMethod);
				}
				insert.setRemark(remark);
				if (points != null) {
					insert.setPoints(points);
				} else {
					points = mr * amountPayable;
					insert.setPoints(points);
				}
				if(createDate != null && createDate.length() != 0) {
					LocalDate temp = DatetimeConverter.parseLocalDate(createDate, "yyyy-MM-dd");
					insert.setCreateDate(temp);
				} else {
					insert.setCreateDate(LocalDate.now());
				}
				
				if(invoiceNumber != null) {
					insert.setInvoiceNumber(invoiceNumber);
				} else {
					insert.setInvoiceNumber("73705743");
				}
				
				if (typeName != null && typeName.length() != 0) {
					insert.setTypeName(typeName);
				} else {
					insert.setTypeName("全額繳清");
				}
				if (amountPaid != null && amountPaid != 0) {
					insert.setAmountPaid(amountPaid);
				} else {
					insert.setAmountPaid(0);
				}
				
				insert.setAmountPayable(amountPayable);
				return bookingPaymentRepository.save(insert);
			}

		return null;
	}
	
	public BookingPayment modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			
			Integer bpId = obj.isNull("bpId") ? null : obj.getInt("bpId");
			Integer bId = obj.isNull("bId") ? null : obj.getInt("bId");
			String typeName = obj.isNull("typeName") ? null : obj.getString("typeName");
			Integer amountPayable = obj.isNull("amountPayable") ? null : obj.getInt("amountPayable");
			Integer amountPaid = obj.isNull("amountPaid") ? null : obj.getInt("amountPaid");
			String expireDate = obj.isNull("expireDate") ? null : obj.getString("expireDate");
			String paymentDate = obj.isNull("paymentDate") ? null : obj.getString("paymentDate");
			String paymentMethod = obj.isNull("paymentMethod") ? null : obj.getString("paymentMethod");
			String remark = obj.isNull("paymentRemark") ? null : obj.getString("paymentRemark");
			String invoiceNumber = obj.isNull("invoiceNumber") ? null : obj.getString("invoiceNumber");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
			String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
			
			if(bpId != null && bId != null) {
				Booking booking = bookingService.findById(bId);
				Optional<BookingPayment> opt = bookingPaymentRepository.findById(bpId);
				
				if(opt.isPresent() && booking != null) {
					BookingPayment update = opt.get();
					if (booking != null) {
						update.setBooking(booking);
					}
					if (typeName != null) {
						update.setTypeName(typeName);
					}
					if (amountPayable != null) {
						update.setAmountPayable(amountPayable);
					}
					if (amountPaid != null) {
						update.setAmountPaid(amountPaid);
					}
					if(expireDate != null && expireDate.length() != 0) {
						LocalDate temp = DatetimeConverter.parseLocalDate(expireDate, "yyyy-MM-dd");
						update.setExpireDate(temp);
					}
					if(paymentDate != null && paymentDate.length() != 0) {
						LocalDate temp = DatetimeConverter.parseLocalDate(paymentDate, "yyyy-MM-dd");
						update.setPaymentDate(temp);
					}
					if (paymentMethod != null) {
						update.setPaymentMethod(paymentMethod);
					}
					if (remark != null) {
						update.setRemark(remark);
					}
					update.setPoints(amountPaid);
					if (invoiceNumber != null) {
						update.setInvoiceNumber(invoiceNumber);
					}
					if(lastModifiedEmp != null) {
						update.setLastModifiedDate(LocalDateTime.now());
						update.setLastModifiedEmp(lastModifiedEmp);
						update.setLastModifiedText(lastModifiedText);
					}
					
					return bookingPaymentRepository.save(update);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public BookingPayment findById(Integer id) {
		if(id != null) {
			Optional<BookingPayment> opt = bookingPaymentRepository.findById(id);
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
	
	public List<BookingPayment> find(){
		return bookingPaymentRepository.findAll();
	}
	
	public List<BookingPayment> findByBooking(Booking booking) {
		if(booking != null) {
			return bookingPaymentRepository.findByBooking(booking);
		}
		return null;
	}
	
	public Boolean delete(Integer id) {
		if(id != null) {
			Optional<BookingPayment> opt = bookingPaymentRepository.findById(id);
			if(opt.isPresent()) {
				bookingPaymentRepository.deleteById(id);
				return true;
			}
		}
		return false;
	}
	
}
