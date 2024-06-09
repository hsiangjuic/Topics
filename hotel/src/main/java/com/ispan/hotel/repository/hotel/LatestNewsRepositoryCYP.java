package com.ispan.hotel.repository.hotel;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.LatestNews;

@Repository
public interface LatestNewsRepositoryCYP extends JpaRepository<LatestNews, Integer> {
	//內建 save(DiscountDetail d)
	//內建 findById(Integer id)
	//內建 findAll()
	//內建 count()
	//內建 deleteById(Integer id)
	//內建 delete(DiscountDetail d)
	
	//select * from LatestNews where status in (1 ,2,3) or type in (4,5,6)
	
	
	
	//沒想到峰迴路轉我以為我不需要你了結果我又用到你了....嗎?(0521抓回來用)
	@Query("from LatestNews where status in (:statuses)")
	public List<LatestNews> selectByStatus(@Param(value="statuses")List<String> statuses);
	
	//能用嗎?? order by? 可用
	@Query("from LatestNews where status in (:statuses)")
	public Page<LatestNews> selectByStatusP(@Param(value="statuses")List<String> statuses,Pageable pageable);

	//沒測過
	@Query("from LatestNews where type in (:types)")
	public Page<LatestNews> selectByTypeP(@Param(value="types")List<String> types,Pageable pageable);
	
	//依狀態和類型進行查詢
	@Query("FROM LatestNews WHERE status IN (:statusArray) AND type IN (:typeArray)")
	public Page<LatestNews> selectByStatusAndType(@Param("statusArray") List<String> statusArray, @Param("typeArray") List<String> typeArray,Pageable pageable);
	
			
	//會用到嗎?
	//public long countByStatus(String status);
	
	//List<LatestNews> findByStatusIn(List<String> statuses);
	
	//額外寫但不知道是寫這還是要寫哪
//	List<DiscountDetail> find(JSONObject obj);
//
//	long count(JSONObject obj);

}
