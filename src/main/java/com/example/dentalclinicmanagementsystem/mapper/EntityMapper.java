package com.example.dentalclinicmanagementsystem.mapper;

import java.util.List;

public interface EntityMapper<Entity, EntityDto> {

    Entity toEntity(EntityDto entityDto);

    EntityDto toDto(Entity entity);

    List<Entity> toEntity(List<EntityDto> entityDtos);

    List<EntityDto> toDto(List<Entity> entities);
}
