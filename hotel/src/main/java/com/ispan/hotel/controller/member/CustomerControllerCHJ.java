package com.ispan.hotel.controller.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.MemberRank;
import com.ispan.hotel.service.member.CustomerServiceCHJ;
import com.ispan.hotel.util.DatetimeConverter;

import org.json.JSONArray;
import org.json.JSONObject;

@RestController
@RequestMapping("/customer/pages")
@CrossOrigin
public class CustomerControllerCHJ {

    @Autowired
    private CustomerServiceCHJ customerService;

    @PostMapping("/customer/find")
    public String find() {
        JSONObject responseJson = new JSONObject();
        JSONArray array = new JSONArray();
        List<Customer> customers = customerService.findAll();
        if (customers != null && !customers.isEmpty()) {
            for (Customer customer : customers) {

                // String beginDate = DatetimeConverter.toString(customer.getBeginDate(),
                // "yyyy-MM-dd HH:mm:ss EEEE");

                String lastModifiedDate = DatetimeConverter.toString(customer.getLastModifiedDate(),
                        "yyyy-MM-dd HH:mm:ss EEEE ");

                JSONObject item = new JSONObject()
                        .put("cId", customer.getcId())
                        .put("firstName", customer.getFirstName())
                        .put("lastName", customer.getLastName())
                        .put("identification", customer.getIdentification())
                        .put("passportNumber", customer.getPassportNumber())
                        .put("tel", customer.getTel())
                        .put("address", customer.getAddress())
                        .put("birthday", customer.getBirthday())
                        .put("gender", customer.getGender())
                        .put("country", customer.getCountry())
                        .put("email", customer.getEmail())
                        .put("remark", customer.getRemark())
                        .put("titleOfCourtesy", customer.getTitleOfCourtesy())
                        .put("allowPromotionMail", customer.getAllowPromotionMail())
                        .put("username", customer.getUsername())
                        .put("password", customer.getPassword())
                        .put("memberStatus", customer.getMemberStatus())
                        .put("totalPoints", customer.getTotalPoints())
                        .put("lastModifiedEmp", customer.getLastModifiedEmp())
                        .put("beginDate", customer.getBeginDate())
                        // .put("beginDate", beginDate)
                        .put("lastModifiedDate", lastModifiedDate)

                        .put("lastModifiedText", customer.getLastModifiedText());

                MemberRank memberRank = customer.getMemberRank();
                if (memberRank != null) {
                    String memberRankLastModifiedDate = DatetimeConverter.toString(memberRank.getLastModifiedDate(),
                            "yyyy-MM-dd HH:mm:ss EEEE");
                    item.put("memberRank", new JSONObject()
                            .put("mrId", memberRank.getMrId())
                            .put("name", memberRank.getName())
                            .put("neededPoints", memberRank.getNeededPoints())
                            .put("neededBookingDays", memberRank.getNeededBookingDays())
                            .put("gainPoints", memberRank.getGainPoints())
                            .put("cardPath", memberRank.getCardPath())
                            .put("lastModifiedDate", memberRankLastModifiedDate)
                            .put("lastModifiedEmp", memberRank.getLastModifiedEmp())
                            .put("lastModifiedText", memberRank.getLastModifiedText()));
                }
                array.put(item);
            }
        }
        responseJson.put("list", array);
        return responseJson.toString();
    }

    @PostMapping("/customer")
    public String save(@RequestBody String json) {
        JSONObject obj = new JSONObject(json);
        Customer customer = new Customer();
        if (obj.has("cId")) {
            customer.setcId(obj.getInt("cId"));
        }
        customer.setFirstName(obj.getString("firstName"));
        customer.setLastName(obj.getString("lastName"));
        customer.setIdentification(obj.optString("identification"));
        customer.setPassportNumber(obj.optString("passportNumber"));
        customer.setTel(obj.getString("tel"));
        customer.setAddress(obj.getString("address"));
        customer.setBirthday(DatetimeConverter.parseLocalDate(obj.getString("birthday"), "yyyy-MM-dd"));
        customer.setGender(obj.getString("gender"));
        customer.setCountry(obj.getString("country"));
        customer.setEmail(obj.getString("email"));
        customer.setRemark(obj.optString("remark"));
        customer.setTitleOfCourtesy(obj.optString("titleOfCourtesy"));
        customer.setAllowPromotionMail(obj.optString("allowPromotionMail"));
        customer.setUsername(obj.getString("username"));
        customer.setPassword(obj.getString("password"));

        customer.setMemberStatus(obj.getString("memberStatus"));
        customer.setTotalPoints(obj.getInt("totalPoints"));

        Integer mrId = obj.isNull("mrId") ? null : obj.getInt("mrId");
        if (mrId != null) {
            Optional<MemberRank> optionalMemberRank = customerService.findMemberRankById(mrId);
            optionalMemberRank.ifPresent(customer::setMemberRank);
        }

        customerService.save(customer);
        return new JSONObject().put("status", "success").toString();
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Optional<Customer> optionalCustomer = customerService.findById(id);
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();

                // 更新可，如果字段存在
                if (obj.has("firstName"))
                    customer.setFirstName(obj.getString("firstName"));
                if (obj.has("lastName"))
                    customer.setLastName(obj.getString("lastName"));
                if (obj.has("identification"))
                    customer.setIdentification(obj.optString("identification"));
                if (obj.has("passportNumber"))
                    customer.setPassportNumber(obj.optString("passportNumber"));
                if (obj.has("tel"))
                    customer.setTel(obj.getString("tel"));
                if (obj.has("address"))
                    customer.setAddress(obj.getString("address"));
                if (obj.has("birthday"))
                    customer.setBirthday(DatetimeConverter.parseLocalDate(obj.getString("birthday"), "yyyy-MM-dd"));
                if (obj.has("gender"))
                    customer.setGender(obj.getString("gender"));
                if (obj.has("country"))
                    customer.setCountry(obj.getString("country"));
                if (obj.has("email"))
                    customer.setEmail(obj.getString("email"));
                if (obj.has("remark"))
                    customer.setRemark(obj.optString("remark"));
                if (obj.has("titleOfCourtesy"))
                    customer.setTitleOfCourtesy(obj.optString("titleOfCourtesy"));
                if (obj.has("allowPromotionMail"))
                    customer.setAllowPromotionMail(obj.optString("allowPromotionMail"));
                if (obj.has("username"))
                    customer.setUsername(obj.getString("username"));
                if (obj.has("password"))
                    customer.setPassword(obj.getString("password"));

                if (obj.has("memberStatus"))
                    customer.setMemberStatus(obj.getString("memberStatus"));
                if (obj.has("totalPoints"))
                    customer.setTotalPoints(obj.getInt("totalPoints"));

                Integer mrId = obj.isNull("mrId") ? null : obj.optInt("mrId");
                if (mrId != null) {
                    Optional<MemberRank> optionalMemberRank = customerService.findMemberRankById(mrId);
                    optionalMemberRank.ifPresent(customer::setMemberRank);
                }

                customerService.save(customer);
                return new ResponseEntity<>(new JSONObject().put("status", "success").toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new JSONObject().put("status", "error").put("message", "Customer not found").toString(),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new JSONObject().put("status", "error").put("message", e.getMessage()).toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/customer/{id}")
    public String delete(@PathVariable Integer id) {
        customerService.deleteById(id);
        return new JSONObject().put("status", "success").toString();
    }
}
