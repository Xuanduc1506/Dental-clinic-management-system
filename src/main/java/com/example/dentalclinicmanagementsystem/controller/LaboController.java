package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.LaboDTO;
import com.example.dentalclinicmanagementsystem.service.LaboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/labos")
public class LaboController {

    @Autowired
    private LaboService laboService;

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.LABO_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.LABO_WRITE + "\")")
    @GetMapping("get_list_labos")
    public ResponseEntity<Page<LaboDTO>> getListLabo(@RequestParam(required = false, defaultValue = "") String name,
                                                     @RequestParam(required = false, defaultValue = "") String phone,
                                                     Pageable pageable) {

        return ResponseEntity.ok().body(laboService.getListLabo(name, phone, pageable));
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.LABO_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.LABO_WRITE + "\")")
    @GetMapping("{id}")
    public ResponseEntity<LaboDTO> getDetailLabo(@NotNull @PathVariable Long id,
                                                 @RequestParam(required = false) Integer month,
                                                 @RequestParam(required = false) Integer year) {

        return ResponseEntity.ok().body(laboService.getDetailLabo(id, month, year));
    }

    @GetMapping("get_all_labo")
    public ResponseEntity<List<LaboDTO>> getAllLabo(@RequestParam(required = false, defaultValue = "") String name) {
        return ResponseEntity.ok().body(laboService.getAllLabo(name));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.LABO_WRITE + "\")")
    @PostMapping()
    public ResponseEntity<LaboDTO> addLabo(@Validated(LaboDTO.Create.class) @RequestBody LaboDTO laboDTO) {

        return ResponseEntity.ok().body(laboService.addLabo(laboDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.LABO_WRITE + "\")")
    @PutMapping("{id}")
    public ResponseEntity<LaboDTO> updateLabo(@NotNull @PathVariable Long id,
                                              @Validated(LaboDTO.Update.class) @RequestBody LaboDTO laboDTO) {

        return ResponseEntity.ok().body(laboService.updateLabo(id, laboDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.LABO_WRITE + "\")")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLabo(@NotNull @PathVariable Long id) {

        laboService.deleteLabo(id);
        return ResponseEntity.ok().build();
    }





}
