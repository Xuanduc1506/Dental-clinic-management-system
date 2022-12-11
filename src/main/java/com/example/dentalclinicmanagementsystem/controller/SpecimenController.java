package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.SpecimensDTO;
import com.example.dentalclinicmanagementsystem.service.SpecimenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/specimens")
public class SpecimenController {

    @Autowired
    private SpecimenService specimenService;

    @GetMapping("get_list_speciemns")
    @PreAuthorize("hasAuthority(\"" + PermissionConstant.SPECIMEN_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.SPECIMEN_WRITE + "\")")
    public ResponseEntity<Page<SpecimensDTO>> getPageSpecimens(@RequestParam(required = false,defaultValue = "") String specimenName,
                                                               @RequestParam(required = false,defaultValue = "") String patientName,
                                                               @RequestParam(required = false,defaultValue = "") String receiveDate,
                                                               @RequestParam(required = false,defaultValue = "") String deliveryDate,
                                                               @RequestParam(required = false,defaultValue = "") String laboName,
                                                               @RequestParam(required = false,defaultValue = "") String serviceName,
                                                               @RequestParam(required = false) Integer status,
                                                               Pageable pageable) {
        return ResponseEntity.ok().body(specimenService.getPageSpecimens(specimenName, patientName, receiveDate, deliveryDate, laboName, serviceName, status, pageable));
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.SPECIMEN_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.SPECIMEN_WRITE + "\")")
    @GetMapping("{id}")
    public ResponseEntity<SpecimensDTO> getDetail(@PathVariable Long id) {

        return ResponseEntity.ok().body(specimenService.getDetail(id));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.SPECIMEN_WRITE + "\")")
    @PostMapping()
    public ResponseEntity<SpecimensDTO> addSpecimen(
            @RequestBody @Validated(SpecimensDTO.Create.class) SpecimensDTO specimensDTO) {

        return ResponseEntity.ok().body(specimenService.addSpecimen(specimensDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.SPECIMEN_WRITE + "\")")
    @PutMapping("{id}")
    public ResponseEntity<SpecimensDTO> updateSpecimen(@PathVariable Long id,
                                                       @RequestBody @Validated(SpecimensDTO.Update.class) SpecimensDTO specimensDTO) {

        return ResponseEntity.ok().body(specimenService.updateSpecimen(id, specimensDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.SPECIMEN_WRITE + "\")")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSpecimen(@PathVariable Long id) {

        specimenService.deleteSpecimen(id);
        return ResponseEntity.ok().build();
    }


}
