package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.constant.StatusConstant;
import com.example.dentalclinicmanagementsystem.dto.PatientDTO;
import com.example.dentalclinicmanagementsystem.entity.Patient;
import com.example.dentalclinicmanagementsystem.entity.PatientRecord;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.UsingEntityException;
import com.example.dentalclinicmanagementsystem.mapper.PatientMapper;
import com.example.dentalclinicmanagementsystem.repository.PatientRecordRepository;
import com.example.dentalclinicmanagementsystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class PatientService {


    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientRecordRepository patientRecordRepository;

    @Autowired
    private PatientMapper patientMapper;


    public Page<PatientDTO> getListPatient(String name, String birthdate, Boolean gender, String address, String phone,
                                           String email, String bodyPrehistory, String teethPrehistory, Integer status,
                                           Pageable pageable) {


        return patientRepository.getListPatient(name, birthdate, gender, address, phone, email, bodyPrehistory,
                teethPrehistory, status, pageable);
    }

    public PatientDTO getDetailPatient(Long id) {

        Patient patient = patientRepository.findByPatientIdAndIsDeleted(id, Boolean.FALSE);

        if (Objects.isNull(patient)) {
            throw new EntityNotFoundException(MessageConstant.Patient.PATIENT_NOT_FOUND,
                    EntityName.Patient.PATIENT, EntityName.Patient.PATIENT_ID);
        }

        return patientMapper.toDto(patient);
    }

    public PatientDTO addPatient(PatientDTO patientDTO) {
        patientDTO.setPatientId(null);
        patientDTO.setIsDeleted(Boolean.FALSE);
        patientDTO.setStatus(StatusConstant.NOT_TREATMENT);

        Patient patient = patientMapper.toEntity(patientDTO);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        patientDTO.setPatientId(id);

        Patient patientDb = patientRepository.findByPatientIdAndIsDeleted(id, Boolean.FALSE);

        if (Objects.isNull(patientDb)) {
            throw new EntityNotFoundException(MessageConstant.Patient.PATIENT_NOT_FOUND,
                    EntityName.Patient.PATIENT, EntityName.Patient.PATIENT_ID);
        }
        Patient patient = patientMapper.toEntity(patientDTO);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public void deletePatient(Long id) {

        Patient patient = patientRepository.findByPatientIdAndIsDeleted(id, Boolean.FALSE);

        if (Objects.isNull(patient)) {
            throw new EntityNotFoundException(MessageConstant.Patient.PATIENT_NOT_FOUND,
                    EntityName.Patient.PATIENT, EntityName.Patient.PATIENT_ID);
        }

        List<PatientRecord> patientRecords = patientRecordRepository.getAllByPatientId(id);
        if (!CollectionUtils.isEmpty(patientRecords)) {
            throw new UsingEntityException(MessageConstant.Patient.PATIENT_HAVE_BEEN_USED, EntityName.Patient.PATIENT);
        }
        patient.setIsDeleted(Boolean.TRUE);

        patientRepository.save(patient);
    }

    public List<PatientDTO> getAllPatient(String name) {
        return patientMapper.toDto(patientRepository.findAllByPatientNameContaining(name));
    }
}
