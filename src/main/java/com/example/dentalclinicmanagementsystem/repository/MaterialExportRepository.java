package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.MaterialExport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialExportRepository extends JpaRepository<MaterialExport, Long> {


}
