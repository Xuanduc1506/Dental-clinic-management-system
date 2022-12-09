package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO;
import com.example.dentalclinicmanagementsystem.entity.WaitingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WaitingRoomRepository extends JpaRepository<WaitingRoom, Long> {

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO(wr.waitingRoomId, wr.patientId,wr.date, p.patientName, wr.status) " +
            "FROM WaitingRoom wr JOIN Patient p ON wr.patientId = p.patientId WHERE wr.status <> 3 ORDER BY wr.isBooked DESC")
    Page<WaitingRoomDTO> getListWaitingRoom(@Param("patientName") String patientName,
                                            @Param("date") LocalDate date,
                                            Pageable pageable);

    WaitingRoom findByWaitingRoomIdAndStatus(Long id, Integer status);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO(wr.waitingRoomId, wr.patientId,wr.date, p.patientName, wr.status) " +
            "FROM WaitingRoom wr JOIN Patient p ON wr.patientId = p.patientId WHERE wr.status = 3 AND wr.isDeleted = true")
    List<WaitingRoomDTO> findAllListConfirm();


    WaitingRoom findByPatientIdAndDateAndIsDeleted(Long patientId, LocalDate date, Boolean isDeleted);

}
