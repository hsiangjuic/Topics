package com.ispan.hotel.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DatetimeConverter {
	public static String toString(Date datetime, String format) {
		String result = "";
		try {
			if (datetime != null) {
				result = new SimpleDateFormat(format).format(datetime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String toString(LocalDate datetime, String format) {
		String result = "";
		try {
			if (datetime != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                result = datetime.format(formatter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String toString(LocalDateTime datetime, String format) {
		String result = "";
		try {
			if (datetime != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                result = datetime.format(formatter);			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Date parse(String datetime, String format) {
		Date result = new Date();
		try {
			result = new SimpleDateFormat(format).parse(datetime);
		} catch (Exception e) {
			result = new Date();
			e.printStackTrace();
		}
		return result;
	}

	public static LocalDate parseLocalDate(String datetime, String format) {
		Date date = new Date();
		try {
			date = new SimpleDateFormat(format).parse(datetime);
		} catch (Exception e) {
			date = new Date();
			e.printStackTrace();
		}
		
        LocalDate result = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return result;
	}
	
	public static LocalDateTime parseLocalDateTime(String datetime, String format) {
		Date date = new Date();
		try {
			date = new SimpleDateFormat(format).parse(datetime);
		} catch (Exception e) {
			date = new Date();
			e.printStackTrace();
		}
		
        LocalDateTime result = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		return result;
	}
	
	public static LocalDateTime convertLocalDateTimeFormat(LocalDateTime datetime, String format) {
        LocalDateTime result = null;
        try {
            if (datetime != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                String formattedString = datetime.format(formatter);
                result = LocalDateTime.parse(formattedString, formatter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
	
}
