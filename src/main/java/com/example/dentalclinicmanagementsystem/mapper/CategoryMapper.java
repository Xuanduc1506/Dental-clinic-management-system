package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.CategoryServiceDTO;
import com.example.dentalclinicmanagementsystem.entity.CategoryServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryServiceEntity, CategoryServiceDTO> {

    CategoryServiceEntity toEntity(CategoryServiceDTO categoryServiceDTO);

    CategoryServiceDTO toDto(CategoryServiceEntity categoryService);
}
