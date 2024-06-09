package com.ispan.hotel.service.hotel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.Employee;
import com.ispan.hotel.repository.hotel.DeptRepositoryCYP;
import com.ispan.hotel.repository.hotel.EmployeeRepositoryCYP;

@Transactional
@Service
public class EmployeeServiceCYP {
	
	@Autowired 
	private EmployeeRepositoryCYP employeeRepository;
	@Autowired 
	private DeptRepositoryCYP deptRepository;
	
	private String passwordMD5(String password){
	    try {
	        // 使用 MD5 算法創建 MessageDigest 對象
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 將明文密碼轉換為字節數組並計算 MD5 散列值
	        byte[] hashedBytes = md.digest(password.getBytes());
	        // 將字節數組轉換為十六進制字符串
	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashedBytes) {
	            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	        }
	        // 加密後的密碼即為 sb.toString()
	        System.out.println("加密後的密碼：" + sb.toString());
	        return sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	        System.err.println("MD5 算法不存在");
	    }
	    return null;
	}
	
	//簡易註冊
	public Employee easyRegister(String username, String password) {
		Employee e=new Employee();
		String encodePass=passwordMD5(password);
		e.setId(username);
		e.setPassword(encodePass);
		e.setDept(deptRepository.findById(1).get());
		employeeRepository.save(e);
		return e;
	}
	
	//登入
	public Employee login(String username, String password) { 
		//找帳號
		if(username!=null && username.length()!=0) {
			Optional<Employee> optional = employeeRepository.selectById(username);
			if(optional.isPresent()) {
				Employee bean = optional.get();
				//從資料庫找密碼
				String pass = bean.getPassword();//資料庫抓
				//轉換使用者輸入的密碼
				if(password!=null && password.length()!=0) {
					String encodePass=passwordMD5(password);//使用者輸入
					if(encodePass.equals(pass)) {
						return bean;
					}
				}
			}
			
		}
		return null;
	}
}
