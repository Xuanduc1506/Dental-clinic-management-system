package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CategoryServiceDTO {

    private Long categoryServiceId;

    @Length(max = 50, groups = {CategoryServiceDTO.Create.class, CategoryServiceDTO.Update.class})
    @NotBlank(groups = {CategoryServiceDTO.Create.class, CategoryServiceDTO.Update.class})
    private String categoryServiceName;

    private Boolean isDeleted;

    private List<ServiceDTO> serviceDTOS;

    public interface Create {
    }

    public interface Update {
    }
}
