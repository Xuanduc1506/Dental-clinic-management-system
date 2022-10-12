package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Integer price;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "amount", insertable = false, updatable = false)
    private String amountTemp;

    @Column(name = "price", insertable = false, updatable = false)
    private String priceTemp;

}
