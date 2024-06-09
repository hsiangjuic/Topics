package com.ispan.hotel.service.discount;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.ispan.hotel.model.DiscountDetail;

@SpringBootTest
public class DiscountDetailServiceCYPTests {

	@Autowired
	private DiscountDetailServiceCYP discountService;
	
//	@Test
//	public void testSelect( ){
//		List<DiscountDetail> list=discountService.select(null);
//		System.out.println("list="+list);
//	}
	
	
	String dataForPageContent="	{"
			+ "    \"current\":1,"
			+ "    \"rows\":3,"
			+ "    \"dir\":false,"
			+ "    \"order\":\"ddId\","
			+ " 	   \"statuses\":[\"顯示中\"],"
			+ " \"types\":[\"專案優惠\"]"
			+ "}";
	
	@Test
	public void findByStatusAndType() {
		Page<DiscountDetail> p =discountService.findByStatusAndType(dataForPageContent);
		System.out.print(p.getTotalElements());
		System.out.print(p.getContent());
	}
}
