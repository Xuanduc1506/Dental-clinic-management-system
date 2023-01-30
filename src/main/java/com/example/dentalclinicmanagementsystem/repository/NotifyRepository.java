package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.NotifyDTO;
import com.example.dentalclinicmanagementsystem.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.NotifyDTO(n.notifyId, n.treatmentId, n.isRead, p.patientName)" +
            " FROM Notify n JOIN Treatment t ON n.treatmentId = t.treatmentId " +
            "JOIN Patient p ON p.patientId = t.patientId " +
            "ORDER BY n.notifyId DESC ")
    List<NotifyDTO> getListNotify();

    Notify findByNotifyId(Long id);
}
