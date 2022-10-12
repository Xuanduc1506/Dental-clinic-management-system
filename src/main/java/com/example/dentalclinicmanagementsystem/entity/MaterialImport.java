package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "material_import")
public class MaterialImport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_import_id")
    private Long materialImportId;

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "supply_name")
    private String supplyName;

    @Column(name = "date", insertable = false, updatable = false)
    private String dateTemp;

    @Column(name = "amount", insertable = false, updatable = false)
    private String amountTemp;

}
