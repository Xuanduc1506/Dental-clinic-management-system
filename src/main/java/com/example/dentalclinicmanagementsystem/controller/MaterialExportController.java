package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO;
import com.example.dentalclinicmanagementsystem.service.MaterialExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/material_export")
public class MaterialExportController {

    @Autowired
    private MaterialExportService materialExportService;

    @GetMapping("/get_list_export")
    public ResponseEntity<Page<MaterialExportDTO>> getListExport(
            @RequestParam(required = false, defaultValue = "") String materialName,
            @RequestParam(required = false, defaultValue = "") String date,
            @RequestParam(required = false, defaultValue = "") String amount,
            @RequestParam(required = false, defaultValue = "") String totalPrice,
            @RequestParam(required = false, defaultValue = "") String patientName,
            Pageable pageable) {

        return ResponseEntity.ok().body(
                materialExportService.getListExport(materialName, date, amount, totalPrice, patientName, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialExportDTO> getDetailMaterialExport(@PathVariable Long id) {

        return ResponseEntity.ok().body(materialExportService.getDetailMaterialExport(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialExportDTO> updateMaterialExport(
            @PathVariable Long id,
            @Validated(MaterialExportDTO.Update.class) @RequestBody MaterialExportDTO materialExportDTO) {

        return ResponseEntity.ok().body(materialExportService.updateMaterialExport(id, materialExportDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterialExport(@PathVariable Long id) {

        materialExportService.deleteMaterialExport(id);
        return ResponseEntity.ok().build();
    }
}
