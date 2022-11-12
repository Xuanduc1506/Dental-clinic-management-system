package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.constant.StatusConstant;
import com.example.dentalclinicmanagementsystem.dto.PatientRecordDTO;
import com.example.dentalclinicmanagementsystem.dto.PatientRecordInterfaceDTO;
import com.example.dentalclinicmanagementsystem.entity.*;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.TokenException;
import com.example.dentalclinicmanagementsystem.exception.UsingEntityException;
import com.example.dentalclinicmanagementsystem.mapper.PatientRecordMapper;
import com.example.dentalclinicmanagementsystem.repository.*;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
public class PatientRecordService extends AbstractService{

    @Autowired
    private PatientRecordRepository patientRecordRepository;

    @Autowired
    private PatientRecordMapper patientRecordMapper;

    @Autowired
    private PatientRecordServiceMapRepository patientRecordServiceMapRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private TreatmentServiceMapRepository treatmentServiceMapRepository;


    public Page<PatientRecordInterfaceDTO> getListPatientRecord(Long patientId, String reason, String diagnostic,
                                                                String causal, String date, String treatment,
                                                                String laboName, String serviceName,
                                                                Pageable pageable) {

        return patientRecordRepository.getAllByPatientId(patientId, reason, diagnostic, causal, date, treatment,
                 laboName, serviceName, pageable);

    }

    public PatientRecordInterfaceDTO getDetailRecord(Long id) {
        PatientRecordInterfaceDTO patientRecordInterfaceDTO = patientRecordRepository.findPatientRecordDtoByPatientRecordId(id);

        if (Objects.isNull(patientRecordInterfaceDTO)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }
        return patientRecordInterfaceDTO;

    }


    public PatientRecordDTO addPatientRecord(String token, Long patientId, PatientRecordDTO patientRecordDTO) {

        Long userId = getUserId(token);

        patientRecordDTO.setPatientRecordId(null);
        patientRecordDTO.setUserId(userId);
        patientRecordDTO.setDate(LocalDate.now());

        Patient patient = patientRepository.findByPatientIdAndIsDeleted(patientId, Boolean.FALSE);
        if (Objects.equals(patient.getStatus(), StatusConstant.DONE)
                || Objects.equals(patient.getStatus(), StatusConstant.NOT_TREATMENT)) {
            Treatment treatment = new Treatment();
            treatment.setPatientId(patientId);
            treatmentRepository.save(treatment);
            patientRecordDTO.setTreatmentId(treatment.getTreatmentId());
        }

        if (Objects.equals(patient.getStatus(), StatusConstant.TREATING)) {

            Treatment oldTreatment = treatmentRepository.findFirstByPatientIdOrderByTreatmentIdDesc(patientId);
            patientRecordDTO.setTreatmentId(oldTreatment.getTreatmentId());
        }

        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);
        patientRecordRepository.save(patientRecord);

        saveServiceToTreatmentAndRecord(patientRecordDTO, patientRecord);

        return patientRecordMapper.toDto(patientRecord);
    }

    public PatientRecordDTO updateRecord(Long id, PatientRecordDTO patientRecordDTO) {

        patientRecordDTO.setPatientRecordId(id);

        PatientRecord patientRecordDb = patientRecordRepository.findByPatientRecordId(id);
        if (Objects.isNull(patientRecordDb)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }

        if (patientRecordDb.getDate().plusDays(1).isAfter(LocalDate.now())) {
            throw new UsingEntityException(MessageConstant.PatientRecord.PATIENT_RECORD_OVER_DATE,
                    EntityName.PatientRecord.PATIENT_RECORD);
        }

        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);
        patientRecordRepository.save(patientRecord);

        treatmentServiceMapRepository.deleteAllByStartRecordId(id);
        patientRecordServiceMapRepository.deleteAllByPatientRecordId(id);

        saveServiceToTreatmentAndRecord(patientRecordDTO, patientRecord);

        return patientRecordMapper.toDto(patientRecord);
    }

    private void saveServiceToTreatmentAndRecord(PatientRecordDTO patientRecordDTO, PatientRecord patientRecord) {
        List<TreatmentServiceMap> treatmentServiceMaps = new ArrayList<>();
        List<PatientRecordServiceMap> patientRecordServiceMaps = new ArrayList<>();

        patientRecordDTO.getServiceDTOS().forEach(serviceDTO -> {
            if(Objects.equals(serviceDTO.getIsNew(), Boolean.TRUE)) {
                TreatmentServiceMap treatmentServiceMap = new TreatmentServiceMap();
                treatmentServiceMap.setTreatmentServiceMapId(null);
                treatmentServiceMap.setTreatmentId(patientRecordDTO.getTreatmentId());
                treatmentServiceMap.setCurrentPrice(serviceDTO.getPrice());
                treatmentServiceMap.setDiscount(serviceDTO.getDiscount());
                treatmentServiceMap.setServiceId(serviceDTO.getServiceId());
                treatmentServiceMap.setStartRecordId(patientRecord.getPatientRecordId());
                treatmentServiceMaps.add(treatmentServiceMap);
            }

            PatientRecordServiceMap patientRecordServiceMap = new PatientRecordServiceMap();
            patientRecordServiceMap.setPatientRecordServiceMapId(null);
            patientRecordServiceMap.setPatientRecordId(patientRecord.getPatientRecordId());
            patientRecordServiceMap.setServiceId(serviceDTO.getServiceId());
            patientRecordServiceMap.setStatus(serviceDTO.getStatus());
            patientRecordServiceMaps.add(patientRecordServiceMap);
        });

        patientRecordServiceMapRepository.saveAll(patientRecordServiceMaps);
        treatmentServiceMapRepository.saveAll(treatmentServiceMaps);
    }

    public void deleteRecord(Long id) {

        PatientRecord patientRecordDb = patientRecordRepository.findByPatientRecordId(id);
        if (Objects.isNull(patientRecordDb)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }

        if (patientRecordDb.getDate().plusDays(1).isAfter(LocalDate.now())) {
            throw new UsingEntityException(MessageConstant.PatientRecord.PATIENT_RECORD_OVER_DATE,
                    EntityName.PatientRecord.PATIENT_RECORD);
        }

        patientRecordServiceMapRepository.deleteAllByPatientRecordId(id);
        patientRecordRepository.delete(patientRecordDb);
    }
}
