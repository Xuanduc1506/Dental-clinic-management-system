package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
import com.example.dentalclinicmanagementsystem.entity.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecimenRepository extends JpaRepository<Specimen, Long> {

    @Query("SELECT SUM(s.price) FROM Specimen s WHERE s.laboId = :id AND MONTH(s.receiveDate) = :month")
    Integer findTotalCostInMonthOfLabo(@Param("id")Long id, @Param("month")Integer month);

    @Query("SELECT SUM(s.price) FROM Specimen s WHERE s.laboId = :id AND YEAR(s.receiveDate) = :year")
    Integer findTotalCostInYearOfLabo(@Param("id")Long id, @Param("year")Integer year);

    @Query("SELECT SUM(s.price) FROM Specimen s WHERE MONTH(s.receiveDate) = :month")
    Integer findTotalCostInMonth(@Param("month")Integer month);

    @Query("SELECT SUM(s.price) FROM Specimen s WHERE YEAR(s.receiveDate) = :year")
    Integer findTotalCostInYear(@Param("year")Integer year);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO(l.laboName, s.receiveDate, s.price) " +
            "FROM Labo l JOIN Specimen s ON l.laboId = s.laboId")
    List<IncomeDetailDTO> findTotalPrice(@Param("month")Integer month,
                                         @Param("year")Integer year);

}
