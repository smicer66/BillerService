package com.syncstate.probase.bills.BillerService.services;


import com.google.gson.Gson;
import com.syncstate.probase.bills.BillerService.enums.ServiceProviderFieldType;
import com.syncstate.probase.bills.BillerService.enums.Status;
import com.syncstate.probase.bills.BillerService.models.*;
import com.syncstate.probase.bills.BillerService.models.dto.ValidationEndPointDTO;
import com.syncstate.probase.bills.BillerService.models.dto.ValidationEndPointDataDTO;
import com.syncstate.probase.bills.BillerService.models.dto.ValidationEndPointHeaderDTO;
import com.syncstate.probase.bills.BillerService.models.requests.*;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponse;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponseCode;
import com.syncstate.probase.bills.BillerService.repositories.*;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class NHIMAServices {


    private Map<String, String> nhisSelfServiceMembers  = new HashMap<String, String>();
    public Map validateEmployee(HttpServletRequest request) throws IOException {
        nhisSelfServiceMembers.put("1234", "password");
        nhisSelfServiceMembers.put("5678", "password1");
        nhisSelfServiceMembers.put("9012", "password2");
        String authorization = request.getHeader("Authorization");
        String accessCode = request.getHeader("AccessCode");
        System.out.println("authorization = " + authorization);
        System.out.println("accessCode = " + accessCode);

        Map successMap = new HashMap();
        successMap.put("status", false);
        successMap.put("statusMessage", "Invalid membership ID provided");
        if(accessCode.equals("12345") && authorization.equals("Basic eHl6MTIzNDU2"))
        {
            ServletInputStream sis = request.getInputStream();
            String sisString = IOUtils.toString(sis);
            System.out.println(sisString);
            JSONObject object = new JSONObject(sisString);


            String nhisMembershipID = object.getString("nhisMembershipID");
            String nhisPassword = object.getString("nhisPassword");
            System.out.println(nhisMembershipID);
            nhisSelfServiceMembers.forEach((k, v) -> {
                System.out.println("=======>");
                if(k.equals(nhisMembershipID) && v.equals(nhisPassword))
                {
                    successMap.put("employee_name", "John Doe");
                    successMap.put("employee_company", "John Doe & Sons");
                    successMap.put("status", true);
                    successMap.put("statusMessage", "Successfully validated NHIS Membership");


                }
            });
        }
        System.out.println(".1111....");


        return successMap;
    }

    public Map validateFileReturns(MultipartFile file, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String accessCode = request.getHeader("AccessCode");
        System.out.println("authorization = " + authorization);
        System.out.println("accessCode = " + accessCode);

        Map successMap = new HashMap();
        successMap.put("status", false);
        successMap.put("statusMessage", "Invalid membership ID provided");
        if(accessCode.equals("12345") && authorization.equals("Basic eHl6MTIzNDU2"))
        {
            successMap.put("amount", 2000);
            successMap.put("validation_message", "Returns file validated");
            successMap.put("status", true);
            successMap.put("statusMessage", "Successfully validated Payments");
        }
        System.out.println(".1111....");


        return successMap;
    }
}
