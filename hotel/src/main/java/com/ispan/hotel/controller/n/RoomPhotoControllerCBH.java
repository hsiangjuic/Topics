package com.ispan.hotel.controller.n;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.ispan.hotel.model.RoomPhoto;
import com.ispan.hotel.repository.n.RoomPhotoRepositoryCBH;
import com.ispan.hotel.service.n.RoomPhotoServiceCBH;
import com.ispan.hotel.util.DatetimeConverter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;






@RestController
@CrossOrigin
public class RoomPhotoControllerCBH {
	
	@Autowired
	private RoomPhotoServiceCBH photoService;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	
	
	@Autowired
	private RoomPhotoRepositoryCBH photoRepository;
	
	// 圖片檔案太大錯誤處理 MVC DAY 3-1 1:42:46
	
	
	// 上傳圖片
	@PostMapping("/photos/insertphoto")
	public String insertPhotoPost(
		@RequestParam String photoname, 
		@RequestParam(required = false) MultipartFile photoFile,
		@RequestParam String photoDescription,
		@RequestParam(required = false) String roomName // required = false 房型名稱可可以沒有
	) throws IOException {
		
		// noimage 的 byte[], fileData就是要傳給 photo 的 byte[]
		String filePath="C:\\noimg\\noimage.jpg";
		File file = new File(filePath);
		byte[] fileData = new byte[(int) file.length()];
		// 這裡用到 autocloseable 的概念, 所以不用另外寫 close
		/*	
		
		並沒有對 noimg 圖片異常進行處理
		
		try (FileInputStream fis = new FileInputStream(file)) {
		     fis.read(fileData);
		   } catch (IOException e) {
		   e.printStackTrace();
		   return null;
		}
		
		*/
		if (file.exists() && file.isFile()) {
            try {
                fileData = new byte[(int) file.length()];
                // 使用 try-with-resources 确保自动关闭资源
                try (FileInputStream fis = new FileInputStream(file)) {
                    int bytesRead = fis.read(fileData);
                    if (bytesRead != fileData.length) {
                        throw new IOException("Failed to read the entire file");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                fileData = null; // 發生 IOException 時將 fileData 設為空值
            }
        } else {
        	// 文件不存在或不是文件時將 fileData 設為空值
            fileData = null;
        }
		// ------------------------------------------------------
		
		
		JSONObject responseJson = new JSONObject();
		RoomPhoto formDataImg = null;
		if(photoFile == null) {
			
			formDataImg = photoService.fromDatainsertWhithOutImg(photoname, fileData, photoDescription, roomName);
			
		} else {
			// 小問題加上這個判斷後前端的房型資料框一定要輸入
			formDataImg = photoService.fromDatainsertWhithImg(photoname, photoFile, photoDescription, roomName);
			
		}
		
		photoService.savePhoto(formDataImg);
		
		if(formDataImg == null) {
			
			responseJson.put("message", "新增失敗");
			
		} else {
			responseJson.put("message", "新增成功");
			return responseJson.toString();
		}
			
			
			
		return null;
			
	}
	
	// 修改圖片內容
	@PutMapping("/photos/updatephoto")
	public String updatePhotoPost(
		@RequestParam Integer rpId,
		@RequestParam String photoname, 
		@RequestParam(required = false) MultipartFile photoFile,
		@RequestParam String photoDescription,
		@RequestParam(required = false) String roomName // required = false 房型名稱可可以沒有
	) throws IOException {
		
		// noimage 的 byte[], fileData就是要傳給 photo 的 byte[]
		String filePath="C:\\noimg\\noimage.jpg";
		//C:\noimg
		File file = new File(filePath);
		
		if (!file.exists()) {
            System.err.println("File not found: " + filePath);
        }
		
		byte[] fileData = new byte[(int) file.length()];
		// 這裡用到 autocloseable 的概念, 所以不用另外寫 close
		/*
		try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        */
		if (file.exists() && file.isFile()) {
            try {
                fileData = new byte[(int) file.length()];
                // 使用 try-with-resources 确保自动关闭资源
                try (FileInputStream fis = new FileInputStream(file)) {
                    int bytesRead = fis.read(fileData);
                    if (bytesRead != fileData.length) {
                        throw new IOException("Failed to read the entire file");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                fileData = null; // 發生 IOException 時將 fileData 設為空值
            }
        } else {
        	// 文件不存在或不是文件時將 fileData 設為空值
            fileData = null;
        }
		// ------------------------------------------------------
		
		JSONObject responseJson = new JSONObject();
		RoomPhoto updateImg = null;
		/*
		如果使用者案開啟修改後沒有選擇上傳圖片的檔案就使用 noimage 的 byte[] 更新資料.
		*/
		if(photoFile != null) {
			
			updateImg = photoService.fromDataUpdate(rpId, photoname, photoFile, photoDescription, roomName);
			
		} else {
			
			updateImg = photoService.fromDataUpdateWithoutImg(rpId, photoname, fileData, photoDescription, roomName);
		}
		
		 

		if(updateImg == null) {
			
			responseJson.put("message", "修改失敗");
			
		} else {
			responseJson.put("message", "新增成功");
			
		}
			
		return responseJson.toString();	
			
		
			
	}
	
	
	
	// 找資料庫的圖片 spring mvc day 3-1 
	@GetMapping("/photos/findbyid")
	public ResponseEntity<byte[]> downloadImage(@RequestParam Integer rpId){
		
		// 通過 id 找到該筆資料物件
		RoomPhoto gp = photoService.findPhotoById(rpId);
		
		// 取得這筆物件資料的 byte[]
		byte[] photoByteFile = gp.getPhotoFile();
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		
		return new ResponseEntity<byte[]>(photoByteFile, headers, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/photo/deleteById")
	public void deletePhoto(@RequestParam("rpId") Integer rpId) {
		photoService.deletePhoto(rpId);
	}
	 
	
	@GetMapping("/photos/findbyid/entity")
	public String findByIdEntity (@RequestParam("rpId") Integer rpId) {
		
		JSONObject responseJson = new JSONObject();
        JSONArray array = new JSONArray();
		RoomPhoto find = photoService.findPhotoById(rpId);
		if(find != null) {
			JSONObject item = new JSONObject()
                    .put("rpId", find.getRpId()) 
                    .put("photoname", find.getPhotoname())
                    .put("photoDescription", find.getPhotoDescription())
					.put("RoomtypeName", find.getRoomType().getRoomName());
               
            array.put(item);
		}
		
		responseJson.put("list", array);
		return responseJson.toString();
		
	}
	

	
	
//	// 圖片分頁
//	@Transactional
//	@PostMapping("/photos/pageQueryByExample")
//	public Page<RoomPhoto> pageQueryByExample(@RequestBody String json) {
//		
//		JSONObject pageJson = new JSONObject(json);
//		Integer pageNumber = pageJson.getInt("page");
//		Integer rows = pageJson.getInt("rows");
//		String roomName = pageJson.getString("criteriaQueryName");
//		
//		
//		// 查詢條件
//		RoomPhoto photo = new RoomPhoto();
//		// 如果房型選擇的 put 有選擇並且選擇的不是"未選取任何資料", 就把選擇的值加到條件中
//		if(roomName != null && !roomName.equals("未選取任何房型")) {
//			photo.setPhotoname(roomName);
//		}
//		
//		
//		// 匹配器
//		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
//		
//		// 通過 Example 建構查詢條件
//		Example<RoomPhoto> example = Example.of(photo, matcher);
//		
//		Pageable pgb = PageRequest.of((pageNumber-1), rows, Sort.Direction.ASC, "rpId");
//		Page<RoomPhoto> pageQueryByExample = photoRepository.findAll(example, pgb);
//		
//		return pageQueryByExample;
//		
//		
//	}
	
	
	// 
	@GetMapping("/photos/findAll")
	public List<RoomPhoto> byFindAll() {
		
		return photoService.findAllPhotodata();
		
	}
	
	
	@PostMapping("/roomphoto/find")
    public String find(@RequestBody String json) {
        JSONObject responseJson = new JSONObject();

        JSONArray array = new JSONArray();
        List<RoomPhoto> roomPhotoList = photoService.find(json);
        
        
        if(roomPhotoList!=null && !roomPhotoList.isEmpty()) {
            for(RoomPhoto rp : roomPhotoList) {
                JSONObject item = new JSONObject();
                
                item.put("rpId", rp.getRpId());
                if(rp.getPhotoname() != null) {
                	item.put("photoname", rp.getPhotoname());
                }
                if(rp.getPhotoDescription() != null) {
                	item.put("photoDescription", rp.getPhotoDescription());
                }
                
                item.put("addedDate", DatetimeConverter.toString(rp.getAddedDate(), "yyyy-MM-dd HH:mm:ss"));
                
                if(rp.getRoomType().getRoomName() != null) {
                	item.put("RoomtypeName", rp.getRoomType().getRoomName());
                }
                
                if(rp.getRoomType().getFeatureTittle() != null){
                	item.put("RoomtypefeatureTittle", rp.getRoomType().getFeatureTittle());
                }
                
                if(rp.getRoomType().getFeatureTittleContent() != null) {
                	item.put("RoomtypefeatureTittleContent", rp.getRoomType().getFeatureTittleContent());
                }
                
                array.put(item);
            }
        }
        responseJson.put("list", array);

        // 根據目前條件要回傳幾筆資料, 要傳給前端當分頁參數的
        long count = photoService.count(json);
        responseJson.put("count", count);

        return responseJson.toString();
    }
	
	// 前端用取得房型照片, 豪華客房單人房
	@GetMapping("/roomphoto/findsuperiorroom")
    public String findSuperiorRoom() {
        
		
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("豪華客房單人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
			
			rpId = roomphotolist.get(i).getRpId();
			
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
		
		return responseJson.toString();
		
    }
	
	// 前端用取得房型照片, 豪華客房兩人房
	@GetMapping("/roomphoto/findsuperiorroom2")
	public String findSuperiorRoom2() {
	        
			
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("豪華客房兩人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
				
			rpId = roomphotolist.get(i).getRpId();
				
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
			
		return responseJson.toString();
			
	 }
	
	// 前端用取得房型照片, 豪華客房四人房
	@GetMapping("/roomphoto/findsuperiorroom4")
	public String findSuperiorRoom4() {
		        
				
			List<RoomPhoto> roomphotolist = photoService.findbyroomName("豪華客房兩人房");
			System.out.println(roomphotolist.get(0));
			JSONObject responseJson = new JSONObject();
			int rpId = 0;
			for(int i = 0; i < roomphotolist.size() ; i++) {
					
				rpId = roomphotolist.get(i).getRpId();
					
				String frojson = "rpIdList" + (i + 1);
				responseJson.put(frojson, rpId);
				System.out.println(rpId);
			}
				
			return responseJson.toString();
				
	}
	
	// 前端尊榮客房單人房
	@GetMapping("/roomphoto/deluxe1")
	public String findDeluxeRoom1() {
			        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("尊榮客房單人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
						
			rpId = roomphotolist.get(i).getRpId();
						
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
					
		return responseJson.toString();
					
	}		
		
	// 前端尊榮客房兩人房
	@GetMapping("/roomphoto/deluxe2")
	public String findDeluxeRoom2() {
				        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("尊榮客房兩人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
							
			rpId = roomphotolist.get(i).getRpId();
							
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
						
		return responseJson.toString();
						
	}
	
	// 前端尊榮客房四人房
	@GetMapping("/roomphoto/deluxe4")
	public String findDeluxeRoom4() {
					        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("尊榮客房四人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
								
			rpId = roomphotolist.get(i).getRpId();
								
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
							
		return responseJson.toString();
							
	}
	
	// 前端景觀尊榮客房單人房
	@GetMapping("/roomphoto/deluxeviewroom1")
	public String findDeluxeViewRoom1() {
						        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("景觀尊榮客房單人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
									
			rpId = roomphotolist.get(i).getRpId();
									
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
								
		return responseJson.toString();
								
	}
	
	// 前端景觀尊榮客房兩人房
	@GetMapping("/roomphoto/deluxeviewroom2")
	public String findDeluxeViewRoom2() {
							        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("景觀尊榮客房兩人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
										
			rpId = roomphotolist.get(i).getRpId();
										
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
									
		return responseJson.toString();
									
	}
	
	// 前端景觀尊榮客房四人房
	@GetMapping("/roomphoto/deluxeviewroom4")
	public String findDeluxeViewRoom4() {
								        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("景觀尊榮客房四人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
											
			rpId = roomphotolist.get(i).getRpId();
											
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
										
		return responseJson.toString();
										
	}
	
	// 前端特選尊榮家庭客房四人房
	@GetMapping("/roomphoto/deluxefamilyroom4")
	public String findDeluxeFamilyRoom4() {
									        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("特選尊榮家庭客房四人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
												
			rpId = roomphotolist.get(i).getRpId();
												
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
											
		return responseJson.toString();
											
	}
	
	// 前端超豪華客房單人房
	@GetMapping("/roomphoto/premierroomtype1")
	public String findPremierRoom1() {
										        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("超豪華客房單人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
													
			rpId = roomphotolist.get(i).getRpId();
													
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
												
		return responseJson.toString();
												
	}
	
	// 前端超豪華客房兩人房
	@GetMapping("/roomphoto/premierroomtype2")
	public String findPremierRoom2() {
											        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("超豪華客房兩人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
														
			rpId = roomphotolist.get(i).getRpId();
														
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
													
		return responseJson.toString();
													
	}
	
	// 前端超豪華客房四人房
	@GetMapping("/roomphoto/premierroomtype4")
	public String findPremierRoom4() {
												        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("超豪華客房四人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
															
			rpId = roomphotolist.get(i).getRpId();
															
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
														
		return responseJson.toString();
														
	}
	
	// 前端豪華閣豪華客房單人房
	@GetMapping("/roomphoto/horizonsuperiorroomtype1")
	public String findHorizonSuperiorRoomRoomType1() {
													        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("豪華閣豪華客房單人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																
			rpId = roomphotolist.get(i).getRpId();
																
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
															
		return responseJson.toString();
															
	}
	
	// 前端豪華閣豪華客房兩人房
	@GetMapping("/roomphoto/horizonsuperiorroomtype2")
	public String findHorizonSuperiorRoomRoomType2() {
														        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("豪華閣豪華客房兩人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																	
			rpId = roomphotolist.get(i).getRpId();
																	
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																
		return responseJson.toString();
																
	}
	
	// 前端豪華閣豪華客房四人房
	@GetMapping("/roomphoto/horizonsuperiorroomtype4")
	public String findHorizonSuperiorRoomRoomType4() {
															        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("豪華閣豪華客房四人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																		
			rpId = roomphotolist.get(i).getRpId();
																		
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																	
		return responseJson.toString();
																	
	}
	
	// 前端豪華閣尊榮客房單人房
	@GetMapping("/roomphoto/horizondeluxeroomtype1")
	public String findHorizonDeluxeRoom1() {
																        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("豪華閣尊榮客房單人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																			
			rpId = roomphotolist.get(i).getRpId();
																			
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
				System.out.println(rpId);
		}
																		
		return responseJson.toString();
																		
	}
	
	// 前端豪華閣尊榮客房兩人房
		@GetMapping("/roomphoto/horizondeluxeroomtype2")
		public String findHorizonDeluxeRoom2() {
																	        
			List<RoomPhoto> roomphotolist = photoService.findbyroomName("豪華閣尊榮客房兩人房");
			System.out.println(roomphotolist.get(0));
			JSONObject responseJson = new JSONObject();
			int rpId = 0;
			for(int i = 0; i < roomphotolist.size() ; i++) {
																				
				rpId = roomphotolist.get(i).getRpId();
																				
				String frojson = "rpIdList" + (i + 1);
				responseJson.put(frojson, rpId);
					System.out.println(rpId);
			}
																			
			return responseJson.toString();
																			
		}
	
	// 豪華閣尊榮客房四人房
	@GetMapping("/roomphoto/horizondeluxeroomtype4")
	public String findHorizonDeluxeRoom4() {
																	        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("豪華閣尊榮客房四人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																				
			rpId = roomphotolist.get(i).getRpId();
																				
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																			
		return responseJson.toString();
																			
	}
	
	// 景觀超豪華客房單人房
	@GetMapping("/roomphoto/premierviewroomroomtype1")
	public String findPremierViewRoom1() {
																		        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("景觀超豪華客房單人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																					
			rpId = roomphotolist.get(i).getRpId();
																					
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																				
		return responseJson.toString();
																				
	}
	
	// 景觀超豪華客房兩人房
	@GetMapping("/roomphoto/premierviewroomroomtype2")
	public String findPremierViewRoom2() {
																			        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("景觀超豪華客房兩人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																						
			rpId = roomphotolist.get(i).getRpId();
																						
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																					
		return responseJson.toString();
																					
	}
	
	// 景觀超豪華客房四人房
	@GetMapping("/roomphoto/premierviewroomroomtype4")
	public String findPremierViewRoom4() {
																				        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("景觀超豪華客房四人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																							
			rpId = roomphotolist.get(i).getRpId();
																							
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																						
		return responseJson.toString();
																						
	}
	
	// 特級套房單人房
	@GetMapping("/roomphoto/specialtysuiteroom1")
	public String findSpecialtySuiteRoom1() {
																					        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("特級套房單人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																								
			rpId = roomphotolist.get(i).getRpId();
																								
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																							
		return responseJson.toString();
																							
	}
	
	// 特級套房兩人房
	@GetMapping("/roomphoto/specialtysuiteroom2")
	public String findSpecialtySuiteRoom2() {
																						        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("特級套房兩人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																									
			rpId = roomphotolist.get(i).getRpId();
																									
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																								
		return responseJson.toString();
																								
	}
	
	// 特級套房四人房
	@GetMapping("/roomphoto/specialtysuiteroom4")
	public String findSpecialtySuiteRoom4() {
																							        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("特級套房四人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																										
			rpId = roomphotolist.get(i).getRpId();
																										
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																									
	return responseJson.toString();
																									
	}
	
	// 雅仕套房兩人房
	@GetMapping("/roomphoto/plazasuiteroom2")
	public String findPlazaSuiteRoom2() {
																								        
		List<RoomPhoto> roomphotolist = photoService.findbyroomName("雅仕套房兩人房");
		System.out.println(roomphotolist.get(0));
		JSONObject responseJson = new JSONObject();
		int rpId = 0;
		for(int i = 0; i < roomphotolist.size() ; i++) {
																											
			rpId = roomphotolist.get(i).getRpId();
																											
			String frojson = "rpIdList" + (i + 1);
			responseJson.put(frojson, rpId);
			System.out.println(rpId);
		}
																										
		return responseJson.toString();
																										
	}
	
	
	
	
	
	
	
	
	

}
