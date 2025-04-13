package com.syncstate.probase.bills.BillerService.models.requests;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DataTablesRequest {
    private String draw;
    private Map<String, String> search;






}
