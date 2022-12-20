package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.MaterialImportDTO;
import com.example.dentalclinicmanagementsystem.service.MaterialImportService;
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
@RequestMapping("api/material_imports")
public class MaterialImportController {

    @Autowired
    private MaterialImportService materialImportService;

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.IMPORT_MATERIAL_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.IMPORT_MATERIAL_WRITE + "\")")
    @GetMapping("get_list_import")
    public ResponseEntity<Page<MaterialImportDTO>> getListImport(
            @RequestParam(required = false, defaultValue = "") String materialName,
            @RequestParam(required = false, defaultValue = "") String date,
            @RequestParam(required = false, defaultValue = "") String amount,
            @RequestParam(required = false, defaultValue = "") String unitPrice,
            @RequestParam(required = false, defaultValue = "") String supplyName,
            Pageable pageable
            ){

        return ResponseEntity.ok().body(materialImportService.getListImport(materialName, date, amount,unitPrice,
                supplyName, pageable));
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.IMPORT_MATERIAL_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.IMPORT_MATERIAL_WRITE + "\")")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialImportDTO> getDetail(@NotNull @PathVariable Long id){
        return ResponseEntity.ok().body(materialImportService.getDetail(id));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.IMPORT_MATERIAL_WRITE + "\")")
    @PostMapping()
    public ResponseEntity<MaterialImportDTO> importMaterial(
            @Validated(MaterialImportDTO.Create.class) @RequestBody MaterialImportDTO materialImportDTO){
        return ResponseEntity.ok().body(materialImportService.importMaterial(materialImportDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.IMPORT_MATERIAL_WRITE + "\")")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialImportDTO> updateImport(
            @NotNull @PathVariable Long id,
            @Validated(MaterialImportDTO.Create.class) @RequestBody MaterialImportDTO materialImportDTO){

        return ResponseEntity.ok().body(materialImportService.updateImport(id, materialImportDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.IMPORT_MATERIAL_WRITE + "\")")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImport(@NotNull @PathVariable Long id){
        materialImportService.deleteImport(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.IMPORT_MATERIAL_WRITE + "\")")
    @PostMapping("/add_list_import")
    public ResponseEntity<Void> addListImport(@NotNull @RequestBody List<MaterialImportDTO> materialImportDTOS) {
        materialImportService.addListImport(materialImportDTOS);
        return ResponseEntity.ok().build();
    }


}
