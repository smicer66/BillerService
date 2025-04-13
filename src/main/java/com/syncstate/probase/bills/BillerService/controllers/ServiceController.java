package com.syncstate.probase.bills.BillerService.controllers;


import com.google.gson.Gson;
import com.syncstate.probase.bills.BillerService.models.User;
import com.syncstate.probase.bills.BillerService.models.requests.*;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponse;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponseCode;
import com.syncstate.probase.bills.BillerService.repositories.UserRepository;
import com.syncstate.probase.bills.BillerService.services.BillServices;
import com.syncstate.probase.bills.BillerService.services.JwtService;
import com.syncstate.probase.bills.BillerService.util.UtilityHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    @Autowired
    private BillServices billServices;

    @Value("${path.uploads.farmer_templates}")
    private String fileDestinationPath;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/create-new-service", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> createNewService(@RequestBody CreateServiceRequest createServiceRequest, BindingResult bindingResult,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response)
    {
        DiademResponse diademResponse = billServices.createBillService(createServiceRequest);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }

    @RequestMapping(value = "/get-services", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> getServices(HttpServletRequest request,
                                                           HttpServletResponse response)
    {
        DiademResponse diademResponse = billServices.getBillService();
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }


    @RequestMapping(value = "/create-new-service-provider", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> createNewServiceProvider(@RequestBody CreateServiceProviderRequest createServiceProviderRequest, BindingResult bindingResult,
                                                                   HttpServletRequest request,
                                                                   HttpServletResponse response)
    {
        DiademResponse diademResponse = billServices.createBillServiceProvider(createServiceProviderRequest);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }


    @RequestMapping(value = "/get-service-providers/{serviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> getServiceProviders(
            @PathVariable(required = true) Long serviceId,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        DiademResponse diademResponse = billServices.getServiceProvidersByServiceId(serviceId);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }


    @RequestMapping(value = "/get-all-service-providers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> getAllServiceProviders(
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        DiademResponse diademResponse = billServices.getServiceProviders();
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }


    @RequestMapping(value = "/create-new-service-provider-field", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> createNewServiceProviderField(@RequestBody CreateServiceProviderFieldRequest createServiceProviderFieldRequest, BindingResult bindingResult,
                                                                        HttpServletRequest request,
                                                                        HttpServletResponse response)
    {
        DiademResponse diademResponse = billServices.createBillServiceProviderField(createServiceProviderFieldRequest);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }


    @RequestMapping(value = "/get-service-provider-fields/{serviceProviderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> getServiceProviderFields(
            @PathVariable(required = true) Long serviceProviderId,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        System.out.println("get-service-provider-fields...");
        DiademResponse diademResponse = billServices.getServiceProviderFieldsByServiceProviderId(serviceProviderId);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }




    @RequestMapping(value = "/purchase-bill-service", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> purchaseBillService(@RequestBody PurchaseBillServiceRequest purchaseBillServiceRequest, BindingResult bindingResult,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response) throws IOException {

        logger.info("asddf", purchaseBillServiceRequest);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("asddf", authentication.getPrincipal());


        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        User user = null;

        if(authHeader!=null) {
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<User> userOpt = userRepository.findByUsername(userEmail);
                user = userOpt.orElse(null);
            }
        }


        String ipAddress = null;
        if (xForwardedForHeader == null) {
            ipAddress = request.getRemoteAddr();
        } else {

            ipAddress = new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }





        DiademResponse diademResponse = billServices.createPurchaseBillServiceRequest(purchaseBillServiceRequest, ipAddress, fileDestinationPath, user);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }



    @RequestMapping(value = "/update-bill-service-purchase", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> updateBillServicePurchase(@RequestBody UpdateBillServicePurchaseRequest updateBillServicePurchaseRequest, BindingResult bindingResult,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response)
    {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        String ipAddress = null;
        if (xForwardedForHeader == null) {
            ipAddress = request.getRemoteAddr();
        } else {

            ipAddress = new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }
        DiademResponse diademResponse = billServices.updateBillServicePurchaseRequest(updateBillServicePurchaseRequest, ipAddress);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }


    @RequestMapping(value = "/create-new-enumeration-field-value", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> createNewEnumeratedFieldValue(@RequestBody CreateNewEnumeratedFieldRequest createNewEnumeratedFieldRequest, BindingResult bindingResult,
                                                                        HttpServletRequest request,
                                                                        HttpServletResponse response)
    {
        DiademResponse diademResponse = billServices.createNewEnumeratedFieldValue(createNewEnumeratedFieldRequest);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }


    @RequestMapping(value = "/get-enumeration-field-values-by-service-field-id/{serviceProviderFieldId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> getEnumerationValuesByServiceFieldId(
            @PathVariable(required = true) Long serviceProviderFieldId,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        DiademResponse diademResponse = billServices.getEnumerationValuesByServiceFieldId(serviceProviderFieldId);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }



    @RequestMapping(value = "/get-enumeration-field-values-by-service-provider-id/{serviceProviderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> getEnumerationValuesByServiceProviderId(
            @PathVariable(required = true) Long serviceProviderId,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        DiademResponse diademResponse = billServices.getEnumerationValuesByServiceProviderId(serviceProviderId);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }




    @RequestMapping(value = "/create-new-validation-end-point", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> createNewService(@RequestBody CreateValidationEndPointRequest createValidationEndPointRequest, BindingResult bindingResult,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response)
    {
        DiademResponse diademResponse = billServices.createValidationEndPoint(createValidationEndPointRequest);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }


    @RequestMapping(value = "/get-validation-data/{serviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> getValidationDataByServiceId(
            @PathVariable(required = true) Long serviceId,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        DiademResponse diademResponse = billServices.getValidationDataByServiceId(serviceId);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }




    @RequestMapping(value = "/get-transaction-by-ref-no/{orderRefNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiademResponse> getTransactionByRefNo(
            @PathVariable(required = true) String orderRefNo,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        DiademResponse diademResponse = billServices.getTransactionByRefNo(orderRefNo);
        if(diademResponse.getResponseCode().equals(DiademResponseCode.SUCCESS.value))
            return ResponseEntity.ok().body(diademResponse);
        else if(diademResponse.getResponseCode().equals(DiademResponseCode.UNSUCCESSFUL.value))
            return ResponseEntity.notFound().build();

        return ResponseEntity.badRequest().body(diademResponse);

    }

}
