package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.LaboDTO;
import com.example.dentalclinicmanagementsystem.entity.Labo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboRepository extends JpaRepository<Labo, Long> {


    @Query(value = "SELECT new com.example.dentalclinicmanagementsystem.dto.LaboDTO(l.laboId, l.laboName, l.phone," +
            "CASE WHEN sum(s.unitPrice) is null THEN 0 ELSE sum(s.unitPrice) END)" +
            "FROM Labo l LEFT JOIN Specimen s ON l.laboId = s.laboId WHERE l.isDeleted = FALSE " +
            "AND (:name is null or l.laboName like %:name%)" +
            "AND (:phone is null or l.phone like %:phone%)" +
            "GROUP BY l.laboId " +
            "ORDER BY l.laboId DESC",
            countQuery = "SELECT COUNT(l.laboId) FROM Labo l WHERE l.isDeleted = FALSE " +
                    "AND (:name is null or l.laboName like %:name%)" +
                    "AND (:phone is null or l.phone like %:phone%)" +
                    "GROUP BY l.laboId " +
                    "ORDER BY l.laboId DESC")
    Page<LaboDTO> getListLabo(@Param("name") String name,
                              @Param("phone") String phone,
                              Pageable pageable);

    Labo findByLaboIdAndIsDeleted(Long id, Boolean isDelete);

    Labo findByLaboNameAndIsDeleted(String name, Boolean isDeleted);

    List<Labo> findAllByLaboNameContainingAndIsDeletedOrderByLaboIdDesc(String name, Boolean isDeleted);
}

