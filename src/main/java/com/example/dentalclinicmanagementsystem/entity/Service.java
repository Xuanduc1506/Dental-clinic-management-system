package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "market_price")
    private Integer marketPrice;

    @Column(name = "price")
    private Integer price;

    @Column(name = "category_service_id")
    private Long categoryServiceId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


}
