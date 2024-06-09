package com.ispan.hotel.service.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.Customer;
import com.ispan.hotel.repository.booking.CustomerRepositoryYTH;
import com.ispan.hotel.util.DatetimeConverter;


@Service
@Transactional
public class CustomerServiceYTH {
	
	@Autowired
	private CustomerRepositoryYTH customerRepository;
	
	
	public Customer create(JSONObject obj) {
			String firstName = obj.isNull("firstName") ? null : obj.getString("firstName");
			String lastName = obj.isNull("lastName") ? null : obj.getString("lastName");
			String identification = obj.isNull("identification") ? null : obj.getString("identification");
			String passportNumber = obj.isNull("passportNumber") ? null : obj.getString("passportNumber");
			String tel = obj.isNull("tel") ? null : obj.getString("tel");
			String address = obj.isNull("address") ? null : obj.getString("address");
			String birthday = obj.isNull("birthday") ? null : obj.getString("birthday");
			String gender = obj.isNull("gender") ? null : obj.getString("gender");
			String country = obj.isNull("country") ? null : obj.getString("country");
			String email = obj.isNull("email") ? null : obj.getString("email");
			String remark = obj.isNull("remark") ? null : obj.getString("remark");
			String titleOfCourtesy = obj.isNull("titleOfCourtesy") ? null : obj.getString("titleOfCourtesy");
			String allowPromotionMail = obj.isNull("allowPromotionMail") ? null : obj.getString("allowPromotionMail");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
			String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
			String username = obj.isNull("username") ? null : obj.getString("username");
			
			if (firstName != null) {
				Customer customer = customerRepository.findByUsername(username);
				if(customer == null) {
					Customer insert = new Customer();
					insert.setFirstName(firstName);
					insert.setLastName(lastName);
					insert.setIdentification(identification);
					insert.setPassportNumber(passportNumber);
					insert.setTel(tel);
					insert.setAddress(address);
					if (birthday != null && birthday.length() != 0) {
						LocalDate temp = DatetimeConverter.parseLocalDate(birthday, "yyyy-MM-dd");
						insert.setBirthday(temp);
					} else {
						insert.setBirthday(null);
					}
					insert.setGender(gender);
					insert.setCountry(country);
					insert.setEmail(email);
					insert.setRemark(remark);
					insert.setTitleOfCourtesy(titleOfCourtesy);
					insert.setAllowPromotionMail(allowPromotionMail);
					insert.setLastModifiedEmp(lastModifiedEmp);
					insert.setLastModifiedText(lastModifiedText);
					insert.setTotalPoints(0);
					
					return customerRepository.save(insert);
					
				}
			}
		
		return null;
	}
	
	public Customer findById(Integer id) {
		if(id != null) {
			Optional<Customer> opt = customerRepository.findById(id);
			if (opt.isPresent()) {
				return opt.get();
			}
		
		}
		return null;
	}
	
//	public Customer findByIdentification(String id) {
//		if(id != null) {
//			return customerRepository.findByIdentification(id);
//		}
//		return null;
//	}
	
	public Customer findByUsername(String username) {
		if(username != null && username.length() != 0) {
			return customerRepository.findByUsername(username);
		}
		return null;
	}
	
	public List<Customer> find() {
		return customerRepository.findAll();
	}
	
	public Customer setPoint(Customer customer,Integer newPoints) {
		if(newPoints != 0 && newPoints != null && customer != null) {
			Integer oldPoints = 0;
			if(customer.getTotalPoints() != null) {
				oldPoints = customer.getTotalPoints();
			}
			Integer points = oldPoints + newPoints;
			Customer update = customer;
			update.setTotalPoints(points);
			return customerRepository.save(update);
		}
		return null;
	}
	
	public Customer modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer cId = obj.isNull("cId") ? null : obj.getInt("cId");
			String firstName = obj.isNull("firstName") ? null : obj.getString("firstName");
			String lastName = obj.isNull("lastName") ? null : obj.getString("lastName");
			String identification = obj.isNull("identification") ? null : obj.getString("identification");
			String passportNumber = obj.isNull("passportNumber") ? null : obj.getString("passportNumber");
			String tel = obj.isNull("tel") ? null : obj.getString("tel");
			String address = obj.isNull("address") ? null : obj.getString("address");
			String birthday = obj.isNull("birthday") ? null : obj.getString("birthday");
			String gender = obj.isNull("gender") ? null : obj.getString("gender");
			String country = obj.isNull("country") ? null : obj.getString("country");
			String email = obj.isNull("email") ? null : obj.getString("email");
			String remark = obj.isNull("remark") ? null : obj.getString("remark");
			String titleOfCourtesy = obj.isNull("titleOfCourtesy") ? null : obj.getString("titleOfCourtesy");
			String allowPromotionMail = obj.isNull("allowPromotionMail") ? null : obj.getString("allowPromotionMail");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
			String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");

			
			
			if (cId != null) {
				Optional<Customer> opt = customerRepository.findById(cId);
				if(opt.isPresent()) {
					Customer update = opt.get();
					update.setFirstName(firstName);
					update.setLastName(lastName);
					update.setIdentification(identification);
					update.setPassportNumber(passportNumber);
					update.setTel(tel);
					update.setAddress(address);
					if (birthday != null && birthday.length() != 0) {
						LocalDate temp = DatetimeConverter.parseLocalDate(birthday, "yyyy-MM-dd");
						update.setBirthday(temp);
					} else {
						update.setBirthday(null);
					}
					update.setGender(gender);
					update.setCountry(country);
					update.setEmail(email);
					update.setRemark(remark);
					update.setTitleOfCourtesy(titleOfCourtesy);
					update.setAllowPromotionMail(allowPromotionMail);
					
					if(lastModifiedEmp != null) {
						update.setLastModifiedDate(LocalDateTime.now());
						update.setLastModifiedEmp(lastModifiedEmp);
						update.setLastModifiedText(lastModifiedText);
					}
					
					return customerRepository.save(update);
					
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean delete(Integer id) {
		if(id != null) {
			Optional<Customer> opt = customerRepository.findById(id);
			if(opt.isPresent()) {
				customerRepository.deleteById(id);
				return true;
			}
		}
		return false;
	}
	
	
	
	
}
	

