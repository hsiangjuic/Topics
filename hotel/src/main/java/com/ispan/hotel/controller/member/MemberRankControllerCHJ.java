package com.ispan.hotel.controller.member;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.hotel.model.MemberRank;
import com.ispan.hotel.service.member.MemberRankServiceCHJ;
import com.ispan.hotel.util.DatetimeConverter;

@RestController
@RequestMapping("/member/pages")
@CrossOrigin
public class MemberRankControllerCHJ {

    @Autowired
    private MemberRankServiceCHJ memberService;

    @GetMapping("/member/exists/{mrId}")
    public ResponseEntity<Map<String, Boolean>> checkMemberExists(@PathVariable Integer mrId) {
        boolean exists = memberService.existById(mrId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/member/name/{name}")
    public String existsByName(@PathVariable("name") String name) {
        JSONObject responseJson = new JSONObject();
        boolean exist = memberService.existsByName(name);
        if (exist) {
            responseJson.put("success", false);
            responseJson.put("message", "名稱已存在");
        } else {
            responseJson.put("success", true);
            responseJson.put("message", "名稱不存在");
        }
        return responseJson.toString();
    }

    @PostMapping("/member/find")
    public String find(@RequestBody String json) {
        JSONObject responseJson = new JSONObject();

        JSONArray array = new JSONArray();
        List<MemberRank> members = memberService.find();
        if (members != null && !members.isEmpty()) {
            for (MemberRank member : members) {
                String lastModifiedDate = DatetimeConverter.toString(member.getLastModifiedDate(),
                        "yyyy-MM-dd HH:mm:ss EEEE");
                JSONObject item = new JSONObject()
                        .put("mrId", member.getMrId())
                        .put("name", member.getName())
                        .put("neededPoints", member.getNeededPoints())
                        .put("lastModifiedDate", lastModifiedDate)
                        .put("gainPoints", member.getGainPoints())
                        .put("neededBookingDays", member.getNeededBookingDays())
                        .put("lastModifiedText", member.getLastModifiedText())
                        .put("cardPath", member.getCardPath())
                        .put("lastModifiedEmp", member.getLastModifiedEmp());

                array.put(item);
            }
        }
        responseJson.put("list", array);

        return responseJson.toString();
    }

    @DeleteMapping("/member/{pk}")
    public String remove(@PathVariable(name = "pk") Integer mrId) {
        JSONObject responseJson = new JSONObject();
        if (mrId == null) {
            responseJson.put("success", false);
            responseJson.put("message", "id是必要欄位");
        } else if (!memberService.existById(mrId)) {
            responseJson.put("success", false);
            responseJson.put("message", "id不存在");
        } else {
            if (memberService.delete(mrId)) {
                responseJson.put("success", true);
                responseJson.put("message", "刪除成功");
            } else {
                responseJson.put("success", false);
                responseJson.put("message", "刪除失敗");
            }
        }
        return responseJson.toString();
    }

    @PostMapping("/member")
    public ResponseEntity<String> memberPhoto(@RequestParam("mrId") Integer mrId, String name,
            Integer neededPoints, Integer neededBookingDays, Integer gainPoints,
            @RequestParam("photoFile") MultipartFile file) {
        try {
            if (memberService.existById(mrId)) {
                JSONObject errorJson = new JSONObject();
                errorJson.put("message", "上傳失敗");
                errorJson.put("error", "會員等級ID已經存在");
                return new ResponseEntity<>(errorJson.toString(), HttpStatus.BAD_REQUEST);
            }

            MemberRank member = new MemberRank();
            member.setMrId(mrId);
            member.setName(name);
            member.setNeededPoints(neededPoints);
            member.setNeededBookingDays(neededBookingDays);
            member.setGainPoints(gainPoints);
            member.setPhotoFile(file.getBytes());
            memberService.savePhoto(member);
            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "上傳成功");
            responseJson.put("mrId", member.getMrId());
            return new ResponseEntity<>(responseJson.toString(), HttpStatus.OK);
        } catch (IOException e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("message", "上傳失敗");
            errorJson.put("error", e.getMessage());
            return new ResponseEntity<>(errorJson.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/member/{pk}")
    public String findById(@PathVariable(name = "pk") Integer mrId) {
        JSONObject responseJson = new JSONObject();
        JSONArray array = new JSONArray();
        MemberRank member = memberService.findById(mrId);
        if (member != null) {
            String lastModifiedDate = DatetimeConverter.toString(member.getLastModifiedDate(), "yyyy-MM-dd");

            JSONObject item = new JSONObject()
                    .put("mrId", member.getMrId())
                    .put("name", member.getName())
                    .put("neededPoints", member.getNeededPoints())
                    .put("lastModifiedDate", lastModifiedDate)
                    .put("gainPoints", member.getGainPoints())
                    .put("neededBookingDays", member.getNeededBookingDays())
                    .put("lastModifiedText", member.getLastModifiedText())
                    .put("cardPath", member.getCardPath())
                    .put("lastModifiedEmp", member.getLastModifiedEmp());
            array.put(item);
        }
        responseJson.put("list", array);
        return responseJson.toString();
    }

    // 新增PutMapping修改資料的功能
    @PutMapping("/member/{mrId}")
    public ResponseEntity<String> updateMember(@PathVariable Integer mrId,
            @RequestParam String name,
            @RequestParam Integer neededPoints,
            @RequestParam Integer neededBookingDays,
            @RequestParam Integer gainPoints,

            @RequestParam(value = "photoFile", required = false) MultipartFile file) {

        try {
            // 檢查是否存在該會員等級
            if (!memberService.existById(mrId)) {

                JSONObject errorJson = new JSONObject();
                errorJson.put("message", "更新失敗");
                errorJson.put("error", "會員等級ID不存在");
                return new ResponseEntity<>(errorJson.toString(), HttpStatus.BAD_REQUEST);
            }

            MemberRank member = memberService.findById(mrId);
            if (member == null) {
                JSONObject errorJson = new JSONObject();
                errorJson.put("message", "更新失敗");
                errorJson.put("error", "找不到該會員等級");
                return new ResponseEntity<>(errorJson.toString(), HttpStatus.NOT_FOUND);
            }

            // 更新會員資訊
            member.setName(name);
            member.setNeededPoints(neededPoints);
            member.setNeededBookingDays(neededBookingDays);
            member.setGainPoints(gainPoints);

            // 如果有上傳新的會員卡照片，則更新
            if (file != null && !file.isEmpty()) {
                member.setPhotoFile(file.getBytes());
            }

            // 儲存更新後的會員資訊
            memberService.savePhoto(member);

            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "更新成功");
            responseJson.put("mrId", member.getMrId());
            return new ResponseEntity<>(responseJson.toString(), HttpStatus.OK);
        } catch (IOException e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("message", "更新失敗");
            errorJson.put("error", e.getMessage());
            return new ResponseEntity<>(errorJson.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 找圖囉!!!
    @GetMapping("/{mrId}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Integer mrId) {
        MemberRank member = memberService.findById(mrId);
        if (member != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(member.getPhotoFile(), headers, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<MemberRank>> find() {
        List<MemberRank> members = memberService.find();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

}
