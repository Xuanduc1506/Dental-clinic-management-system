package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.PatientRecordDTO;
import com.example.dentalclinicmanagementsystem.dto.PatientRecordInterfaceDTO;
import com.example.dentalclinicmanagementsystem.entity.PatientRecord;
import com.example.dentalclinicmanagementsystem.entity.PatientRecordServiceMap;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.TokenException;
import com.example.dentalclinicmanagementsystem.exception.UsingEntityException;
import com.example.dentalclinicmanagementsystem.mapper.PatientRecordMapper;
import com.example.dentalclinicmanagementsystem.repository.PatientRecordRepository;
import com.example.dentalclinicmanagementsystem.repository.PatientRecordServiceMapRepository;
import com.example.dentalclinicmanagementsystem.repository.ServiceRepository;
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


@Service
@Transactional
public class PatientRecordService {

    @Autowired
    private PatientRecordRepository patientRecordRepository;

    @Autowired
    private PatientRecordMapper patientRecordMapper;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PatientRecordServiceMapRepository patientRecordServiceMapRepository;


    public Page<PatientRecordInterfaceDTO> getListPatientRecord(Long patientId, String reason, String diagnostic,
                                                                String causal, String date, String treatment,
                                                                String totalCost, String realCost, String debit,
                                                                String costIncurred, String laboName, String serviceName,
                                                                Pageable pageable) {

        return patientRecordRepository.getAllByPatientId(patientId, reason, diagnostic, causal, date, treatment,
                totalCost, realCost, debit, costIncurred, laboName, serviceName, pageable);

    }

    public PatientRecordInterfaceDTO getDetailRecord(Long id) {
        PatientRecordInterfaceDTO patientRecordInterfaceDTO = patientRecordRepository.findPatientRecordDtoByPatientRecordId(id);

        if (Objects.isNull(patientRecordInterfaceDTO)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }
        return patientRecordInterfaceDTO;

    }

    private Long getUserId(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new TokenException("Token invalid");
        }

        return Long.parseLong(Jwts.parser()
                .setSigningKey("dental_clinic")
                .parseClaimsJws(token)
                .getBody().getId());
    }

    public PatientRecordDTO addPatientRecord(String token, Long patientId, PatientRecordDTO patientRecordDTO) {

        Long userId = getUserId(token);

        patientRecordDTO.setPatientRecordId(null);
        patientRecordDTO.setUserId(userId);
        patientRecordDTO.setPatientId(patientId);
        patientRecordDTO.setDate(LocalDate.now());

        if (Objects.nonNull(patientRecordDTO.getPreRecordId())) {
            PatientRecord preRecord = patientRecordRepository.findByPatientRecordId(patientRecordDTO.getPreRecordId());
            if (Objects.isNull(preRecord)) {
                throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                        EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PRE_RECORD);
            }
        }

        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);
        patientRecordRepository.save(patientRecord);

        List<com.example.dentalclinicmanagementsystem.entity.Service> services = serviceRepository
                .findAllByServiceIdIn(patientRecordDTO.getServiceId());

        if (Objects.equals(services.size(), patientRecordDTO.getServiceId().size())) {
            throw new EntityNotFoundException(MessageConstant.Service.SERVICE_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.Service.SERVICE_NAME);

        }

        savePatientRecordServiceMap(patientRecordDTO, patientRecord);
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

        List<com.example.dentalclinicmanagementsystem.entity.Service> services = serviceRepository
                .findAllByServiceIdIn(patientRecordDTO.getServiceId());

        if (Objects.equals(services.size(), patientRecordDTO.getServiceId().size())) {
            throw new EntityNotFoundException(MessageConstant.Service.SERVICE_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.Service.SERVICE_NAME);

        }

        patientRecordServiceMapRepository.deleteAllByPatientRecordId(id);

        savePatientRecordServiceMap(patientRecordDTO, patientRecord);
        return patientRecordMapper.toDto(patientRecord);
    }

    private void savePatientRecordServiceMap(PatientRecordDTO patientRecordDTO, PatientRecord patientRecord) {
        List<PatientRecordServiceMap> patientRecordServiceMaps = new ArrayList<>();

        patientRecordDTO.getServiceId().forEach(serviceId -> {
            PatientRecordServiceMap patientRecordServiceMap = new PatientRecordServiceMap();
            patientRecordServiceMap.setPatientRecordServiceMapId(null);
            patientRecordServiceMap.setServiceId(serviceId);
            patientRecordServiceMap.setPatientRecordId(patientRecord.getPatientRecordId());
            patientRecordServiceMaps.add(patientRecordServiceMap);
        });

        patientRecordServiceMapRepository.saveAll(patientRecordServiceMaps);

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
