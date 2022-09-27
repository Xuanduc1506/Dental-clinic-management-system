package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "materials")
public class Materials {
    @Id
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

}
