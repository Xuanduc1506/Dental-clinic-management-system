package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.CategoryServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryServiceEntity, Long> {

    Page<CategoryServiceEntity> findAllByCategoryServiceNameContainingIgnoreCase(
            String CategoryServiceName, Pageable pageable);

    CategoryServiceEntity findByCategoryServiceName(String name);

    CategoryServiceEntity findByCategoryServiceId(Long id);
}
