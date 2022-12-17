package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.ReceiptDTO;
import com.example.dentalclinicmanagementsystem.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.RECEIPT_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.RECEIPT_WRITE + "\")")
    @GetMapping("get_list_receipts/{patientId}")
    public ResponseEntity<Page<ReceiptDTO>> getListReceipts(@PathVariable Long patientId,
                                                            @RequestParam(required = false, defaultValue = "") String payment,
                                                            @RequestParam(required = false, defaultValue = "") String date,
                                                            @RequestParam(required = false, defaultValue = "") String debit,
                                                            Pageable pageable) {

        return ResponseEntity.ok().body(receiptService.getListReceipts(patientId, payment, date, debit, pageable));

    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.RECEIPT_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.RECEIPT_WRITE + "\")")
    @GetMapping("get_list_receipts_by_treatment/{treatmentId}")
    public ResponseEntity<List<ReceiptDTO>> getListReceiptsByTreatmentId(@PathVariable Long treatmentId,
                                                                         @RequestParam(required = false, defaultValue = "") String payment,
                                                                         @RequestParam(required = false, defaultValue = "") String date,
                                                                         @RequestParam(required = false, defaultValue = "") String debit) {

        return ResponseEntity.ok().body(receiptService.getListReceiptsByTreatmentId(treatmentId, payment, date, debit));

    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.RECEIPT_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.RECEIPT_WRITE + "\")")
    @GetMapping("{id}")
    public ResponseEntity<ReceiptDTO> getDetailReceipt(@PathVariable Long id) {

        return ResponseEntity.ok().body(receiptService.getDetailReceipt(id));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.RECEIPT_WRITE + "\")")
    @PostMapping("{treatmentId}")
    public ResponseEntity<ReceiptDTO> addReceipt(@PathVariable("treatmentId") Long treatmentId,
                                                 @Validated(ReceiptDTO.Create.class) @RequestBody ReceiptDTO receiptDTO) {

        return ResponseEntity.ok().body( receiptService.addReceipt(treatmentId, receiptDTO));
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.RECEIPT_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.RECEIPT_WRITE + "\")")
    @GetMapping("new_receipts/{treatmentId}")
    public ResponseEntity<ReceiptDTO> getNewReceipts(@PathVariable Long treatmentId) {
        return ResponseEntity.ok().body(receiptService.getNewReceipts(treatmentId));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.RECEIPT_WRITE + "\")")
    @PutMapping("{id}")
    public ResponseEntity<ReceiptDTO> updateReceipt(@PathVariable Long id,
                                                    @Validated(ReceiptDTO.Update.class) @RequestBody ReceiptDTO receiptDTO) {

        return ResponseEntity.ok().body(receiptService.updateReceipt(id, receiptDTO));
    }


}
