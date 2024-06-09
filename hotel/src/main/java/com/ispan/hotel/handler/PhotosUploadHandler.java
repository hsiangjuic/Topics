package com.ispan.hotel.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice // 攔截 interceptor 的錯誤處理器 (error handler)
public class PhotosUploadHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String uploadSizeHandler(Model model) {

		model.addAttribute("errorMsg", "圖片太大，請重新上傳");

		return "photos/uploadPage";
	}

}
