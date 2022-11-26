package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.CategoryServiceDTO;
import com.example.dentalclinicmanagementsystem.dto.DisplayServiceDTO;
import com.example.dentalclinicmanagementsystem.dto.ServiceDTO;
import com.example.dentalclinicmanagementsystem.service.CategoryService;
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
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.CATEGORY_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @GetMapping("get_list_category_service")
    public ResponseEntity<Page<CategoryServiceDTO>> getListService(
            @RequestParam(required = false, defaultValue = "") String name,
            Pageable pageable) {

        return ResponseEntity.ok().body(categoryService.getListService(name, pageable));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @PostMapping()
    public ResponseEntity<CategoryServiceDTO> addCategory(
            @Validated(CategoryServiceDTO.Create.class) @RequestBody CategoryServiceDTO categoryServiceDTO) {

        return ResponseEntity.ok().body(categoryService.addCategory(categoryServiceDTO));

    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.CATEGORY_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryServiceDTO> getDetailCategory(@NotNull @PathVariable Long id) {

        return ResponseEntity.ok().body(categoryService.getDetailCategory(id));
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.CATEGORY_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @GetMapping("get_list_service")
    public ResponseEntity<DisplayServiceDTO> displayAllService(
            @RequestParam(required = false, defaultValue = "") String name) {

        return ResponseEntity.ok().body(categoryService.displayAllService(name));

    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryServiceDTO> updateCategory(
            @NotNull @PathVariable Long id,
            @Validated(CategoryServiceDTO.Update.class) @RequestBody CategoryServiceDTO categoryServiceDTO) {

        return ResponseEntity.ok().body(categoryService.updateCategory(id, categoryServiceDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@NotNull @PathVariable Long id) {

        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.CATEGORY_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @GetMapping("get_detail_service/{serviceId}")
    public ResponseEntity<ServiceDTO> getDetailService(@NotNull @PathVariable Long serviceId) {

        return ResponseEntity.ok().body(categoryService.getDetailService(serviceId));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @PostMapping("add_service")
    public ResponseEntity<ServiceDTO> addService(
            @Validated(ServiceDTO.Create.class) @RequestBody ServiceDTO serviceDTO) {

        return ResponseEntity.ok().body(categoryService.addService(serviceDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @PutMapping("update_service/{serviceId}")
    public ResponseEntity<ServiceDTO> updateService(
            @NotNull @PathVariable Long serviceId,
            @Validated(ServiceDTO.Update.class)@RequestBody ServiceDTO serviceDTO) {

        return ResponseEntity.ok().body(categoryService.updateService(serviceId, serviceDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @DeleteMapping("delete_service/{serviceId}")
    public ResponseEntity<Void> deleteService(@NotNull @PathVariable Long serviceId) {
        categoryService.deleteService(serviceId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.CATEGORY_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.CATEGORY_WRITE + "\")")
    @GetMapping("get_treating_service/{patientId}")
    public ResponseEntity<List<ServiceDTO>> getTreatingService(@PathVariable Long patientId){

        return ResponseEntity.ok().body(categoryService.getTreatingService(patientId));
    }


}
