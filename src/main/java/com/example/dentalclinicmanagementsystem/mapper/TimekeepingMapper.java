package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.TimekeepingDTO;
import com.example.dentalclinicmanagementsystem.entity.Timekeeping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimekeepingMapper extends EntityMapper<Timekeeping, TimekeepingDTO> {

    Timekeeping toEntity(TimekeepingDTO timekeepingDTO);

    TimekeepingDTO toDto(Timekeeping timekeeping);
}
