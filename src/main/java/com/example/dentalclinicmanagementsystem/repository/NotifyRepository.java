package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {

    @Query("SELECT n FROM Notify n ORDER BY n.notifyId DESC ")
    List<Notify> getListNotify();

    Notify findByNotifyId(Long id);
}
