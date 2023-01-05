package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.PatientDTO;
import com.example.dentalclinicmanagementsystem.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.PatientDTO(p.patientId, p.patientName, p.birthdate," +
            "p.gender, p.address, p.phone, p.email, p.bodyPrehistory, p.teethPrehistory, p.status) " +
            "FROM Patient p WHERE p.isDeleted = FALSE " +
            "AND (:name is null or p.patientName like %:name%)" +
            "AND (:birthdate is null or p.birthdate like %:birthdate%)" +
            "AND (:gender is null or  p.gender = :gender)" +
            "AND (:address is null or p.address like %:address%)" +
            "AND (:phone is null or p.phone like %:phone%)" +
            "AND (:email is null or p.email like %:email%)" +
            "AND (:status = -1 or p.status = :status) " +
            "ORDER BY p.patientId DESC")
    Page<PatientDTO> getListPatient(@Param("name") String name,
                                    @Param("birthdate") String birthdate,
                                    @Param("gender") Boolean gender,
                                    @Param("address") String address,
                                    @Param("phone") String phone,
                                    @Param("email") String email,
                                    @Param("status") Integer status,
                                    Pageable pageable);


    Patient findByPatientIdAndIsDeleted(Long id, Boolean isDeleted);

    @Query("SELECT distinct(p) FROM Patient p Join Treatment t ON p.patientId = t.patientId " +
            "WHERE p.isDeleted = FALSE " +
            "AND (:name is null or p.patientName like %:name%) ")
    List<Patient> findAllByPatientNameContaining(@Param("name") String name);

    @Query("SELECT p FROM Patient p JOIN Treatment t ON p.patientId = t.treatmentId " +
            "JOIN PatientRecord pr ON t.treatmentId = pr.treatmentId " +
            "WHERE pr.patientRecordId= :patientRecordId AND p.isDeleted = FALSE ")
    Patient findByPatientRecordId(@Param("patientRecordId") Long patientRecordId);

    List<Patient> findAllByPatientIdInAndIsDeleted(List<Long> patientIds, Boolean isDeleted);
}
