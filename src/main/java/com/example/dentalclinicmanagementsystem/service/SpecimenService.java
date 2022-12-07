package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.SpecimensDTO;
import com.example.dentalclinicmanagementsystem.entity.Labo;
import com.example.dentalclinicmanagementsystem.entity.PatientRecord;
import com.example.dentalclinicmanagementsystem.entity.Specimen;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.mapper.SpecimenMapper;
import com.example.dentalclinicmanagementsystem.repository.LaboRepository;
import com.example.dentalclinicmanagementsystem.repository.PatientRecordRepository;
import com.example.dentalclinicmanagementsystem.repository.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

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
    private SpecimenMapper specimenMapper;

    public SpecimensDTO getDetail(Long id) {

        SpecimensDTO specimensDTO = specimenRepository.getDetail(id);
        if (Objects.isNull(specimensDTO)) {
            throw new EntityNotFoundException(MessageConstant.Specimen.SPECIMEN_NOT_FOUND,
                    EntityName.Specimen.SPECIMEN, EntityName.Specimen.SPECIMEN_ID);
        }

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

        if (specimensDTO.getReceiveDate().isAfter(specimensDTO.getDeliveryDate())) {
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
}
