package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "timekeeping")
public class Timekeeping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timekeeping_id")
    private Long timekeepingId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "time_checkin")
    private LocalDateTime timeCheckin;

    @Column(name = "time_checkout")
    private LocalDateTime timeCheckout;

}
