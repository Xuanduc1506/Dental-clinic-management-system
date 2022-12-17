package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO;
import com.example.dentalclinicmanagementsystem.entity.WaitingRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WaitingRoomMapper extends EntityMapper<WaitingRoom, WaitingRoomDTO> {

    WaitingRoom toEntity(WaitingRoomDTO userDTO);

    WaitingRoomDTO toDto(WaitingRoom user);

}
