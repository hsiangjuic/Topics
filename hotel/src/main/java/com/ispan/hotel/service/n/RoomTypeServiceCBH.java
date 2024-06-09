package com.ispan.hotel.service.n;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Hotel;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.repository.n.RoomTypeRepositoryCBH;
import com.ispan.hotel.repository.n.hotelRepositoryCBH;
import com.ispan.hotel.util.DatetimeConverter;

    







@Service
public class RoomTypeServiceCBH {
	
	@Autowired
	private RoomTypeRepositoryCBH RoomTypeRepo;
	
	@Autowired
	private hotelRepositoryCBH hotelRepository;
	
	// 一對多, 新增一筆資料
	public RoomType insertRoomType(RoomType roomType) {
		return RoomTypeRepo.save(roomType);
	}
	
	// 傳入json新增資料
	public RoomType insertJsonRoomType(String json) {
		
		JSONObject jsonObject = new JSONObject(json);
		String RoomName = jsonObject.getString("roomName");
		String featureTittle = jsonObject.getString("featureTittle");
		String featureTittleContent = jsonObject.getString("featureTittleContent");
		Integer roomAmount = jsonObject.getInt("roomAmount");
		Integer bedNumber = jsonObject.getInt("bedNumber");
		String bedType = jsonObject.getString("bedType");
		Integer memberPrice = jsonObject.getInt("memberPrice");
		Integer flexiblePrice = jsonObject.getInt("flexiblePrice");
		String pet = jsonObject.getString("pet");
		String allowAddBed = jsonObject.getString("allowAddBed");
		String accessibleRoom = jsonObject.getString("accessibleRoom");
		Integer peopleMaxAmount = jsonObject.getInt("peopleMaxAmount");
		String lastModifiedEmp = jsonObject.getString("lastModifiedEmp");
		String lastModifiedText = jsonObject.getString("lastModifiedText");
		String hotelName = jsonObject.getString("hotelName");
		
		LocalDateTime now = LocalDateTime.now();
		
		List<Hotel> hotel = hotelRepository.findByHotelName(hotelName);
		if(hotel.size() > 0) {
			Hotel byName = hotel.get(0);
			RoomType insert = new RoomType(RoomName, featureTittle, featureTittleContent,roomAmount, bedNumber, bedType, memberPrice, flexiblePrice, pet, allowAddBed, accessibleRoom, peopleMaxAmount, now, lastModifiedEmp, lastModifiedText, byName); 
			return insert;
		}
		
		
        
        
        
        return null;   
		
		
	}
	
	// 傳入json修改資料
	public RoomType updateJsonRoomType(String json) {
		
		LocalDateTime now = LocalDateTime.now();
		
		JSONObject obj = new JSONObject(json);
		
		System.out.println(obj);
		
		Integer rtId = obj.isNull("rtId") ? null : obj.getInt("rtId");
		
		// obj.isNull("roomTypeName") 打成 obj.isNull("RoomTypeName") 導致 roomTypeName 判斷是空值, 空值無法插入
		String RoomName = obj.isNull("roomName") ? null : obj.getString("roomName");
		String featureTittle = obj.isNull("featureTittle") ? null : obj.getString("featureTittle");
		String featureTittleContent = obj.isNull("featureTittleContent") ? null : obj.getString("featureTittleContent");
		Integer roomAmount = obj.isNull("roomAmount") ? null : obj.getInt("roomAmount");
		Integer bedNumber = obj.isNull("bedNumber") ? null : obj.getInt("bedNumber");
		String bedType = obj.isNull("bedType") ? null : obj.getString("bedType");
		Integer memberPrice = obj.isNull("memberPrice") ? null : obj.getInt("memberPrice");
		Integer flexiblePrice = obj.isNull("flexiblePrice") ? null : obj.getInt("flexiblePrice");
		String pet = obj.isNull("pet") ? null : obj.getString("pet");
		String allowAddBed = obj.isNull("allowAddBed") ? null : obj.getString("allowAddBed");
		String accessibleRoom = obj.isNull("accessibleRoom") ? null : obj.getString("accessibleRoom");
		Integer peopleMaxAmount = obj.isNull("peopleMaxAmount") ? null : obj.getInt("peopleMaxAmount");
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
		String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
		
		if(rtId != null) {
			Optional<RoomType> optionalRoomType = RoomTypeRepo.findById(rtId);
			if(optionalRoomType.isPresent()) {
				RoomType roomTypeUpdate = optionalRoomType.get();
				roomTypeUpdate.setRoomName(RoomName);
				roomTypeUpdate.setFeatureTittle(featureTittle);
				roomTypeUpdate.setFeatureTittleContent(featureTittleContent);
				roomTypeUpdate.setRoomAmount(roomAmount);
				roomTypeUpdate.setBedNumber(bedNumber);
				roomTypeUpdate.setBedType(bedType);
				roomTypeUpdate.setMemberPrice(memberPrice);
				roomTypeUpdate.setFlexiblePrice(flexiblePrice);
				roomTypeUpdate.setPet(pet);
				roomTypeUpdate.setAllowAddBed(allowAddBed);
				roomTypeUpdate.setAccessibleRoom(accessibleRoom);
				roomTypeUpdate.setPeopleMaxAmount(peopleMaxAmount);
				roomTypeUpdate.setLastModifiedDate(now);
				roomTypeUpdate.setLastModifiedEmp(lastModifiedEmp);
				roomTypeUpdate.setLastModifiedText(lastModifiedText);
				
				return RoomTypeRepo.save(roomTypeUpdate);
			}
		}
		return null;
	}
	
	// 一對多查詢, 以 id 尋找
	public RoomType findById(Integer rtId) {
		
		// 都用 Optional 來驗證物件是否是空值並且回傳值不要跟物件同名, 不然有時候會觸發遞迴錯誤
		if(rtId!=null) {
			Optional<RoomType> byId = RoomTypeRepo.findById(rtId);
			if(byId.isPresent()) {
				RoomType rt = byId.get();
				return rt;
			}
		}
		return null;
		
	}
	
	/*
	update 是調用 repository 的 id 尋找舊有的物件 findById 後再使用 repository 的 save() 保存資料,
	在現在這個 service 中
	*/
	
	// 一對多, 以 id 刪除資料
	public void deleteRoomTypeById(Integer rtId) {
		RoomTypeRepo.deleteById(rtId);
	}
	
	// 找所有資料
	public List<RoomType> findAllRoomType() {
		return RoomTypeRepo.findAll();
	}
	
	// 分頁
	public Page<RoomType> findByPage(Integer pageNumber, Integer rows){
		
		Pageable pgb = PageRequest.of((pageNumber-1), rows, Sort.Direction.ASC, "rtId");
		Page<RoomType> pageRoomType = RoomTypeRepo.findAll(pgb);
		
		return pageRoomType;

	}
	
	
	
	
	

}
