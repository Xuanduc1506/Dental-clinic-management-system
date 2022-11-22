package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.ReceiptDTO;
import com.example.dentalclinicmanagementsystem.entity.Receipt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceiptMapper extends EntityMapper<Receipt, ReceiptDTO> {

    Receipt toEntity(ReceiptDTO receiptDTO);

    ReceiptDTO toDto(Receipt receipt);
}
