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

    List<PatientRecord> getAllByPatientId(Long id);

    @Query(value = "SELECT pr.patient_record_id AS patientRecordId, " +
            "pr.reason, " +
            "pr.diagnostic," +
            "pr.causal," +
            "pr.date," +
            "pr.treatment," +
            "pr.total_cost AS `totalCost`," +
            "pr.real_cost AS `realCost`," +
            "pr.marrow_record AS `marrowRecord`," +
            "pr.debit," +
            "pr.cost_incurred AS costIncurred," +
            "pr.note," +
            "pr.prescription," +
            "group_concat(DISTINCT(l.labo_name) SEPARATOR ',') AS `laboName`," +
            "group_concat(DISTINCT(se.service_name) SEPARATOR ',') AS `services` " +
            "FROM patient_records pr " +
            "LEFT JOIN patient_record_service_map prsm on prsm.patient_record_id = pr.patient_record_id " +
            "LEFT JOIN services se on prsm.service_id = se.service_id " +
            "LEFT JOIN specimens s on pr.patient_record_id = s.patient_record_id " +
            "LEFT JOIN labos l on l.labo_id = s.labo_id " +
            "WHERE pr.patient_id = :patientId " +
            "AND pr.reason like %:reason% " +
            "AND pr.diagnostic like %:diagnostic% " +
            "AND pr.causal like %:causal% " +
            "AND pr.date like %:date% " +
            "AND pr.treatment like %:treatment% " +
            "AND pr.total_cost like %:totalCost% " +
            "AND pr.real_cost like %:realCost% " +
            "AND pr.debit like %:debit% " +
            "AND pr.cost_incurred like %:costIncurred% " +
            "GROUP BY pr.patient_record_id " +
            "HAVING (`services` like %:serviceName%) " +
            "AND (`laboName` is null or `laboName` like %:laboName%)", nativeQuery = true)
    Page<PatientRecordInterfaceDTO> getAllByPatientId(@Param("patientId") Long patientId,
                                                      @Param("reason") String reason,
                                                      @Param("diagnostic") String diagnostic,
                                                      @Param("causal") String causal,
                                                      @Param("date") String date,
                                                      @Param("treatment") String treatment,
                                                      @Param("totalCost") String totalCost,
                                                      @Param("realCost") String realCost,
                                                      @Param("debit") String debit,
                                                      @Param("costIncurred") String costIncurred,
                                                      @Param("laboName") String laboName,
                                                      @Param("serviceName") String serviceName,
                                                      Pageable pageable);

    @Query(value = "SELECT pr.patient_record_id as patientRecordId, " +
            "pr.reason, " +
            "pr.diagnostic," +
            "pr.causal," +
            "pr.date," +
            "pr.treatment," +
            "pr.total_cost AS `totalCost`," +
            "pr.real_cost AS `realCost`," +
            "pr.marrow_record AS `marrowRecord`," +
            "pr.debit," +
            "pr.cost_incurred AS costIncurred," +
            "pr.note," +
            "pr.prescription," +
            "group_concat(DISTINCT(l.labo_name) SEPARATOR ',') AS `laboName`," +
            "group_concat(DISTINCT(se.service_name) SEPARATOR ',') as `services` " +
            "FROM patient_records pr " +
            "LEFT JOIN patient_record_service_map prsm on prsm.patient_record_id = pr.patient_record_id " +
            "LEFT JOIN services se on prsm.service_id = se.service_id " +
            "LEFT JOIN specimens s on pr.patient_record_id = s.patient_record_id " +
            "LEFT JOIN labos l on l.labo_id = s.labo_id " +
            "WHERE pr.patient_record_id = :id " +
            "GROUP BY pr.patient_record_id " +
            "AND (`laboName` is null or `laboName` like %:laboName%)", nativeQuery = true)
    PatientRecordInterfaceDTO findPatientRecordDtoByPatientRecordId(Long id);

    PatientRecord findByPatientRecordId(Long id);
}
