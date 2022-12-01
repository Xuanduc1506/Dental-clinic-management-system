package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate date;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "supply_name")
    private String supplyName;

    @Column(name = "unit_price")
    private Integer unitPrice;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "date", insertable = false, updatable = false)
    private String dateTemp;

    @Column(name = "amount", insertable = false, updatable = false)
    private String amountTemp;

    @Column(name = "unit_price", insertable = false, updatable = false)
    private String unitPriceTemp;

}
