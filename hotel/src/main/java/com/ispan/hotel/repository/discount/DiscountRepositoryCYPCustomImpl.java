package com.ispan.hotel.repository.discount;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.Discount;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.LatestNews;
import com.ispan.hotel.model.RoomType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class DiscountRepositoryCYPCustomImpl implements DiscountRepositoryCYPCustom{
	@PersistenceContext
    private EntityManager entityManager;
	
	//透過discountDetail和roomType搜尋單筆Discount資料(修改對應房型後 刪除資料前 的查找)
	@Override
	@Transactional(readOnly = true)
	public Discount selectByDiscountDetailAndRoomType(DiscountDetail discountDetail, RoomType roomType) {
		CriteriaBuilder criteriaBuilder =entityManager.getCriteriaBuilder();
		CriteriaQuery<Discount> criteriaQuery=criteriaBuilder.createQuery(Discount.class);
		
		//select * from Discount where DD= :UPDATEOBJ AND RTID IN (:RTIDS)
			
		//from
		Root<Discount> root=criteriaQuery.from(Discount.class);
		
		//DD= UPDATEOBJ
		Predicate p1= criteriaBuilder.equal(root.get("discountDetail"), discountDetail);
		
		//RoomTypeId = roomType
		Predicate p2=criteriaBuilder.equal(root.get("roomType"), roomType);
		
		//where
		criteriaQuery.where(p1,p2);
		
		TypedQuery<Discount> typedQuery=entityManager.createQuery(criteriaQuery);
			//.setFirstResult(0).setMaxResult(20)
//		List<Discount> result =typedQuery.getResultList();
		System.out.println("==================here");
		  try {
			  System.out.println("==================OK");
		        return typedQuery.getSingleResult();
		  }catch (NonUniqueResultException e) {
	            // 處理找到多筆資料的情況，例如記錄警告消息
			  System.out.println("==================error1");
	            System.err.println("Found multiple results for DiscountDetail and RoomType");
	            e.printStackTrace();
	            // 返回 null 或者進行其他處理
	            return null;
	        } catch (NoResultException e) {
	        	System.out.println("==================error2");
	            return null; // 沒有找到符合條件的資料
	        }
		  

	}
}
