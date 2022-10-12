package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.MaterialDTO;
import com.example.dentalclinicmanagementsystem.entity.Material;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.mapper.MaterialMapper;
import com.example.dentalclinicmanagementsystem.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialRepository materialRepository;


    public Page<MaterialDTO> getListMaterials(String name, String unit, String price, String amount, Pageable pageable) {

        return materialRepository.findAndSearchAllByMaterial(name, unit, price, amount, pageable).map(material ->
                materialMapper.toDto(material));

    }

    public MaterialDTO getDetailMaterial(Long id) {
        Material material = materialRepository.findByMaterialIdAndEnable(id, Boolean.TRUE);

        if(Objects.isNull(material)){
            throw new EntityNotFoundException(MessageConstant.Material.MATERIAL_NOT_FOUND, EntityName.Material.MATERIAL,
                    EntityName.Material.MATERIAL_ID);
        }
         return materialMapper.toDto(material);

    }

    public MaterialDTO addMaterial(MaterialDTO materialDTO) {
        Material materialDb = materialRepository.findByMaterialName(materialDTO.getMaterialName());
        if(Objects.nonNull(materialDb)) {
            throw new RuntimeException();
        }

        materialDTO.setMaterialId(null);
        materialDTO.setEnable(Boolean.TRUE);
        Material material = materialMapper.toEntity(materialDTO);
        return materialMapper.toDto(materialRepository.save(material));
    }

    public MaterialDTO updateMaterial(Long id, MaterialDTO materialDTO) {
        materialDTO.setMaterialId(id);
        Material materialDb = materialRepository.findByMaterialIdAndEnable(id, Boolean.TRUE);

        if(Objects.isNull(materialDb)){
            throw new EntityNotFoundException(MessageConstant.Material.MATERIAL_NOT_FOUND, EntityName.Material.MATERIAL,
                    EntityName.Material.MATERIAL_ID);
        }
        if(!Objects.equals(materialDTO.getMaterialName(), materialDb.getMaterialName())){
            Material materialHasName = materialRepository.findByMaterialName(materialDTO.getMaterialName());
            if(Objects.nonNull(materialHasName)){
                throw new RuntimeException();
            }
        }

        Material material = materialMapper.toEntity(materialDTO);
        material.setEnable(Boolean.TRUE);
        return materialMapper.toDto(materialRepository.save(material));
    }

    public void deleteMaterial(Long id) {
        Material materialDb = materialRepository.findByMaterialIdAndEnable(id, Boolean.TRUE);

        if(Objects.isNull(materialDb)){
            throw new EntityNotFoundException(MessageConstant.Material.MATERIAL_NOT_FOUND, EntityName.Material.MATERIAL,
                    EntityName.Material.MATERIAL_ID);
        }
        materialDb.setEnable(Boolean.FALSE);

        materialRepository.save(materialDb);
    }
}