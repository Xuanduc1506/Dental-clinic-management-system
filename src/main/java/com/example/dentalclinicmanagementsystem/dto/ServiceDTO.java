package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {

    private Long serviceId;

    @Length(max = 255, groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    @NotBlank(groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    private String serviceName;

    @Length(max = 45, groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    @NotBlank(groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    private String unit;

    @Positive
    @NotNull(groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    private Integer marketPrice;

    @Positive
    @NotNull(groups = {ServiceDTO.Create.class, ServiceDTO.Update.class})
    private Integer price;

    @NotNull(groups = {ServiceDTO.Create.class})
    private Long categoryServiceId;

    private Integer status;

    private Boolean isNew;

    private Integer discount;

    private Boolean isDeleted;

    private Integer amount;

    public ServiceDTO(Long serviceId, String serviceName, Integer status) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.status = status;
    }

    public interface Create {
    }

    public interface Update {
    }
}
