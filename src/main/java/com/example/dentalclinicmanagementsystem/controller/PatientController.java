package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.PatientDTO;
import com.example.dentalclinicmanagementsystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.PATIENT_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.PATIENT_WRITE + "\")")
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

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.PATIENT_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.PATIENT_WRITE + "\")")
    @GetMapping("get_all_patients")
    public ResponseEntity<List<PatientDTO>> getAllPatient(@RequestParam(required = false, defaultValue = "") String name) {
        return ResponseEntity.ok().body(patientService.getAllPatient(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getDetailPatient(@NotNull @PathVariable Long id) {

        return ResponseEntity.ok().body(patientService.getDetailPatient(id));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.PATIENT_WRITE + "\")")
    @PostMapping()
    public ResponseEntity<PatientDTO> addPatient(@Validated @RequestBody PatientDTO patientDTO) {

        return ResponseEntity.ok().body(patientService.addPatient(patientDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.PATIENT_WRITE + "\")")
    @PutMapping("{id}")
    public ResponseEntity<PatientDTO> updatePatient(@NotNull @PathVariable Long id,
                                                    @Validated @RequestBody PatientDTO patientDTO) {

        return ResponseEntity.ok().body(patientService.updatePatient(id, patientDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.PATIENT_WRITE + "\")")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePatient(@NotNull @PathVariable Long id) {

        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }

}
