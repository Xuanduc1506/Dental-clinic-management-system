package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.TreatmentDTO;
import com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO;
import com.example.dentalclinicmanagementsystem.entity.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    Treatment findFirstByPatientIdOrderByTreatmentIdDesc(Long patientId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.TreatmentDTO(t.treatmentId, t.patientId, p.patientName, " +
            "p.phone, (SUM(tsm.currentPrice * tsm.amount) + COALESCE(SUM(me.unitPrice * me.amount), 0)), SUM(tsm.discount), " +
            "(SUM(tsm.currentPrice * tsm.amount) + COALESCE(SUM(me.unitPrice * me.amount), 0) - SUM(tsm.discount))) " +
            "FROM Treatment t JOIN Patient p ON t.patientId = p.patientId " +
            "JOIN TreatmentServiceMap tsm ON t.treatmentId = tsm.treatmentId " +
            "JOIN PatientRecord pr ON pr.treatmentId = t.treatmentId " +
            "LEFT JOIN MaterialExport me ON me.patientRecordId = pr.patientRecordId " +
            "WHERE (:patientName is null or p.patientName like %:patientName%)" +
            "AND (:phone is null or p.phone like %:phone%)" +
            "GROUP BY t.treatmentId " +
            "ORDER BY t.treatmentId DESC ")
    Page<TreatmentDTO> getListBills(@Param("patientName")String patientName,
                                    @Param("phone")String phone,
                                    Pageable pageable);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.TreatmentDTO(t.treatmentId, t.patientId, p.patientName, " +
            "p.phone, (SUM(tsm.currentPrice * tsm.amount) + COALESCE(SUM(me.unitPrice * me.amount), 0)), SUM(tsm.discount), " +
            "(SUM(tsm.currentPrice * tsm.amount) + COALESCE(SUM(me.unitPrice * me.amount), 0) - SUM(tsm.discount))) " +
            "FROM Treatment t JOIN Patient p ON t.patientId = p.patientId " +
            "JOIN TreatmentServiceMap tsm ON t.treatmentId = tsm.treatmentId " +
            "JOIN PatientRecord pr ON pr.treatmentId = t.treatmentId " +
            "LEFT JOIN MaterialExport me ON me.patientRecordId = pr.patientRecordId " +
            "WHERE t.treatmentId = :id " +
            "GROUP BY t.treatmentId")
    TreatmentDTO getTreatmentById(Long id);

    Treatment findByTreatmentId(Long treatmentId);

    @Query("SELECT t.patientId FROM Treatment t WHERE t.treatmentId = :treatmentId")
    Long findPatientIdByTreatmentId(@Param("treatmentId") Long treatmentId);
}
