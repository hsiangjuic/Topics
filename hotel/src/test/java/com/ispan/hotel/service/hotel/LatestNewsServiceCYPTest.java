package com.ispan.hotel.service.hotel;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.hotel.model.LatestNews;

@SpringBootTest
public class LatestNewsServiceCYPTest {
	
	@Autowired
	private LatestNewsServiceCYP latestNewsService;
	
//	@Test
//	public void testInsert( ){
//		LatestNews bean =new LatestNews();
//		bean.setLnId(1);
//		//bean.setHotel(????????);
//		bean.setTitle("反詐騙公告");
//		bean.setStartDate(DatetimeConverter.parseLocalDate("2024-05-01", "yyyy-MM-dd"));
//		bean.setEndDate(DatetimeConverter.parseLocalDate("2024-05-31", "yyyy-MM-dd"));
//		bean.setContent("近期詐騙猖獗，不肖集團冒用旅店名義詐騙！\r\n"
//				+ "提醒您如接獲可疑電話(886、+號 開頭)，假藉旅店名義告知：\r\n"
//				+ "任何款項問題或要求核對個資、及操作轉帳動作，都是詐騙行為，請勿理會。\r\n"
//				+ "如有問題儘速致電防詐專線165，或掛掉電話重新撥打詢問\r\n"
//				+ "(切勿直接回撥手機來電號碼)");
//		bean.setImage(null);
//		bean.setType("一般公告");
//		bean.setStatus("顯示中");
//		
//		LatestNews news1=latestNewsService.insert(bean);
//	}

//	@Test
//	public void testUpdate( ){
//		LatestNews bean =new LatestNews();
//		bean.setLnId(2);
//		//bean.setHotel(????????);
//		bean.setTitle("反詐騙公告(修改版)");
//		bean.setStartDate(DatetimeConverter.parseLocalDate("2024-05-01", "yyyy-MM-dd"));
//		bean.setEndDate(DatetimeConverter.parseLocalDate("2024-05-31", "yyyy-MM-dd"));
//		bean.setContent("近期詐騙猖獗，不肖集團冒用旅店名義詐騙！\r\n"
//				+ "提醒您如接獲可疑電話(886、+號 開頭)，假藉旅店名義告知：\r\n"
//				+ "任何款項問題或要求核對個資、及操作轉帳動作，都是詐騙行為，請勿理會。\r\n"
//				+ "如有問題儘速致電防詐專線165，或掛掉電話重新撥打詢問\r\n"
//				+ "(切勿直接回撥手機來電號碼)");
//		bean.setImage(null);
//		bean.setType("一般公告");
//		bean.setStatus("顯示中");
//		
//		LatestNews news1=latestNewsService.update(bean);
//	}
//	private String data="{"
//			+ "  \"hId\": 1,"
//			+ "  \"title\": \"666測試標題\","
//			+ "  \"startDate\": \"2024-05-01\","
//			+ "  \"endDate\": \"2024-05-31\","
//			+ "  \"content\": \"這是一個測試內容\","
//			+ "  \"image\": \"http://example.com/image.jpg\","
//			+ "  \"status\": \"active\","
//			+ "  \"type\": \"測試類型\" "
//			+ "}";

//	@Test
//	public void testCreate() {
//		
//		latestNewsService.create(data);
//		System.out.print("測試用JSON新增資料");
//		
//	}
	
//	private String dataForUpdate="{"
//			+ " \"lnId\":2," //修改lnId為2的資料
//			+ "  \"hId\": 1,"
//			+ "  \"title\": \"666測試標題\","
//			+ "  \"startDate\": \"2024-05-01\","
//			+ "  \"endDate\": \"2024-09-31\","
//			+ "  \"content\": \"修改八餔八餔\","
//			+ "  \"image\": \"http://example.com/image.jpg\","
//			+ "  \"status\": \"active\","
//			+ "  \"type\": \"測試類型\" "
//			+ "}";
	
//	@Test
//	public void testModify() {
//		
//		latestNewsService.modify(dataForUpdate);
//		System.out.print("測試用JSON修改資料");
//		
//	}
	
	String dataForPageContent="	{"
			+ "    \"start\":2,"
			+ "    \"rows\":3,"
			+ "    \"dir\":false,"
			+ "    \"order\":\"lnId\","
			//+ "    \"name\":\"a\""
			+ "}";
	
	@Test
	public void testFindAll(){
		List<LatestNews> list=latestNewsService.findPageContent(dataForPageContent);
		//測試
		for(LatestNews item:list) {
			System.out.println(item);
		}
        
	}
	
	

}
