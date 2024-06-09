package com.ispan.hotel.controller.booking;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.RoomPhoto;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.service.booking.RoomPhotoServiceYTH;
import com.ispan.hotel.service.booking.RoomTypeServiceYTH;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class RoomPhotoControllerYTH {
    @Autowired
    private RoomPhotoServiceYTH photoServiceYTH;
    
    @Autowired
    private RoomTypeServiceYTH roomTypeServiceYTH;

    @GetMapping(
		path = "/detail/{rtId}",
		produces = {MediaType.IMAGE_JPEG_VALUE}
	)
    public @ResponseBody byte[] findPhotoByPhotoId(@PathVariable(name = "rtId") Integer rtId) {
    	RoomType roomType = roomTypeServiceYTH.findById(rtId);
    	List<RoomPhoto> roomPhotos =  photoServiceYTH.findByRoomType(roomType);
    	RoomPhoto photo = new RoomPhoto();
		for(RoomPhoto rp : roomPhotos) {
			String pd = rp.getPhotoDescription();
			if(pd.substring(pd.length() - 3).equals("照片1")) {
				photo = rp;
				break;
			}
		}
    	
        byte[] result = this.photo;
        if(photo!=null) {
            result = photo.getPhotoFile();
        }
        return result;
    }

    private byte[] photo = null;
    @PostConstruct
    public void initialize() throws IOException {
		byte[] buffer = new byte[8192];

		ClassLoader classLoader = getClass().getClassLoader();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedInputStream is = new BufferedInputStream(
				classLoader.getResourceAsStream("static/images/no-image.jpg"));
		int len = is.read(buffer);
		while(len!=-1) {
			os.write(buffer, 0, len);
			len = is.read(buffer);
		}
		is.close();
		this.photo = os.toByteArray();
    }
	

}
