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

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO(wr.waitingRoomId, wr.patientId,wr.date, p.patientName, wr.status, u.userId, u.userName) " +
            "FROM WaitingRoom wr JOIN Patient p ON wr.patientId = p.patientId " +
            "LEFT JOIN User u ON u.userId = wr.userId " +
            "WHERE (wr.status = 1 OR wr.status = 2) " +
            "AND wr.isDeleted = FALSE " +
            "AND (:patientName is null or p.patientName like %:patientName%) " +
            "AND wr.date = :date " +
            "ORDER BY wr.status ASC, wr.isBooked DESC")
    Page<WaitingRoomDTO> getListWaitingRoom(@Param("patientName") String patientName,
                                            @Param("date") LocalDate date,
                                            Pageable pageable);

    WaitingRoom findByWaitingRoomIdAndStatus(Long id, Integer status);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO(wr.waitingRoomId, wr.patientId,wr.date, p.patientName, wr.status, u.userId, u.fullName) " +
            "FROM WaitingRoom wr JOIN Patient p ON wr.patientId = p.patientId " +
            "LEFT JOIN User u ON wr.userId = u.userId " +
            "WHERE wr.status = 3 AND wr.isDeleted = FALSE ")
    List<WaitingRoomDTO> findAllListConfirm();


    WaitingRoom findByPatientIdAndDateAndIsDeleted(Long patientId, LocalDate date, Boolean isDeleted);

    WaitingRoom findByWaitingRoomIdAndStatusAndAndIsDeleted(Long id, Integer status, Boolean isDelete);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO(wr.waitingRoomId, wr.patientId, wr.date, wr.status, wr.note, p.patientName, p.phone) " +
            "FROM WaitingRoom wr JOIN Patient p ON wr.patientId = p.patientId WHERE wr.isDeleted = FALSE " +
            "AND wr.status = 0 " +
            "AND (:patientName is null or p.patientName LIKE %:patientName%) " +
            "AND wr.date >= :date")
    Page<WaitingRoomDTO> getListSchedule(@Param("patientName") String patientName,
                                         @Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO(wr.waitingRoomId, wr.patientId, wr.date, wr.status, wr.note, p.patientName, p.phone) " +
            "FROM WaitingRoom wr JOIN Patient p ON wr.patientId = p.patientId WHERE wr.isDeleted = FALSE " +
            "AND wr.status = 0 " +
            "AND (:patientName is null or p.patientName LIKE %:patientName%) " +
            "AND wr.date = :date")
    Page<WaitingRoomDTO> getListScheduleInDay(@Param("patientName") String patientName,
                                         @Param("date") LocalDate date, Pageable pageable);
    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO(wr.waitingRoomId, wr.patientId, wr.date, wr.status, wr.note, p.patientName, p.phone) " +
            "FROM WaitingRoom wr JOIN Patient p ON wr.patientId = p.patientId " +
            "WHERE wr.isDeleted = FALSE AND wr.waitingRoomId = :id AND wr.status = 0")
    WaitingRoomDTO getDetailSchedule(@Param("id") Long id);

    @Query("SELECT wr.patientId FROM WaitingRoom wr " +
            "WHERE wr.isDeleted = FALSE AND wr.isBooked = TRUE AND wr.status = 0 AND wr.date = :date ")
    List<Long> getListPatientIdInDay(@Param("date") LocalDate date);

    WaitingRoom findAllByWaitingRoomIdAndIsDeleted(Long id, Boolean isDelete);

    WaitingRoom findAllByPatientIdAndDateAndIsBooked(Long patientId, LocalDate date, Boolean isBooked);
}
