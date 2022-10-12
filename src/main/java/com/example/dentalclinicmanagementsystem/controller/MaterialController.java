package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.MaterialDTO;
import com.example.dentalclinicmanagementsystem.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@CrossOrigin
@RestController
@RequestMapping("api/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("get_list_materials")
    public ResponseEntity<Page<MaterialDTO>> getListMaterials(@RequestParam(required = false, defaultValue = "") String name,
                                                              @RequestParam(required = false, defaultValue = "") String unit,
                                                              @RequestParam(required = false, defaultValue = "") String price,
                                                              @RequestParam(required = false, defaultValue = "") String amount,
                                                              Pageable pageable) {

        return ResponseEntity.ok().body(materialService.getListMaterials(name, unit, price, amount, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> getDetailMaterial(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok().body(materialService.getDetailMaterial(id));
    }

    @PostMapping()
    public ResponseEntity<MaterialDTO> addMaterial(
            @Validated(MaterialDTO.Create.class) @RequestBody MaterialDTO materialDTO) {
        return ResponseEntity.ok().body(materialService.addMaterial(materialDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> updateMaterial(@NotNull @PathVariable Long id,
                                                      @Validated(MaterialDTO.Update.class) @RequestBody MaterialDTO materialDTO) {
        return ResponseEntity.ok().body(materialService.updateMaterial(id, materialDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@NotNull @PathVariable Long id){

        materialService.deleteMaterial(id);
        return ResponseEntity.ok().build();
    }


}
