package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
import com.example.dentalclinicmanagementsystem.dto.SpecimensDTO;
import com.example.dentalclinicmanagementsystem.entity.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecimenRepository extends JpaRepository<Specimen, Long> {

    @Query("SELECT SUM(s.unitPrice * s.amount) FROM Specimen s WHERE s.laboId = :id " +
            "AND s.isDeleted = FALSE " +
            "AND (:month is null or MONTH(s.receiveDate) = :month) " +
            "AND (:year is null or YEAR(s.receiveDate) = :year)")
    Integer findTotalCostInTime(@Param("id")Long id,
                                @Param("month")Integer month,
                                @Param("year")Integer year);
    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO(l.laboName, s.receiveDate, s.unitPrice * s.amount) " +
            "FROM Labo l JOIN Specimen s ON l.laboId = s.laboId WHERE s.isDeleted = FALSE ")
    List<IncomeDetailDTO> findTotalPrice(@Param("month")Integer month,
                                         @Param("year")Integer year);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.SpecimensDTO(s.specimenId, s.specimenName, " +
            "s.receiveDate, s.deliveryDate, s.amount, s.unitPrice, s.laboId, pr.patientRecordId, p.patientName) " +
            "FROM Specimen s JOIN Labo l ON s.laboId = l.laboId " +
            "JOIN PatientRecord pr ON s.patientRecordId = pr.patientRecordId " +
            "JOIN Treatment t ON pr.treatmentId = t.treatmentId " +
            "JOIN Patient p ON t.patientId = p.patientId WHERE s.laboId = :id " +
            "AND s.isDeleted = FALSE " +
            "AND (:month is null or MONTH(s.receiveDate) = :month) " +
            "AND (:year is null or YEAR(s.receiveDate) = :year)")
    List<SpecimensDTO> findAllByLaboIdInTime(@Param("id")Long id,
                                             @Param("month")Integer month,
                                             @Param("year")Integer year);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.SpecimensDTO(s.specimenId, s.specimenName, " +
            "s.receiveDate, s.deliveryDate, s.amount, s.unitPrice, s.laboId, pr.patientRecordId, p.patientName) " +
            "FROM Specimen s JOIN PatientRecord pr ON s.patientRecordId = pr.patientRecordId " +
            "JOIN Treatment t ON pr.treatmentId = t.treatmentId " +
            "JOIN Patient p ON t.patientId = p.patientId " +
            "WHERE s.specimenId = :id AND s.isDeleted = FALSE")
    SpecimensDTO getDetail(@Param("id") Long id);

    Specimen findBySpecimenIdAndIsDeleted(Long id, Boolean isDeleted);

    void deleteAllByPatientRecordId(Long patientRecordId);
}
