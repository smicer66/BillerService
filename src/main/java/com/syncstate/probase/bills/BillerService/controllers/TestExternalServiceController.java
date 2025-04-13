package com.syncstate.probase.bills.BillerService.controllers;


import com.syncstate.probase.bills.BillerService.models.requests.*;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponse;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponseCode;
import com.syncstate.probase.bills.BillerService.models.responses.ErrorMessage;
import com.syncstate.probase.bills.BillerService.services.BillServices;
import com.syncstate.probase.bills.BillerService.services.NHIMAServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/nhima")
public class TestExternalServiceController {

    @Autowired
    private BillServices billServices;

    @Autowired
    private NHIMAServices nhimaServices;

    @RequestMapping(value = "/validate-employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> validateEmployee(HttpServletRequest request,
                                                           HttpServletResponse response) throws IOException {
        Map diademResponse = nhimaServices.validateEmployee(request);

        System.out.println(diademResponse);
        return ResponseEntity.ok().body(diademResponse);

    }

        @RequestMapping(value = "/validate-employee-test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> validateEmployeeTest(HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        Map diademResponse = nhimaServices.validateEmployee(request);

        return ResponseEntity.ok().body(diademResponse);

    }

    @RequestMapping(value = "/validate-file-returns", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> validateEmployeeTest(@RequestParam(value = "file", required = true) @Valid MultipartFile file, //BindingResult bindingResult,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {

//        if (bindingResult.hasErrors()) {
//            List errorMessageList =  bindingResult.getFieldErrors().stream().map(fe -> {
//                return new ErrorMessage(fe.getField(), fe.getDefaultMessage());
//            }).collect(Collectors.toList());
//
//            Map diademResponse = new HashMap();
//            diademResponse.put("status", false);
//            diademResponse.put("statusMessage", "Employee validation was not successful");
//            diademResponse.put("errorList", errorMessageList);
//            return ResponseEntity.badRequest().body(diademResponse);
//        }

        System.out.println(file.getOriginalFilename());

        if(file!=null && file.getSize()>0) {
            Map diademResponse = nhimaServices.validateFileReturns(file, request);

            return ResponseEntity.ok().body(diademResponse);

        }

        Map diademResponse = new HashMap();
        diademResponse.put("status", false);
        diademResponse.put("statusMessage", "Invalid or no file uploaded");

        return ResponseEntity.ok().body(diademResponse);

    }

}
