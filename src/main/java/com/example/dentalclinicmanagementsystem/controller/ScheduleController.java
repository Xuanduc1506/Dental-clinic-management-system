package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO;
import com.example.dentalclinicmanagementsystem.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

//    @Scheduled(cron = "* 0/1 * * * *")
//    public void sentNotifyToPatient() {
//        System.out.println("hahaha");
//    }


    @GetMapping("get_list_schedule")
    public ResponseEntity<Page<WaitingRoomDTO>> getListSchedule(@RequestParam(required = false, defaultValue = "") String patientName,
                                                                @RequestParam(required = false) LocalDate date,
                                                                Pageable pageable) {
        return ResponseEntity.ok().body(scheduleService.getListSchedule(patientName, date, pageable));

    }

    @GetMapping("{id}")
    public ResponseEntity<WaitingRoomDTO> getDetailSchedule(@PathVariable Long id) {
        return ResponseEntity.ok().body(scheduleService.getDetail(id));
    }

    @PostMapping("")
    public ResponseEntity<WaitingRoomDTO> addSchedule(@Validated(WaitingRoomDTO.Create.class) @RequestBody WaitingRoomDTO waitingRoomDTO) {
        return ResponseEntity.ok().body(scheduleService.addSchedule(waitingRoomDTO));

    }

    @PutMapping("{id}")
    public ResponseEntity<WaitingRoomDTO> updateSchedule(@PathVariable Long id,
                                                         @Validated(WaitingRoomDTO.Create.class) @RequestBody WaitingRoomDTO waitingRoomDTO) {
        return ResponseEntity.ok().body(scheduleService.updateSchedule(id, waitingRoomDTO));
    }



}
