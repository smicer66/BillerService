package com.syncstate.probase.bills.BillerService.repositories;

import com.syncstate.probase.bills.BillerService.models.EnumeratedFieldValue;
import com.syncstate.probase.bills.BillerService.models.ServiceProviderField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnumeratedFieldValueRepository extends JpaRepository<EnumeratedFieldValue, Long> {

    @Query("Select bt from EnumeratedFieldValue bt where bt.deletedAt IS NULL ORDER BY bt.serialNo")
    List<EnumeratedFieldValue> getEnumeratedFieldValues();

    @Query("Select bt from EnumeratedFieldValue bt where bt.deletedAt IS NULL AND bt.serviceProviderFieldId = :serviceProviderFieldId ORDER BY bt.serialNo")
    List<EnumeratedFieldValue> getEnumerationValuesByServiceFieldId(Long serviceProviderFieldId);

    @Query("Select bt from EnumeratedFieldValue bt where bt.serviceProviderId = :serviceProviderId ORDER BY bt.serialNo")
    List<EnumeratedFieldValue> getEnumerationValuesByServiceProviderId(Long serviceProviderId);
}
