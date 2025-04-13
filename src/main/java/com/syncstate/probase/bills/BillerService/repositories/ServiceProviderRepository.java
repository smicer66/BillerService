package com.syncstate.probase.bills.BillerService.repositories;


import com.syncstate.probase.bills.BillerService.models.BillService;
import com.syncstate.probase.bills.BillerService.models.ServiceProvider;
import com.syncstate.probase.bills.BillerService.models.ServiceProviderField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    @Query("Select bt from ServiceProvider bt where bt.deletedAt IS NULL")
    List<ServiceProvider> getServiceProviders();

    @Query("Select bt from ServiceProvider bt where bt.deletedAt IS NULL AND bt.serviceProviderId = :serviceProviderId")
    ServiceProvider getServiceProviderById(Long serviceProviderId);

    @Query("Select bt from ServiceProvider bt where bt.deletedAt IS NULL AND bt.serviceId = :serviceId")
    List<ServiceProvider> getServiceProviderByServiceId(Long serviceId);

}
