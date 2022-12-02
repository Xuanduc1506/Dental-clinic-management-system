package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecimensDTO {

    private Long specimenId;

    @Length(max = 255, groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    @NotBlank(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private String specimenName;

    @NotBlank(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private LocalDate receiveDate;

    @NotBlank(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private LocalDate deliveryDate;

    @Positive
    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Integer amount;

    @Positive
    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Integer price;

    private Long laboId;

    private String patientName;

    public interface Create {
    }

    public interface Update {
    }
}
