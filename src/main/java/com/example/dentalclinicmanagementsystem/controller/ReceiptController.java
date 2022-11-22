package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.ReceiptDTO;
import com.example.dentalclinicmanagementsystem.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @GetMapping("get_list_receipts/{patientId}")
    public ResponseEntity<Page<ReceiptDTO>> getListReceipts(@PathVariable Long patientId,
                                                            @RequestParam(required = false, defaultValue = "") String payment,
                                                            @RequestParam(required = false, defaultValue = "") String date,
                                                            @RequestParam(required = false, defaultValue = "") String debit,
                                                            Pageable pageable) {

        return ResponseEntity.ok().body(receiptService.getListReceipts(patientId, payment, date, debit, pageable));

    }

    @GetMapping("{id}")
    public ResponseEntity<ReceiptDTO> getDetailReceipt(@PathVariable Long id) {

        return ResponseEntity.ok().body(receiptService.getDetailReceipt(id));
    }

    @PostMapping("{patientId}")
    public ResponseEntity<ReceiptDTO> addReceipt(@PathVariable("patientId") Long patientId,
                                                 @Validated(ReceiptDTO.Create.class) @RequestBody ReceiptDTO receiptDTO) {

        return ResponseEntity.ok().body( receiptService.addReceipt(patientId, receiptDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReceiptDTO> updateReceipt(@PathVariable Long id,
                                                    @Validated(ReceiptDTO.Update.class) @RequestBody ReceiptDTO receiptDTO) {

        return ResponseEntity.ok().body(receiptService.updateReceipt(id, receiptDTO));
    }


}
