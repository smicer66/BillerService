package com.syncstate.probase.bills.BillerService.models.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.probase.bills.BillerService.deserializers.TimestampDeserializer;
import com.syncstate.probase.bills.BillerService.serializer.JsonDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class PurchaseBillServiceRequest implements Serializable {

    private List<Long> serviceId;

    private List<Long> serviceProviderId;

    private List<String> dataFields;

    private List<Double> billAmount;

    private String requestRefNo;

    private String sourceIPAddress;

//    @JsonProperty("file[]")
//    private List<MultipartFile> file;

    private List<String> fileName;
}
