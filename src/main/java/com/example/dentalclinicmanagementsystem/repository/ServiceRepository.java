package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.ServiceDTO;
import com.example.dentalclinicmanagementsystem.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findAllByCategoryServiceId(Long id);

    List<Service> findAllByServiceNameContainingIgnoreCaseAndIsDeleted(String name, Boolean isDeleted);

    Service findByServiceIdAndIsDeleted(Long id, Boolean isDeleted);

    Service findByServiceName(String name);

    List<Service> findAllByServiceIdIn(List<Long> ids);

    @Query("SELECT s FROM Service s JOIN TreatmentServiceMap tsm ON s.serviceId = tsm.serviceId " +
            "JOIN Treatment t ON t.treatmentId = tsm.treatmentId " +
            "JOIN Patient p ON p.patientId = t.patientId " +
            "WHERE p.patientId = :patientId")
    List<Service> findAllServiceNotPayingByPatientId(@PathVariable("patientId") Long patientId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.ServiceDTO(s.serviceId, s.serviceName,tsm.currentPrice, tsm.discount, prsm.status) " +
            "FROM PatientRecord pr " +
            "JOIN PatientRecordServiceMap prsm ON pr.patientRecordId = prsm.patientRecordId " +
            "JOIN Service s ON prsm.serviceId = s.serviceId " +
            "JOIN TreatmentServiceMap  tsm ON tsm.startRecordId = pr.patientRecordId " +
            "WHERE pr.patientRecordId = :patientRecordId AND prsm.status = 1")
    List<ServiceDTO> findTreatingService(@Param("patientRecordId") Long patientRecordId);

    List<Service> findAllByServiceNameContainingAndCategoryServiceId(String name, Long categoryId);
}
