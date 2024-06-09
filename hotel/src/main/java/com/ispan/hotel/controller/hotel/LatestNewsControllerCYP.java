package com.ispan.hotel.controller.hotel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.hotel.model.LatestNews;
import com.ispan.hotel.service.hotel.LatestNewsServiceCYP;

@RestController
//@RequestMapping("")
@CrossOrigin
public class LatestNewsControllerCYP {
	@Autowired
	private LatestNewsServiceCYP latestNewsService;

	//目前有幾個欄位都沒有傳，之後要更新
	//0525測試圖片上傳功能C:\Users\pomj1\Desktop\latestNewsPhotos
    private static String UPLOADED_FOLDER = "C:/Users/pomj1/Desktop/latestNewsPhotos/";
	
	//(未使用)新增(包含圖片的版本(0525)(如果沒有選圖片會出錯，不知是前端錯還是後端錯，總之先用於原本的版本)
    @PostMapping("/latestNews2")
    public String create2(@RequestParam("json") String json,@RequestParam(name="file", required = false) MultipartFile file) {
        JSONObject responseJson = new JSONObject();
        JSONObject obj = new JSONObject(json);
    
        String title = obj.isNull("title") ? null : obj.getString("title");
        String content = obj.isNull("content") ? null : obj.getString("content");
        if( title==null || title=="" ) {
            responseJson.put("success", false);
            responseJson.put("message", "標題不能為空，請輸入標題");
        }else if(content==null || content=="" ){
        	responseJson.put("success", false);
            responseJson.put("message", "內文不能為空，請輸入內文");
        } else {
        	LatestNews latestNews = latestNewsService.create(json);
            if(latestNews==null) {
                responseJson.put("success", false);
                responseJson.put("message", "新增公告失敗");
            } else {
                responseJson.put("success", true);
                responseJson.put("message", "新增公告成功");
                
                if (file == null||file.isEmpty()) {
                	//放入預設圖片
                }

                try {
                	// 確保資料夾存在
                    File directory = new File(UPLOADED_FOLDER);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                	// 獲取文件名和擴展名
                    String originalFileName = file.getOriginalFilename();
                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String newFileName = "latestNewsPhoto_"+latestNews.getLnId()+ fileExtension;

                    // 確定文件的保存路徑
                    Path path = Paths.get(UPLOADED_FOLDER + newFileName);

                    // 將文件保存到指定位置
                    Files.write(path, file.getBytes());
                    responseJson.put("success", true);
                	responseJson.put("message","新增公告成功+圖片上傳成功" ); 

                } catch (IOException e) {
                    e.printStackTrace();
                    responseJson.put("success", false);
                	responseJson.put("message","上傳失敗"+e.getMessage() ); 
                }
            }
        }
        return responseJson.toString();
    }
	
