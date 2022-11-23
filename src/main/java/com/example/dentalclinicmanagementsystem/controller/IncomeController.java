package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.IncomeDTO;
import com.example.dentalclinicmanagementsystem.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping()
    public ResponseEntity<IncomeDTO> getIncome(@RequestParam(required = false, defaultValue = "month") String statisticsBy,
                                               @RequestParam(required = false) Integer number) {

        return ResponseEntity.ok().body(incomeService.getIncome(statisticsBy, number));
    }



}
