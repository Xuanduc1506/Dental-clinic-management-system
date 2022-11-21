package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO;
import com.example.dentalclinicmanagementsystem.entity.Material;
import com.example.dentalclinicmanagementsystem.entity.MaterialExport;
import com.example.dentalclinicmanagementsystem.entity.PatientRecord;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.UsingEntityException;
import com.example.dentalclinicmanagementsystem.mapper.MaterialExportMapper;
import com.example.dentalclinicmanagementsystem.repository.MaterialExportRepository;
import com.example.dentalclinicmanagementsystem.repository.MaterialRepository;
import com.example.dentalclinicmanagementsystem.repository.PatientRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Objects;

@Service
@Transactional
public class MaterialExportService {

    @Autowired
    private MaterialExportRepository materialExportRepository;

    @Autowired
    private MaterialExportMapper materialExportMapper;

    @Autowired
    private PatientRecordRepository patientRecordRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public Page<MaterialExportDTO> getListExport(String materialName, String date, String amount, String totalPrice,
                                                 String patientName, Pageable pageable) {
        return materialExportRepository.getListMaterialExport(materialName, date, amount, totalPrice, patientName, pageable);
    }

    public MaterialExportDTO getDetailMaterialExport(Long id) {

        MaterialExport materialExport = materialExportRepository.findByMaterialExportIdAndIsDelete(id, Boolean.FALSE);
        if (Objects.isNull(materialExport)) {
            throw new EntityNotFoundException(MessageConstant.MaterialExport.MATERIAL_EXPORT_NOT_FOUND,
                    EntityName.MaterialExport.MATERIAL_EXPORT, EntityName.MaterialExport.MATERIAL_EXPORT_ID);
        }

        return materialExportMapper.toDto(materialExport);
    }


    public MaterialExportDTO updateMaterialExport(Long id, MaterialExportDTO materialExportDTO) {

        MaterialExport materialExport = materialExportRepository.findByMaterialExportIdAndIsDelete(id, Boolean.FALSE);
        validateUpdateAndDelete(materialExport);

        Material newMaterial = materialRepository.findByMaterialId(materialExport.getMaterialId());
        if (Objects.isNull(newMaterial)) {
            throw new EntityNotFoundException(MessageConstant.Material.MATERIAL_NOT_FOUND, EntityName.Material.MATERIAL,
                    EntityName.Material.MATERIAL_NAME);
        }

        if (Objects.equals(materialExport.getMaterialId(), materialExportDTO.getMaterialId())) {

            newMaterial.setAmount(newMaterial.getAmount() + materialExport.getAmount() - materialExportDTO.getAmount());
            materialRepository.save(newMaterial);
        } else {

            Material oldMaterial = materialRepository.findByMaterialId(materialExport.getMaterialId());
            oldMaterial.setAmount(oldMaterial.getAmount() + materialExport.getAmount());
            materialRepository.save(oldMaterial);

            newMaterial.setAmount(newMaterial.getAmount() - materialExportDTO.getAmount());
            materialRepository.save(newMaterial);
        }

        MaterialExport newMaterialExport = materialExportMapper.toEntity(materialExportDTO);
        return materialExportMapper.toDto(materialExportRepository.save(newMaterialExport));

    }

    public void deleteMaterialExport(Long id) {

        MaterialExport materialExport = materialExportRepository.findByMaterialExportIdAndIsDelete(id, Boolean.FALSE);
        validateUpdateAndDelete(materialExport);


    }

    private void validateUpdateAndDelete(MaterialExport materialExport) {
        if (Objects.isNull(materialExport)) {
            throw new EntityNotFoundException(MessageConstant.MaterialExport.MATERIAL_EXPORT_NOT_FOUND,
                    EntityName.MaterialExport.MATERIAL_EXPORT, EntityName.MaterialExport.MATERIAL_EXPORT_ID);
        }

        PatientRecord patientRecord = patientRecordRepository.findByPatientRecordId(materialExport.getPatientRecordId());

        if (patientRecord.getDate().plusDays(1).isAfter(LocalDate.now())) {
            throw new UsingEntityException(MessageConstant.MaterialExport.MATERIAL_EXPORT_OVER_DATE,
                    EntityName.MaterialExport.MATERIAL_EXPORT);
        }
    }

    public MaterialExportDTO addMaterialExport(MaterialExportDTO materialExportDTO) {
        materialExportDTO.setMaterialExportId(null);

        Material material = materialRepository.findByMaterialId(materialExportDTO.getMaterialId());
        if (Objects.isNull(material)) {
            throw new EntityNotFoundException(MessageConstant.Material.MATERIAL_NOT_FOUND, EntityName.Material.MATERIAL,
                    EntityName.Material.MATERIAL_NAME);
        }

        PatientRecord patientRecord = patientRecordRepository.findByPatientRecordId(materialExportDTO.getPatientRecordId());
        if (Objects.isNull(patientRecord)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }

        material.setAmount(material.getAmount() - materialExportDTO.getAmount());
        materialRepository.save(material);

        MaterialExport materialExport = materialExportMapper.toEntity(materialExportDTO);
        materialExportRepository.save(materialExport);

        return materialExportMapper.toDto(materialExport);
    }
}
