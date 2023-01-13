package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.PatientRecordInterfaceDTO;
import com.example.dentalclinicmanagementsystem.entity.PatientRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {

    @Query("SELECT pr FROM PatientRecord pr JOIN Treatment t ON pr.treatmentId = t.treatmentId " +
            "WHERE t.patientId = :patientId")
    List<PatientRecord> getAllByPatientId(@Param("patientId") Long patientId);

    @Query(value = "SELECT pr.patient_record_id AS patientRecordId, " +
            "pr.reason, " +
            "pr.diagnostic," +
            "pr.causal," +
            "pr.date," +
            "pr.treatment," +
            "pr.marrow_record AS `marrowRecord`," +
            "pr.note," +
            "pr.prescription," +
            "group_concat(DISTINCT(l.labo_name) SEPARATOR ',') AS `laboName`," +
            "group_concat(DISTINCT(se.service_name) SEPARATOR ',') AS `services` " +
            "FROM patient_records pr " +
            "JOIN treatments t on pr.treatment_id = t.treatment_id " +
            "LEFT JOIN patient_record_service_map prsm on prsm.patient_record_id = pr.patient_record_id " +
            "LEFT JOIN services se on prsm.service_id = se.service_id " +
            "LEFT JOIN specimens s on pr.patient_record_id = s.patient_record_id " +
            "LEFT JOIN labos l on l.labo_id = s.labo_id " +
            "WHERE t.patient_id = :patientId " +
            "AND pr.is_deleted = FALSE " +
            "AND pr.reason like %:reason% " +
            "AND pr.diagnostic like %:diagnostic% " +
            "AND pr.causal like %:causal% " +
            "AND pr.date like %:date% " +
            "AND pr.treatment like %:treatment% " +
            "GROUP BY pr.patient_record_id " +
            "HAVING (`laboName` IS NULL OR `laboName` like %:laboName%) " +
            "AND (`services` like %:serviceName%) " +
            "ORDER BY pr.patient_record_id DESC", nativeQuery = true,
            countQuery ="SELECT count(pr.patient_record_id) " +
                    "FROM patient_records pr " +
                    "JOIN treatments t on pr.treatment_id = t.treatment_id " +
                    "LEFT JOIN patient_record_service_map prsm on prsm.patient_record_id = pr.patient_record_id " +
                    "LEFT JOIN services se on prsm.service_id = se.service_id " +
                    "LEFT JOIN specimens s on pr.patient_record_id = s.patient_record_id " +
                    "LEFT JOIN labos l on l.labo_id = s.labo_id " +
                    "WHERE t.patient_id = :patientId " +
                    "AND pr.is_deleted = FALSE " +
                    "AND pr.reason like %:reason% " +
                    "AND pr.diagnostic like %:diagnostic% " +
                    "AND pr.causal like %:causal% " +
                    "AND pr.date like %:date% " +
                    "AND pr.treatment like %:treatment% " +
                    "GROUP BY pr.patient_record_id " +
                    "HAVING (group_concat(DISTINCT(l.labo_name) SEPARATOR ',') IS NULL OR group_concat(DISTINCT(l.labo_name) SEPARATOR ',') like %:laboName%)" +
                    "AND (group_concat(DISTINCT(se.service_name) SEPARATOR ',') like %:serviceName%)" +
                    "ORDER BY pr.patient_record_id DESC" )
    Page<PatientRecordInterfaceDTO> getAllByPatientId(@Param("patientId") Long patientId,
                                                      @Param("reason") String reason,
                                                      @Param("diagnostic") String diagnostic,
                                                      @Param("causal") String causal,
                                                      @Param("date") String date,
                                                      @Param("treatment") String treatment,
                                                      @Param("laboName") String laboName,
                                                      @Param("serviceName") String serviceName,
                                                      Pageable pageable);

    @Query(value = "SELECT pr.patient_record_id AS patientRecordId, " +
            "pr.reason, " +
            "pr.diagnostic," +
            "pr.causal," +
            "pr.date," +
            "pr.treatment," +
            "pr.marrow_record AS `marrowRecord`," +
            "pr.note," +
            "pr.prescription," +
            "group_concat(DISTINCT(l.labo_name) SEPARATOR ',') AS `laboName`," +
            "group_concat(DISTINCT(se.service_name) SEPARATOR ',') AS `services` ," +
            "pr.treatment_id as treatmentId " +
            "FROM patient_records pr " +
            "LEFT JOIN patient_record_service_map prsm on prsm.patient_record_id = pr.patient_record_id " +
            "LEFT JOIN services se on prsm.service_id = se.service_id " +
            "LEFT JOIN specimens s on pr.patient_record_id = s.patient_record_id " +
            "LEFT JOIN labos l on l.labo_id = s.labo_id " +
            "WHERE pr.patient_record_id = :id " +
            "AND pr.is_deleted = FALSE " +
            "GROUP BY pr.patient_record_id ", nativeQuery = true)
    PatientRecordInterfaceDTO findPatientRecordDtoByPatientRecordId(Long id);

    PatientRecord findByPatientRecordIdAndIsDeleted(Long id, Boolean isDeleted);

    @Query("SELECT pr FROM PatientRecord pr JOIN Treatment t ON pr.treatmentId = t.treatmentId " +
            "WHERE t.patientId = :patientId AND (:date is null or pr.dateTemp like %:date%) AND pr.isDeleted = FALSE " +
            "ORDER BY pr.patientRecordId DESC")
    List<PatientRecord> findRecordByPatientId(@Param("patientId") Long patientId,
                                              @Param("date") String date);


    @Query("SELECT MAX(pr.patientRecordId) FROM Treatment t join PatientRecord pr ON t.treatmentId = pr.treatmentId " +
            "WHERE t.patientId = :patientId")
    Long getLastRecordId(@Param("patientId") Long patientId);

    @Query("SELECT COUNT(pr.patientRecordId) FROM PatientRecord pr WHERE pr.treatmentId = :treatmentId")
    Integer countRecordByTreatmentId(@Param("treatmentId") Long treatmentId);

}
