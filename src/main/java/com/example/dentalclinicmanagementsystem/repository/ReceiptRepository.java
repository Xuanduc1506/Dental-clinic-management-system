package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
import com.example.dentalclinicmanagementsystem.dto.ReceiptDTO;
import com.example.dentalclinicmanagementsystem.entity.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("SELECT sum(r.payment) FROM Receipt r WHERE r.treatmentId = :treatmentId")
    Integer getPaidByTreatmentId(@Param("treatmentId") Long treatmentId);

    Receipt findByReceiptId(Long id);

    @Query(value = "SELECT new com.example.dentalclinicmanagementsystem.dto.ReceiptDTO(r.treatmentId, r.receiptId, r.payment, r.date, r.debit)" +
            "FROM Receipt r JOIN Treatment t ON r.treatmentId = t.treatmentId " +
            "WHERE t.patientId = :patientId " +
            "AND (:payment is null or r.paymentTemp like %:payment%) " +
            "AND (:date is null or r.dateTemp like %:date%) " +
            "AND (:debit is null or r.debitTemp like %:debit%)",
            countQuery = "SELECT count(r.receiptId)" +
            "FROM Receipt r JOIN Treatment t ON r.treatmentId = t.treatmentId " +
            "WHERE t.patientId = :patientId " +
            "AND (:payment is null or r.paymentTemp like %:payment%) " +
            "AND (:date is null or r.dateTemp like %:date%) " +
            "AND (:debit is null or r.debitTemp like %:debit%)")
    Page<ReceiptDTO> getListReceipts(@Param("patientId") Long patientId,
                                     @Param("payment") String payment,
                                     @Param("date") String date,
                                     @Param("debit") String debit,
                                     Pageable pageable);


    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.ReceiptDTO(r.treatmentId, r.receiptId, r.payment, r.date, r.debit)" +
            "FROM Receipt r WHERE r.receiptId <= :id")
    List<ReceiptDTO> findLastTwoReceipt(Long id, Pageable pageable);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO(p.patientName, r.date, r.payment)" +
            "FROM Receipt r JOIN Treatment t ON r.treatmentId = t.treatmentId JOIN Patient p ON t.patientId = p.patientId " +
            "WHERE r.date BETWEEN :startDate AND :endDate")
    List<IncomeDetailDTO> findIncomeInTime(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    Receipt findFirstByTreatmentIdOrderByReceiptIdDesc(Long treatmentId);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.ReceiptDTO(r.treatmentId, r.receiptId, r.payment, r.date, r.debit) " +
            "FROM Receipt r WHERE r.treatmentId = :treatmentId " +
            "AND (:payment is null or r.paymentTemp like %:payment%) " +
            "AND (:date is null or r.dateTemp like %:date%) " +
            "AND (:debit is null or r.debitTemp like %:debit%)")
    List<ReceiptDTO> getListReceiptsByTreatmentId(@Param("treatmentId") Long treatmentId,
                                                  @Param("payment") String payment,
                                                  @Param("date") String date,
                                                  @Param("debit") String debit);

    @Query("SELECT r.debit FROM Receipt r WHERE r.treatmentId = :treatmentId " +
            "ORDER BY r.receiptId DESC ")
    List<Integer> getDebit(@Param("treatmentId") Long treatmentId);
}
