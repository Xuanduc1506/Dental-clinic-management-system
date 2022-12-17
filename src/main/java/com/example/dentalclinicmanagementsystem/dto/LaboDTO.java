package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboDTO {

    private Long laboId;

    @Length(max = 255, groups = {LaboDTO.Create.class, LaboDTO.Update.class})
    @NotBlank(groups = {LaboDTO.Create.class, LaboDTO.Update.class})
    private String laboName;

    @Length(max = 10, groups = {LaboDTO.Create.class, LaboDTO.Update.class})
    @NotBlank(groups = {LaboDTO.Create.class, LaboDTO.Update.class})
    private String phone;
    private Boolean isDeleted;

    private Integer totalMoney;

    private List<SpecimensDTO> specimensDTOS;

    public LaboDTO(Long laboId, String laboName, String phone, Integer totalMoney) {
        this.laboId = laboId;
        this.laboName = laboName;
        this.phone = phone;
        this.totalMoney = totalMoney;
    }

    public interface Create {
    }

    public interface Update {
    }
}
