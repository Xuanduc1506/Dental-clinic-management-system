package com.example.dentalclinicmanagementsystem.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Component
@RequestMapping("api/schedule")
public class ScheduleController {

    @Scheduled(cron = "* 0/1 * * * *")
    public void sentNotifyToPatient() {
        System.out.println("hahaha");

    }


}
