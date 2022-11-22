package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.ReceiptDTO;
import com.example.dentalclinicmanagementsystem.entity.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("SELECT sum(r.payment) FROM Receipt r WHERE r.treatmentId = :treatmentId")
    Integer getPaidByTreatmentId(@Param("treatmentId") Long treatmentId);

    Receipt findByReceiptId(Long id);

    @Query(value = "SELECT new com.example.dentalclinicmanagementsystem.dto.ReceiptDTO(r.receiptId, r.payment, r.date, r.debit)" +
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


    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.ReceiptDTO(r.receiptId, r.payment, r.date, r.debit)" +
            "FROM Receipt r WHERE r.receiptId <= :id")
    List<ReceiptDTO> findLaseTowReceipt(Long id, Pageable pageable);
//
//    List<Receipt> findAllByReceiptIdLessThanEqual(Long id);

}
