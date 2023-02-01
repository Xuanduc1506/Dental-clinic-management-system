package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaitingRoomDTO {

    private Long waitingRoomId;

    @NotNull(groups = {WaitingRoomDTO.Create.class, WaitingRoomDTO.Update.class})
    private Long patientId;

    @NotNull(groups = {WaitingRoomDTO.Create.class, WaitingRoomDTO.Update.class})
    private LocalDate date;

    private Integer status;

    private Boolean isBooked;

    private Boolean isDeleted;

    private String note;

    private String patientName;
    private String phone;

    private Long userId;

    private String userName;


    public WaitingRoomDTO(Long waitingRoomId, Long patientId, LocalDate date, String patientName, Integer status, Long userId, String userName) {
        this.waitingRoomId = waitingRoomId;
        this.patientId = patientId;
        this.date = date;
        this.status = status;
        this.patientName = patientName;
        this.userId = userId;
        this.userName = userName;
    }


    public WaitingRoomDTO(Long waitingRoomId, Long patientId, LocalDate date, Integer status, String note, String patientName, String phone) {
        this.waitingRoomId = waitingRoomId;
        this.patientId = patientId;
        this.date = date;
        this.status = status;
        this.note = note;
        this.patientName = patientName;
        this.phone = phone;
    }

    public interface Create {
    }

    public interface Update {
    }
}
