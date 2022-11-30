package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private Integer status;

    private Boolean isNew;

    private Integer discount;

    private Boolean isDeleted;

    public ServiceDTO(Long serviceId, String serviceName,Integer price, Integer discount, Integer status) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.status = status;
        this.price = price;
        this.discount = discount;
    }

    public interface Create {
    }

    public interface Update {
    }
}
