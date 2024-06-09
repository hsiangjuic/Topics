package com.ispan.hotel.service.booking;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.BookingDetail;
import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.Hotel;
import com.ispan.hotel.repository.booking.BookingDetailRepositoryYTH;


@Service
public class BookingDetailServiceYTH {
	
	@Autowired
	private BookingDetailRepositoryYTH bookingDetailRepository;
	
	@Autowired
	private CustomerServiceYTH customerService;
	
	@Autowired
	private HotelServiceYTH hotelService;
	

	
	public BookingDetail create(JSONObject obj, Customer customer) {
		Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
		String customerRemark = obj.isNull("customerRemark") ? "無" : obj.getString("customerRemark");
		Integer childNumber = obj.isNull("childNumber") ? 0 : obj.getInt("childNumber");
		Boolean infantUtility = obj.isNull("infantUtility") ? false : obj.getBoolean("infantUtility");
		Integer adultNumber = obj.isNull("adultNumber") ? 0 : obj.getInt("adultNumber");
		Integer petNumber = obj.isNull("petNumber") ? 0 : obj.getInt("petNumber");
		String paymentStatus = obj.isNull("paymentStatus") ? "未付款" : obj.getString("paymentStatus");
		String arrivalTime = obj.isNull("arrivalTime") ? "15:00" : obj.getString("arrivalTime");
		Integer totalRoom = obj.isNull("totalRoom") ? 0 : obj.getInt("totalRoom");
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
		String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
		

		if(customer != null && hId != null) {
			Hotel hotel = hotelService.findById(hId);
			if(hotel != null) {
				BookingDetail insert = new BookingDetail();
				insert.setCustomer(customer);
				insert.setHotel(hotel);
				insert.setCustomerRemark(customerRemark);
				insert.setChildNumber(childNumber);
				insert.setInfantUtility(infantUtility);
				insert.setAdultNumber(adultNumber);
				insert.setPetNumber(petNumber);
				if(paymentStatus == null) {
					paymentStatus ="未付款";
				}
				insert.setPaymentStatus(paymentStatus);
				insert.setArrivalTime(arrivalTime);
				insert.setTotalRoom(totalRoom);
				insert.setLastModifiedEmp(lastModifiedEmp);
				insert.setLastModifiedText(lastModifiedText);
				
				insert.setOrderNumber(generateOrderNumber());
				
				return bookingDetailRepository.save(insert);
			}
			
		}
		return null;
	}
	public BookingDetail customerModify(BookingDetail bookingDetail, String json) {
		JSONObject obj = new JSONObject(json);
		if(bookingDetail != null) {
			
			String arrivalTime = obj.isNull("arrivalTime") ? null : obj.getString("arrivalTime");
			String customerRemark = obj.isNull("customerRemark") ? "無" : obj.getString("customerRemark");
			
			if(arrivalTime != null) {
				bookingDetail.setArrivalTime(arrivalTime);
			}
			bookingDetail.setCustomerRemark(customerRemark);
			return bookingDetailRepository.save(bookingDetail);
		}
		
		return null;
	}
	
	public BookingDetail modifyPaymentStatus(BookingDetail bd, String type) {
		if(bd != null && type.length()!= 0 && type != null) {
			if(type.equals("deposit")) {
				bd.setPaymentStatus("已付訂金");
			}
			if(type.equals("full") || type.equals("remain")){
				bd.setPaymentStatus("已付款");
			}
			return bookingDetailRepository.save(bd);
		}
		return null;
	}
	
	public BookingDetail modify(String json) {
		
		JSONObject obj = new JSONObject(json);
		
		Integer bdId = obj.isNull("bdId") ? null : obj.getInt("bdId");
		Integer cId = obj.isNull("cId") ? null : obj.getInt("cId");
		Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
		String customerRemark = obj.isNull("customerRemark") ? "無" : obj.getString("customerRemark");
		Integer childNumber = obj.isNull("childNumber") ? null : obj.getInt("childNumber");
		Boolean infantUtility = obj.isNull("infantUtility") ? null : obj.getBoolean("infantUtility");
		Integer adultNumber = obj.isNull("adultNumber") ? null : obj.getInt("adultNumber");
		Integer petNumber = obj.isNull("petNumber") ? null : obj.getInt("petNumber");
		String paymentStatus = obj.isNull("paymentStatus") ? null : obj.getString("paymentStatus");
		String arrivalTime = obj.isNull("arrivalTime") ? null : obj.getString("arrivalTime");
		Integer totalRoom = obj.isNull("totalRoom") ? null : obj.getInt("totalRoom");
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
		String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");

		if (bdId != null && cId != null && hId != null) {
			Optional<BookingDetail> opt = bookingDetailRepository.findById(bdId);
			Customer customer = customerService.findById(cId);
			Hotel hotel = hotelService.findById(hId);
			if (opt.isPresent() && customer != null && hotel != null) {
				BookingDetail update = opt.get();
				update.setCustomer(customer);
				update.setHotel(hotel);
				if(customerRemark != null) {
					update.setCustomerRemark(customerRemark);
				}
				if(childNumber != null) {
					update.setChildNumber(childNumber);
				}
				if(infantUtility != null) {
					update.setInfantUtility(infantUtility);
				}
				if(adultNumber != null) {
					update.setAdultNumber(adultNumber);
				}
				if(petNumber != null) {
					update.setPetNumber(petNumber);
				}
				if(paymentStatus != null) {
					update.setPaymentStatus(paymentStatus);
				}
				if(arrivalTime != null) {
					update.setArrivalTime(arrivalTime);
				}
				if(totalRoom != null) {
					update.setTotalRoom(totalRoom);
				}
				if(lastModifiedEmp != null) {
					update.setLastModifiedDate(LocalDateTime.now());
					update.setLastModifiedEmp(lastModifiedEmp);
					update.setLastModifiedText(lastModifiedText);
				}

				return bookingDetailRepository.save(update);
			} 
		}
		return null;
	}
	
	public BookingDetail findById(Integer id) {
		if(id!=null) {
			Optional<BookingDetail> optional = bookingDetailRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
	
	public List<BookingDetail> find(){
		return bookingDetailRepository.findAll();
	}
	
	public List<BookingDetail> find(JSONObject obj){
		return bookingDetailRepository.find(obj);
	}
	
	public BookingDetail findByOrderNumber(String orderNumber) {
		return bookingDetailRepository.findByOrderNumber(orderNumber);
	}
	
	public Boolean delete(Integer id) {
		if(id != null) {
			Optional<BookingDetail> opt = bookingDetailRepository.findById(id);
			if(opt.isPresent()) {
				bookingDetailRepository.deleteById(id);
				return true;
			}
		}
		return false;
	}
	
    public String generateOrderNumber() {
        // 獲取當前日期時間
        LocalDateTime now = LocalDateTime.now();
        // 格式化日期時間
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 計算日期時間的 MD5 雜湊值
        String hashValue = calculateHash(formattedDateTime);

        // 組合訂單編號
        String orderNumber = formattedDateTime.substring(0, 8) + hashValue.substring(0, 8); // 取雜湊值的前8位數作為編號的一部分

        return orderNumber;
    }

    // 計算雜湊值
    private String calculateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            // 轉換為十六進位，相較十進位增加可讀性（有英文字母）、降低資源、轉換一致以及普遍hash都是轉十六進位
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                //檢查是否只有一位數
                if (hex.length() == 1) {
                	//若僅一位數，前面塞0以確保都是兩位數
                	hexString.append('0');
                }
                //塞數字
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
