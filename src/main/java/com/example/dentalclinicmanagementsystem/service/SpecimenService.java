package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.constant.StatusConstant;
import com.example.dentalclinicmanagementsystem.dto.SpecimenHistoryDTO;
import com.example.dentalclinicmanagementsystem.dto.SpecimensDTO;
import com.example.dentalclinicmanagementsystem.entity.Labo;
import com.example.dentalclinicmanagementsystem.entity.PatientRecord;
import com.example.dentalclinicmanagementsystem.entity.Specimen;
import com.example.dentalclinicmanagementsystem.entity.SpecimenHistory;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.mapper.SpecimenHistoryMapper;
import com.example.dentalclinicmanagementsystem.mapper.SpecimenMapper;
import com.example.dentalclinicmanagementsystem.repository.LaboRepository;
import com.example.dentalclinicmanagementsystem.repository.PatientRecordRepository;
import com.example.dentalclinicmanagementsystem.repository.SpecimenHistoryRepository;
import com.example.dentalclinicmanagementsystem.repository.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class SpecimenService {

    @Autowired
    private SpecimenRepository specimenRepository;

    @Autowired
    private PatientRecordRepository patientRecordRepository;

    @Autowired
    private LaboRepository laboRepository;

    @Autowired
    private SpecimenHistoryRepository specimenHistoryRepository;

    @Autowired
    private SpecimenMapper specimenMapper;

    @Autowired
    private SpecimenHistoryMapper specimenHistoryMapper;

    public SpecimensDTO getDetail(Long id) {

        SpecimensDTO specimensDTO = specimenRepository.getDetail(id);
        if (Objects.isNull(specimensDTO)) {
            throw new EntityNotFoundException(MessageConstant.Specimen.SPECIMEN_NOT_FOUND,
                    EntityName.Specimen.SPECIMEN, EntityName.Specimen.SPECIMEN_ID);
        }

        specimensDTO.setButtonUseEnable(Objects.equals(specimensDTO.getStatus(), StatusConstant.LABO_DELIVERY));

        specimensDTO.setButtonReportEnable(Objects.equals(specimensDTO.getStatus(), StatusConstant.LABO_DELIVERY)
                || Objects.equals(specimensDTO.getStatus(), StatusConstant.PATIENT_USED));

        return specimensDTO;
    }

    public SpecimensDTO addSpecimen(SpecimensDTO specimensDTO) {
        specimensDTO.setSpecimenId(null);
        specimensDTO.setIsDeleted(Boolean.FALSE);

        return saveSpecimen(specimensDTO);
    }

    public SpecimensDTO updateSpecimen(Long id, SpecimensDTO specimensDTO) {
        specimensDTO.setSpecimenId(id);
        specimensDTO.setIsDeleted(Boolean.FALSE);

        Specimen specimen = specimenRepository.findBySpecimenIdAndIsDeleted(id, Boolean.FALSE);
        if (Objects.isNull(specimen)) {
            throw new EntityNotFoundException(MessageConstant.Specimen.SPECIMEN_NOT_FOUND,
                    EntityName.Specimen.SPECIMEN, EntityName.Specimen.PATIENT_RECORD_NOT_FOUND);
        }

        return saveSpecimen(specimensDTO);
    }

    private SpecimensDTO saveSpecimen(SpecimensDTO specimensDTO) {
        PatientRecord patientRecord = patientRecordRepository.findByPatientRecordIdAndIsDeleted(specimensDTO.getPatientRecordId(), Boolean.FALSE);
        if (Objects.isNull(patientRecord)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.Specimen.SPECIMEN, EntityName.Specimen.PATIENT_RECORD_NOT_FOUND);
        }

        Labo labo = laboRepository.findByLaboIdAndIsDeleted(specimensDTO.getLaboId(), Boolean.FALSE);
        if (Objects.isNull(labo)) {
            throw new EntityNotFoundException(MessageConstant.Labo.LABO_NOT_FOUND,
                    EntityName.Specimen.SPECIMEN, EntityName.Specimen.LABO_NOT_FOUND);
        }

        if (Objects.nonNull(specimensDTO.getReceiveDate()) && Objects.nonNull(specimensDTO.getDeliveryDate())) {
            if (specimensDTO.getReceiveDate().isAfter(specimensDTO.getDeliveryDate())) {
                throw new AccessDenyException(MessageConstant.Specimen.RECEIVE_DATE_MUST_BEFORE_DELIVERY_DATE, EntityName.Specimen.SPECIMEN);
            }
            specimensDTO.setStatus(StatusConstant.LABO_DELIVERY);
        } else if (Objects.nonNull(specimensDTO.getReceiveDate()) && Objects.isNull(specimensDTO.getDeliveryDate())) {
            specimensDTO.setStatus(StatusConstant.LABO_RECEIVE);
        } else if (Objects.isNull(specimensDTO.getReceiveDate()) && Objects.isNull(specimensDTO.getDeliveryDate())) {
            specimensDTO.setStatus(StatusConstant.PREPARE_SPECIMEN);
        } else {
            throw new AccessDenyException(MessageConstant.Specimen.RECEIVE_DATE_MUST_BEFORE_DELIVERY_DATE, EntityName.Specimen.SPECIMEN);
        }

        Specimen specimen = specimenMapper.toEntity(specimensDTO);
        return specimenMapper.toDto(specimenRepository.save(specimen));
    }


    public void deleteSpecimen(Long id) {

        Specimen specimen = specimenRepository.findBySpecimenIdAndIsDeleted(id, Boolean.FALSE);
        if (Objects.isNull(specimen)) {
            throw new EntityNotFoundException(MessageConstant.Specimen.SPECIMEN_NOT_FOUND,
                    EntityName.Specimen.SPECIMEN, EntityName.Specimen.PATIENT_RECORD_NOT_FOUND);
        }
        specimen.setIsDeleted(Boolean.TRUE);
        specimenRepository.save(specimen);
    }

    public Page<SpecimensDTO> getPageSpecimens(String specimenName, String patientName, String receiveDate, String deliveryDate, String usedDate, String laboName, String serviceName, Integer status, Pageable pageable) {

        return specimenRepository.getPageSpecimens(specimenName, patientName, receiveDate, deliveryDate, usedDate, laboName, serviceName, status, pageable);
    }

    public void useSpecimen(Long id) {
        Specimen specimen = specimenRepository.findBySpecimenIdAndIsDeleted(id, Boolean.FALSE);
        if (Objects.isNull(specimen)) {
            throw new EntityNotFoundException(MessageConstant.Specimen.SPECIMEN_NOT_FOUND,
                    EntityName.Specimen.SPECIMEN, EntityName.Specimen.PATIENT_RECORD_NOT_FOUND);
        }

        specimen.setStatus(StatusConstant.PATIENT_USED);
        specimen.setUsedDate(LocalDate.now());
        specimenRepository.save(specimen);
    }

    public SpecimensDTO reportSpecimen(Long id, SpecimenHistoryDTO specimenHistoryDTO) {

        Specimen specimen = specimenRepository.findBySpecimenIdAndIsDeleted(id, Boolean.FALSE);
        if (Objects.isNull(specimen)) {
            throw new EntityNotFoundException(MessageConstant.Specimen.SPECIMEN_NOT_FOUND,
                    EntityName.Specimen.SPECIMEN, EntityName.Specimen.PATIENT_RECORD_NOT_FOUND);
        }

        specimenHistoryDTO.setSpecimenHistoryId(null);
        specimenHistoryDTO.setSpecimenId(id);
        specimenHistoryDTO.setReceiveDate(specimen.getReceiveDate());
        specimenHistoryDTO.setDeliveryDate(specimen.getDeliveryDate());
        specimenHistoryDTO.setUsedDate(specimen.getUsedDate());
        specimenHistoryDTO.setAmount(specimen.getAmount());
        specimenHistoryDTO.setUnitPrice(specimen.getUnitPrice());

        SpecimenHistory specimenHistory = specimenHistoryMapper.toEntity(specimenHistoryDTO);
        specimenHistoryRepository.save(specimenHistory);

        specimen.setReceiveDate(null);
        specimen.setDeliveryDate(null);
        specimen.setUsedDate(null);
        specimen.setStatus(StatusConstant.SPECIMEN_ERROR);
        specimenRepository.save(specimen);
        return specimenMapper.toDto(specimen);
    }

    public void laboReceive(List<SpecimensDTO> specimensDTOS) {

        List<SpecimensDTO> specimensDTOSReceived = specimensDTOS.stream()
                .filter(specimensDTO -> Boolean.TRUE.equals(specimensDTO.getChecked()))
                .peek(s -> {
                    s.setReceiveDate(LocalDate.now());
                    s.setStatus(StatusConstant.LABO_RECEIVE);
                }).collect(Collectors.toList());

        List<Specimen> specimens = specimenMapper.toEntity(specimensDTOSReceived);
        specimenRepository.saveAll(specimens);
    }

    public void laboDelivery(List<SpecimensDTO> specimensDTOS) {

        List<SpecimensDTO> specimensDTOSReceived = specimensDTOS.stream()
                .filter(specimensDTO -> Boolean.TRUE.equals(specimensDTO.getChecked()))
                .peek(s -> {
                    s.setDeliveryDate(LocalDate.now());
                    s.setStatus(StatusConstant.LABO_DELIVERY);
                }).collect(Collectors.toList());

        List<Specimen> specimens = specimenMapper.toEntity(specimensDTOSReceived);
        specimenRepository.saveAll(specimens);
    }
}
