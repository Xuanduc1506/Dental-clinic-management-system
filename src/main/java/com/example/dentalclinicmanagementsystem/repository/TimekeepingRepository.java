package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.TimekeepingDTO;
import com.example.dentalclinicmanagementsystem.entity.Timekeeping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TimekeepingRepository extends JpaRepository<Timekeeping, Long> {

    Timekeeping findFirstByUserIdOrderByTimekeepingIdDesc(Long userId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.TimekeepingDTO(t.timekeepingId, t.userId, t.timeCheckin, t.timeCheckout, u.fullName) " +
            "FROM Timekeeping t JOIN User u ON t.userId = u.userId " +
            "WHERE (:startTime is null or t.timeCheckin >= :startTime) " +
            "AND (:endTime is null or t.timeCheckout <= :endTime) " +
            "AND (:fullName is null or u.fullName like %:fullName%) " +
            "ORDER BY t.timeCheckin DESC ")
    Page<TimekeepingDTO> getListTimeKeepingOfAdmin(@Param("startTime")LocalDate startTime,
                                                   @Param("endTime")LocalDate endTime,
                                                   @Param("fullName")String fullName,
                                                   Pageable pageable);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.TimekeepingDTO(t.timekeepingId, t.userId, t.timeCheckin, t.timeCheckout) " +
            "FROM Timekeeping t " +
            "WHERE t.userId = :userId " +
            "AND MONTH(t.timeCheckin) = :month ORDER BY t.timeCheckin DESC ")
    Page<TimekeepingDTO> getListTimeKeepingOfUser(@Param("month")Integer month,
                                                   @Param("userId")Long userId,
                                                   Pageable pageable);

//    Timekeeping findFirstByUserIdOrderByTimekeepingIdDesc(Long userId);

    @Query("SELECT COUNT(t.timekeepingId) FROM Timekeeping t " +
            "WHERE t.userId = :userId " +
            "AND MONTH(t.timeCheckin) = :month " +
            "AND MONTH(t.timeCheckout) = :month")
    Integer findWorkDatByUserIdAndMonth(@Param("userId")Long userId,
                                        @Param("month")Integer month);

}
