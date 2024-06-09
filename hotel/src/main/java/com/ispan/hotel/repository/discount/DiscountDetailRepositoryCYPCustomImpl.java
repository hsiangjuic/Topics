package com.ispan.hotel.repository.discount;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.DiscountDetail;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Transactional
@Repository
public class DiscountDetailRepositoryCYPCustomImpl implements DiscountDetailRepositoryCYPCustom {
	@PersistenceContext
    private EntityManager entityManager;
	
	//透過類型/狀態查詢(0528：加入order by desc)
	@Override
	@Transactional(readOnly = true)
	public List<DiscountDetail> selectByStatusAndType(List<String> statusArray, List<String> typeArray,Boolean dir) {
		CriteriaBuilder criteriaBuilder =entityManager.getCriteriaBuilder();
		CriteriaQuery<DiscountDetail> criteriaQuery=criteriaBuilder.createQuery(DiscountDetail.class);
		
		//@Query("FROM LatestNews WHERE status IN (:statusArray) AND type IN (:typeArray)")
		//orderBy!!!!!沒寫的話分頁會有問題
		
		
		//from
		Root<DiscountDetail> root =criteriaQuery.from(DiscountDetail.class);
		
		//where p1 and p2		
		Predicate p1 = root.get("status").in(statusArray);
		Predicate p2 = root.get("discountType").in(typeArray);
        criteriaQuery.where(p1,p2);
        
        //order by ddId desc (0528)
        if(dir) {
        	criteriaQuery.orderBy(criteriaBuilder.desc(root.get("beginDate")));
        }else {
        	criteriaQuery.orderBy(criteriaBuilder.asc(root.get("beginDate")));
        }
        
        TypedQuery<DiscountDetail> typedQuery=entityManager.createQuery(criteriaQuery);
        
        //排序 相關!!!!!!!
        
		return typedQuery.getResultList();	
	}

	
}
