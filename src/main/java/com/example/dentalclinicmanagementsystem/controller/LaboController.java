package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.LaboDTO;
import com.example.dentalclinicmanagementsystem.service.LaboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@CrossOrigin
@RestController
@RequestMapping("api/labos")
public class LaboController {

    @Autowired
    private LaboService laboService;

    @GetMapping("get_list_labos")
    public ResponseEntity<Page<LaboDTO>> getListLabo(@RequestParam(required = false, defaultValue = "") String name,
                                                     @RequestParam(required = false, defaultValue = "") String phone,
                                                     Pageable pageable) {

        return ResponseEntity.ok().body(laboService.getListLabo(name, phone, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<LaboDTO> getDetailLabo(@NotNull @PathVariable Long id,
                                                 @RequestParam(defaultValue = "month") String statisticBy,
                                                 @RequestParam(required = false) Integer number) {

        return ResponseEntity.ok().body(laboService.getDetailLabo(id, statisticBy, number));
    }

    @PostMapping()
    public ResponseEntity<LaboDTO> addLabo(@Validated(LaboDTO.Create.class) @RequestBody LaboDTO laboDTO) {

        return ResponseEntity.ok().body(laboService.addLabo(laboDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<LaboDTO> updateLabo(@NotNull @PathVariable Long id,
                                              @Validated(LaboDTO.Update.class) @RequestBody LaboDTO laboDTO) {

        return ResponseEntity.ok().body(laboService.updateLabo(id, laboDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLabo(@NotNull @PathVariable Long id) {

        laboService.deleteLabo(id);
        return ResponseEntity.ok().build();
    }



}
