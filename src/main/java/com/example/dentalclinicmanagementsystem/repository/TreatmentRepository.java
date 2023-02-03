package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.TreatmentDTO;
import com.example.dentalclinicmanagementsystem.dto.TreatmentInterfaceDTO;
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

    @Query(value = "select tbl.treatment_id as treatmentId, tbl.patient_id as patientId, tbl.patient_name as patientName, tbl.phone as phone, " +
            "SUM(tbl.total) as totalPrice, SUM(tbl.discount) as totalDiscount, SUM(tbl.total) - SUM(tbl.discount) as 'realCost' " +
            "from (select t.treatment_id, t.patient_id, p.patient_name, p.phone, tsm.current_price * tsm.amount as 'total', tsm.discount as 'discount' " +
            "from treatments t join patients p on t.patient_id = p.patient_id " +
            "join treatment_service_map tsm ON t.treatment_id = tsm.treatment_id " +
            "union " +
            "select t.treatment_id, t.patient_id, p.patient_name, p.phone, me.unit_price * me.amount as 'total', 0 as 'discount' " +
            "from treatments t join patients p on t.patient_id = p.patient_id " +
            "join patient_records pr ON pr.treatment_id = t.treatment_id AND pr.is_deleted = FALSE " +
            "left join material_export me on me.patient_record_id = pr.patient_record_id) tbl " +
            "WHERE (tbl.patient_name like %:patientName%)" +
            "AND (tbl.phone like %:phone%) " +
            "group by tbl.treatment_id, tbl.patient_id, tbl.patient_name, tbl.phone " +
            "order by tbl.treatment_id DESC", nativeQuery = true,
            countQuery = "select count(tbl.treatment_id)" +
            "from (select t.treatment_id, t.patient_id, p.patient_name, p.phone, tsm.current_price * tsm.amount as 'total', tsm.discount as 'discount' " +
            "from treatments t join patients p on t.patient_id = p.patient_id " +
            "join treatment_service_map tsm ON t.treatment_id = tsm.treatment_id " +
            "union " +
            "select t.treatment_id, t.patient_id, p.patient_name, p.phone, me.unit_price * me.amount as 'total', 0 as 'discount' " +
            "from treatments t join patients p on t.patient_id = p.patient_id " +
            "join patient_records pr ON pr.treatment_id = t.treatment_id AND pr.is_deleted = FALSE " +
            "left join material_export me on me.patient_record_id = pr.patient_record_id) tbl " +
            "WHERE (tbl.patient_name like %:patientName%)" +
            "AND (tbl.phone like %:phone%) " +
            "group by tbl.treatment_id, tbl.patient_id, tbl.patient_name, tbl.phone " +
            "order by tbl.treatment_id DESC")
    Page<TreatmentInterfaceDTO> getListBills(@Param("patientName") String patientName,
                                             @Param("phone") String phone,
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