    //(未使用)測試上傳圖片功能
    @PostMapping("/upload/{id}")
    public String uploadFile(@RequestParam("file") MultipartFile file,Integer id) {
    	JSONObject responseJson = new JSONObject();	
        if (file.isEmpty()) {
    		responseJson.put("success", false);
        	responseJson.put("message","請選擇要上傳的檔案" ); 
        }

        try {
        	// 確保資料夾存在
            File directory = new File(UPLOADED_FOLDER);
            if (!directory.exists()) {
                directory.mkdirs();
            }

        	// 獲取文件名和擴展名
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = "latestNewsPhoto_"+id+ fileExtension;

            // 確定文件的保存路徑
            Path path = Paths.get(UPLOADED_FOLDER + newFileName);

            // 將文件保存到指定位置
            Files.write(path, file.getBytes());
            responseJson.put("success", true);
        	responseJson.put("message","上傳成功" ); 

        } catch (IOException e) {
            e.printStackTrace();
            responseJson.put("success", false);
        	responseJson.put("message","上傳失敗"+e.getMessage() ); 
        }
        
        return responseJson.toString();
    }
    
    
    //測試上傳圖片功能0531
    @PostMapping("/uploadTemp")
    public String uploadFileTemp(@RequestParam("file") MultipartFile file) {
    	JSONObject responseJson = new JSONObject();	
        if (file.isEmpty()) {
    		responseJson.put("success", false);
        	responseJson.put("message","請選擇要上傳的檔案" ); 
        }

        try {
        	// 確保資料夾存在
            File directory = new File(UPLOADED_FOLDER);
            if (!directory.exists()) {
                directory.mkdirs();
            }

        	// 獲取文件名和擴展名
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = "latestNewsPhoto_Temp"+ fileExtension;

            // 確定文件的保存路徑
            Path path = Paths.get(UPLOADED_FOLDER + newFileName);

            // 將文件保存到指定位置
            file.transferTo(path);
            //Files.write(path, file.getBytes());
            responseJson.put("success", true);
        	responseJson.put("message","上傳成功" ); 

        } catch (IOException e) {
            e.printStackTrace();
            responseJson.put("success", false);
        	responseJson.put("message","上傳失敗"+e.getMessage() ); 
        }
        
        return responseJson.toString();
    }

	
	//根據Status和Type進行查詢(有分頁) (0519更新)(0520對應更新findByStatusAndType變成回傳page物件)
	@PostMapping("/latestNews/findByStatusAndType")
	public String findByStatusAndType(@RequestBody String json) {
		JSONObject responseJson = new JSONObject();		
		JSONArray array = new JSONArray();//用來裝本頁要顯示的內容
		List<LatestNews> latestNews=latestNewsService.findByStatusAndType(json).getContent();//找到本頁要顯示的資料list
		if(latestNews!=null && !latestNews.isEmpty()) {//如果有找到
			for(LatestNews one:latestNews) { //我找到latestNews裡的每個物件one，把one的資料取出後放到JSON物件item中，再把item放進JSONArray array裡
				JSONObject item = new JSONObject()
					.put("lnId", one.getLnId())
					.put("title",one.getTitle())
					.put("startDate",one.getStartDate())
					.put("endDate", one.getEndDate())
					.put("content",one.getContent())
					.put("image", one.getImage())
					.put("status", one.getStatus())
					.put("type",one.getType())
					.put("lastModifiedDate", one.getLastModifiedDate())
					.put("lastModifiedEmp",one.getLastModifiedEmp());
				
				//0519更新：1.防null pointer所以fk有關的額外用if包起來
				//2.改用hotelName，原本的hId也傳但應該用不到
				if (one.getHotel() != null) {
		            item.put("hId", one.getHotel().gethId())  
						.put("hotelName",one.getHotel().getName()); 
		        }
				
				array.put(item);
			}
		}
		responseJson.put("list", array);//把array放進responseJson	
		
		responseJson.put("count", latestNewsService.findByStatusAndType(json).getTotalElements());  //資料總數
		responseJson.put("pages", latestNewsService.findByStatusAndType(json).getTotalPages()); 	//總頁數
		
		return responseJson.toString();
	}

	//暫停顯示/恢復正常(不變)(0521用到了)
	@PutMapping("/latestNews/changePauseStatus/{pk}")
	public String changePauseStatus(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();
		
		if(id==null) {                           //沒給id的話
          responseJson.put("success", false);
          responseJson.put("message", "id是必要欄位");
      } else if(!latestNewsService.existsById(id)) { //id不存在的話
          responseJson.put("success", false);
          responseJson.put("message", "id不存在");
      } else {									//有給id且id存在的話 	  
    	  if(latestNewsService.changePauseStatus(id)) {
    		  responseJson.put("success", true);
    		  responseJson.put("message", "切換狀態成功");
    	  }else {
    		  responseJson.put("success", false);
    		  responseJson.put("message", "無法更改狀態");
    	  } 
      }	
		return responseJson.toString();
	}
	
	//(0521)資料狀態更新(單筆)(用不到
	@PutMapping("/latestNews/statusUpdate/{pk}")
	public String singleStatusUpdate(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();
		
		LatestNews data = latestNewsService.findById(id);
		latestNewsService.UpdateStatus(data);
		
		responseJson.put("success", true);
		responseJson.put("message","單筆資料狀態更新完畢" ); 
		
		return responseJson.toString();
	}

