package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.TimekeepingWithButtonDTO;
import com.example.dentalclinicmanagementsystem.service.TimekeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/timekeeping")
public class TimekeepingController {

    @Autowired
    private TimekeepingService timekeepingService;

    @GetMapping("get_list_timekeeping")
    public ResponseEntity<TimekeepingWithButtonDTO> getListTimekeeping(@RequestHeader("Authorization") String token,
                                                                             @RequestParam(required = false, defaultValue = "") String fullName,
                                                                             @RequestParam(required = false, defaultValue = "") LocalDate startTime,
                                                                             @RequestParam(required = false, defaultValue = "") LocalDate endTime,
                                                                             @RequestParam(required = false, defaultValue = "") Integer month,
                                                                             Pageable pageable) {

        return ResponseEntity.ok().body(timekeepingService.getListTimekeeping(token, fullName, startTime, endTime, month,  pageable));

    }

    @PostMapping("checkin")
    public ResponseEntity<Void> checkin(@RequestHeader("Authorization") String token) {
        timekeepingService.checkin(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("checkout")
    public ResponseEntity<Void> checkout(@RequestHeader("Authorization") String token) {
        timekeepingService.checkout(token);
        return ResponseEntity.ok().build();
    }


}
