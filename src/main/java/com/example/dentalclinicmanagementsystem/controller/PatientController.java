package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.PatientDTO;
import com.example.dentalclinicmanagementsystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin
@RequestMapping("api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("get_list_patients")
    public ResponseEntity<Page<PatientDTO>> getListPatient(@RequestParam(required = false, defaultValue = "")String name,
                                                           @RequestParam(required = false, defaultValue = "")String birthdate,
                                                           @RequestParam(required = false, defaultValue = "")Boolean gender,
                                                           @RequestParam(required = false, defaultValue = "")String address,
                                                           @RequestParam(required = false, defaultValue = "")String phone,
                                                           @RequestParam(required = false, defaultValue = "")String email,
                                                           @RequestParam(required = false, defaultValue = "")String bodyPrehistory,
                                                           @RequestParam(required = false, defaultValue = "")String teethPrehistory,
                                                           @RequestParam(required = false, defaultValue = "-1")Integer status,
                                                           Pageable pageable) {

        return ResponseEntity.ok().body(patientService.getListPatient(name, birthdate, gender, address, phone, email,
                bodyPrehistory, teethPrehistory, status, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getDetailPatient(@NotNull @PathVariable Long id) {

        return ResponseEntity.ok().body(patientService.getDetailPatient(id));
    }

    @PostMapping()
    public ResponseEntity<PatientDTO> addPatient(@Validated @RequestBody PatientDTO patientDTO) {

        return ResponseEntity.ok().body(patientService.addPatient(patientDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<PatientDTO> updatePatient(@NotNull @PathVariable Long id,
                                                    @Validated @RequestBody PatientDTO patientDTO) {

        return ResponseEntity.ok().body(patientService.updatePatient(id, patientDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePatient(@NotNull @PathVariable Long id) {

        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }




}
