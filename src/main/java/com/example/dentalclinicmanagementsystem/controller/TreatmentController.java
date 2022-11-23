package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.TreatmentDTO;
import com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO;
import com.example.dentalclinicmanagementsystem.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/bills")
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("get_list_bills")
    public ResponseEntity<Page<TreatmentDTO>> getListBills(@RequestParam(required = false, defaultValue = "") String patientName,
                                                           @RequestParam(required = false, defaultValue = "") String phone,
                                                           Pageable pageable) {

        return ResponseEntity.ok().body(treatmentService.getListBills(patientName, phone, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<TreatmentDTO> getDetail(@PathVariable Long id) {

        return ResponseEntity.ok().body(treatmentService.getDetail(id));
    }

}
