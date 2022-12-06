package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.ServiceDTO;
import com.example.dentalclinicmanagementsystem.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findAllByCategoryServiceId(Long id);

    List<Service> findAllByServiceNameContainingIgnoreCaseAndIsDeleted(String name, Boolean isDeleted);

    Service findByServiceIdAndIsDeleted(Long id, Boolean isDeleted);

    Service findByServiceName(String name);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.ServiceDTO(s.serviceId, s.serviceName, prsm.status) " +
            "FROM Service s JOIN PatientRecordServiceMap prsm ON s.serviceId = prsm.serviceId " +
            "WHERE s.serviceId in :ids AND prsm.patientRecordId = :patientRecordId ")
    List<ServiceDTO> findAllByServiceIdIn(List<Long> ids,
                                          Long patientRecordId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.ServiceDTO(s.serviceId, s.serviceName," +
//            "tsm.currentPrice, tsm.discount, " +
            "prsm.status) " +
            "FROM PatientRecord pr " +
            "JOIN PatientRecordServiceMap prsm ON pr.patientRecordId = prsm.patientRecordId AND prsm.status = 1" +
            "JOIN Service s ON prsm.serviceId = s.serviceId " +
//            "JOIN TreatmentServiceMap  tsm ON tsm.treatmentId = pr.treatmentId AND tsm.serviceId = s.serviceId " +
            "WHERE pr.patientRecordId = :patientRecordId ")
    List<ServiceDTO> findTreatingService(@Param("patientRecordId") Long patientRecordId);

    List<Service> findAllByServiceNameContainingAndCategoryServiceIdAndIsDeletedOrderByServiceIdDesc(String name, Long categoryId, Boolean isDeleted);
}
