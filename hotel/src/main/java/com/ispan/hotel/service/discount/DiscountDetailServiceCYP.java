package com.ispan.hotel.service.discount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.Discount;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.repository.discount.DiscountDetailRepositoryCYP;
import com.ispan.hotel.repository.discount.DiscountRepositoryCYP;
import com.ispan.hotel.repository.discount.RoomTypeRepositoryCYP;
import com.ispan.hotel.repository.discount.UseDiscountRepositoryCYP;
import com.ispan.hotel.repository.hotel.HotelRepositoryCYP;
import com.ispan.hotel.util.DatetimeConverter;
import com.ispan.hotel.util.ListToPageConverter;

@Service
public class DiscountDetailServiceCYP {
	@Autowired
	DiscountDetailRepositoryCYP ddRepo;
	@Autowired
	HotelRepositoryCYP hRepo;
	@Autowired
	DiscountRepositoryCYP dRepo;
	@Autowired
	RoomTypeRepositoryCYP rtRepo;
	@Autowired
	UseDiscountRepositoryCYP uRepo;
	
	//新增一筆資料(用JSON字串)(0521完成但還沒測過)
	@Transactional
	public DiscountDetail create(String json) {
		try {
			JSONObject obj = new JSONObject(json); //把傳入的字串變成一個JSONObject
			Integer hId = obj.isNull("hId") ? 1 : obj.getInt("hId"); //預設1
			String hotelName =obj.isNull("hotelName") ? null : obj.getString("hotelName");
			String promoCode =obj.isNull("promoCode") ? null : obj.getString("promoCode");//之後要驗證是否重複
			String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");//前端一定會傳資料回來
			String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");//前端一定會傳資料回來
			Integer discountRate=obj.isNull("discountRate") ? null : obj.getInt("discountRate");//擇一
			Integer discountPrice=obj.isNull("discountPrice") ? null : obj.getInt("discountPrice");//擇一
			String name = obj.isNull("name") ? null : obj.getString("name");
			String status = obj.isNull("status") ? null : obj.getString("status"); //前端給值
			String remark = obj.isNull("remark") ? null : obj.getString("remark");
			Integer maxTimes = obj.isNull("maxTimes") ? null : obj.getInt("maxTimes");
			String discountType = obj.isNull("discountType") ? null : obj.getString("discountType");
			//String lastModifiedDate = obj.isNull("lastModifiedDate") ? null : obj.getString("lastModifiedDate");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
			//String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
			Boolean memberOnly = obj.isNull("memberOnly") ? false : obj.getBoolean("memberOnly");
			Boolean idVerification = obj.isNull("idVerification") ? false : obj.getBoolean("idVerification");
			//新增
			DiscountDetail newObj=new DiscountDetail();
			newObj.setHotel(hRepo.findById(hId).get());
			newObj.setPromoCode(promoCode.toUpperCase());
			newObj.setBeginDate(DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd"));
			newObj.setLastDate(DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd"));
			newObj.setDiscountRate(discountRate);
			newObj.setDiscountPrice(discountPrice);
			newObj.setName(name);
			newObj.setStatus(status);
			newObj.setRemark(remark);
			newObj.setMaxTimes(maxTimes);
			newObj.setDiscountType(discountType);			
			//newObj.setLastModifiedDate(DatetimeConverter.parseLocalDateTime(lastModifiedDate, "yyyy-MM-dd"));//格式不知道怎麼寫但應該用不到
			newObj.setLastModifiedEmp(lastModifiedEmp);
			//newObj.setLastModifiedText(lastModifiedText);//ENTITY物件型別錯了啊啊啊
			newObj.setMemberOnly(memberOnly);
			newObj.setIdVerification(idVerification);
			ddRepo.save(newObj);
			
			//每新增一種優惠DiscountDetail，也要相應新增N筆Discount，JSON部分用一個ID傳?
			//JSON裡用陣列儲存可適用的本優惠的rtId
			JSONArray roomTypes = obj.isNull("roomTypes") ? null : obj.getJSONArray("roomTypes");
			//JSONArray轉換為List<RoomType>
			List<RoomType> roomTypesList = new ArrayList<>();
	        for (int i = 0; i < roomTypes.length(); i++) {
	            Integer rtId = roomTypes.getInt(i);
	            roomTypesList.add(rtRepo.findById(rtId).get());
	        }
			//新增N筆Discount
			for(RoomType rt:roomTypesList) {
				Discount newDiscountObj=new Discount();
				newDiscountObj.setDiscountDetail(newObj);
				newDiscountObj.setRoomType(rt);
				dRepo.save(newDiscountObj);
			}
			
			return ddRepo.save(newObj); //先這樣
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//修改一筆資料(用JSON字串)(0522完成但還沒測過)
	@Transactional
	public DiscountDetail modify(String json) {
		JSONObject obj = new JSONObject(json); //把傳入的字串變成一個JSONObject
		Integer ddId = obj.isNull("ddId") ? null : obj.getInt("ddId");
		Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
		String hotelName =obj.isNull("hotelName") ? null : obj.getString("hotelName");
		String promoCode =obj.isNull("promoCode") ? null : obj.getString("promoCode");
		String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
		String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");
		Integer discountRate=obj.isNull("discountRate") ? null : obj.getInt("discountRate");
		Integer discountPrice=obj.isNull("discountPrice") ? null : obj.getInt("discountPrice");
		String name = obj.isNull("name") ? null : obj.getString("name");
		String status = obj.isNull("status") ? null : obj.getString("status");
		String remark = obj.isNull("remark") ? null : obj.getString("remark");
		Integer maxTimes = obj.isNull("maxTimes") ? null : obj.getInt("maxTimes");
		String discountType = obj.isNull("discountType") ? null : obj.getString("discountType");
		//String lastModifiedDate = obj.isNull("lastModifiedDate") ? null : obj.getString("lastModifiedDate");
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
		//String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
		Boolean memberOnly = obj.isNull("memberOnly") ? false : obj.getBoolean("memberOnly");
		Boolean idVerification = obj.isNull("idVerification") ? false : obj.getBoolean("idVerification");
		//修改
		if (ddId != null) {
			Optional<DiscountDetail> op = ddRepo.findById(ddId);
			if (op.isPresent()) {
				DiscountDetail UpdateObj=op.get();
				UpdateObj.setHotel(hRepo.findById(hId).get());
				UpdateObj.setPromoCode(promoCode.toUpperCase());
				UpdateObj.setBeginDate(DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd"));
				UpdateObj.setLastDate(DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd"));
				UpdateObj.setDiscountRate(discountRate);
				UpdateObj.setDiscountPrice(discountPrice);
				UpdateObj.setName(name);
				UpdateObj.setStatus(status);
				UpdateObj.setRemark(remark);
				UpdateObj.setMaxTimes(maxTimes);
				UpdateObj.setDiscountType(discountType);			
				//UpdateObj.setLastModifiedDate(DatetimeConverter.parseLocalDateTime(lastModifiedDate, "yyyy-MM-dd"));//X
				UpdateObj.setLastModifiedEmp(lastModifiedEmp);
				//UpdateObj.setLastModifiedText(lastModifiedText);//X
				UpdateObj.setMemberOnly(memberOnly);
				UpdateObj.setIdVerification(idVerification);
				ddRepo.save(UpdateObj);
				
				//從資料庫中查詢當前優惠對應的所有房型記錄(舊)。
				List<Integer> beforeList = dRepo.findIdByDiscountDetail(UpdateObj);
				//修改後對應的所有房型紀錄(新)
				JSONArray roomTypes = obj.isNull("roomTypes") ? null : obj.getJSONArray("roomTypes");
				List<Integer> afterList = new ArrayList<>();
		        for (int i = 0; i < roomTypes.length(); i++) {
		            Integer rtId = roomTypes.getInt(i);
		            afterList.add(rtId);
		        }				
		        // 找出新增的數字
		        List<Integer> added = new ArrayList<>();
		        for (Integer id : afterList) {  //從修改後資料中找每個id
		            if (!beforeList.contains(id)) { //原本的list沒有而現在有的話
		                added.add(id); //加到added中
		            }
		        }
		        
		        // 找出刪除的數字
		        List<Integer> removed = new ArrayList<>();
		        for (Integer id : beforeList) { //從舊資料中找每個id
		            if (!afterList.contains(id)) {	//新資料中沒有但舊資料中有的話
		                removed.add(id);//加到removed中
		            }
		        }

		        // 新增新的房型對應記錄 ->可
		        for(Integer newId:added) {
		        	Discount newDiscount = new Discount();
		        	newDiscount.setRoomType(rtRepo.findById(newId).get());
		        	newDiscount.setDiscountDetail(UpdateObj);
		        	dRepo.save(newDiscount);
		        }
		        //刪除被移除的房型對應記錄->可
		        for(Integer deleteId:removed) {
		        	Discount toBeDelete=dRepo.selectByDiscountDetailAndRoomType(UpdateObj,rtRepo.findById(deleteId).get());
		        	dRepo.delete(toBeDelete);
		        }

		        return ddRepo.save(UpdateObj);
			}	
		}			
		return null;
	}
	
	//查詢多筆資料:依據狀態及類別(回傳PAGE物件) //0528多一個參數決定排序
	public Page<DiscountDetail> findByStatusAndType(String json) {
		JSONObject obj = new JSONObject(json);
		Boolean dir = obj.isNull("dir") ? true : obj.getBoolean("dir");
		JSONArray statuses = obj.isNull("statuses") ? null : obj.getJSONArray("statuses"); //取得JSONArray
		JSONArray types= obj.isNull("types") ? null : obj.getJSONArray("types"); //取得JSONArray
		List<String> statusList = new ArrayList<>();
		List<String> typeList = new ArrayList<>();
		if(statuses!=null) {
			//JSONArray轉換為List<String> stringList
	        for (int i = 0; i < statuses.length(); i++) {
	            String value = statuses.getString(i);
	            statusList.add(value);
	        }
		}		
		if(types!=null) {  
	        for (int i = 0; i < types.length(); i++) {
	            String value = types.getString(i);
	            typeList.add(value);
	        }
		}

		//有分頁版本
		// 解析 JSON，獲取分頁相關的參數
		Pageable pageable = getPageableFromJson(obj);
	    // 執行分頁查詢
		List<DiscountDetail> resultList = ddRepo.selectByStatusAndType(statusList,typeList,dir);
		
		// 使用ListToPageConverter返回分頁結果的數據列表
		Page<DiscountDetail> pageResult = ListToPageConverter.convertListToPage(resultList, pageable);
        return pageResult;
		
    }
	
	//使用discountDetail查詢該優惠目前使用次數(0524) 
	public long countUsedTimes(DiscountDetail discountDetail){		
		return uRepo.countByDiscountDetail(discountDetail);
	}
	
	//使用discountDetail查詢該優惠對應房型List(0524)
	public List<RoomType> findRoomtypes(DiscountDetail discountDetail){
		return dRepo.findRoomTypeListByDiscountDetail(discountDetail);
	}
	
	//使用discountDetail查詢該優惠對應房型ID的陣列List(0605)
	public List<Integer> findRoomtypes0605(DiscountDetail discountDetail){
		return dRepo.findIdByDiscountDetail(discountDetail);
	}
	
	//查詢單筆資料(用Id)0522
	public DiscountDetail findById(Integer id) {
		if(id!=null) {
			Optional<DiscountDetail> op=ddRepo.findById(id);
			return op.get();
		}
		return null;
	}
	

	//刪除一筆資料(用Id)0522(完成)
	@Transactional
	public boolean deleteById(Integer id) {
		if(id!=null) {
			Optional<DiscountDetail> toBeDeleteDDopt = ddRepo.findById(id);
			if(toBeDeleteDDopt.isPresent()) { //確認[有]這筆資料，則
				 ddRepo.deleteById(id);  //執行刪除
				 
				 //然後要刪除Discount對應紀錄->因為有設CascadeType.REMOVE所以會自動刪掉，讚!寫完才發現下面全部不用寫，我也不吱道下面的東西到底對不對.....QWQ
				 
				 //用findIdByDiscountDetail找到要移除的對應房型編號id們
//				 List<Integer> removedList =dRepo.findIdByDiscountDetail(toBeDeleteDDopt.get());				 
//				 
//		        for(Integer deleteId:removedList) {
//		        	Discount toBeDeleteD=dRepo.selectByDiscountDetailAndRoomType(toBeDeleteDDopt.get(),rtRepo.findById(deleteId).get());
//		        	dRepo.delete(toBeDeleteD);
//		        }
				 
				 return true;
			}
		}
		return false;
	}
	
	//確認資料是否存在(用ID)0522(完成)
	public boolean existsById(Integer id) {
		if(id!=null) {
			return ddRepo.existsById(id);
		}
		return false;
	}
	
	//改變暫停狀態(用ID)(0524)
	public boolean changePauseStatus(Integer id) {
		if(ddRepo.existsById(id)) {
			DiscountDetail obj=ddRepo.findById(id).get();
			if(obj.getStatus().equals("啟用中")) {
				obj.setStatus("暫停中");
				ddRepo.save(obj);
				return true;
			}else if (obj.getStatus().equals("暫停中")) {
				// 比較日期
		        //   date1.compareTo(date2)
				// 如果第一個日期在第二個日期之前，返回負整數。
		        // 如果兩個日期相等，返回零。
		        // 如果第一個日期在第二個日期之後，返回正整數。
				LocalDate today = LocalDate.now();
				obj.getBeginDate();
				obj.getLastDate();

				int startComparison = obj.getBeginDate().compareTo(today); 
				int endComparison= obj.getLastDate().compareTo(today); 
				
				if(startComparison>0) {		//startDate > today 
					obj.setStatus("尚未開始");
				}else if(startComparison <= 0 && endComparison>=0) {   //startDate <= today && endDate >= today
					obj.setStatus("啟用中");
				}else if(endComparison < 0 ) { //endDate < today
					obj.setStatus("已封存");
				}
				ddRepo.save(obj);
				return true;
			}		
		}
		return false;

	}
	
	//改成封存狀態(用ID)(0604)
		public boolean archive(Integer id) {
			if(ddRepo.existsById(id)) {
				DiscountDetail obj=ddRepo.findById(id).get();
				obj.setLastDate(LocalDate.now());
				obj.setStatus("已封存");
				ddRepo.save(obj);
				return true;
				}		
			return false;
		}
	
	//[分頁用private方法]
	private Pageable getPageableFromJson(JSONObject obj) {
	    Integer current = obj.isNull("current") ? 1 : obj.getInt("current");
	    Integer rows = obj.isNull("rows") ? 10 : obj.getInt("rows");
	    Boolean dir = obj.isNull("dir") ? true : obj.getBoolean("dir"); 		//預設為true
	    String order = obj.isNull("order") ? "beginDate" : obj.getString("order"); 	//預設為"beginDate"
	    // 指定分頁和排序
	    Sort.Direction direction = dir ? Sort.Direction.DESC : Sort.Direction.ASC; 
	    return PageRequest.of(current -1, rows, direction, order);    
	}
	
	//查詢多筆資料:只依據狀態(背景更新資料用)(0524)
	public List<DiscountDetail> findByStatus(List<String> statuses) {
		return ddRepo.selectByStatus(statuses);
    }
	
	//依日期判定更新資料狀態(背景更新資料用)(0524)
	public void UpdateStatus(DiscountDetail data) {
		LocalDate today=LocalDate.now();

		if (data.getStatus().equals("啟用中")) {
			if(data.getLastDate().isBefore(today)) {
				data.setStatus("已封存");
			}				
		}else if(data.getStatus().equals("尚未開始")) {
			if(data.getBeginDate().isBefore(today)||data.getBeginDate().isEqual(today)) {
				data.setStatus("啟用中");
			}				
		}
		ddRepo.save(data);
    }
	
	//查詢PROMOCODE看是否重複?(0524)
	public Boolean isPromoCodeDuplicate(String promoCode) {
		List<DiscountDetail> result=ddRepo.findByPromoCode(promoCode);
		if (!result.isEmpty()) { //不是空值=找到了=有重複
		    return true;
		} else {
			return false;
		}
	}
	
	//查詢name看是否重複?(0528)
	public Boolean isNameDuplicate(String name) {
		List<DiscountDetail> result=ddRepo.findByName(name);
		if (!result.isEmpty()) { //不是空值=找到了=有重複
		    return true;
		} else {
			return false;
		}
	}

}
