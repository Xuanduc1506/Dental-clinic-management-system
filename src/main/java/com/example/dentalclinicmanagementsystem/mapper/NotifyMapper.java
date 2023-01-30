package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.NotifyDTO;
import com.example.dentalclinicmanagementsystem.entity.Notify;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyMapper extends EntityMapper<Notify, NotifyDTO>{

    Notify toEntity(NotifyDTO notifyDTO);

    NotifyDTO toDto(Notify notify);
}
