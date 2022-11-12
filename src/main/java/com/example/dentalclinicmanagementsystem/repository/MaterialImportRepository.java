package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.MaterialImportDTO;
import com.example.dentalclinicmanagementsystem.entity.MaterialImport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialImportRepository extends JpaRepository<MaterialImport, Long> {

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.MaterialImportDTO(mi.materialImportId,mi.materialId, mi.date, " +
            "mi.amount, mi.supplyName, m.materialName, mi.totalPrice) FROM MaterialImport mi " +
            "JOIN Material m ON mi.materialId = m.materialId " +
            "WHERE mi.isDelete = FALSE " +
            "AND (:materialName is null or m.materialName like %:materialName%) " +
            "AND (:date is null or mi.dateTemp like %:date%)" +
            "AND (:amount is null or mi.amountTemp like %:amount%)" +
            "AND (:totalPrice is null or mi.totalPriceTemp like %:totalPrice%)" +
            "AND (:supplyName is null or mi.supplyName like %:supplyName%)")
    Page<MaterialImportDTO> getListImport(@Param("materialName") String materialName,
                                          @Param("date") String date,
                                          @Param("amount") String amount,
                                          @Param("totalPrice") String totalPrice,
                                          @Param("supplyName") String supplyName,
                                          Pageable pageable);


    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.MaterialImportDTO(mi.materialImportId, mi.materialId, mi.date, " +
            "mi.amount, mi.supplyName, m.materialName, mi.totalPrice) FROM MaterialImport mi " +
            "JOIN Material m on mi.materialId = m.materialId " +
            "WHERE mi.materialImportId = :id")
    MaterialImportDTO getDetail(@Param("id") Long id);

    MaterialImport findByMaterialImportIdAndIsDelete(Long id, Boolean isDelete);
}
