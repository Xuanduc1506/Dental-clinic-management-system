package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.ServiceDTO;
import com.example.dentalclinicmanagementsystem.entity.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper extends EntityMapper<Service, ServiceDTO> {

    Service toEntity(ServiceDTO serviceDTO);

    ServiceDTO toDto(Service service);
}
