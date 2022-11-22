package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.MaterialImportDTO;
import com.example.dentalclinicmanagementsystem.entity.Material;
import com.example.dentalclinicmanagementsystem.entity.MaterialImport;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.mapper.MaterialImportMapper;
import com.example.dentalclinicmanagementsystem.repository.MaterialImportRepository;
import com.example.dentalclinicmanagementsystem.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Objects;

@Service
@Transactional
public class MaterialImportService {

    @Autowired
    private MaterialImportMapper materialImportMapper;

    @Autowired
    private MaterialImportRepository materialImportRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public Page<MaterialImportDTO> getListImport(String materialName, String date, String amount,String totalPrice,
                                                 String supplyName, Pageable pageable) {
        return materialImportRepository.getListImport(materialName, date, amount,totalPrice, supplyName, pageable);
    }

    public MaterialImportDTO getDetail(Long id) {

        MaterialImportDTO materialImportDTO = materialImportRepository.getDetail(id);
        if (Objects.isNull(materialImportDTO)) {
            throw new EntityNotFoundException(MessageConstant.MaterialImport.MATERIAL_IMPORT_NOT_FOUND,
                    EntityName.MaterialImport.MATERIAL_IMPORT, EntityName.MaterialImport.MATERIAL_IMPORT_ID);
        }

        return materialImportDTO;
    }

    public MaterialImportDTO importMaterial(MaterialImportDTO materialImportDTO) {

        materialImportDTO.setMaterialImportId(null);
        Material material = materialRepository.findByMaterialId(materialImportDTO.getMaterialId());
        if (Objects.isNull(material)) {
            throw new EntityNotFoundException(MessageConstant.Material.MATERIAL_NOT_FOUND, EntityName.Material.MATERIAL,
                    EntityName.Material.MATERIAL_NAME);
        }

        material.setAmount(material.getAmount() + materialImportDTO.getAmount());
        materialRepository.save(material);
        materialImportDTO.setIsDelete(Boolean.FALSE);

        MaterialImport materialImport = materialImportMapper.toEntity(materialImportDTO);
        return materialImportMapper.toDto(materialImportRepository.save(materialImport));

    }

    public MaterialImportDTO updateImport(Long id, MaterialImportDTO materialImportDTO) {

        materialImportDTO.setMaterialImportId(id);
        MaterialImport oldMaterialImport = materialImportRepository.findByMaterialImportIdAndIsDelete(id, Boolean.FALSE);

        if (Objects.isNull(oldMaterialImport)) {
            throw new EntityNotFoundException(MessageConstant.MaterialImport.MATERIAL_IMPORT_NOT_FOUND,
                    EntityName.MaterialImport.MATERIAL_IMPORT, EntityName.MaterialImport.MATERIAL_IMPORT_ID);
        }

        if (oldMaterialImport.getDate().plusDays(1).isAfter(LocalDate.now())) {
            throw new AccessDenyException(MessageConstant.MaterialImport.MATERIAL_IMPORT_OVER_DATE,
                    EntityName.MaterialImport.MATERIAL_IMPORT);
        }

        Material newMaterial = materialRepository.findByMaterialId(materialImportDTO.getMaterialId());
        if (Objects.isNull(newMaterial)) {
            throw new EntityNotFoundException(MessageConstant.Material.MATERIAL_NOT_FOUND, EntityName.Material.MATERIAL,
                    EntityName.Material.MATERIAL_NAME);
        }
        if (Objects.equals(oldMaterialImport.getMaterialId(), materialImportDTO.getMaterialId())) {

            newMaterial.setAmount(newMaterial.getAmount() - oldMaterialImport.getAmount() + materialImportDTO.getAmount());
            materialRepository.save(newMaterial);
        } else {

            Material oldMaterial = materialRepository.findByMaterialId(oldMaterialImport.getMaterialId());
            oldMaterial.setAmount(oldMaterial.getAmount() - oldMaterialImport.getAmount());
            materialRepository.save(oldMaterial);

            newMaterial.setAmount(newMaterial.getAmount() + materialImportDTO.getAmount());
            materialRepository.save(newMaterial);
        }
        materialImportDTO.setIsDelete(Boolean.FALSE);

        MaterialImport newMaterialImport = materialImportMapper.toEntity(materialImportDTO);
        return materialImportMapper.toDto(materialImportRepository.save(newMaterialImport));
    }

    public void deleteImport(Long id) {
        MaterialImport materialImport = materialImportRepository.findByMaterialImportIdAndIsDelete(id, Boolean.FALSE);

        if (Objects.isNull(materialImport)) {
            throw new EntityNotFoundException(MessageConstant.MaterialImport.MATERIAL_IMPORT_NOT_FOUND,
                    EntityName.MaterialImport.MATERIAL_IMPORT_ID, EntityName.MaterialImport.MATERIAL_IMPORT_ID);
        }

        if (materialImport.getDate().plusDays(1).isAfter(LocalDate.now())) {
            throw new AccessDenyException(MessageConstant.MaterialImport.MATERIAL_IMPORT_OVER_DATE,
                    EntityName.MaterialImport.MATERIAL_IMPORT);
        }

        Material material = materialRepository.findByMaterialId(materialImport.getMaterialId());
        if (Objects.isNull(material)) {
            throw new EntityNotFoundException(MessageConstant.Material.MATERIAL_NOT_FOUND, EntityName.Material.MATERIAL,
                    EntityName.Material.MATERIAL_NAME);
        }

        material.setAmount(material.getAmount() - materialImport.getAmount());
        materialRepository.save(material);
        materialImport.setIsDelete(Boolean.TRUE);
        materialImportRepository.save(materialImport);
    }
}
