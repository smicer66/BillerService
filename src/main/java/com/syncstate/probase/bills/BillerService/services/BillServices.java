package com.syncstate.probase.bills.BillerService.services;


import com.google.gson.Gson;
import com.syncstate.probase.bills.BillerService.enums.ServiceProviderFieldType;
import com.syncstate.probase.bills.BillerService.enums.Status;
import com.syncstate.probase.bills.BillerService.models.*;
import com.syncstate.probase.bills.BillerService.models.dto.*;
import com.syncstate.probase.bills.BillerService.models.requests.*;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponse;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponseCode;
import com.syncstate.probase.bills.BillerService.repositories.*;
import com.syncstate.probase.bills.BillerService.util.UtilityHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BillServices {

    @Autowired
    private BillServiceRepository billServiceRepository;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ServiceProviderFieldRepository serviceProviderFieldRepository;

    @Autowired
    private BillServicePurchaseRequestRepository billServicePurchaseRequestRepository;

    @Autowired
    private EnumeratedFieldValueRepository enumeratedFieldValueRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ValidationEndPointRepository validationEndPointRepository;

    @Autowired
    private ValidationEndPointDataRepository validationEndPointDataRepository;

    @Autowired
    private ValidationEndPointHeaderRepository validationEndPointHeaderRepository;

    @Autowired
    private BillServicePurchaseRequestFileRepository billServicePurchaseRequestFileRepository;


    public DiademResponse getBillServices()
    {
        List<BillService> allBillServices = billServiceRepository.getBillServices();

        DiademResponse diademResponse = new DiademResponse();
        if(allBillServices!=null && !allBillServices.isEmpty()) {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(allBillServices);
            diademResponse.setMessage("Services found");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Services not found");
        }

        return diademResponse;
    }

    public DiademResponse createBillService(CreateServiceRequest createServiceRequest)
    {
        BillService billService = new BillService();
        billService.setCreatedAt(LocalDateTime.now());
        billService.setServiceName(createServiceRequest.getBillTypeName());
        billService.setServiceLogo(createServiceRequest.getBillTypeLogo());
        billService.setServiceFriendlyUrl(createServiceRequest.getBillTypeFriendlyUrl());
        billService.setUpdatedAt(LocalDateTime.now());
        billService.setMenuIcon(createServiceRequest.getMenuIcon());
        billService.setBillServiceStatus(Status.ACTIVE);



        billService = billServiceRepository.save(billService);
        DiademResponse diademResponse = new DiademResponse();
        diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        diademResponse.setResponseData(billService);
        diademResponse.setMessage("Service created successfully");

        return diademResponse;
    }

    public DiademResponse getBillService()
    {
        Collection<BillService> billServices = billServiceRepository.getBillServices();
        DiademResponse diademResponse = new DiademResponse();
        diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        diademResponse.setResponseData(billServices);
        diademResponse.setMessage("Services available are listed below");

        return diademResponse;
    }



    public DiademResponse createBillServiceProvider(CreateServiceProviderRequest createServiceProviderRequest)
    {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setCreatedAt(LocalDateTime.now());
        serviceProvider.setServiceProviderName(createServiceProviderRequest.getServiceProviderName());
        serviceProvider.setServiceProviderNote(createServiceProviderRequest.getServiceProviderNote());
        serviceProvider.setServiceProviderFriendlyUrl(createServiceProviderRequest.getServiceProviderFriendlyUrl());
        serviceProvider.setServiceProviderLogo(createServiceProviderRequest.getServiceProviderLogo());
        serviceProvider.setServiceEndpoint(createServiceProviderRequest.getServiceEndpoint());
        serviceProvider.setUpdatedAt(LocalDateTime.now());
        serviceProvider.setServiceId(createServiceProviderRequest.getServiceId());
        serviceProvider.setServiceProviderStatus(Status.ACTIVE);


        serviceProvider = serviceProviderRepository.save(serviceProvider);
        DiademResponse diademResponse = new DiademResponse();
        diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        diademResponse.setResponseData(serviceProvider);
        diademResponse.setMessage("Service Provider created successfully");

        return diademResponse;
    }



    public DiademResponse createBillServiceProviderField(CreateServiceProviderFieldRequest createServiceProviderFieldRequest)
    {
        ServiceProviderField serviceProviderField = new ServiceProviderField();
        serviceProviderField.setCreatedAt(LocalDateTime.now());
        serviceProviderField.setServiceProviderFieldName(createServiceProviderFieldRequest.getServiceProviderFieldName());
        serviceProviderField.setServiceProviderFieldType(ServiceProviderFieldType.valueOf(createServiceProviderFieldRequest.getServiceProviderFieldType()));
        serviceProviderField.setServiceProviderFieldTitle(createServiceProviderFieldRequest.getServiceProviderFieldTitle());
        serviceProviderField.setServiceValidationEndpoint(createServiceProviderFieldRequest.getServiceValidationEndpoint());
        serviceProviderField.setServiceTypeEnumeratedValues(createServiceProviderFieldRequest.getServiceTypeEnumeratedValues());
        serviceProviderField.setMaxSize(createServiceProviderFieldRequest.getMaxSize());
        serviceProviderField.setMinSize(createServiceProviderFieldRequest.getMinSize());
        serviceProviderField.setIsBillAmount(createServiceProviderFieldRequest.getIsBillAmount());
        serviceProviderField.setIsRequired(createServiceProviderFieldRequest.getIsRequired());
        serviceProviderField.setIsHidden(createServiceProviderFieldRequest.getIsHidden());
        serviceProviderField.setServiceFieldDataSourceField(createServiceProviderFieldRequest.getServiceFieldDataSourceField());
        serviceProviderField.setServiceFieldDataSourceFieldKey(createServiceProviderFieldRequest.getServiceFieldDataSourceFieldKey());
        serviceProviderField.setServiceFieldDataEndpoint(createServiceProviderFieldRequest.getServiceFieldDataEndpoint());
        serviceProviderField.setUpdatedAt(LocalDateTime.now());
        serviceProviderField.setSerialNo(createServiceProviderFieldRequest.getSerialNo());
        serviceProviderField.setServiceProviderFieldStatus(Status.ACTIVE);
        serviceProviderField.setServiceProviderId(createServiceProviderFieldRequest.getServiceProviderId());
        serviceProviderField.setServiceId(createServiceProviderFieldRequest.getServiceId());
        serviceProviderField.setIsEmailRecipient(createServiceProviderFieldRequest.getIsEmailRecipient());
        serviceProviderField.setIsSmsRecipient(createServiceProviderFieldRequest.getIsSmsRecipient());
        serviceProviderField.setDisplayBasedOnServiceField(createServiceProviderFieldRequest.getDisplayBasedOnServiceField());
        serviceProviderField.setDisplayBasedOnServiceFieldValue(createServiceProviderFieldRequest.getDisplayBasedOnServiceFieldValue());

        serviceProviderField = serviceProviderFieldRepository.save(serviceProviderField);

        DiademResponse diademResponse = new DiademResponse();
        diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        diademResponse.setResponseData(serviceProviderField);
        diademResponse.setMessage("Service provider fields created successfully");

        return diademResponse;
    }





    public DiademResponse createValidationEndPoint(CreateValidationEndPointRequest createValidationEndPointRequest)
    {


        ValidationEndPoint validationEndPoint = null;
        ValidationEndPointDTO validationEndPointRequest = createValidationEndPointRequest.getValidationEndPoint();
        validationEndPoint = new ValidationEndPoint();
        validationEndPoint.setCreatedAt(LocalDateTime.now());
        validationEndPoint.setUpdatedAt(LocalDateTime.now());
        validationEndPoint.setMethod(validationEndPointRequest.getMethod());
        validationEndPoint.setServiceProviderFieldId(createValidationEndPointRequest.getServiceProviderFieldId());
        validationEndPoint.setEndPointUrl(validationEndPointRequest.getEndPointUrl());
        validationEndPoint.setContentType(validationEndPointRequest.getContentType());
        validationEndPoint.setServiceId(createValidationEndPointRequest.getServiceId());
        validationEndPoint.setServiceProviderId(createValidationEndPointRequest.getServiceProviderId());
        validationEndPoint = validationEndPointRepository.save(validationEndPoint);

        List<ValidationEndPointHeaderDTO> validationEndPointHeaderRequest = createValidationEndPointRequest.getValidationEndPointHeader();
        System.out.println("validationEndPointHeaderRequest...." + validationEndPointHeaderRequest.size());
        ValidationEndPoint validationEndPoint1 = validationEndPoint;
        validationEndPointHeaderRequest.forEach(vephr -> {
            System.out.println(vephr.getEndPointHeaderKey());
            ValidationEndPointHeader validationEndPointHeader = new ValidationEndPointHeader();
            validationEndPointHeader.setCreatedAt(LocalDateTime.now());
            validationEndPointHeader.setUpdatedAt(LocalDateTime.now());
            validationEndPointHeader.setEndPointHeaderKey(vephr.getEndPointHeaderKey());
            validationEndPointHeader.setEndPointHeaderKeyValue(vephr.getEndPointHeaderKeyValue());
            validationEndPointHeader.setValidationEndPointId(validationEndPoint1.getValidationEndPointId());
            validationEndPointHeader.setServiceId(createValidationEndPointRequest.getServiceId());
            validationEndPointHeader.setServiceProviderId(createValidationEndPointRequest.getServiceProviderId());
            validationEndPointHeader = validationEndPointHeaderRepository.save(validationEndPointHeader);
        });

        List<ValidationEndPointDataDTO> validationEndPointDataRequest = createValidationEndPointRequest.getValidationEndPointData();
        System.out.println("validationEndPointDataRequest...." + validationEndPointDataRequest.size());
        validationEndPointDataRequest.forEach(vepdr -> {
            ValidationEndPointData validationEndPointData = new ValidationEndPointData();
            validationEndPointData.setCreatedAt(LocalDateTime.now());
            validationEndPointData.setUpdatedAt(LocalDateTime.now());
            validationEndPointData.setEndPointDataKey(vepdr.getEndPointDataKey());
            validationEndPointData.setEndPointDataKeyValueFromServiceProviderFieldId(vepdr.getEndPointDataKeyValueFromServiceProviderFieldId());
            validationEndPointData.setValidationEndPointId(validationEndPoint1.getValidationEndPointId());
            validationEndPointData.setEndPointDataKeyValueNotFromServiceProviderFieldId(vepdr.getEndPointDataKeyValueNotFromServiceProviderFieldId());
            validationEndPointData.setServiceId(createValidationEndPointRequest.getServiceId());
            validationEndPointData.setServiceProviderId(createValidationEndPointRequest.getServiceProviderId());
            validationEndPointData = validationEndPointDataRepository.save(validationEndPointData);
        });
        DiademResponse diademResponse = new DiademResponse();
        diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        diademResponse.setResponseData(validationEndPoint);
        diademResponse.setMessage("Validation endpoint created successfully");

        return diademResponse;
    }



    public DiademResponse getServiceProviders()
    {
        List<ServiceProvider> serviceProviders = serviceProviderRepository.getServiceProviders();

        DiademResponse diademResponse = new DiademResponse();
        if(serviceProviders!=null && !serviceProviders.isEmpty()) {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(serviceProviders);
            diademResponse.setMessage("Service providers found");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Services providers not found");
        }

        return diademResponse;
    }

    public DiademResponse getServiceProvidersByServiceId(Long serviceId)
    {
        List<ServiceProvider> serviceProviders = serviceProviderRepository.getServiceProviderByServiceId(serviceId);

        DiademResponse diademResponse = new DiademResponse();
        if(serviceProviders!=null && !serviceProviders.isEmpty()) {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(serviceProviders);
            diademResponse.setMessage("Service providers found");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Services providers not found");
        }

        return diademResponse;
    }

    public DiademResponse getServiceProviderFields()
    {
        List<ServiceProviderField> serviceProviderFields = serviceProviderFieldRepository.getServiceProviderFields();

        DiademResponse diademResponse = new DiademResponse();
        if(serviceProviderFields!=null && !serviceProviderFields.isEmpty()) {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(serviceProviderFields);
            diademResponse.setMessage("Service provider fields found");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Services provider fields not found");
        }

        return diademResponse;
    }

    public DiademResponse getServiceProviderFieldsByServiceProviderId(Long serviceProviderId)
    {
        System.out.println("get-service-provider-fields...1");
        List<ServiceProviderField> serviceProviderFields = serviceProviderFieldRepository.getServiceProviderFieldByServiceProviderId(serviceProviderId);
        System.out.println("serviceProviderFields length " + serviceProviderFields.size());

        DiademResponse diademResponse = new DiademResponse();
        if(serviceProviderFields!=null && !serviceProviderFields.isEmpty()) {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(serviceProviderFields);
            diademResponse.setMessage("Service provider fields found");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Services provider fields not found");
        }

        return diademResponse;
    }

    public DiademResponse createPurchaseBillServiceRequest(PurchaseBillServiceRequest purchaseBillServiceRequest, String sourceIPAddress, String fileDestinationPath, User user) {

        List<BillServicePurchaseRequest> billServicePurchaseRequestCreated = new ArrayList<>();
        Double totalAmountToPay = 0.00;
        String requestRefNo = RandomStringUtils.randomAlphanumeric(16);
        for(int i=0; i<purchaseBillServiceRequest.getServiceId().size(); i++)
        {
            BillServicePurchaseRequest billServicePurchaseRequest = new BillServicePurchaseRequest();
            billServicePurchaseRequest.setBillAmount((Double) purchaseBillServiceRequest.getBillAmount().get(i));
            billServicePurchaseRequest.setCreatedAt(LocalDateTime.now());
            billServicePurchaseRequest.setUpdatedAt(LocalDateTime.now());
            billServicePurchaseRequest.setRequestRefNo(requestRefNo);
            billServicePurchaseRequest.setSourceIPAddress(sourceIPAddress);
            billServicePurchaseRequest.setDataFields(purchaseBillServiceRequest.getDataFields().get(i));
            billServicePurchaseRequest.setPurchaseRequestStatus(Status.ACTIVE);
            billServicePurchaseRequest.setBillServiceId((Long) purchaseBillServiceRequest.getServiceId().get(i));
            billServicePurchaseRequest.setBillServiceProviderId((Long) purchaseBillServiceRequest.getServiceProviderId().get(i));
            if(user!=null)
                billServicePurchaseRequest.setUserId(user.getId());

            billServicePurchaseRequest = billServicePurchaseRequestRepository.save(billServicePurchaseRequest);
            totalAmountToPay = totalAmountToPay + purchaseBillServiceRequest.getBillAmount().get(i);
            billServicePurchaseRequestCreated.add(billServicePurchaseRequest);

            /*if(billServicePurchaseRequest!=null) {

                List<MultipartFile> files = purchaseBillServiceRequest.getFile();
                List<String> fileNameList = purchaseBillServiceRequest.getFileName();


                if (files != null && fileNameList != null && files.size() == fileNameList.size()) {
                    for (int x1 = 0; x1 < files.size(); x1++) {
                        MultipartFile file = files.get(x1);
                        String fileName = fileNameList.get(x1);
                        try {
                            String newFileName = UtilityHelper.uploadFile(file, fileDestinationPath);
                            BillServicePurchaseRequestFile billServicePurchaseRequestFile = new BillServicePurchaseRequestFile();
                            billServicePurchaseRequestFile.setCreatedAt(LocalDateTime.now());
                            billServicePurchaseRequestFile.setUpdatedAt(LocalDateTime.now());
                            billServicePurchaseRequestFile.setNewFileName(newFileName);
                            billServicePurchaseRequestFile.setOriginalFileName(fileName);
                            billServicePurchaseRequestFile.setBillServicePurchaseRequestId(billServicePurchaseRequest.getBillServicePurchaseRequestId());
                            billServicePurchaseRequestFileRepository.save(billServicePurchaseRequestFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }*/

        }

        DiademResponse diademResponse = new DiademResponse();
        if(billServicePurchaseRequestCreated.size()>0)
        {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            BillServicePurchaseRequestResponse billServicePurchaseRequestResponse = new BillServicePurchaseRequestResponse();
            billServicePurchaseRequestResponse.setBillServicePurchaseRequestList(billServicePurchaseRequestCreated);
            billServicePurchaseRequestResponse.setTotalAmountToPay(totalAmountToPay);
            billServicePurchaseRequestResponse.setRequestRefNo(requestRefNo);
            diademResponse.setResponseData(billServicePurchaseRequestResponse);
            diademResponse.setMessage("Bill service purchase request created");
        }
                else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Bill service purchase request could not be created");
        }

        return diademResponse;
    }

    public DiademResponse updateBillServicePurchaseRequest(UpdateBillServicePurchaseRequest updateBillServicePurchaseRequest, String sourceIPAddress) {


        Transaction transaction = new Transaction();
        List<BillServicePurchaseRequest> billServicePurchaseRequestList = (List<BillServicePurchaseRequest>)billServicePurchaseRequestRepository.
                getBillServicePurchaseRequestByRequestRefNo(updateBillServicePurchaseRequest.getOrderRefNo());
        //transaction.setBillServicePurchaseRequestId(billServicePurchaseRequest.getBillServicePurchaseRequestId());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setTransactionAmount(updateBillServicePurchaseRequest.getTransactionAmount());
        transaction.setTransactionStatus(Status.SUCCESSFUL);
        transaction.setSwitchTransactionRef(updateBillServicePurchaseRequest.getSwitchTransactionRef());
        transaction.setSourceIPAddress(sourceIPAddress);
        transaction.setOrderRefNo(updateBillServicePurchaseRequest.getOrderRefNo());
        transaction.setChannel(updateBillServicePurchaseRequest.getChannel());
        transaction.setCurrency(updateBillServicePurchaseRequest.getCurrency());
        transaction.setCustomerEmail(updateBillServicePurchaseRequest.getCustomerEmail());
        transaction.setCustomerPhone(updateBillServicePurchaseRequest.getCustomerPhone());
        transaction.setFees(updateBillServicePurchaseRequest.getFees()/100);
        transaction.setDomain(updateBillServicePurchaseRequest.getDomain());
        transaction.setGatewayResponse(updateBillServicePurchaseRequest.getGatewayResponse());
        transaction.setPaymentIPAddress(updateBillServicePurchaseRequest.getPaymentIPAddress());
        transaction.setSwitchPaymentDate(updateBillServicePurchaseRequest.getSwitchPaymentDate());
        transaction.setChannel(updateBillServicePurchaseRequest.getChannel());
        transaction.setAccountName(updateBillServicePurchaseRequest.getAccountName());
        transaction.setBank(updateBillServicePurchaseRequest.getBank());
        transaction.setBin(updateBillServicePurchaseRequest.getBin());
        transaction.setBrand(updateBillServicePurchaseRequest.getBrand());
        transaction.setCardType(updateBillServicePurchaseRequest.getCardType());
        transaction.setExpMonth(updateBillServicePurchaseRequest.getExpMonth());
        transaction.setExpYear(updateBillServicePurchaseRequest.getExpYear());
        transaction.setLast4(updateBillServicePurchaseRequest.getLast4());
        transaction.setBillPayerEmailAddress(updateBillServicePurchaseRequest.getBillPayerEmailAddress());
        transaction.setBillPayerMobile(updateBillServicePurchaseRequest.getBillPayerMobile());

        transaction = transactionRepository.save(transaction);

        Transaction finalTransaction = transaction;
        billServicePurchaseRequestList.forEach((br) -> {
                br.setTransactionId(finalTransaction.getTransactionId());
                br.setPurchaseRequestStatus(Status.SUCCESSFUL);
                billServicePurchaseRequestRepository.save(br);
        });

        DiademResponse diademResponse = new DiademResponse();
        if(transaction!=null) {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(transaction);
            diademResponse.setMessage("Transaction created successfully");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Transaction could not be created");
        }

        return diademResponse;
    }

    public DiademResponse getEnumerationValuesByServiceFieldId(Long serviceProviderFieldId) {
        List<EnumeratedFieldValue> enumeratedFieldValues = enumeratedFieldValueRepository.getEnumerationValuesByServiceFieldId(serviceProviderFieldId);

        DiademResponse diademResponse = new DiademResponse();
        if(enumeratedFieldValues!=null && !enumeratedFieldValues.isEmpty()) {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(enumeratedFieldValues);
            diademResponse.setMessage("Enumerated field values found");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Enumerated field values not found");
        }

        return diademResponse;
    }


    public DiademResponse getEnumerationValuesByServiceProviderId(Long serviceProviderId) {
        List<EnumeratedFieldValue> enumeratedFieldValues = enumeratedFieldValueRepository.getEnumerationValuesByServiceProviderId(serviceProviderId);

        DiademResponse diademResponse = new DiademResponse();
        if(enumeratedFieldValues!=null && !enumeratedFieldValues.isEmpty()) {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(enumeratedFieldValues);
            diademResponse.setMessage("Enumerated field values found");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Enumerated field values not found");
        }

        return diademResponse;
    }

    public DiademResponse createNewEnumeratedFieldValue(CreateNewEnumeratedFieldRequest createNewEnumeratedFieldRequest) {
        Long serviceProviderFieldId = createNewEnumeratedFieldRequest.getServiceProviderFieldId();

        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<EnumeratedFieldValue> createdEnumeratedFieldValues = createNewEnumeratedFieldRequest.getEnumeratedFieldValueList().stream().map(efd -> {
            EnumeratedFieldValue enumeratedFieldValue = new EnumeratedFieldValue();
            enumeratedFieldValue.setCreatedAt(LocalDateTime.now());
            enumeratedFieldValue.setUpdatedAt(LocalDateTime.now());
            enumeratedFieldValue.setOptionValue(efd.getValue());
            enumeratedFieldValue.setOptionTitle(efd.getTitle());
            enumeratedFieldValue.setStatus(Status.ACTIVE);
            enumeratedFieldValue.setServiceProviderFieldId(serviceProviderFieldId);
            enumeratedFieldValue.setSerialNo(atomicInteger.getAndIncrement());
            enumeratedFieldValue.setServiceProviderId(createNewEnumeratedFieldRequest.getServiceProviderId());
            return enumeratedFieldValueRepository.save(enumeratedFieldValue);
        }).collect(Collectors.toList());


        DiademResponse diademResponse = new DiademResponse();
        if(createdEnumeratedFieldValues!=null && createdEnumeratedFieldValues.size()>0) {
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(createdEnumeratedFieldValues);
            diademResponse.setMessage("Enumerated values has been created successfully");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Enumerated values has not been created successfully");
        }

        return diademResponse;
    }

    public DiademResponse getValidationDataByServiceId(Long serviceId) {
        List<ValidationEndPoint> validationEndPoint = validationEndPointRepository.getValidationEndPointByServiceId(serviceId);
        List<ValidationEndPointData> validationEndPointData = validationEndPointDataRepository.getValidationEndPointDataByServiceId(serviceId);
        List<ValidationEndPointHeader> validationEndPointHeader = validationEndPointHeaderRepository.getValidationEndPointHeaderByServiceId(serviceId);

        DiademResponse diademResponse = new DiademResponse();
        List responseDataList = new ArrayList();
        responseDataList.add(validationEndPoint);
        responseDataList.add(validationEndPointData);
        responseDataList.add(validationEndPointHeader);

        diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        diademResponse.setResponseData(responseDataList);
        diademResponse.setMessage("Validation data found");

        return diademResponse;
    }

    public DiademResponse getTransactionByRefNo(String orderRefNo) {
        ReceiptTransactionDTO receiptTransactionDTO = transactionRepository.getReceiptTransactionByOrderNo(orderRefNo);

        DiademResponse diademResponse = new DiademResponse();
        if(receiptTransactionDTO!=null) {
            List<ReceiptDataDTO> allBillServicePurchaseRequests = billServicePurchaseRequestRepository.getBillServicePurchaseRequestByTransactionId(receiptTransactionDTO.getTransactionId());
            ReceiptDTO receiptDTO = new ReceiptDTO();
            receiptDTO.setReceiptTransactionDTO(receiptTransactionDTO);
            receiptDTO.setReceiptDataDTO(allBillServicePurchaseRequests);
            diademResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            diademResponse.setResponseData(receiptDTO);
            diademResponse.setMessage("Transaction receipt found");
        }
        else {
            diademResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            diademResponse.setMessage("Enumerated field values not found");
        }

        return diademResponse;
    }
}
