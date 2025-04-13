package com.syncstate.probase.bills.BillerService.repositories;


import com.syncstate.probase.bills.BillerService.models.ValidationEndPoint;
import com.syncstate.probase.bills.BillerService.models.ValidationEndPointData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValidationEndPointDataRepository extends JpaRepository<ValidationEndPointData, Long> {

    @Query("Select bt from ValidationEndPointData bt where bt.deletedAt IS NULL ORDER BY bt.validationEndPointDataId ASC")
    List<ValidationEndPointData> getValidationEndPointData();

    @Query("Select bt from ValidationEndPointData bt where bt.deletedAt IS NULL AND bt.validationEndPointDataId = :validationEndPointDataId")
    ValidationEndPointData getValidationEndPointDataById(Long validationEndPointDataId);

    @Query("Select bt from ValidationEndPointData bt where bt.deletedAt IS NULL AND bt.serviceId = :serviceId")
    List<ValidationEndPointData> getValidationEndPointDataByServiceId(Long serviceId);
}
