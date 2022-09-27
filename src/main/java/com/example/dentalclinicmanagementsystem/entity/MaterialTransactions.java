package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "material_transactions")
public class MaterialTransactions {
    @Id
    @Column(name = "metarial_transection_id")
    private Long metarialTransectionId;

    @Column(name = "meterial_id")
    private Long meterialId;

    @Column(name = "date")
    private java.sql.Date date;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "is_buy")
    private Byte isBuy;

    @Column(name = "patient_code")
    private String patientCode;

}
