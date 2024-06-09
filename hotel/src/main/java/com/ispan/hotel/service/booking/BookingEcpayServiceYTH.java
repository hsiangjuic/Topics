package com.ispan.hotel.service.booking;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class BookingEcpayServiceYTH {

	public AioCheckOutALL createAioCheckOutALL(String json) {
		JSONObject obj = new JSONObject(json);
		String orderNumber = obj.isNull("orderNumber") ? null : obj.getString("orderNumber");
		String totalPaid = obj.isNull("totalPaid") ? null : obj.getString("totalPaid");
		String customerName = obj.isNull("customerName") ? null : obj.getString("customerName");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		String payType = obj.isNull("payType") ? null : obj.getString("payType");
		
		AioCheckOutALL acoa = new AioCheckOutALL();
		acoa.setMerchantID("3002607");
		acoa.setMerchantTradeNo(orderNumber.substring(0, 10)+generateOrderNumber());
		acoa.setMerchantTradeDate(formattedDateTime);
		acoa.setTotalAmount(totalPaid);
		acoa.setTradeDesc(customerName);
		acoa.setItemName(orderNumber);
		acoa.setReturnURL("http://192.168.31.160:8080/booking/book/paymentend");
		acoa.setOrderResultURL("http://192.168.31.160:8080/booking/book/paymentend");
		acoa.setNeedExtraPaidInfo("N");
		acoa.setClientBackURL("http://192.168.31.160:5173/bookingPages/bookingFinal");
		acoa.setCustomField1(orderNumber);
		acoa.setCustomField2(payType);
//		acoa.setPeriodAmount(totalPaid);
//		acoa.setPeriodType("PeriodType=D、Frequency=1、ExecTimes=5");
//		acoa.setFrequency("10");
//		acoa.setExecTimes("10");
		System.out.println("acoa = "+acoa.toString());
		
		return acoa;
	}
	
    public String generateOrderNumber() {
        // 獲取當前日期時間
        LocalDateTime now = LocalDateTime.now();
        // 格式化日期時間
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 計算日期時間的 MD5 雜湊值
        String hashValue = calculateHash(formattedDateTime);


        return hashValue.substring(0, 5);
    }

    // 計算雜湊值
    private String calculateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            // 轉換為十六進位，相較十進位增加可讀性（有英文字母）、降低資源、轉換一致以及普遍hash都是轉十六進位
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                //檢查是否只有一位數
                if (hex.length() == 1) {
                	//若僅一位數，前面塞0以確保都是兩位數
                	hexString.append('0');
                }
                //塞數字
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
