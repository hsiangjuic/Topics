package com.ispan.hotel.service.n;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ispan.hotel.model.Room;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.model.Room_;
import com.ispan.hotel.repository.n.RoomRepositoryCBH;
import com.ispan.hotel.repository.n.RoomTypeRepositoryCBH;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;




@Service
public class RoomServiceCBH {
	
	@Autowired
	private RoomRepositoryCBH roomRepository;
	
	@Autowired
	private RoomTypeRepositoryCBH roomTypeRepository;
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	
	public Room saveRoom(Room room) {
		return roomRepository.save(room);
	}
	
	
	
	
	public List<Room> findAllRoom(){
		
		return roomRepository.findAll();
		
	}
	
	@Transactional
	public List<Room> criteriaPagePraticePage(String json) {
		
		//前端傳過來的查詢參數
		JSONObject pageJson = new JSONObject(json);
		
		String roomNumber = pageJson.isNull("roomNumber") ? null : pageJson.getString("roomNumber");
		String floor = pageJson.isNull("floor") ? null : pageJson.getString("floor");
		String roomStatus = pageJson.isNull("roomStatus") ? null : pageJson.getString("roomStatus");
		String roomType = pageJson.isNull("roomType") ? null : pageJson.getString("roomType");
			
		
			
		//Criteria 開始:基本架構
		CriteriaBuilder criteriabuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<Room> criteriaQuery = criteriabuilder.createQuery(Room.class);
			
			
			
		//查詢語句
		Root<Room> table = criteriaQuery.from(Room.class); // from Room
			
		//select * 可省略不寫 select  
			
		//建立一個 List<Predicate> 裝所有條件, 而每個條件加到集合中的判斷是每個條件需要的參數不為 null 或空字串才建立條件並加入.
		List<Predicate> allPredicate = new ArrayList<>();
		
		if(roomNumber != null && roomNumber.length() != 0) {
			
			Predicate p1 = criteriabuilder.equal(table.get(Room_.roomNumber), roomNumber);
			allPredicate.add(p1);
				
		}
			
		if(floor != null && floor.length() != 0) {
				
			Predicate p2 = criteriabuilder.equal(table.get(Room_.floor), floor);
			allPredicate.add(p2);
				
		}
			
		if(roomStatus != null && roomStatus.length() != 0 && !roomStatus.equals("未選取任何狀態")) {
				
			Predicate p3 = criteriabuilder.equal(table.get(Room_.roomStatus), roomStatus);
			allPredicate.add(p3);
				
		}
			
		if(roomType != null && roomType.length() != 0) {
				
			Optional<RoomType> roomTypeop =  roomTypeRepository.findByRoomTypeNameOptimal(roomType);
			if(roomTypeop.isPresent()) {
					
				RoomType fromJson = roomTypeop.get();
				Predicate p4 = criteriabuilder.equal(table.get(Room_.roomType), fromJson);
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
		String order = pageJson.isNull("order") ? "rId" : pageJson.getString("order"); 
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
		TypedQuery<Room> typqQuery = getSession().createQuery(criteriaQuery);
			
		// 分頁需要 start 跟 rows
		int start = pageJson.isNull("start") ? 0 : pageJson.getInt("start"); //從第幾筆資料開始顯示, 第一次放在這裡比較好對照之後都放在上面就好了
		int rows = pageJson.isNull("rows") ? 10 : pageJson.getInt("rows"); //每頁顯示幾筆資料, 第一次放在這裡比較好對照之後都放在上面就好了
		typqQuery = typqQuery.setFirstResult(start);
		typqQuery = typqQuery.setMaxResults(rows);
		
		List<Room> result = typqQuery.getResultList();
		// 這個判斷有點多餘就當嚴謹吧.
		if(result != null && !result.isEmpty()) {
			return result;
		}
			return null;
			
	}
	
	@Transactional
	public long criteriaPagePraticePageCount(String json) {
		
		//select count(*) from Room where...
		
		//前端傳過來的查詢參數
		JSONObject pageJson = new JSONObject(json);
		String roomNumber = pageJson.isNull("roomNumber") ? null : pageJson.getString("roomNumber");
		String floor = pageJson.isNull("floor") ? null : pageJson.getString("floor");
		String roomStatus = pageJson.isNull("roomStatus") ? null : pageJson.getString("roomStatus");
		String roomType = pageJson.isNull("roomType") ? null : pageJson.getString("roomType");
						
		//Criteria 開始:基本架構
		CriteriaBuilder criteriabuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriabuilder.createQuery(Long.class);
				
				
				
		//查詢語句
		Root<Room> table = criteriaQuery.from(Room.class); // from Room
				
		//select count(*) 
		Expression<Long> exp1 = criteriabuilder.count(table);
		criteriaQuery = criteriaQuery.select(exp1);
		
				
		//建立一個 List<Predicate> 裝所有條件
		List<Predicate> allPredicate = new ArrayList<>();
				
		if(floor != null && floor.length() != 0) {
			if(roomNumber != null && roomNumber.length() != 0) {
				
				Predicate p1 = criteriabuilder.equal(table.get(Room_.roomNumber), roomNumber);
				allPredicate.add(p1);
					
			}
				
			if(floor != null && floor.length() != 0) {
					
				Predicate p2 = criteriabuilder.equal(table.get(Room_.floor), floor);
				allPredicate.add(p2);
					
			}
				
			if(roomStatus != null && roomStatus.length() != 0) {
					
				Predicate p3 = criteriabuilder.equal(table.get(Room_.roomStatus), roomStatus);
				allPredicate.add(p3);
					
			}
				
			if(roomType != null && roomType.length() != 0) {
					
				Optional<RoomType> roomTypeop =  roomTypeRepository.findByRoomTypeNameOptimal(roomType);
				if(roomTypeop.isPresent()) {
						
					RoomType fromJson = roomTypeop.get();
					Predicate p4 = criteriabuilder.equal(table.get(Room_.roomType), fromJson);
					allPredicate.add(p4);
						
				}
					
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
	public Room findByID(Integer rId) {
			
		Optional<Room> room = roomRepository.findById(rId);
		Room result = null;
			
		if (room != null) {
				
			result = room.get();
			return result;
				
		}
			
		return null;
			
	}
	
	
	
	// 傳入json新增資料
	public Room insertJsonRoom(String json) {
			
		LocalDateTime now = LocalDateTime.now();
		RoomType roomTypeFromJson = null;
		
		JSONObject obj = new JSONObject(json);
		String roomType = obj.isNull("roomType") ? null : obj.getString("roomType");
		Optional<RoomType> rt =  roomTypeRepository.findByRoomTypeNameOptimal(roomType);
		
		String floor = obj.isNull("floor")? null : obj.getString("floor");
		String roomNumber = obj.isNull("roomNumber")? null : obj.getString("roomNumber");
		String roomStatus = obj.isNull("roomStatus")? null : obj.getString("roomStatus");
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
		String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
		
		if(rt.isPresent()) {
			roomTypeFromJson = rt.get();
			Room r1 = new Room();
			r1.setRoomType(roomTypeFromJson);
			r1.setFloor(floor);
			r1.setRoomStatus(roomStatus);
			r1.setRoomNumber(roomNumber);
			r1.setLastModifiedDate(now);
			r1.setLastModifiedEmp(lastModifiedEmp);
			r1.setLastModifiedText(lastModifiedText);
			return r1;
		}
		
		return null;
		
	}
	
	public Room updateJsonRoom(String json) {
		
		LocalDateTime now = LocalDateTime.now();
		
		JSONObject obj = new JSONObject(json);
		Integer rId = obj.isNull("rId") ? null : obj.getInt("rId");
		String roomType = obj.isNull("roomType") ? null : obj.getString("roomType");
		
		Optional<Room> room = roomRepository.findById(rId);
		// 該 room
		Room result = room.isPresent() ? room.get() : null;
		
		// 找到的房型
		Optional<RoomType> roomytpe = roomTypeRepository.findByRoomTypeNameOptimal(roomType);
		RoomType fromJson = roomytpe.isPresent() ? roomytpe.get() : null;
		
		String floor = obj.isNull("floor")? null : obj.getString("floor");
		String roomNumber = obj.isNull("roomNumber")? null : obj.getString("roomNumber");
		String roomStatus = obj.isNull("roomStatus")? null : obj.getString("roomStatus");
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
		String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
        
        if(rId != null) {
        	
        	result.setRoomNumber(roomNumber);
        	result.setRoomType(fromJson);
        	result.setFloor(floor);
        	result.setRoomStatus(roomStatus);
        	result.setLastModifiedDate(now);
        	result.setLastModifiedEmp(lastModifiedEmp);
        	result.setLastModifiedText(lastModifiedText);
        	
        	return result;

        }
		
        return null;
		
	}

	
	
	@Transactional
	public void deleteRoomById(Integer rId) {
		
		roomRepository.deleteById(rId);
		
	}
}
