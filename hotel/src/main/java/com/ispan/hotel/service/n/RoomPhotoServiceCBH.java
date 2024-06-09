package com.ispan.hotel.service.n;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.hotel.model.RoomPhoto;
import com.ispan.hotel.model.RoomPhoto_;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.repository.n.RoomPhotoRepositoryCBH;
import com.ispan.hotel.repository.n.RoomTypeRepositoryCBH;
import com.ispan.hotel.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Transactional
@Service
public class RoomPhotoServiceCBH {
	
	@Autowired
	private RoomPhotoRepositoryCBH photoRepository;
	
	@Autowired
	private RoomTypeRepositoryCBH roomTypeRepository;
	
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	
	public RoomPhoto savePhoto(RoomPhoto photo) {
		
		return photoRepository.save(photo);
		
	}
	
	
	public RoomPhoto findPhotoById(Integer rpId) {
		
		Optional<RoomPhoto> opitem = photoRepository.findById(rpId);
		
		if(opitem.isPresent()) {
			
			return opitem.get();
			
		}
		
		return null;
		
	}
	
	public List<RoomPhoto> findAllPhotodata(){
		
		return photoRepository.findAll();
		
	}
	
	// 透過前端回傳的 formData, 不含圖片
	public RoomPhoto fromDatainsertWhithOutImg(String photoname, byte[] fileData,String photoDescription, String roomName) throws IOException {
		
		
		LocalDateTime nowFormat = DatetimeConverter.convertLocalDateTimeFormat(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
		
		List<RoomType> findByName = roomTypeRepository.findByRoomName(roomName);
		if(findByName.size() > 0) {
			// 在Java中，List接口提供了get(int index)方法，可以用来获取集合中指定索引处的元素。
			RoomType oneData = findByName.get(0);
			RoomPhoto fromData = new RoomPhoto(photoname, fileData, photoDescription, nowFormat, oneData);
			
			return fromData;
				
		} 
			
		return null;
	}
	
	// 透過前端回傳的 formData, 含圖片
	public RoomPhoto fromDatainsertWhithImg(String photoname, MultipartFile photoFile,String photoDescription, String roomName) throws IOException {
			
		LocalDateTime now = LocalDateTime.now();
		List<RoomType> findByName = roomTypeRepository.findByRoomName(roomName);
		if(findByName.size() > 0) {
			// 在Java中，List接口提供了get(int index)方法，可以用来获取集合中指定索引处的元素。
			RoomType oneData = findByName.get(0);
			RoomPhoto fromData = new RoomPhoto(photoname, photoFile.getBytes(), photoDescription, now, oneData);
			
			return fromData;
				
		} 
			
		return null;
			
	}
	
	// 透過前端回傳的 formData 包含圖片, 修改資料
	public RoomPhoto fromDataUpdate(Integer rpid, String photoname, MultipartFile photoFile,String photoDescription, String roomName) throws IOException {
		
		// 獲取現在的時間
		LocalDateTime now = LocalDateTime.now();
		
		// 以前端回傳的房型名稱獲得 RoomType 物件
		List<RoomType> findByName = roomTypeRepository.findByRoomName(roomName);
		RoomType oneData = null;
		if(findByName.size() > 0) {
			// 在Java中，List接口提供了get(int index)方法，可以用来获取集合中指定索引处的元素。
			oneData = findByName.get(0);
				
		} 
		
		// 獲得Photo物件
		Optional<RoomPhoto> updateData = photoRepository.findById(rpid);
		RoomPhoto byId = null;
		
		// 確認有該筆資料後設定要更新的職
		if(updateData.isPresent()) {
			
			byId = updateData.get();
			byId.setPhotoname(photoname);
			byId.setPhotoFile(photoFile.getBytes());
			byId.setPhotoDescription(photoDescription);
			byId.setRoomType(oneData);
			byId.setAddedDate(now);
			
			photoRepository.save(byId);
			
			return byId;
		}
		
			
		return null;
			
	}
	
	// 透過前端回傳的 formData 不包含圖片, 修改資料
		public RoomPhoto fromDataUpdateWithoutImg(Integer rpId, String photoname, byte[] fileData,String photoDescription, String roomName) throws IOException {
			
			// 獲取現在的時間
			LocalDateTime now = LocalDateTime.now();
			
			// 以前端回傳的房型名稱獲得 RoomType 物件
			List<RoomType> findByName = roomTypeRepository.findByRoomName(roomName);
			RoomType oneData = null;
			if(findByName.size() > 0) {
				// 在Java中，List接口提供了get(int index)方法，可以用来获取集合中指定索引处的元素。
				oneData = findByName.get(0);
					
			} 
			
			// 獲得Photo物件
			Optional<RoomPhoto> updateData = photoRepository.findById(rpId);
			RoomPhoto byId = null;
			
			// 確認有該筆資料後設定要更新的職
			if(updateData.isPresent()) {
				
				byId = updateData.get();
				byId.setPhotoname(photoname);
				byId.setPhotoFile(fileData);
				byId.setPhotoDescription(photoDescription);
				byId.setRoomType(oneData);
				byId.setAddedDate(now);
				
				photoRepository.save(byId);
				
				return byId;
			}
			
				
			return null;
				
		}
	
	// 刪除
	public void deletePhoto(Integer rpId) {
		photoRepository.deleteById(rpId);
	}
	
	
	// 分頁
	public Page<RoomPhoto> findByPage(Integer pageNumber, Integer rows){
				
	Pageable pgb = PageRequest.of((pageNumber - 1), rows, Sort.Direction.ASC, "rpId");
	Page<RoomPhoto> pagePhoto = photoRepository.findAll(pgb);
	return pagePhoto;
	
	}
	
	
	@Transactional
	public List<RoomPhoto> find(String json) {
		
		//前端傳過來的查詢參數
		JSONObject pageJson = new JSONObject(json);
		
		String roomType = pageJson.isNull("roomType") ? null : pageJson.getString("roomType");
			
		//Criteria 開始:基本架構
		CriteriaBuilder criteriabuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<RoomPhoto> criteriaQuery = criteriabuilder.createQuery(RoomPhoto.class);
			
		//查詢語句
		Root<RoomPhoto> table = criteriaQuery.from(RoomPhoto.class); // from Room
			
		//select * 可省略不寫 select  
			
		//建立一個 List<Predicate> 裝所有條件, 而每個條件加到集合中的判斷是每個條件需要的參數不為 null 或空字串才建立條件並加入.
		List<Predicate> allPredicate = new ArrayList<>();
			
		if(roomType != null && roomType.length() != 0) {
				
			Optional<RoomType> roomTypeop =  roomTypeRepository.findByRoomTypeNameOptimal(roomType);
			if(roomTypeop.isPresent()) {
					
				RoomType fromJson = roomTypeop.get();
				Predicate p4 = criteriabuilder.equal(table.get(RoomPhoto_.roomType), fromJson);
				allPredicate.add(p4);
					
			}
				
		}
			
		//有可能使用者都沒有輸入條件, 如果都沒有輸入就不用 where 了.
		if(allPredicate != null && !allPredicate.isEmpty()) {
				
			//where ...
		    Predicate[] predicatesArray = new Predicate[allPredicate.size()];
		    allPredicate.toArray(predicatesArray);
		    
		    criteriaQuery = criteriaQuery.where(predicatesArray);
				
		}
			
		//Order By 
		String order = pageJson.isNull("order") ? "rpId" : pageJson.getString("order"); 
		boolean dir = pageJson.isNull("dir") ? false : pageJson.getBoolean("dir");	  
			
		if(dir) {
				
			//true就是指定反向排序
			Order o1 =  criteriabuilder.desc(table.get(order));
			criteriaQuery = criteriaQuery.orderBy(o1); 
				
		} else {
				
			//false就是不指定, 默認是正向排序
			Order o1 =  criteriabuilder.asc(table.get(order));
			criteriaQuery = criteriaQuery.orderBy(o1); 
				
		}
			
		// 建立查詢, 取得查詢結果
		TypedQuery<RoomPhoto> typqQuery = getSession().createQuery(criteriaQuery);
			
		// 分頁需要 start 跟 rows
		int start = pageJson.isNull("start") ? 0 : pageJson.getInt("start"); //從第幾筆資料開始顯示, 第一次放在這裡比較好對照之後都放在上面就好了
		int rows = pageJson.isNull("rows") ? 10 : pageJson.getInt("rows"); //每頁顯示幾筆資料, 第一次放在這裡比較好對照之後都放在上面就好了
		typqQuery = typqQuery.setFirstResult(start);
		typqQuery = typqQuery.setMaxResults(rows);
		
		List<RoomPhoto> result = typqQuery.getResultList();
		// 這個判斷有點多餘就當嚴謹吧.
		if(result != null && !result.isEmpty()) {
			return result;
		}
			return null;
			
	}
	
	@Transactional
	public long count(String json) {
		
		//select count(*) from Room where...
		
		//前端傳過來的查詢參數
		JSONObject pageJson = new JSONObject(json);
		
		String roomType = pageJson.isNull("roomType") ? null : pageJson.getString("roomType");
						
		//Criteria 開始:基本架構
		CriteriaBuilder criteriabuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriabuilder.createQuery(Long.class);
				
				
				
		//查詢語句
		Root<RoomPhoto> table = criteriaQuery.from(RoomPhoto.class); // from Room
				
		//select count(*) 
		Expression<Long> exp1 = criteriabuilder.count(table);
		criteriaQuery = criteriaQuery.select(exp1);
		
				
		//建立一個 List<Predicate> 裝所有條件
		List<Predicate> allPredicate = new ArrayList<>();
				
		if(roomType != null && roomType.length() != 0) {
					
		Optional<RoomType> roomTypeop =  roomTypeRepository.findByRoomTypeNameOptimal(roomType);
		if(roomTypeop.isPresent()) {
						
			RoomType fromJson = roomTypeop.get();
			Predicate p4 = criteriabuilder.equal(table.get(RoomPhoto_.roomType), fromJson);
			allPredicate.add(p4);
						
			}
					
		}
		
		if(allPredicate != null && !allPredicate.isEmpty()) {
					
			//where ...
			Predicate[] predicatesArray = new Predicate[allPredicate.size()];
			allPredicate.toArray(predicatesArray);
			
			criteriaQuery = criteriaQuery.where(predicatesArray);
					
		}
				
				
		// 建立查詢, 取得查詢結果
		TypedQuery<Long> typqQuery = getSession().createQuery(criteriaQuery);
				
		Long result = typqQuery.getSingleResult();
		// 這個判斷有點多餘就當嚴謹吧.
		if(result != null) {
			return result;
		}
			
		return 0;
				
	}
	
	@Transactional
	public List<RoomPhoto> findbyroomName(String roomName) {
		
		
		
		String roomType = roomName;
			
		//Criteria 開始:基本架構
		CriteriaBuilder criteriabuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<RoomPhoto> criteriaQuery = criteriabuilder.createQuery(RoomPhoto.class);
			
		//查詢語句
		Root<RoomPhoto> table = criteriaQuery.from(RoomPhoto.class); // from Room
			
		//select * 可省略不寫 select  
			
		//建立一個 List<Predicate> 裝所有條件, 而每個條件加到集合中的判斷是每個條件需要的參數不為 null 或空字串才建立條件並加入.
		List<Predicate> allPredicate = new ArrayList<>();
			
		if(roomType != null && roomType.length() != 0) {
				
			Optional<RoomType> roomTypeop =  roomTypeRepository.findByRoomTypeNameOptimal(roomType);
			if(roomTypeop.isPresent()) {
					
				RoomType fromJson = roomTypeop.get();
				Predicate p4 = criteriabuilder.equal(table.get(RoomPhoto_.roomType), fromJson);
				allPredicate.add(p4);
					
			}
				
		}
			
		//有可能使用者都沒有輸入條件, 如果都沒有輸入就不用 where 了.
		if(allPredicate != null && !allPredicate.isEmpty()) {
				
			//where ...
		    Predicate[] predicatesArray = new Predicate[allPredicate.size()];
		    allPredicate.toArray(predicatesArray);
		    
		    criteriaQuery = criteriaQuery.where(predicatesArray);
				
		}
			
			
		// 建立查詢, 取得查詢結果
		TypedQuery<RoomPhoto> typqQuery = getSession().createQuery(criteriaQuery);
		List<RoomPhoto> result = typqQuery.getResultList();
		// 這個判斷有點多餘就當嚴謹吧.
		if(result != null && !result.isEmpty()) {
			return result;
		}
			return null;
			
	}
	
		
}
