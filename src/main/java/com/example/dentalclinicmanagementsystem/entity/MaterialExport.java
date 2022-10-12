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

    @Column(name = "patient_record_id")
    private Long patientRecordId;
}
