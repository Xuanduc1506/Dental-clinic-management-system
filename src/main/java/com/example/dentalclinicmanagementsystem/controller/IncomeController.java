package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.IncomeDTO;
import com.example.dentalclinicmanagementsystem.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.INCOME_READ + "\")")
    @GetMapping()
    public ResponseEntity<IncomeDTO> getIncome(@RequestParam(required = false) Integer month,
                                               @RequestParam(required = false) Integer year) {

        return ResponseEntity.ok().body(incomeService.getIncome(month, year));
    }



}
