package com.syncstate.probase.bills.BillerService.repositories;


import com.syncstate.probase.bills.BillerService.models.BillService;
import com.syncstate.probase.bills.BillerService.models.ServiceProvider;
import com.syncstate.probase.bills.BillerService.models.ServiceProviderField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceProviderFieldRepository extends JpaRepository<ServiceProviderField, Long> {

    @Query("Select bt from ServiceProviderField bt where bt.deletedAt IS NULL ORDER BY bt.serialNo")
    List<ServiceProviderField> getServiceProviderFields();

    @Query("Select bt from ServiceProviderField bt where bt.deletedAt IS NULL AND bt.serviceProviderFieldId = :serviceProviderFieldId ORDER BY bt.serialNo")
    ServiceProviderField getServiceProviderFieldById(Long serviceProviderFieldId);

    @Query("Select bt from ServiceProviderField bt where bt.deletedAt IS NULL AND bt.ServiceProviderId = :ServiceProviderId ORDER BY bt.serialNo")
    List<ServiceProviderField> getServiceProviderFieldByServiceProviderId(Long ServiceProviderId);
}
