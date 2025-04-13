package com.syncstate.probase.bills.BillerService.repositories;


import com.syncstate.probase.bills.BillerService.models.BillService;
import com.syncstate.probase.bills.BillerService.models.ValidationEndPoint;
import com.syncstate.probase.bills.BillerService.models.ValidationEndPointData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValidationEndPointRepository extends JpaRepository<ValidationEndPoint, Long> {

    @Query("Select bt from ValidationEndPoint bt where bt.deletedAt IS NULL ORDER BY bt.validationEndPointId ASC")
    List<ValidationEndPoint> getValidationEndPoints();

    @Query("Select bt from ValidationEndPoint bt where bt.deletedAt IS NULL AND bt.validationEndPointId = :validationEndPointId")
    ValidationEndPoint getValidationEndPointById(Long validationEndPointId);

    @Query("Select bt from ValidationEndPoint bt where bt.deletedAt IS NULL AND bt.serviceId = :serviceId")
    List<ValidationEndPoint> getValidationEndPointByServiceId(Long serviceId);
}
