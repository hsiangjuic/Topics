//package com.ispan.hotel.util;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class DatetimeConverterTests {
//	
//	
////	@Test
//	public void testToString() {
//		LocalDate localdate = LocalDate.now();
//		LocalDateTime localdatetime = LocalDateTime.now();
//		System.out.println("localdate= "+DatetimeConverter.toString(localdate, "yyyy-MM-dd"));
//		System.out.println("localdatetime= "+DatetimeConverter.toString(localdatetime, "yyyy-MM-dd-HH-mm-ss"));
//	}
//	
//	@Test
//	public void testParse() {
//		String localdate = "2024-05-09";
//		String localdatetime = "2024-05-09-16-24-51";
//		
//		System.out.println("localdate= "+DatetimeConverter.parseLocalDate(localdate, "yyyy-MM-dd"));
//		System.out.println("localdatetime= "+DatetimeConverter.parseLocalDateTime(localdatetime, "yyyy-MM-dd-HH-mm-ss"));
//	}
//}
