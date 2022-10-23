package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ServiceDTO {

    private Long serviceId;

    @NotBlank(groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    private String serviceName;

    @NotBlank(groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    private String unit;

    @NotNull(groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    private Integer marketPrice;

    @NotNull(groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    private Integer price;

    @NotNull(groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    private Long categoryServiceId;

    public interface Create {
    }

    public interface Update {
    }
}
