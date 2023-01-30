package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.IncomeDTO;
import com.example.dentalclinicmanagementsystem.dto.TotalIncomeDTO;
import com.example.dentalclinicmanagementsystem.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.INCOME_READ + "\")")
    @GetMapping()
    public ResponseEntity<IncomeDTO> getIncome(@RequestParam(required = false) String startDate,
                                               @RequestParam(required = false) String endDate) {

        return ResponseEntity.ok().body(incomeService.getIncome(startDate, endDate));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.INCOME_READ + "\")")
    @GetMapping("net_income")
    public ResponseEntity<IncomeDTO> getNetIncome(@RequestParam(required = false) String startDate,
                                                  @RequestParam(required = false) String endDate) {

        return ResponseEntity.ok().body(incomeService.getNetIncome(startDate, endDate));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.INCOME_READ + "\")")
    @GetMapping("total_spend")
    public ResponseEntity<IncomeDTO> getTotalSpend(@RequestParam(required = false) String startDate,
                                                   @RequestParam(required = false) String endDate) {

        return ResponseEntity.ok().body(incomeService.getTotalSpend(startDate, endDate));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.INCOME_READ + "\")")
    @GetMapping("get_list_income")
    public ResponseEntity<List<TotalIncomeDTO>> getListIncome(@RequestParam(required = false) String startDate,
                                                              @RequestParam(required = false) String endDate) {

        return ResponseEntity.ok().body(incomeService.getListIncome(startDate, endDate));
    }




}
