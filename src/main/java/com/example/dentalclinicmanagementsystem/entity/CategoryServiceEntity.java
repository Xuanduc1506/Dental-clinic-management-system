package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category_service")
public class CategoryServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_service_id")
    private Long categoryServiceId;

    @Column(name = "category_service_name")
    private String categoryServiceName;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
