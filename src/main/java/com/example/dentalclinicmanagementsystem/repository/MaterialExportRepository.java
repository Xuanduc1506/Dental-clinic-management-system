package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
import com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO;
import com.example.dentalclinicmanagementsystem.entity.MaterialExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaterialExportRepository extends JpaRepository<MaterialExport, Long> {

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO(me.materialExportId, m.materialId," +
            " me.amount,me.patientRecordId, me.unitPrice, m.materialName, pr.date, p.patientName) " +
            "FROM MaterialExport me JOIN Material m ON me.materialId = m.materialId " +
            "JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "JOIN Treatment t ON pr.treatmentId = t.treatmentId " +
            "JOIN Patient p ON t.patientId = p.patientId " +
            "WHERE me.isDelete = FALSE " +
            "AND (:materialName is null or m.materialName like %:materialName%)" +
            "AND (:date is null or pr.dateTemp like %:date%)" +
            "AND (:amount is null or me.amountTemp like %:amount%)" +
            "AND (:unitPrice is null or me.unitPriceTemp like %:unitPrice%)" +
            "AND (:patientName is null or p.patientName like %:patientName%) " +
            "ORDER BY me.materialExportId DESC")
    Page<MaterialExportDTO> getListMaterialExport(@Param("materialName")String materialName,
                                                  @Param("date")String date,
                                                  @Param("amount")String amount,
                                                  @Param("unitPrice")String unitPrice,
                                                  @Param("patientName")String patientName,
                                                  Pageable pageable);


    MaterialExport findByMaterialExportIdAndIsDelete(Long id, Boolean isDelete);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO(me.materialExportId,m.materialId," +
            " me.amount, me.patientRecordId, me.unitPrice, m.materialName, me.isShow) " +
            "FROM MaterialExport me JOIN Material m ON me.materialId = m.materialId WHERE me.patientRecordId = :patientRecordId")
    List<MaterialExportDTO> findAllDTOByPatientRecordId(Long patientRecordId);

    List<MaterialExport> findAllByPatientRecordId(Long patientRecordId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO(me.materialExportId, me.materialId, " +
            "me.amount, me.patientRecordId, me.unitPrice, m.materialName, pr.date, p.patientName) " +
            "FROM MaterialExport me JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "JOIN Material m ON me.materialId = m.materialId " +
            "JOIN Treatment t ON t.treatmentId = pr.treatmentId " +
            "JOIN Patient p ON p.patientId = t.patientId " +
            "WHERE me.materialExportId = :id AND me.isDelete = FALSE ")
    MaterialExportDTO getDetail(@Param("id") Long id);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO(m.materialName, pr.date, me.unitPrice * me.amount) " +
            "FROM MaterialExport me JOIN Material m ON me.materialId = m.materialId " +
            "JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "WHERE me.unitPrice <> 0 AND pr.date BETWEEN :startDate AND :endDate")
    List<IncomeDetailDTO> findAllMaterialExportInTime(@Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO(me.materialExportId, me.materialId, " +
            "me.amount, me.patientRecordId, me.unitPrice, m.materialName, pr.date) FROM MaterialExport me " +
            "JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "JOIN Treatment t ON pr.treatmentId = t.treatmentId JOIN Material m ON me.materialId = m.materialId " +
            "WHERE t.patientId = :patientId")
    List<MaterialExportDTO> getListMaterialExportOfPatient(@Param("patientId") Long patientId);

    List<MaterialExport> findAllByMaterialExportIdInAndIsDelete(List<Long> exportIds, Boolean isDeleted);

    @Query("SELECT me FROM MaterialExport me JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "WHERE pr.treatmentId = :treatmentId")
    List<MaterialExport> findAllByTreatmentId(@Param("treatmentId")Long treatmentId);
}
