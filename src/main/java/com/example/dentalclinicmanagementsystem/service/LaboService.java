package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.LaboDTO;
import com.example.dentalclinicmanagementsystem.entity.Labo;
import com.example.dentalclinicmanagementsystem.exception.DuplicateNameException;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.mapper.LaboMapper;
import com.example.dentalclinicmanagementsystem.repository.LaboRepository;
import com.example.dentalclinicmanagementsystem.repository.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Objects;

@Service
@Transactional
public class LaboService {

    @Autowired
    private LaboRepository laboRepository;

    @Autowired
    private SpecimenRepository specimenRepository;

    @Autowired
    private LaboMapper laboMapper;

    public Page<LaboDTO> getListLabo(String name, String phone, Pageable pageable) {

        return laboRepository.getListLabo(name, phone, pageable);
    }

    public LaboDTO getDetailLabo(Long id, Integer month, Integer year) {

        Labo labo = laboRepository.findByLaboIdAndIsDeleted(id, Boolean.FALSE);
        if (Objects.isNull(labo)) {
            throw new EntityNotFoundException(MessageConstant.Labo.LABO_NOT_FOUND,
                    EntityName.Labo.LABO, EntityName.Labo.LABO_ID);
        }

        LaboDTO laboDTO = laboMapper.toDto(labo);


        if (Objects.nonNull(month) && Objects.isNull(year)) {
            year = LocalDate.now().getYear();
        }

        laboDTO.setTotalMoney(specimenRepository.findTotalCostInTime(id, month, year));

        laboDTO.setSpecimensDTOS(specimenRepository.findAllByLaboIdInTime(id, month, year));

        return laboDTO;
    }


    public LaboDTO addLabo(LaboDTO laboDTO) {

        laboDTO.setLaboId(null);
        Labo laboDb = laboRepository.findByLaboNameAndIsDeleted(laboDTO.getLaboName(), Boolean.FALSE);
        if (Objects.nonNull(laboDb)) {
            throw new DuplicateNameException(MessageConstant.Labo.LABO_NAME_ALREADY_EXIST,
                    EntityName.Labo.LABO);
        }

        laboDTO.setIsDeleted(Boolean.FALSE);
        Labo labo = laboMapper.toEntity(laboDTO);
        return laboMapper.toDto(laboRepository.save(labo));
    }

    public LaboDTO updateLabo(Long id, LaboDTO laboDTO) {
        laboDTO.setLaboId(id);

        Labo labo = laboRepository.findByLaboIdAndIsDeleted(id, Boolean.FALSE);
        if (Objects.isNull(labo)) {
            throw new EntityNotFoundException(MessageConstant.Labo.LABO_NOT_FOUND,
                    EntityName.Labo.LABO, EntityName.Labo.LABO_ID);
        }

        if (!Objects.equals(labo.getLaboName(), laboDTO.getLaboName())) {
            Labo laboDb = laboRepository.findByLaboNameAndIsDeleted(laboDTO.getLaboName(), Boolean.FALSE);
            if (Objects.nonNull(laboDb)) {
                throw new DuplicateNameException(MessageConstant.Labo.LABO_NAME_ALREADY_EXIST,
                        EntityName.Labo.LABO);
            }
        }

        laboDTO.setIsDeleted(Boolean.FALSE);
        Labo laboNew = laboMapper.toEntity(laboDTO);
        return laboMapper.toDto(laboRepository.save(laboNew));
    }

    public void deleteLabo(Long id) {
        Labo labo = laboRepository.findByLaboIdAndIsDeleted(id, Boolean.FALSE);
        if (Objects.isNull(labo)) {
            throw new EntityNotFoundException(MessageConstant.Labo.LABO_NOT_FOUND,
                    EntityName.Labo.LABO, EntityName.Labo.LABO_ID);
        }

        labo.setIsDeleted(Boolean.TRUE);
        laboRepository.save(labo);
    }
}
