package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notify")
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id")
    private Long notifyId;

    @Column(name = "treatment_id")
    private Long treatmentId;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "time")
    private LocalDateTime time;
}
