package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "material_export")
public class MaterialExport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_export_id")
    private Long materialExportId;

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "unit_price")
    private Integer unitPrice;

    @Column(name = "patient_record_id")
    private Long patientRecordId;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "is_show")
    private Boolean isShow;

    @Column(name = "amount", insertable = false, updatable = false)
    private String amountTemp;

    @Column(name = "unit_price", insertable = false, updatable = false)
    private String unitPriceTemp;
}
