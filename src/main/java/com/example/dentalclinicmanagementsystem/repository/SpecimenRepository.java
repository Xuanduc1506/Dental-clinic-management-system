package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
import com.example.dentalclinicmanagementsystem.dto.SpecimensDTO;
import com.example.dentalclinicmanagementsystem.entity.Specimen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecimenRepository extends JpaRepository<Specimen, Long> {

    @Query("SELECT SUM(s.unitPrice * s.amount) FROM Specimen s WHERE s.laboId = :id " +
            "AND s.isDeleted = FALSE " +
            "AND (:month is null or MONTH(s.receiveDate) = :month) " +
            "AND (:year is null or YEAR(s.receiveDate) = :year)")
    Integer findTotalCostInTime(@Param("id")Long id,
                                @Param("month")Integer month,
                                @Param("year")Integer year);
    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO(l.laboName, s.receiveDate, s.unitPrice * s.amount) " +
            "FROM Labo l JOIN Specimen s ON l.laboId = s.laboId WHERE s.receiveDate is not null and s.isDeleted = FALSE ")
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

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.SpecimensDTO(s.specimenId, s.specimenName, s.receiveDate, " +
            "s.deliveryDate, s.amount, s.unitPrice, s.laboId, s.status, s.serviceId, ser.serviceName, s.patientRecordId, " +
            "p.patientName, l.laboName, s.usedDate, p.patientId) " +
            "FROM Specimen s JOIN PatientRecord pr ON s.patientRecordId = pr.patientRecordId " +
            "JOIN Service ser ON s.serviceId = ser.serviceId " +
            "JOIN Treatment t ON pr.treatmentId = t.treatmentId " +
            "JOIN Patient p ON t.patientId = p.patientId " +
            "JOIN Labo l ON s.laboId = l.laboId " +
            "WHERE s.specimenId = :id AND s.isDeleted = FALSE")
    SpecimensDTO getDetail(@Param("id") Long id);

    Specimen findBySpecimenIdAndIsDeleted(Long id, Boolean isDeleted);

    void deleteAllByPatientRecordId(Long patientRecordId);

    List<Specimen> findAllByPatientRecordIdInAndServiceIdInAndIsDeleted(List<Long> patientRecordId, List<Long> serviceId, Boolean isDeleted);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.SpecimensDTO(s.specimenId, s.specimenName, s.receiveDate, " +
            "s.deliveryDate, s.amount, s.unitPrice, s.laboId, s.status, s.serviceId, ser.serviceName, s.patientRecordId, " +
            "p.patientName, l.laboName, s.usedDate, p.patientId) FROM Specimen s " +
            "JOIN PatientRecord pr ON s.patientRecordId = pr.patientRecordId " +
            "JOIN Treatment t ON pr.treatmentId = t.treatmentId " +
            "JOIN Patient p ON t.patientId = p.patientId " +
            "JOIN Service ser ON s.serviceId = ser.serviceId " +
            "JOIN Labo l ON s.laboId = l.laboId WHERE s.isDeleted = FALSE " +
            "AND (:specimenName is null or s.specimenName like %:specimenName%) " +
            "AND (:patientName is null or p.patientName like %:patientName%) " +
            "AND (:receiveDate is null or s.tempReceiveDate is null or s.tempReceiveDate like %:receiveDate%) " +
            "AND (:usedDate is null or s.tempUsedDate is null or  s.tempUsedDate like %:usedDate%) " +
            "AND (:deliveryDate is null or s.tempDeliveryDate is null or s.tempDeliveryDate like %:deliveryDate%) " +
            "AND (:laboName is null or l.laboName like %:laboName%) " +
            "AND (:serviceName is null or ser.serviceName like %:serviceName%) " +
            "AND (:status is null or s.status = :status) ORDER BY s.specimenId DESC")
    Page<SpecimensDTO> getPageSpecimens(@Param("specimenName") String specimenName,
                                        @Param("patientName") String patientName,
                                        @Param("receiveDate") String receiveDate,
                                        @Param("deliveryDate") String deliveryDate,
                                        @Param("usedDate") String usedDate,
                                        @Param("laboName") String laboName,
                                        @Param("serviceName") String serviceName,
                                        @Param("status") Integer status,
                                        Pageable pageable);
}
