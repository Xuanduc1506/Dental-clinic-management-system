package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "waiting_room")
public class WaitingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiting_room_id")
    private Long waitingRoomId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_booked")
    private Boolean isBooked;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
