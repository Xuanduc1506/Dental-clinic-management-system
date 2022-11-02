package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.PatientRecordServiceMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRecordServiceMapRepository extends JpaRepository<PatientRecordServiceMap, Long> {

    void deleteAllByPatientRecordId(Long patientRecordId);
}
