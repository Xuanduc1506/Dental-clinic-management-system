package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.SpecimensDTO;
import com.example.dentalclinicmanagementsystem.service.SpecimenService;
import org.springframework.beans.factory.annotation.Autowired;
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
