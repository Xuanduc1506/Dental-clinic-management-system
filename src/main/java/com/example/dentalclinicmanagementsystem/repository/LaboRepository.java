package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.LaboDTO;
import com.example.dentalclinicmanagementsystem.entity.Labo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboRepository extends JpaRepository<Labo, Long> {


    @Query(value = "SELECT new com.example.dentalclinicmanagementsystem.dto.LaboDTO(l.laboId, l.laboName, l.phone)" +
            "FROM Labo l WHERE l.isDeleted = FALSE " +
            "AND (:name is null or l.laboName like %:name%)" +
            "AND (:phone is null or l.phone like %:phone%)",
            countQuery = "SELECT COUNT(l.laboId) FROM Labo l WHERE l.isDeleted = FALSE " +
                    "AND (:name is null or l.laboName like %:name%)" +
                    "AND (:phone is null or l.phone like %:phone%)")
    Page<LaboDTO> getListLabo(@Param("name") String name,
                              @Param("phone") String phone,
                              Pageable pageable);

    Labo findByLaboIdAndIsDeleted(Long id, Boolean isDelete);

    Labo findByLaboName(String name);
}

