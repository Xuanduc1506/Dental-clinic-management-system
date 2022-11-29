package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
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

    List<TreatmentServiceMap> findAllByTreatmentId(Long id);

    @Query("SELECT SUM(tsm.currentPrice - tsm.discount) FROM TreatmentServiceMap tsm " +
            "JOIN PatientRecord pr ON tsm.treatmentId = pr.treatmentId WHERE MONTH(pr.date) = :month")
    Integer getTotalIncomeInMonth(@Param("month") Integer month);

    @Query("SELECT SUM(tsm.currentPrice - tsm.discount) FROM TreatmentServiceMap tsm " +
            "JOIN PatientRecord pr ON tsm.treatmentId = pr.treatmentId WHERE YEAR(pr.date) = :year")
    Integer getTotalIncomeInYear(@Param("year") Integer year);

    @Query("SELECT tsm.serviceId FROM TreatmentServiceMap tsm WHERE tsm.startRecordId = :patientRecordId")
    List<Long> findAllServiceIdByPatientRecordId(@Param("patientRecordId")Long patientRecordId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO(s.serviceName, pr.date, tsm.currentPrice - tsm.discount) " +
            "FROM TreatmentServiceMap tsm JOIN Service s ON tsm.serviceId = s.serviceId " +
            "JOIN PatientRecord pr ON tsm.startRecordId = pr.patientRecordId " +
            "WHERE MONTH(pr.date) = :month AND YEAR(pr.date) = :year")
    List<IncomeDetailDTO> findAllServiceInTime(@Param("month") Integer month,
                                               @Param("year")Integer year);

}
