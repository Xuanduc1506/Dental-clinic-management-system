package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
import com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO;
import com.example.dentalclinicmanagementsystem.entity.TreatmentServiceMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentServiceMapRepository extends JpaRepository<TreatmentServiceMap, Long> {

    @Query("SELECT sum(tsm.currentPrice) - sum(tsm.discount) FROM TreatmentServiceMap tsm " +
            "WHERE tsm.treatmentId = :treatmentId")
    Integer getTotalMoney(@Param("treatmentId") Long treatmentId);

    void deleteAllByStartRecordId(Long patientRecordId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO(tsm.serviceId, tsm.currentPrice, tsm.discount, s.serviceName) FROM TreatmentServiceMap tsm " +
            "JOIN Service s ON tsm.serviceId = s.serviceId WHERE tsm.treatmentId = :id")
    List<TreatmentServiceMapDTO> findAllByTreatmentId(Long id);

    @Query("SELECT tsm.serviceId FROM TreatmentServiceMap tsm WHERE tsm.startRecordId = :patientRecordId")
    List<Long> findAllServiceIdByPatientRecordId(@Param("patientRecordId")Long patientRecordId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO(s.serviceName, pr.date, tsm.currentPrice - tsm.discount) " +
            "FROM TreatmentServiceMap tsm JOIN Service s ON tsm.serviceId = s.serviceId " +
            "JOIN PatientRecord pr ON tsm.startRecordId = pr.patientRecordId " +
            "WHERE MONTH(pr.date) = :month AND YEAR(pr.date) = :year")
    List<IncomeDetailDTO> findAllServiceInTime(@Param("month") Integer month,
                                               @Param("year")Integer year);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO(tsm.serviceId, tsm.currentPrice, " +
            "tsm.discount, s.serviceName)" +
            " FROM TreatmentServiceMap tsm JOIN Service s ON tsm.serviceId = s.serviceId" +
            " WHERE tsm.treatmentId = :treatmentId " +
            "AND tsm.startRecordId = " +
            "(SELECT MAX(tsm2.startRecordId) FROM TreatmentServiceMap tsm2 WHERE tsm2.treatmentId = :treatmentId) ")
    List<TreatmentServiceMapDTO> findAllServiceInLastRecord(@Param("treatmentId") Long treatmentId);


    @Query("SELECT tsm FROM TreatmentServiceMap tsm JOIN Treatment t ON tsm.treatmentId = t.treatmentId " +
            "WHERE t.patientId = :patientId AND tsm.serviceId IN :serviceIds")
    List<TreatmentServiceMap> findAllByPatientId(@Param("patientId") Long patientId,
                                                 @Param("serviceIds") List<Long> serviceIds);
}
