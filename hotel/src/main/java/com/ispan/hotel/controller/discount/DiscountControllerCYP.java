package com.ispan.hotel.controller.discount;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.LatestNews;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.repository.discount.RoomTypeRepositoryCYP;
import com.ispan.hotel.repository.discount.UseDiscountRepositoryCYP;
import com.ispan.hotel.service.discount.DiscountDetailServiceCYP;

@RestController
@RequestMapping("discount")
@CrossOrigin
public class DiscountControllerCYP {
	
	@Autowired
	private DiscountDetailServiceCYP discountDetailServiceCYP;
	@Autowired
	private UseDiscountRepositoryCYP useDiscountRepositoryCYP;
	@Autowired
	private RoomTypeRepositoryCYP roomTypeRepositoryCYP;
	//新增
	@PostMapping("/create")
	public String create(@RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        JSONObject newObj = new JSONObject(json);
        String name = newObj.isNull("name") ? null : newObj.getString("name");
        String promoCode =newObj.isNull("promoCode") ? null : newObj.getString("promoCode");
        Integer discountPrice=newObj.isNull("discountPrice") ? null : newObj.getInt("discountPrice");
        Integer discountRate=newObj.isNull("discountRate") ? null : newObj.getInt("discountRate");
        String discountType = newObj.isNull("discountType") ? null : newObj.getString("discountType");
        JSONArray roomTypes = newObj.isNull("roomTypes") ? null : newObj.getJSONArray("roomTypes");
        Integer maxTimes=newObj.isNull("maxTimes") ? null : newObj.getInt("maxTimes");

        //各種驗證
//        "hId": 旅館不能為null或空字串
//        "hotelName": null,
//        "promoCode": null, 如果type=折扣碼則選不能為null
//        "beginDate": null, 預設為今天
//        "lastDate": null, 預設為今天
//        "discountRate": null, 擇一，範圍50~99
//        "discountPrice": null,擇一
//        "name": 優惠名稱不能為null或空字串
//        "status": 預設為"尚未開始"?根據狀態判斷->前端做OK
//        "remark": 備註，可以為null
//        "maxTimes": null=無限次，預設為null
//        "discountType": 專案or折扣碼，不能為null，前端就會做限制
//        "lastModifiedEmp": null,
//        "memberOnly": 預設為false
//        "idVerification": 預設為false
//        "roomTypes": []預設為全選?
        Boolean promoCodeIsDuplicate= discountDetailServiceCYP.isPromoCodeDuplicate(promoCode);

		if (name == null || name == "") {
			responseJson.put("success", false);
			responseJson.put("message", "優惠名稱不能為空，請輸入優惠名稱");
		} else if(discountDetailServiceCYP.isNameDuplicate(name)){
			responseJson.put("success", false);
			responseJson.put("message", "優惠名稱已存在，請重新取名");
		} else if(discountType.equals("專案")&& (promoCode.length()>0)) {
			responseJson.put("success", false);
			responseJson.put("message", "選擇專案不可輸入折扣碼");
		} else if(discountType.equals("折扣碼")&& (promoCode==null||promoCode==""||promoCode.length()<6)) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入折扣碼，至少需6個字");
		} else if(discountType.equals("折扣碼")&& promoCodeIsDuplicate) {
			responseJson.put("success", false);
			responseJson.put("message", "本折扣碼已存在，請重新輸入");
		} else if (discountPrice==null && discountRate==null) { //兩個都沒選
			responseJson.put("success", false);
			responseJson.put("message", "請至少選擇一種折扣計算方式");
		} else if (discountPrice==null &&( discountRate >= 100|| discountRate <50)) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入50~99之間的整數。不允許設定五折以下折扣\"");
		} else if (discountRate==null && discountPrice == 0) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入折扣金額(不得為0)");		
		} else if (discountRate!=null && discountPrice != null) {
			responseJson.put("success", false);
			responseJson.put("message", "只能一種折扣方式)");	
		} else if(roomTypes==null||roomTypes.length() == 0) {
			responseJson.put("success", false);
			responseJson.put("message", "請至少選擇一間可適用房型");
		}else if(maxTimes==null||maxTimes== 0) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入使用次數上限，或勾選無限制");
		}  else {
			DiscountDetail discountDetail = discountDetailServiceCYP.create(json);
			if (discountDetail == null) {
				responseJson.put("success", false);
				responseJson.put("message", "新增優惠失敗");
			} else {
				responseJson.put("success", true);
				responseJson.put("message", "新增優惠成功");
			}
		}
        return responseJson.toString();
	}

	//修改
	@PutMapping("/modify/{pk}")
	public String modify(@PathVariable(name = "pk") Integer id, @RequestBody String json) {
		JSONObject responseJson = new JSONObject();
        JSONObject modifyObj = new JSONObject(json);
        Integer ddId = modifyObj.isNull("ddId") ? null : modifyObj.getInt("ddId");
        String name = modifyObj.isNull("name") ? null : modifyObj.getString("name");
        String promoCode =modifyObj.isNull("promoCode") ? null : modifyObj.getString("promoCode");
        Integer discountPrice=modifyObj.isNull("discountPrice") ? null : modifyObj.getInt("discountPrice");
        Integer discountRate=modifyObj.isNull("discountRate") ? null : modifyObj.getInt("discountRate");
        String discountType = modifyObj.isNull("discountType") ? null : modifyObj.getString("discountType");
        JSONArray roomTypes = modifyObj.isNull("roomTypes") ? null : modifyObj.getJSONArray("roomTypes");
        Integer maxTimes=modifyObj.isNull("maxTimes") ? null : modifyObj.getInt("maxTimes");
        Boolean promoCodeIsDuplicate= discountDetailServiceCYP.isPromoCodeDuplicate(promoCode);
        String oldPomoCode =discountDetailServiceCYP.findById(ddId).getPromoCode();
        String oldName =discountDetailServiceCYP.findById(ddId).getName();
        
        if(id==null) {
            responseJson.put("success", false);
            responseJson.put("message", "id是必要欄位");
        } else if (!id.equals(ddId)) { //如果兩者不相等
        	responseJson.put("success", false);
            responseJson.put("message", "@PathVariable和json內lnId不同，請重新確認");
        } else if(!discountDetailServiceCYP.existsById(id)) {
            responseJson.put("success", false);
            responseJson.put("message", "id不存在");
        } else if (name == null || name == "") {
			responseJson.put("success", false);
			responseJson.put("message", "優惠名稱不能為空，請輸入優惠名稱");
		} else if(!oldName.equals(name)&&discountDetailServiceCYP.isNameDuplicate(name)) { 
			responseJson.put("success", false);
			responseJson.put("message", "優惠名稱已存在，請重新取名"); 
		} else if(discountType.equals("折扣碼")&& (promoCode==null||promoCode==""||promoCode.length()<6)) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入折扣碼，至少需6個字");
		} else if(discountType.equals("折扣碼")&& !oldPomoCode.equals(promoCode)&&promoCodeIsDuplicate) {
			responseJson.put("success", false);
			responseJson.put("message", "本折扣碼已存在，請重新輸入");
		} else if (discountPrice==null && discountRate==null) { //兩個都沒選
			responseJson.put("success", false);
			responseJson.put("message", "請至少選擇一種折扣計算方式");
		} else if (discountPrice==null &&( discountRate >= 100|| discountRate <50)) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入50~99之間的整數。不允許設定五折以下折扣\"");
		} else if (discountRate==null && discountPrice == 0) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入折扣金額(不得為0)");		
		} else if (discountRate!=null && discountPrice != null) {
			responseJson.put("success", false);
			responseJson.put("message", "只能一種折扣方式)");	
		} else if(roomTypes==null||roomTypes.length() == 0) {
			responseJson.put("success", false);
			responseJson.put("message", "請至少選擇一間可適用房型");
		}else if(maxTimes==null||maxTimes== 0) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入使用次數上限，或勾選無限制");
		}  else {
        	DiscountDetail discountDetail = discountDetailServiceCYP.modify(json);
            if(discountDetail==null) {
              responseJson.put("success", false);
              responseJson.put("message", "修改優惠失敗");
          } else {
              responseJson.put("success", true);
              responseJson.put("message", "修改優惠成功");
          }
        }
        
        return responseJson.toString();
		
	}
	
	//刪除
	@DeleteMapping("/delete/{pk}")
	public String remove(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();
		if(id==null) {                           //沒給id的話
          responseJson.put("success", false);
          responseJson.put("message", "id是必要欄位");
      } else if(!discountDetailServiceCYP.existsById(id)) { //id不存在的話
          responseJson.put("success", false);
          responseJson.put("message", "id不存在");
      } else if(discountDetailServiceCYP.countUsedTimes(discountDetailServiceCYP.findById(id))!=0) {
    	  responseJson.put("success", false);
          responseJson.put("message", "系統設定使用次數不為0時無法刪除，如需停止此優惠請手動改成暫停或封存狀態。");
          responseJson.put("askArchive",true);
      } else {									//有給id且id存在的話
          if(discountDetailServiceCYP.deleteById(id)) { //deleteById成功回傳true
              responseJson.put("success", true);
              responseJson.put("message", "刪除成功");
          } else {                
              responseJson.put("success", false); //deleteById 失敗回傳false
              responseJson.put("message", "刪除失敗");
          }
      }
		
		return responseJson.toString();
	}
	
	//根據Status和Type進行多筆查詢(有分頁)(含count)
	@PostMapping("/findByStatusAndType")
	public String findByStatusAndType(@RequestBody String json) {
		JSONObject responseJson = new JSONObject();		
		JSONArray array = new JSONArray();//用來裝本頁要顯示的內容
		List<DiscountDetail> discountDetails=discountDetailServiceCYP.findByStatusAndType(json).getContent();//找到本頁要顯示的資料list
		if(discountDetails!=null && !discountDetails.isEmpty()) {//如果有找到
			for(DiscountDetail one:discountDetails) { //找到discountDetails裡的每個物件one，把one的資料取出後放到JSON物件item中，再把item放進JSONArray array裡
				Long usedTimes =discountDetailServiceCYP.countUsedTimes(one);
				
				JSONObject item = new JSONObject()
					.put("ddId", one.getDdId())
					.put("promoCode", one.getPromoCode())
					.put("beginDate", one.getBeginDate())
					.put("lastDate", one.getLastDate())
					.put("discountRate", one.getDiscountRate())
					.put("discountPrice", one.getDiscountPrice())
					.put("name", one.getName())
					.put("status", one.getStatus())
					.put("remark", one.getRemark())
					.put("maxTimes", one.getMaxTimes())
					.put("discountType", one.getDiscountType())
					.put("lastModifiedEmp", one.getLastModifiedEmp())
					.put("lastModifiedDate", one.getLastModifiedDate())
					.put("memberOnly", one.getMemberOnly())
					.put("idVerification", one.getIdVerification())
					.put("usedTimes", usedTimes);
				if (one.getHotel() != null) {
		            item.put("hId", one.getHotel().gethId())  
						.put("hotelName",one.getHotel().getName()); 
		        }
				array.put(item);
			}
		}else {
        	responseJson.put("success", false);
            responseJson.put("message", "查無符合條件之資料");
        }
		
		responseJson.put("list", array);//把array放進responseJson	
		
		responseJson.put("count", discountDetailServiceCYP.findByStatusAndType(json).getTotalElements());  //資料總數
		responseJson.put("pages", discountDetailServiceCYP.findByStatusAndType(json).getTotalPages()); 	//總頁數
		
		return responseJson.toString();
	}						
	
	//查詢(單筆)(0605棄用)
	@GetMapping("/findById/{pk}")
	public String findById(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();
		if (discountDetailServiceCYP.existsById(id)) {
			DiscountDetail one=discountDetailServiceCYP.findById(id);
			Long usedTimes =useDiscountRepositoryCYP.countByDiscountDetail(one);
			JSONObject item = new JSONObject()
					.put("ddId", one.getDdId())
					.put("promoCode", one.getPromoCode())
					.put("beginDate", one.getBeginDate())
					.put("lastDate", one.getLastDate())
					.put("discountRate", one.getDiscountRate())
					.put("discountPrice", one.getDiscountPrice())
					.put("name", one.getName())
					.put("status", one.getStatus())
					.put("remark", one.getRemark())
					.put("maxTimes", one.getMaxTimes())
					.put("discountType", one.getDiscountType())
					.put("lastModifiedEmp", one.getLastModifiedEmp())
					.put("lastModifiedDate", one.getLastModifiedDate())
					.put("memberOnly", one.getMemberOnly())
					.put("idVerification", one.getIdVerification())
					.put("usedTimes", usedTimes);
				if (one.getHotel() != null) {
		            item.put("hId", one.getHotel().gethId())  
						.put("hotelName",one.getHotel().getName()); 
		        }
				responseJson.put("data", item);
		}
		
		return responseJson.toString();
	}

	//查詢(單筆)0605版本合併找房型
	@GetMapping("/findById0605/{pk}")
	public String findById0605(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();
		if (discountDetailServiceCYP.existsById(id)) {
			DiscountDetail one=discountDetailServiceCYP.findById(id);
			Long usedTimes =useDiscountRepositoryCYP.countByDiscountDetail(one);
			List<Integer> roomTypeList= discountDetailServiceCYP.findRoomtypes0605(discountDetailServiceCYP.findById(id));
			JSONArray roomTypesArray = new JSONArray(roomTypeList);
			JSONObject item = new JSONObject()
					.put("ddId", one.getDdId())
					.put("promoCode", one.getPromoCode())
					.put("beginDate", one.getBeginDate())
					.put("lastDate", one.getLastDate())
					.put("discountRate", one.getDiscountRate())
					.put("discountPrice", one.getDiscountPrice())
					.put("name", one.getName())
					.put("status", one.getStatus())
					.put("remark", one.getRemark())
					.put("maxTimes", one.getMaxTimes())
					.put("discountType", one.getDiscountType())
					.put("lastModifiedEmp", one.getLastModifiedEmp())
					.put("lastModifiedDate", one.getLastModifiedDate())
					.put("memberOnly", one.getMemberOnly())
					.put("idVerification", one.getIdVerification())
					.put("usedTimes", usedTimes)
					.put("roomTypes", roomTypesArray);
				if (one.getHotel() != null) {
		            item.put("hId", one.getHotel().gethId())  
						.put("hotelName",one.getHotel().getName()); 
		        }
				responseJson.put("data", item);
		}
		
		return responseJson.toString();
	}
	
	//根據pk查詢對應房型
	@GetMapping("/findRoomTypesById/{pk}")
	public String findRoomTypesById(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();		
		JSONArray array = new JSONArray();//用來裝本頁要顯示的內容
		if(discountDetailServiceCYP.existsById(id)) {
			List<RoomType> roomTypes=discountDetailServiceCYP.findRoomtypes(discountDetailServiceCYP.findById(id));
			for(RoomType one:roomTypes) { 
				JSONObject room = new JSONObject();
				room.put("rtId", one.getRtId());
				room.put("roomName", one.getRoomName());
				array.put(room);
			}
			
		}else {
			responseJson.put("success", false);
            responseJson.put("message", "找不到這筆優惠資料");
		}
		
		responseJson.put("list", array);//把array放進responseJson	
		return responseJson.toString();
	}
	
	//暫停顯示/恢復正常
	@PutMapping("/changePauseStatus/{pk}")
	public String changePauseStatus(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();
		
		if(id==null) {                           //沒給id的話
          responseJson.put("success", false);
          responseJson.put("message", "id是必要欄位");
      } else if(!discountDetailServiceCYP.existsById(id)) { //id不存在的話
          responseJson.put("success", false);
          responseJson.put("message", "id不存在");
      } else {									//有給id且id存在的話 	  
    	  if(discountDetailServiceCYP.changePauseStatus(id)) {
    		  if(discountDetailServiceCYP.findById(id).getStatus()=="暫停中") {
				  responseJson.put("success", true);
				  responseJson.put("message", "成功切換狀態為暫停中");
    		  }else if(discountDetailServiceCYP.findById(id).getStatus()=="啟用中"){
    			  responseJson.put("success", true);
				  responseJson.put("message", "成功切換狀態為啟用中");
    		  }
    	  }else {
    		  responseJson.put("success", false);
    		  responseJson.put("message", "無法更改狀態");
    	  } 
      }	
		return responseJson.toString();
	}
	
	//封存
	@PutMapping("/archive/{pk}")
	public String archive(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();
		
		if(id==null) {                           //沒給id的話
          responseJson.put("success", false);
          responseJson.put("message", "id是必要欄位");
      } else if(!discountDetailServiceCYP.existsById(id)) { //id不存在的話
          responseJson.put("success", false);
          responseJson.put("message", "id不存在");
      } else {									//有給id且id存在的話 	  
    	  if(discountDetailServiceCYP.archive(id)) {
    		  if(discountDetailServiceCYP.findById(id).getStatus().equals("已封存")) {
				  responseJson.put("success", true);
				  responseJson.put("message", "成功切換狀態為已封存");
    		  }
    	  }else {
    		  responseJson.put("success", false);
    		  responseJson.put("message", "無法更改狀態");
    	  } 
      }	
		return responseJson.toString();
	}
	
	//資料狀態更新(多筆)
	@PutMapping("/statusUpdate")
	public String StatusUpdate() { 
		JSONObject responseJson = new JSONObject();	
		
		List<String> statuses =new ArrayList<String>();
		statuses.add("顯示中");
		statuses.add("尚未開始");
		
		//找到需要更新的資料list
		List<DiscountDetail> datasNeedToUpdate=discountDetailServiceCYP.findByStatus(statuses);

		//對每一筆資料進行判定並更新
		for(DiscountDetail data:datasNeedToUpdate) {
			discountDetailServiceCYP.UpdateStatus(data);
		}
		
		responseJson.put("success", true);
		responseJson.put("message","資料狀態更新完畢" ); 
		
		return responseJson.toString();
	}
	
	//查詢PROMOCODE看是否重複?
	@GetMapping({"/isPromoCodeDuplicate","/isPromoCodeDuplicate/{promoCode}"})
	public String isPromoCodeDuplicate(@PathVariable(name="promoCode",required = false) String promoCode) {
		JSONObject responseJson = new JSONObject();	
	if(promoCode!=null&&promoCode!="") {
		responseJson.put("success", true);
		responseJson.put("result", discountDetailServiceCYP.isPromoCodeDuplicate(promoCode));
	}else {
		responseJson.put("success", false);
	}
		return responseJson.toString();
	}
	
	//查詢name看是否重複? 0528
	@GetMapping("/isNameDuplicate/{name}")
	public String isNameDuplicate(@PathVariable(name="name") String name) {
		JSONObject responseJson = new JSONObject();	

		if(name!=null&&name!="") {
			responseJson.put("result", discountDetailServiceCYP.isNameDuplicate(name));
		}
		return responseJson.toString();
	}
	
	
	//查所有存在的房型列表
	@GetMapping("/findAllRoomType")
	public String findAllRoomType() {
		JSONObject responseJson = new JSONObject();	
		JSONArray array = new JSONArray();//用來裝本頁要顯示的內容
		List<RoomType> allRoomTypeList= roomTypeRepositoryCYP.findAll();
		if(allRoomTypeList!=null && !allRoomTypeList.isEmpty()) {//如果有找到
			for(RoomType one:allRoomTypeList) { //找到discountDetails裡的每個物件one，把one的資料取出後放到JSON物件item中，再把item放進JSONArray array裡
				JSONObject item = new JSONObject()
					.put("rtId", one.getRtId())
					.put("roomName", one.getRoomName());

				array.put(item);
			}
		}else {
        	responseJson.put("success", false);
            responseJson.put("message", "查無符合條件之資料");
        }
		
		responseJson.put("list", array);//把array放進responseJson	
		
		return responseJson.toString();
	}
}



