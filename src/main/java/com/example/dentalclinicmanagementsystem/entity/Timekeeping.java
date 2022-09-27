package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "timekepping")
public class Timekeeping {
    @Id
    @Column(name = "timekeeping_id")
    private Long timekeepingId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "time_checkin")
    private java.sql.Date timeCheckin;

    @Column(name = "time_checkout")
    private java.sql.Date timeCheckout;

}
