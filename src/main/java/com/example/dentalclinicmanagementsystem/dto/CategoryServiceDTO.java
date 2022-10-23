package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CategoryServiceDTO {

    private Long categoryServiceId;

    @NotBlank(groups = {CategoryServiceDTO.Create.class, CategoryServiceDTO.Update.class})
    private String categoryServiceName;

    private List<ServiceDTO> serviceDTOS;

    public interface Create {
    }

    public interface Update {
    }
}
