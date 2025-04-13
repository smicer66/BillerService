package com.syncstate.probase.bills.BillerService.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DiademResponse implements Serializable {

    private String responseCode;
    private String message;
    private Object responseData;

    public void setResponseCode(String responseCode)
    {
        this.responseCode = responseCode;
    }
    public String getResponseCode()
    {
        return this.responseCode;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
    public String getMessage()
    {
        return this.message;
    }

    public void setResponseData(Object responseData)
    {
        this.responseData = responseData;
    }
    public Object getResponseData()
    {
        return this.responseData;
    }
}
