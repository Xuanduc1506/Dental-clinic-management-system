package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpecimenRepository extends JpaRepository<Specimen, Long> {

    @Query("SELECT SUM(s.price) FROM Specimen s WHERE MONTH(s.receiveDate) = :month")
    Integer findTotalCostInMonth(Integer month);

    @Query("SELECT SUM(s.price) FROM Specimen s WHERE YEAR(s.receiveDate) = :year")
    Integer findTotalCostInYear(Integer year);

}
