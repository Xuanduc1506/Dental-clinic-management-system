package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findAllByCategoryServiceId(Long id);

    List<Service> findAllByServiceNameContainingIgnoreCase(String name);

    Service findByServiceId(Long id);

    Service findByServiceName(String name);

    List<Service> findAllByServiceIdIn(List<Long> ids);
}
