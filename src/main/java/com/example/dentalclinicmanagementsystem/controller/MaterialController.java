package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.MaterialDTO;
import com.example.dentalclinicmanagementsystem.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@CrossOrigin
@RestController
@RequestMapping("api/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PreAuthorize("hasAuthority(\"" + "material_read" + "\") or hasAnyAuthority(\"" + "material_write"+ "\")")
    @GetMapping("get_list_materials")
    public ResponseEntity<Page<MaterialDTO>> getListMaterials(@RequestParam(required = false, defaultValue = "") String name,
                                                              @RequestParam(required = false, defaultValue = "") String unit,
                                                              @RequestParam(required = false, defaultValue = "") String price,
                                                              @RequestParam(required = false, defaultValue = "") String amount,
                                                              Pageable pageable) {

        return ResponseEntity.ok().body(materialService.getListMaterials(name, unit, price, amount, pageable));
    }

    @PreAuthorize("hasAuthority(\"" + "material_read" + "\") or hasAnyAuthority(\"" + "material_write"+ "\")")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> getDetailMaterial(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok().body(materialService.getDetailMaterial(id));
    }

    @PreAuthorize("hasAnyAuthority(\"" + "material_write"+ "\")")
    @PostMapping()
    public ResponseEntity<MaterialDTO> addMaterial(
            @Validated(MaterialDTO.Create.class) @RequestBody MaterialDTO materialDTO) {
        return ResponseEntity.ok().body(materialService.addMaterial(materialDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + "material_write"+ "\")")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> updateMaterial(@NotNull @PathVariable Long id,
                                                      @Validated(MaterialDTO.Update.class) @RequestBody MaterialDTO materialDTO) {
        return ResponseEntity.ok().body(materialService.updateMaterial(id, materialDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + "material_write"+ "\")")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@NotNull @PathVariable Long id){

        materialService.deleteMaterial(id);
        return ResponseEntity.ok().build();
    }


}