	//(0521)資料狀態更新(多筆)(
	@PutMapping("/latestNews/statusUpdate")
	public String StatusUpdate() { 
		JSONObject responseJson = new JSONObject();	
		
		List<String> statuses =new ArrayList<String>();
		statuses.add("顯示中");
		statuses.add("尚未開始");
		
		//找到需要更新的資料list
		List<LatestNews> datasNeedToUpdate=latestNewsService.findByStatus(statuses);

		//對每一筆資料進行判定並更新
		for(LatestNews data:datasNeedToUpdate) {
			latestNewsService.UpdateStatus(data);
		}
		
		responseJson.put("success", true);
		responseJson.put("message","資料狀態更新完畢" ); 
		
		return responseJson.toString();
	}
	
	//查詢(單筆)(0519更新)
	@GetMapping("/latestNews/{pk}")
    public String findById(@PathVariable(name = "pk") Integer id) {
        JSONObject responseJson = new JSONObject();
        JSONArray array = new JSONArray();
        if(latestNewsService.existsById(id)) { //如果有查到資料
        	LatestNews latestNews = latestNewsService.findById(id);
        	JSONObject item = new JSONObject()
					.put("lnId", latestNews.getLnId())
					.put("title",latestNews.getTitle())
					.put("startDate",latestNews.getStartDate())
					.put("endDate", latestNews.getEndDate())
					.put("content",latestNews.getContent())
					.put("image", latestNews.getImage())
					.put("status", latestNews.getStatus())
					.put("type",latestNews.getType())
					.put("lastModifiedDate", latestNews.getLastModifiedDate())
					.put("lastModifiedEmp",latestNews.getLastModifiedEmp());
        	
        	//更新：同上
			if (latestNews.getHotel() != null) {
	            item.put("hId", latestNews.getHotel().gethId())  
					.put("hotelName",latestNews.getHotel().getName()); 
	        }
			array.put(item);
			responseJson.put("list", array);
        }else {
        	responseJson.put("success", false);
            responseJson.put("message", "此id查無資料");
        }
        
        
        return responseJson.toString();
    }
	
	//新增(0519更新)
    @PostMapping("/latestNews")
    public String create(@RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        JSONObject obj = new JSONObject(json);
        
        //新增公告：驗證項目
        //lnId：自動生成不需驗證
        //hId：旅館不能為null或空字串。應自動帶入登入者有權限的hotel的id
        //title：標題不能為null或空字串(後端驗證OK)
        //content：內容不能為null或空字串(後端驗證OK)
        //startDate：未輸入起始日則起始日為今天(前端驗證OK)
        //endDate ：未輸入結束日則為null，不能比startDate還早(日期比較OK，TODO：NULL的狀況(永久))
        //image：不能為null? 未設定則依類型帶入預設圖片? 或乾脆允許不設圖片?圖片的長寬高要限制?
        //status：新增時依今天的時間判斷是[尚未開始]或[顯示中] (前端處理OK)
        //type：預設為[一般公告]，如果是null或空字串的話都改為預設值[一般公告](前端處理OK)
        //lastModifiedDate：(尚未處理)今天現在
        //lastModifiedEmp：(尚未處理)此刻登入者
        
        String title = obj.isNull("title") ? null : obj.getString("title");
        String content = obj.isNull("content") ? null : obj.getString("content");
        if( title==null || title=="" ) {
            responseJson.put("success", false);
            responseJson.put("message", "標題不能為空，請輸入標題");
        }else if(content==null || content=="" ){
        	responseJson.put("success", false);
            responseJson.put("message", "內文不能為空，請輸入內文");
        } else {
        	LatestNews latestNews = latestNewsService.create(json);
            if(latestNews==null) {
                responseJson.put("success", false);
                responseJson.put("message", "新增公告失敗");
            } else {
                responseJson.put("success", true);
                responseJson.put("message", "新增公告成功");
            }
        }
        return responseJson.toString();
    }
	
