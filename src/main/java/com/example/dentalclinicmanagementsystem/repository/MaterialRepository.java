package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query("SELECT m FROM Material m WHERE m.enable = true " +
            "AND (:name is null or m.materialName like %:name%)" +
            "AND (:unit is null or m.unit like %:unit%)" +
            "AND (:price is null or m.priceTemp like %:price%)" +
            "AND (:amount is null or m.amountTemp like %:amount%)")
    Page<Material> findAndSearchAllByMaterial(@Param("name") String name,
                                              @Param("unit") String unit,
                                              @Param("price") String price,
                                              @Param("amount") String amount,
                                              Pageable pageable);

    Material findByMaterialId(Long id);

    Material findByMaterialIdAndEnable(Long id, Boolean enable);

    Material findByMaterialName(String name);
}
