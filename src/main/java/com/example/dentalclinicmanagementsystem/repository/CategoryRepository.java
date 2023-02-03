package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.CategoryServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryServiceEntity, Long> {

    Page<CategoryServiceEntity> findAllByCategoryServiceNameContainingIgnoreCaseAndIsDeletedOrderByCategoryServiceIdDesc(
            String CategoryServiceName,Boolean isDeleted, Pageable pageable);

    CategoryServiceEntity findByCategoryServiceName(String name);

    CategoryServiceEntity findByCategoryServiceIdAndIsDeleted(Long id, Boolean isDeleted);

    List<CategoryServiceEntity> findAllByCategoryServiceNameContainingIgnoreCaseAndIsDeleted(String name, Boolean isDeleted);
}