	//修改(不變)
    @PutMapping("/latestNews/{pk}")
    public String modify(@PathVariable(name = "pk") Integer id, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        JSONObject modifyObj = new JSONObject(json);
        Integer lnId = modifyObj.isNull("lnId") ? null : modifyObj.getInt("lnId");

        //修改公告：驗證項目
        //lnId：編號不能為null或空字串，否則抓不到要修改哪一筆
        //hId：旅館不能為null或空字串。應自動帶入登入者有權限的hotel的id。
        //title：標題不能為null或空字串
        //content：內容不能為null或空字串
        //startDate：未輸入起始日則起始日為今天
        //endDate ：未輸入結束日則為null，不能比startDate還早
        //image：...
        //status：...
        //type：...
        //lastModifiedDate：今天現在
        
        if(id==null) {
            responseJson.put("success", false);
            responseJson.put("message", "id是必要欄位");
        } else if (!id.equals(lnId)) { //如果兩者不相等
        	responseJson.put("success", false);
            responseJson.put("message", "@PathVariable和json內lnId不同，請重新確認");
        } else if(!latestNewsService.existsById(id)) {
            responseJson.put("success", false);
            responseJson.put("message", "id不存在");
        } else {
        	LatestNews latestNews = latestNewsService.modify(json);
            if(latestNews==null) {
                responseJson.put("success", false);
                responseJson.put("message", "修改失敗");
            } else {
                responseJson.put("success", true);
                responseJson.put("message", "修改成功");
            }
        }
        return responseJson.toString();
    }
    
	//刪除(不變)
	@DeleteMapping("/latestNews/{pk}")
	public String remove(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();
		if(id==null) {                           //沒給id的話
          responseJson.put("success", false);
          responseJson.put("message", "id是必要欄位");
      } else if(!latestNewsService.existsById(id)) { //id不存在的話
          responseJson.put("success", false);
          responseJson.put("message", "id不存在");
      } else {									//有給id且id存在的話
          if(latestNewsService.deleteById(id)) { //deleteById成功回傳true
              responseJson.put("success", true);
              responseJson.put("message", "刪除成功");
          } else {                
              responseJson.put("success", false); //deleteById 失敗回傳false
              responseJson.put("message", "刪除失敗");
          }
      }
		
		return responseJson.toString();
	}
	

//以下完全用不到(還在摸索的時候寫的東西)-------------------------------------------------------
	
	//查詢(全部/有分頁)(用不到了)(傳hid版本，勿用)
		@PostMapping("/latestNews/findAll")
	public String findAll(@RequestBody String json) {
			JSONObject responseJson = new JSONObject(); //用來裝本方法回傳內容的JSON物件
			
			JSONArray array = new JSONArray();//用來裝本頁要顯示的內容
			List<LatestNews> latestNews=latestNewsService.findPageContent(json);//找到本頁要顯示的資料list
			if(latestNews!=null && !latestNews.isEmpty()) {//如果有找到
				for(LatestNews one:latestNews) { //我找到latestNews裡的每個物件one，把one的資料取出後放到JSON物件item中，再把item放進JSONArray array裡
					JSONObject item = new JSONObject()
						.put("lnId", one.getLnId())
						.put("hId", one.getHotel().gethId())  //如果是NULL會出問題，在某個地方(前端?)要檢查是否為NULL.
						.put("title",one.getTitle())
						.put("startDate",one.getStartDate())
						.put("endDate", one.getEndDate())
						.put("content",one.getContent())
						.put("image", one.getImage())
						.put("status", one.getStatus())
						.put("type",one.getType())
						.put("lastModifiedDate", one.getLastModifiedDate())
						.put("lastModifiedEmp",one.getLastModifiedEmp());
					array.put(item);
				}
			}
			responseJson.put("list", array);//把array放進responseJson
			
//			long count = productService.count(json);
//	      responseJson.put("count", count);
			
			return responseJson.toString();
		}
}


