package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.CategoryServiceDTO;
import com.example.dentalclinicmanagementsystem.dto.ServiceDTO;
import com.example.dentalclinicmanagementsystem.entity.CategoryServiceEntity;
import com.example.dentalclinicmanagementsystem.exception.DuplicateNameException;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
import com.example.dentalclinicmanagementsystem.mapper.CategoryMapper;
import com.example.dentalclinicmanagementsystem.mapper.ServiceMapper;
import com.example.dentalclinicmanagementsystem.repository.CategoryRepository;
import com.example.dentalclinicmanagementsystem.repository.PatientRecordRepository;
import com.example.dentalclinicmanagementsystem.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private PatientRecordRepository patientRecordRepository;

    public Page<CategoryServiceDTO> getListService(String name, Pageable pageable) {

        return categoryRepository.findAllByCategoryServiceNameContainingIgnoreCase(name, pageable).map(
                entity -> categoryMapper.toDto(entity));
    }

    public CategoryServiceDTO addCategory(CategoryServiceDTO categoryServiceDTO) {

        categoryServiceDTO.setCategoryServiceId(null);

        CategoryServiceEntity categoryServiceEntityDb = categoryRepository.
                findByCategoryServiceName(categoryServiceDTO.getCategoryServiceName());
        if (Objects.nonNull(categoryServiceEntityDb)) {
            throw new DuplicateNameException(MessageConstant.CategoryService.CATEGORY_NAME_ALREADY_EXIST,
                    EntityName.CategoryService.CATEGORY_NAME);
        }

        CategoryServiceEntity categoryServiceEntity = categoryMapper.toEntity(categoryServiceDTO);
        return categoryMapper.toDto(categoryRepository.save(categoryServiceEntity));

    }

    public CategoryServiceDTO getDetailCategory(Long id) {

        CategoryServiceEntity categoryService = categoryRepository.findByCategoryServiceId(id);
        if (Objects.isNull(categoryService)) {
            throw new EntityNotFoundException(MessageConstant.CategoryService.CATEGORY_NOT_FOUND,
                    EntityName.CategoryService.CATEGORY, EntityName.CategoryService.CATEGORY_ID);
        }

        CategoryServiceDTO categoryServiceDTO = categoryMapper.toDto(categoryService);

        List<ServiceDTO> serviceDTOS = serviceMapper.toDto(serviceRepository.findAllByCategoryServiceId(id));
        categoryServiceDTO.setServiceDTOS(serviceDTOS);

        return categoryServiceDTO;
    }

    public List<CategoryServiceDTO> displayAllService(String name) {

        List<CategoryServiceDTO> categoryServiceDTOS = categoryMapper.toDto(categoryRepository.findAll());
        List<ServiceDTO> serviceDTOS = serviceMapper.toDto(serviceRepository.findAllByServiceNameContainingIgnoreCaseAndIsDeleted(name, Boolean.FALSE));

        categoryServiceDTOS.forEach(categoryServiceDTO -> {
            List<ServiceDTO> serviceOfCategory = serviceDTOS
                    .stream().filter(serviceDTO -> Objects.equals(categoryServiceDTO.getCategoryServiceId(),
                            serviceDTO.getCategoryServiceId()))
                    .collect(Collectors.toList());

            categoryServiceDTO.setServiceDTOS(serviceOfCategory);
        });

        return categoryServiceDTOS;
    }

    public CategoryServiceDTO updateCategory(Long id, CategoryServiceDTO categoryServiceDTO) {

        categoryServiceDTO.setCategoryServiceId(id);

        CategoryServiceEntity categoryService = categoryRepository.findByCategoryServiceId(id);
        if (Objects.isNull(categoryService)) {
            throw new EntityNotFoundException(MessageConstant.CategoryService.CATEGORY_NOT_FOUND,
                    EntityName.CategoryService.CATEGORY, EntityName.CategoryService.CATEGORY_ID);
        }

        if (!Objects.equals(categoryService.getCategoryServiceName(), categoryServiceDTO.getCategoryServiceName())) {
            CategoryServiceEntity categoryServiceEntityDb = categoryRepository.
                    findByCategoryServiceName(categoryServiceDTO.getCategoryServiceName());
            if (Objects.nonNull(categoryServiceEntityDb)) {
                throw new DuplicateNameException(MessageConstant.CategoryService.CATEGORY_NAME_ALREADY_EXIST,
                        EntityName.CategoryService.CATEGORY_NAME);
            }
        }

        CategoryServiceEntity entity = categoryMapper.toEntity(categoryServiceDTO);
        return categoryMapper.toDto(categoryRepository.save(entity));
    }

    public void deleteCategory(Long id) {

        CategoryServiceEntity categoryService = categoryRepository.findByCategoryServiceId(id);
        if (Objects.isNull(categoryService)) {
            throw new EntityNotFoundException(MessageConstant.CategoryService.CATEGORY_NOT_FOUND,
                    EntityName.CategoryService.CATEGORY, EntityName.CategoryService.CATEGORY_ID);
        }

        List<com.example.dentalclinicmanagementsystem.entity.Service> services =
                serviceRepository.findAllByCategoryServiceId(id);

        if (!CollectionUtils.isEmpty(services)) {
            throw new AccessDenyException(MessageConstant.CategoryService.CATEGORY_HAVE_BEEN_USED,
                    EntityName.CategoryService.CATEGORY_NAME);
        }

        categoryRepository.delete(categoryService);
    }

    public ServiceDTO getDetailService(Long serviceId) {

        com.example.dentalclinicmanagementsystem.entity.Service service = serviceRepository.findByServiceIdAndIsDeleted(serviceId, Boolean.FALSE);
        if (Objects.isNull(service)) {
            throw new EntityNotFoundException(MessageConstant.Service.SERVICE_NOT_FOUND, EntityName.Service.SERVICE,
                    EntityName.Service.SERVICE_ID);
        }

        return serviceMapper.toDto(service);
    }

    public ServiceDTO addService(ServiceDTO serviceDTO) {

        serviceDTO.setServiceId(null);
        com.example.dentalclinicmanagementsystem.entity.Service serviceDb =
                serviceRepository.findByServiceName(serviceDTO.getServiceName());

        if (Objects.nonNull(serviceDb)) {
            throw new DuplicateNameException(MessageConstant.Service.SERVICE_NAME_ALREADY_EXIST,
                    EntityName.Service.SERVICE);
        }

        CategoryServiceEntity categoryServiceEntity =
                categoryRepository.findByCategoryServiceId(serviceDTO.getCategoryServiceId());
        if (Objects.isNull(categoryServiceEntity)) {
            throw new EntityNotFoundException(MessageConstant.CategoryService.CATEGORY_NOT_FOUND,
                    EntityName.Service.SERVICE, EntityName.CategoryService.CATEGORY_ID);
        }

        serviceDTO.setIsDeleted(Boolean.FALSE);
        com.example.dentalclinicmanagementsystem.entity.Service service = serviceMapper.toEntity(serviceDTO);
        return serviceMapper.toDto(serviceRepository.save(service));
    }

    public ServiceDTO updateService(Long serviceId, ServiceDTO serviceDTO) {

        serviceDTO.setServiceId(serviceId);
        com.example.dentalclinicmanagementsystem.entity.Service serviceDb = serviceRepository.findByServiceIdAndIsDeleted(serviceId, Boolean.FALSE);
        if (Objects.isNull(serviceDb)) {
            throw new EntityNotFoundException(MessageConstant.Service.SERVICE_NOT_FOUND, EntityName.Service.SERVICE,
                    EntityName.Service.SERVICE_ID);
        }

        if (!Objects.equals(serviceDb.getServiceName(), serviceDTO.getServiceName())) {
            com.example.dentalclinicmanagementsystem.entity.Service service =
                    serviceRepository.findByServiceName(serviceDTO.getServiceName());

            if (Objects.nonNull(service)) {
                throw new DuplicateNameException(MessageConstant.Service.SERVICE_NAME_ALREADY_EXIST,
                        EntityName.Service.SERVICE_NAME);
            }
        }

        serviceDTO.setIsDeleted(Boolean.FALSE);
        com.example.dentalclinicmanagementsystem.entity.Service service = serviceMapper.toEntity(serviceDTO);
        return serviceMapper.toDto(serviceRepository.save(service));
    }

    public void deleteService(Long serviceId) {

        com.example.dentalclinicmanagementsystem.entity.Service serviceDb = serviceRepository.findByServiceIdAndIsDeleted(serviceId, Boolean.FALSE);
        if (Objects.isNull(serviceDb)) {
            throw new EntityNotFoundException(MessageConstant.Service.SERVICE_NOT_FOUND, EntityName.Service.SERVICE,
                    EntityName.Service.SERVICE_ID);
        }

        serviceDb.setIsDeleted(Boolean.TRUE);

        serviceRepository.save(serviceDb);
    }

    public List<ServiceDTO> getTreatingService(Long patientId) {
        return serviceRepository.findTreatingService(patientRecordRepository.getLastRecordId(patientId));
    }

    public List<ServiceDTO> getAllService(String name) {

        if (Objects.isNull(name)) {
            name = "";
        }

        return serviceMapper.toDto(serviceRepository.findAllByServiceNameContainingIgnoreCaseAndIsDeleted(name, Boolean.FALSE));
    }

    public List<ServiceDTO> getAllServiceByCategoryId(Long categoryId, String name) {

        if (Objects.isNull(name)) {
            name = "";
        }

        return serviceMapper.toDto(serviceRepository.findAllByServiceNameContainingAndCategoryServiceId(name, categoryId));
    }

    public List<CategoryServiceDTO> getAllCategory(String name) {

        if (Objects.isNull(name)) {
            name = "";
        }
        return categoryMapper.toDto(categoryRepository.findAllByCategoryServiceNameContainingIgnoreCase(name));
    }
}
