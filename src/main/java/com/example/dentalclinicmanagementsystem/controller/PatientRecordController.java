package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.PatientRecordDTO;
import com.example.dentalclinicmanagementsystem.dto.PatientRecordInterfaceDTO;
import com.example.dentalclinicmanagementsystem.service.PatientRecordService;
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
@RequestMapping("api/patient_record")
public class PatientRecordController {

    @Autowired
    private PatientRecordService patientRecordService;

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.PATIENT_RECORD_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.PATIENT_RECORD_WRITE + "\")")
    @GetMapping("get_list_record/{patientId}")
    public ResponseEntity<Page<PatientRecordInterfaceDTO>> getListRecord(@NotNull @PathVariable Long patientId,
                                                                         @RequestParam(required = false, defaultValue = "") String reason,
                                                                         @RequestParam(required = false, defaultValue = "") String diagnostic,
                                                                         @RequestParam(required = false, defaultValue = "") String causal,
                                                                         @RequestParam(required = false, defaultValue = "") String date,
                                                                         @RequestParam(required = false, defaultValue = "") String treatment,
                                                                         @RequestParam(required = false, defaultValue = "") String laboName,
                                                                         @RequestParam(required = false, defaultValue = "") String serviceName,
                                                                         Pageable pageable) {

        return ResponseEntity.ok().body(patientRecordService.getListPatientRecord(patientId, reason, diagnostic, causal,
                date, treatment, laboName, serviceName, pageable));

    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.PATIENT_RECORD_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.PATIENT_RECORD_WRITE + "\")")
    @GetMapping("get_all_record/{patientId}")
    public ResponseEntity<List<PatientRecordDTO>> getAllRecord(@PathVariable Long patientId,
                                                               @RequestParam(required = false, defaultValue = "") String date) {
        return ResponseEntity.ok().body(patientRecordService.getAllRecord(patientId, date));
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.PATIENT_RECORD_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.PATIENT_RECORD_WRITE + "\")")
    @GetMapping("{id}")
    public ResponseEntity<PatientRecordDTO> getDetailRecord(@NotNull @PathVariable Long id) {

        return ResponseEntity.ok().body(patientRecordService.getDetailRecord(id));
    }


    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.PATIENT_RECORD_WRITE + "\")")
    @PostMapping("{patientId}")
    public ResponseEntity<PatientRecordDTO> addPatientRecord(
            @RequestHeader("Authorization") String token,
            @NotNull @PathVariable Long patientId,
            @Validated(PatientRecordDTO.Create.class) @RequestBody PatientRecordDTO patientRecordDTO) {

        return ResponseEntity.ok().body(patientRecordService.addPatientRecord(token, patientId, patientRecordDTO));

    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.PATIENT_RECORD_WRITE + "\")")
    @PutMapping("{id}")
    public ResponseEntity<PatientRecordDTO> updateRecord(
            @RequestHeader("Authorization") String token,
            @NotNull @PathVariable Long id,
            @Validated(PatientRecordDTO.Create.class) @RequestBody PatientRecordDTO patientRecordDTO) {

        return ResponseEntity.ok().body(patientRecordService.updateRecord(token, id, patientRecordDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.PATIENT_RECORD_WRITE + "\")")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRecord(@NotNull @PathVariable Long id) {

        patientRecordService.deleteRecord(id);
        return ResponseEntity.ok().build();
    }


}
