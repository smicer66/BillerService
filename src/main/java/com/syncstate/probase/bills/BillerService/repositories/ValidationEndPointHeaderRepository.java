package com.syncstate.probase.bills.BillerService.repositories;


import com.syncstate.probase.bills.BillerService.models.ValidationEndPointData;
import com.syncstate.probase.bills.BillerService.models.ValidationEndPointHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValidationEndPointHeaderRepository extends JpaRepository<ValidationEndPointHeader, Long> {

    @Query("Select bt from ValidationEndPointHeader bt where bt.deletedAt IS NULL ORDER BY bt.validationEndPointHeaderId ASC")
    List<ValidationEndPointHeader> getValidationEndPointHeaders();

    @Query("Select bt from ValidationEndPointHeader bt where bt.deletedAt IS NULL AND bt.validationEndPointHeaderId = :validationEndPointHeaderId")
    ValidationEndPointHeader getValidationEndPointHeaderById(Long validationEndPointHeaderId);

    @Query("Select bt from ValidationEndPointHeader bt where bt.deletedAt IS NULL AND bt.serviceId = :serviceId")
    List<ValidationEndPointHeader> getValidationEndPointHeaderByServiceId(Long serviceId);
}
