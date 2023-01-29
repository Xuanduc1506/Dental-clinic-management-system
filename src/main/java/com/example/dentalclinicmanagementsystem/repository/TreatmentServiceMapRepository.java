package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
import com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO;
import com.example.dentalclinicmanagementsystem.entity.TreatmentServiceMap;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TreatmentServiceMapRepository extends JpaRepository<TreatmentServiceMap, Long> {

    @Query("SELECT sum(tsm.currentPrice * tsm.amount) - sum(tsm.discount) FROM TreatmentServiceMap tsm " +
            "WHERE tsm.treatmentId = :treatmentId")
    Integer getTotalMoney(@Param("treatmentId") Long treatmentId);

    @Query("SELECT sum(me.unitPrice * me.amount) FROM MaterialExport me JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "WHERE pr.treatmentId = :treatmentId")
    Integer getTotalMoneyOfMaterialExport(@Param("treatmentId") Long treatmentId);

    void deleteAllByStartRecordId(Long patientRecordId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO(tsm.treatmentId, tsm.serviceId, tsm.currentPrice, " +
            "tsm.discount, s.serviceName, tsm.amount) FROM TreatmentServiceMap tsm " +
            "JOIN Service s ON tsm.serviceId = s.serviceId WHERE tsm.treatmentId = :id")
    List<TreatmentServiceMapDTO> findAllByTreatmentId(Long id);

    @Query("SELECT tsm.serviceId FROM TreatmentServiceMap tsm WHERE tsm.startRecordId = :patientRecordId")
    List<Long> findAllServiceIdByPatientRecordId(@Param("patientRecordId")Long patientRecordId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO(s.serviceName, pr.date, tsm.currentPrice * tsm.amount - tsm.discount) " +
            "FROM TreatmentServiceMap tsm JOIN Service s ON tsm.serviceId = s.serviceId " +
            "JOIN PatientRecord pr ON tsm.startRecordId = pr.patientRecordId " +
            "WHERE pr.date BETWEEN :startDate AND :endDate ")
    List<IncomeDetailDTO> findAllServiceInTime(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO(tsm.treatmentId, tsm.serviceId, tsm.currentPrice, " +
            "tsm.discount, s.serviceName, tsm.amount)" +
            " FROM TreatmentServiceMap tsm JOIN Service s ON tsm.serviceId = s.serviceId" +
            " WHERE tsm.treatmentId = :treatmentId " +
            "AND tsm.isShow = FALSE ")
    List<TreatmentServiceMapDTO> findAllByTreatmentIdAndIsShow(@Param("treatmentId") Long treatmentId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO(pr.treatmentId, m.materialId, " +
            "me.unitPrice, 0, m.materialName, m.amount) " +
            "FROM MaterialExport me JOIN Material m ON me.materialId = m.materialId " +
            "JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "WHERE pr.treatmentId = :treatmentId AND me.isShow = FALSE ")
    List<TreatmentServiceMapDTO> findAllMaterialExportByTreatment(@Param("treatmentId") Long treatmentId);

    List<TreatmentServiceMap> findAllByTreatmentIdAndIsShow(Long treatmentId, Boolean isShow);


    @Query("SELECT tsm FROM TreatmentServiceMap tsm JOIN Treatment t ON tsm.treatmentId = t.treatmentId " +
            "WHERE t.patientId = :patientId AND tsm.serviceId IN :serviceIds")
    List<TreatmentServiceMap> findAllByPatientId(@Param("patientId") Long patientId,
                                                 @Param("serviceIds") List<Long> serviceIds);

    @Query("SELECT tsm.startRecordId FROM TreatmentServiceMap tsm " +
            "WHERE tsm.treatmentId = :treatmentId AND tsm.serviceId in :serviceIds")
    List<Long> findAllStartRecordByTreatmentIdAndListServiceId(@Param("treatmentId") Long treatmentId,
                                                               @Param("serviceIds") List<Long> serviceIds);
}
