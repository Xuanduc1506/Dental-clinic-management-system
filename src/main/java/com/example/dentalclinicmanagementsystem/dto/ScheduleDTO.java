package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class ScheduleDTO {

    private Long scheduleId;

    private String patientsCode;

    private java.sql.Date date;

    private String status;

    private Byte booked;

    public interface Create {
    }

    public interface Update {
    }
}
