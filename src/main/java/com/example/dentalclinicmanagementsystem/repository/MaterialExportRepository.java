package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO;
import com.example.dentalclinicmanagementsystem.entity.MaterialExport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialExportRepository extends JpaRepository<MaterialExport, Long> {

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO(me.materialExportId, m.materialId," +
            " me.amount,me.patientRecordId, me.totalPrice, m.materialName, pr.date, p.patientName) " +
            "FROM MaterialExport me JOIN Material m ON me.materialId = m.materialId " +
            "JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "JOIN Treatment t ON pr.treatmentId = t.treatmentId " +
            "JOIN Patient p ON t.patientId = p.patientId " +
            "WHERE me.isDelete = FALSE " +
            "AND (:materialName is null or m.materialName like %:materialName%)" +
            "AND (:date is null or pr.dateTemp like %:date%)" +
            "AND (:amount is null or me.amountTemp like :amount)" +
            "AND (:totalPrice is null or me.totalPriceTemp like %:totalPrice%)" +
            "AND (:patientName is null or p.patientName like %:patientName%)")
    Page<MaterialExportDTO> getListMaterialExport(@Param("materialName")String materialName,
                                                  @Param("date")String date,
                                                  @Param("amount")String amount,
                                                  @Param("totalPrice")String totalPrice,
                                                  @Param("patientName")String patientName,
                                                  Pageable pageable);


    MaterialExport findByMaterialExportIdAndIsDelete(Long id, Boolean isDelete);

    List<MaterialExport> findAllByPatientRecordId(Long patientRecordId);

    @Query("SELECT SUM(me.totalPrice) FROM MaterialExport me JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "WHERE me.isDelete = FALSE AND MONTH(pr.date) = :month")
    Integer getIncomeOfMaterialInMonth(@Param("month") Integer month);

    @Query("SELECT SUM(me.totalPrice) FROM MaterialExport me JOIN PatientRecord pr ON me.patientRecordId = pr.patientRecordId " +
            "WHERE me.isDelete = FALSE AND MONTH(pr.date) = :year")
    Integer getIncomeOfMaterialInYear(@Param("year") Integer year);
}
