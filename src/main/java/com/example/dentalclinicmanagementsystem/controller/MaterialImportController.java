package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.MaterialImportDTO;
import com.example.dentalclinicmanagementsystem.service.MaterialImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/material_imports")
public class MaterialImportController {

    @Autowired
    private MaterialImportService materialImportService;


    @GetMapping("get_list_import")
    public ResponseEntity<Page<MaterialImportDTO>> getListImport(
            @RequestParam(required = false, defaultValue = "") String materialName,
            @RequestParam(required = false, defaultValue = "") String date,
            @RequestParam(required = false, defaultValue = "") String amount,
            @RequestParam(required = false, defaultValue = "") String totalPrice,
            @RequestParam(required = false, defaultValue = "") String supplyName,
            Pageable pageable
            ){

        return ResponseEntity.ok().body(materialImportService.getListImport(materialName, date, amount,totalPrice,
                supplyName, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialImportDTO> getDetail(@NotNull @PathVariable Long id){
        return ResponseEntity.ok().body(materialImportService.getDetail(id));
    }

    @PostMapping()
    public ResponseEntity<MaterialImportDTO> importMaterial(
            @Validated(MaterialImportDTO.Create.class) @RequestBody MaterialImportDTO materialImportDTO){
        return ResponseEntity.ok().body(materialImportService.importMaterial(materialImportDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialImportDTO> updateImport(
            @NotNull @PathVariable Long id,
            @Validated(MaterialImportDTO.Create.class) @RequestBody MaterialImportDTO materialImportDTO){

        return ResponseEntity.ok().body(materialImportService.updateImport(id, materialImportDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImport(@NotNull @PathVariable Long id){
        materialImportService.deleteImport(id);
        return ResponseEntity.ok().build();
    }

}
