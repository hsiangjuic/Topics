package com.ispan.hotel.controller.hotel;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Employee;
import com.ispan.hotel.service.hotel.EmployeeServiceCYP;


import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeControllerCYP {
	
	@Autowired 
	private EmployeeServiceCYP employeeServiceCYP;

	@PostMapping("/easyRegister")
	public String easyRegister(@RequestBody String json) {
		JSONObject responseJson = new JSONObject();
		JSONObject newObj = new JSONObject(json);
		
        String id = newObj.isNull("id") ? null : newObj.getString("id");
        String password =newObj.isNull("password") ? null : newObj.getString("password");
        Employee e=employeeServiceCYP.easyRegister(id, password);
        
        
        responseJson.put("success", true);
		responseJson.put("message", "簡易註冊成功");
		
        
        return responseJson.toString();
	}
	
	//...
	@PostMapping("/login")
	public String handlerMethod(HttpSession session, @RequestBody String json) {
		JSONObject responseJson = new JSONObject();
//接收資料
		JSONObject obj = new JSONObject(json);
		String username = obj.isNull("username") ? null : obj.getString("username");
		String password = obj.isNull("password") ? null : obj.getString("password");

//驗證資料
		if(username==null || username.length()==0 || password==null || password.length()==0) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入帳號與密碼");
			return responseJson.toString();
		}
		
//呼叫model
		Employee bean = employeeServiceCYP.login(username, password);

//根據model執行結果，導向view
		if(bean==null) {
			responseJson.put("success", false);
			responseJson.put("message", "登入失敗");
		} else {
			session.setAttribute("user", bean);
			responseJson.put("success", true);
			responseJson.put("message", "登入成功");
			JSONObject user=new JSONObject()
					.put("id",bean.getId())
					.put("name", bean.getLastName()+bean.getFirstName())
					.put("title", bean.getTitle());
			responseJson.put("user", user);
		}
		return responseJson.toString();
		
	}
}
