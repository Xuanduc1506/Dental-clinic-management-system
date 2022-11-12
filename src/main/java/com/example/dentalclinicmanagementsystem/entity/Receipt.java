package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long receiptId;

    @Column(name = "payment")
    private Integer payment;

    @Column(name = "treatment_id")
    private Long treatmentId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "debit")
    private Integer debit;

    @Column(name = "payment", insertable = false, updatable = false)
    private String paymentTemp;

    @Column(name = "date", insertable = false, updatable = false)
    private String dateTemp;

    @Column(name = "debit", insertable = false, updatable = false)
    private String debitTemp;
}
