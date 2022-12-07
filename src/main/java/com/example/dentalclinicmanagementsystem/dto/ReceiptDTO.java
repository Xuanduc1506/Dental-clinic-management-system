package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {

    private Long receiptId;

    @Positive
    @NotNull(groups = {ReceiptDTO.Create.class, ReceiptDTO.Update.class})
    private Integer payment;

    private Long treatmentId;

    @NotNull(groups = {ReceiptDTO.Create.class, ReceiptDTO.Update.class})
    private LocalDate date;

    @Positive
    @NotNull(groups = {ReceiptDTO.Create.class, ReceiptDTO.Update.class})
    private Integer debit;

    private Integer oldDebit;

    private List<TreatmentServiceMapDTO> newServices;

    public ReceiptDTO(Long treatmentId, Long receiptId, Integer payment, LocalDate date, Integer debit) {
        this.treatmentId = treatmentId;
        this.receiptId = receiptId;
        this.payment = payment;
        this.date = date;
        this.debit = debit;
    }

    public interface Create {
    }

    public interface Update {
    }
}
