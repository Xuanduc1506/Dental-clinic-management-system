package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO;
import com.example.dentalclinicmanagementsystem.service.MaterialExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/material_export")
public class MaterialExportController {

    @Autowired
    private MaterialExportService materialExportService;

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.EXPORT_MATERIAL_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.EXPORT_MATERIAL_WRITE + "\")")
    @GetMapping("/get_list_export")
    public ResponseEntity<Page<MaterialExportDTO>> getListExport(
            @RequestParam(required = false, defaultValue = "") String materialName,
            @RequestParam(required = false, defaultValue = "") String date,
            @RequestParam(required = false, defaultValue = "") String amount,
            @RequestParam(required = false, defaultValue = "") String unitPrice,
            @RequestParam(required = false, defaultValue = "") String patientName,
            Pageable pageable) {

        return ResponseEntity.ok().body(
                materialExportService.getListExport(materialName, date, amount, unitPrice, patientName, pageable));
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.EXPORT_MATERIAL_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.EXPORT_MATERIAL_WRITE + "\")")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialExportDTO> getDetailMaterialExport(@PathVariable Long id) {

        return ResponseEntity.ok().body(materialExportService.getDetailMaterialExport(id));

    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.EXPORT_MATERIAL_WRITE + "\")")
    @PostMapping()
    public ResponseEntity<MaterialExportDTO> addMaterialExport(@RequestBody MaterialExportDTO materialExportDTO) {
        return ResponseEntity.ok().body(materialExportService.addMaterialExport(materialExportDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.EXPORT_MATERIAL_WRITE + "\")")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialExportDTO> updateMaterialExport(
            @PathVariable Long id,
            @Validated(MaterialExportDTO.Update.class) @RequestBody MaterialExportDTO materialExportDTO) {

        return ResponseEntity.ok().body(materialExportService.updateMaterialExport(id, materialExportDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.EXPORT_MATERIAL_WRITE + "\")")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterialExport(@PathVariable Long id) {

        materialExportService.deleteMaterialExport(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.EXPORT_MATERIAL_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.EXPORT_MATERIAL_WRITE + "\")")
    @GetMapping("get_list_material_export_of_patient/{patientId}")
    public ResponseEntity<List<MaterialExportDTO>> getListMaterialExportOfPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok().body(materialExportService.getListMaterialExportOfPatient(patientId));
    }
}
