package com.ispan.hotel.service.hotel;

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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.LatestNews;
import com.ispan.hotel.repository.hotel.HotelRepositoryCYP;
import com.ispan.hotel.repository.hotel.LatestNewsRepositoryCYP;
import com.ispan.hotel.util.DatetimeConverter;

@Service
@Transactional //?
public class LatestNewsServiceCYP {

	@Autowired
	private LatestNewsRepositoryCYP repo;
	@Autowired
	private HotelRepositoryCYP hotelRepo;
	
	//新增一筆資料(用JSON字串)(0531改回hId
	public LatestNews create(String json) {
		try {
			JSONObject obj = new JSONObject(json); //把傳入的字串變成一個JSONObject
			Integer lnId = obj.isNull("lnId") ? null : obj.getInt("lnId");
			Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
			String hotelName =obj.isNull("hotelName") ? null : obj.getString("hotelName");//新版新增
			String title = obj.isNull("title") ? null : obj.getString("title");
			String startDate = obj.isNull("startDate") ? null : obj.getString("startDate");
			String endDate = obj.isNull("endDate") ? null : obj.getString("endDate");
			String content = obj.isNull("content") ? null : obj.getString("content");
			String image = obj.isNull("image") ? null : obj.getString("image");
			String status = obj.isNull("status") ? null : obj.getString("status");
			String type = obj.isNull("type") ? null : obj.getString("type");
			//新增沒做任何檢查就是直接新增
			LatestNews newObj = new LatestNews();
			newObj.setHotel(hotelRepo.findById(hId).get()); //改回用hId查吧
			//newObj.setHotel(hotelRepo.findByName(hotelName).get()); 
			newObj.setTitle(title);
			newObj.setStartDate(DatetimeConverter.parseLocalDate(startDate, "yyyy-MM-dd"));
			newObj.setEndDate(DatetimeConverter.parseLocalDate(endDate, "yyyy-MM-dd"));
			newObj.setContent(content);
			newObj.setImage(image);
			newObj.setStatus(status);
			newObj.setType(type);
			return repo.save(newObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//修改一筆資料(用JSON字串)(0531改回用hId查
	public LatestNews modify(String json) {
		try {
			JSONObject obj = new JSONObject(json); //把傳入的字串變成一個JSONObject
			
			Integer lnId = obj.isNull("lnId") ? null : obj.getInt("lnId");
			Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
			String hotelName =obj.isNull("hotelName") ? null : obj.getString("hotelName");
			String title = obj.isNull("title") ? null : obj.getString("title");
			String startDate = obj.isNull("startDate") ? null : obj.getString("startDate");
			String endDate = obj.isNull("endDate") ? null : obj.getString("endDate");
			String content = obj.isNull("content") ? null : obj.getString("content");
			String image = obj.isNull("image") ? null : obj.getString("image");
			String status = obj.isNull("status") ? null : obj.getString("status");
			String type = obj.isNull("type") ? null : obj.getString("type");
			
			if (lnId != null) {
				Optional<LatestNews> op = repo.findById(lnId);
				if (op.isPresent()) {
					LatestNews UpdateObj = op.get();
					UpdateObj.setLnId(lnId);
					UpdateObj.setHotel(hotelRepo.findById(hId).get()); 
					UpdateObj.setTitle(title);
					UpdateObj.setStartDate(DatetimeConverter.parseLocalDate(startDate, "yyyy-MM-dd"));
					UpdateObj.setEndDate(DatetimeConverter.parseLocalDate(endDate, "yyyy-MM-dd"));
					UpdateObj.setContent(content);
					UpdateObj.setImage(image);
					UpdateObj.setStatus(status);
					UpdateObj.setType(type);
					return repo.save(UpdateObj);
				}
			} 		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//[分頁用private方法]
	private Pageable getPageableFromJson(JSONObject obj) {
	    Integer current = obj.isNull("current") ? 1 : obj.getInt("current");
	    Integer rows = obj.isNull("rows") ? 10 : obj.getInt("rows");
	    Boolean dir = obj.isNull("dir") ? true : obj.getBoolean("dir"); 		//預設為true
	    String order = obj.isNull("order") ? "startDate" : obj.getString("order"); 	//預設為"startDate"
	    // 指定分頁和排序
	    Sort.Direction direction = dir ? Sort.Direction.DESC : Sort.Direction.ASC; //學學OwO
	    return PageRequest.of(current -1, rows, direction, order);    
	}
		
	//刪除一筆資料(用Id)(不變)
	public boolean deleteById(Integer id) {
		if(id!=null) {
			Optional<LatestNews> optional = repo.findById(id);
			if(optional.isPresent()) { //3.確認[有]這筆資料，則
				 repo.deleteById(id);  //4.執行刪除
				 return true;
			}
		}
		return false;
	}
	
	//查詢一筆資料(用ID)(不變)
	public LatestNews findById(Integer id) {
		if(id!=null) {
			Optional<LatestNews> op=repo.findById(id);
			return op.get();
		}
		return null;
	}
	
	//確認資料是否存在(用ID)(不變)
	public boolean existsById(Integer id) {
		if(id!=null) {
			return repo.existsById(id);
		}
		return false;
	}
	
	//改變暫停狀態(用ID)(0521加入日期判斷)
	public boolean changePauseStatus(Integer id) {
		if(repo.existsById(id)) {
			LatestNews obj=repo.findById(id).get();
			if(obj.getStatus().equals("顯示中")) {
				obj.setStatus("暫停顯示");
				repo.save(obj);
				return true;
			}else if (obj.getStatus().equals("暫停顯示")) {
				// 比較日期
		        //   date1.compareTo(date2)
				// 如果第一個日期在第二個日期之前，返回負整數。
		        // 如果兩個日期相等，返回零。
		        // 如果第一個日期在第二個日期之後，返回正整數。
				LocalDate today = LocalDate.now();
				obj.getStartDate();
				obj.getEndDate();

				int startComparison = obj.getStartDate().compareTo(today); 
				int endComparison= obj.getEndDate().compareTo(today); 
				
				if(startComparison>0) {		//startDate > today 
					obj.setStatus("尚未開始");
				}else if(startComparison <= 0 && endComparison>=0) {   //startDate <= today && endDate >= today
					obj.setStatus("顯示中");
				}else if(endComparison < 0 ) { //endDate < today
					obj.setStatus("已封存");
				}
				repo.save(obj);
				return true;
			}		
		}
		return false;

	}

	//查詢總共有幾筆資料(不變)(好像沒用到過?)
	public Long count() {
		return repo.count();
	}
	
	//查詢多筆資料:依據狀態及類別(有分頁) 0515的努力成果^^(0520更新為回傳page物件
	public Page<LatestNews> findByStatusAndType(String json) {//List<String> statuses
		JSONObject obj = new JSONObject(json);
		JSONArray statuses = obj.isNull("statuses") ? null : obj.getJSONArray("statuses"); //取得JSONArray
		JSONArray types= obj.isNull("types") ? null : obj.getJSONArray("types"); //取得JSONArray
		
		//JSONArray轉換為List<String> stringList
		List<String> statusList = new ArrayList<>();
        for (int i = 0; i < statuses.length(); i++) {
            String value = statuses.getString(i);
            statusList.add(value);
        }
        List<String> typeList = new ArrayList<>();
        for (int i = 0; i < types.length(); i++) {
            String value = types.getString(i);
            typeList.add(value);
        }

		//有分頁版本
		// 解析 JSON，獲取分頁相關的參數
		Pageable pageable = getPageableFromJson(obj);
	    // 執行分頁查詢
		Page<LatestNews> pageResult = repo.selectByStatusAndType(statusList,typeList, pageable);
//		// 返回分頁結果的數據列表
//		List<LatestNews> result = pageResult.getContent();    

        return pageResult;
    }

	//查詢多筆資料:只依據狀態(背景更新資料用)(0521)
	public List<LatestNews> findByStatus(List<String> statuses) {
		return repo.selectByStatus(statuses);
    }
	
	//依日期判定更新資料狀態(背景更新資料用)(0521)
	public void UpdateStatus(LatestNews data) {
		
		LocalDate today=LocalDate.now();

		if (data.getStatus().equals("顯示中")) {
			if(data.getEndDate().isBefore(today)) {
				data.setStatus("已封存");
			}				
		}else if(data.getStatus().equals("尚未開始")) {
			if(data.getStartDate().isBefore(today)||data.getStartDate().isEqual(today)) {
				data.setStatus("顯示中");
			}				
		}
		repo.save(data);
    }
	
//以下完全用不到(還在摸索的時候寫的東西)-----------------------------------------------
	//查詢多筆資料(分頁的方法寫在裡面)//應該不會用到了就放著
	public List<LatestNews> findPageContent(String json){		
		JSONObject obj = new JSONObject(json);
		Integer start = obj.isNull("start") ? null : obj.getInt("start");
		Integer rows = obj.isNull("rows") ? null : obj.getInt("rows");
		Boolean dir = obj.isNull("dir") ? null : obj.getBoolean("dir");
		String order = obj.isNull("order") ? null : obj.getString("order");
		String name = obj.isNull("name") ? null : obj.getString("name");	
//			{
//			    "start":0,
//			    "rows":3,
//			    "dir":false,
//			    "order":"id",
//			    "name":"a"
//			}
		// 分頁從第 1 頁開始
        if (start == null || start < 1) {
        	start = 1;
        }
        Pageable pageable=null;
     // 指定分頁和排序
        if (dir==true||dir==null) {
        	pageable = PageRequest.of(start - 1, rows, Sort.Direction.DESC, order);
        }else if(dir==false) {
        	pageable = PageRequest.of(start - 1, rows, Sort.Direction.ASC, order);
        }

        // 執行分頁查詢
        Page<LatestNews> page = repo.findAll(pageable);

        // 返回分頁結果的數據列表
        return page.getContent();
		
	}
	
	

	//新增一筆資料(用Bean) 
	public LatestNews insert(LatestNews bean) {
			if(bean!=null && bean.getLnId()!=null) { //1.檢查bean和其Id是否為null
				Optional<LatestNews> optional = repo.findById(bean.getLnId()); //2.根據bean的Id搜尋是否已有這筆資料
				if(optional.isEmpty()) { //3.確認沒有這筆資料，則
					return repo.save(bean);  //4.執行新增
				}
			}
			return null; //1.2.3.有任何一項不滿足都直接回傳null
			
			// 資料驗證：標題不能為空、日期範圍不合法等等
			// 業務邏輯處理
	    	//    可以根據需要進行其他業務邏輯處理，例如檢查是否已經存在相同標題的公告等
		}
	//查詢單筆/多筆資料(用Bean)
	public List<LatestNews> select(LatestNews Bean){
		List<LatestNews> result = null; 
		if(Bean!=null && Bean.getLnId()!=null && !Bean.getLnId().equals(0)) { //確認 1.這個物件存在(不是null)2.這個物件的id存在且有效(不是null且不為0)
			Optional<LatestNews> optional =repo.findById(Bean.getLnId());//可以抓到一個Optional的資料
			if(optional.isPresent()) { //如果資料庫有抓到資料的話
				result = new ArrayList<LatestNews>();
				result.add(optional.get());
			}
		} else { //沒有給參數就是抓全部資料
			result = repo.findAll();
		}
		return result;
	}
	//修改一筆資料(用Bean)
	public LatestNews update(LatestNews bean) {
		if(bean!=null && bean.getLnId()!=null) { //1.檢查bean和其Id是否為null
			Optional<LatestNews> optional = repo.findById(bean.getLnId()); //2.根據bean的Id搜尋是否已有這筆資料
			if(optional.isPresent()) { //3.確認[有]這筆資料，則
				System.out.println("修改中");
				return repo.save(bean);  //4.執行修改
			}
		}
		return null; //1.2.3.有任何一項不滿足都直接回傳null
	}
	//刪除一筆資料(用BEAN)
	public boolean delete(LatestNews bean) {
		if(bean!=null && bean.getLnId()!=null) { //1.檢查bean和其Id是否為null
			Optional<LatestNews> optional = repo.findById(bean.getLnId()); //2.根據bean的Id搜尋是否已有這筆資料
			if(optional.isPresent()) { //3.確認[有]這筆資料，則
				 repo.delete(bean);  //4.執行修改
				 return true;
			}
		}
		return false; //1.2.3.有任何一項不滿足都直接回傳null
	}
	
	
}
