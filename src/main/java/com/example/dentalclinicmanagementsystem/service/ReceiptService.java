package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.ReceiptDTO;
import com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO;
import com.example.dentalclinicmanagementsystem.entity.Patient;
import com.example.dentalclinicmanagementsystem.entity.Receipt;
import com.example.dentalclinicmanagementsystem.entity.Treatment;
import com.example.dentalclinicmanagementsystem.entity.WaitingRoom;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
import com.example.dentalclinicmanagementsystem.mapper.ReceiptMapper;
import com.example.dentalclinicmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReceiptService {

    public static final int FIRST_PAGE = 0;

    public static final int LAST_TOW_RECORD = 2;

    public static final int FIRST_RECORD = 0;

    public static final int SECOND_RECORD = 1;

    public static final int MORE_THEN_1_RECORD = 1;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TreatmentServiceMapRepository treatmentServiceMapRepository;

    @Autowired
    private WaitingRoomRepository waitingRoomRepository;

    public ReceiptDTO addReceipt(Long patientId, ReceiptDTO receiptDTO) {

        Patient patient = patientRepository.findByPatientIdAndIsDeleted(patientId, Boolean.FALSE);

        if (Objects.isNull(patient)) {
            throw new EntityNotFoundException(MessageConstant.Patient.PATIENT_NOT_FOUND, EntityName.Patient.PATIENT,
                    EntityName.Patient.PATIENT_ID);
        }

        Treatment treatment = treatmentRepository.findFirstByPatientIdOrderByTreatmentIdDesc(patientId);

        Integer totalMoney = treatmentServiceMapRepository.getTotalMoney(treatment.getTreatmentId());
        Integer oldPaid = receiptRepository.getPaidByTreatmentId(treatment.getTreatmentId());

        Integer paid = (Objects.nonNull(oldPaid) ? oldPaid : 0) + receiptDTO.getPayment();

        receiptDTO.setReceiptId(null);
        receiptDTO.setTreatmentId(treatment.getTreatmentId());
        receiptDTO.setDebit(totalMoney - paid);
        receiptDTO.setDate(LocalDate.now());
        Receipt receipt = receiptMapper.toEntity(receiptDTO);
        receiptRepository.save(receipt);

        return receiptMapper.toDto(receipt);
    }

    public ReceiptDTO updateReceipt(Long id, ReceiptDTO receiptDTO) {

        receiptDTO.setReceiptId(id);
        Receipt receiptDb = receiptRepository.findByReceiptId(id);

        if (Objects.isNull(receiptDb)) {
            throw new EntityNotFoundException(MessageConstant.Receipt.RECEIPT_NOT_FOUND,
                    EntityName.Receipt.RECEIPT_ID, EntityName.Receipt.RECEIPT_ID);
        }

        if (receiptDb.getDate().plusDays(1).isAfter(LocalDate.now())) {
            throw new AccessDenyException(MessageConstant.Receipt.RECEIPT_OVER_DATE,
                    EntityName.Receipt.RECEIPT_ID);
        }

        receiptDTO.setDebit(receiptDb.getDebit() - (receiptDTO.getPayment() - receiptDb.getPayment()));
        receiptDTO.setDate(LocalDate.now());
        Receipt receipt = receiptMapper.toEntity(receiptDTO);
        receiptRepository.save(receipt);
        return receiptMapper.toDto(receipt);
    }

    public Page<ReceiptDTO> getListReceipts(Long patientId, String payment, String date, String debit,
                                            Pageable pageable) {

        Page<ReceiptDTO> page = receiptRepository.getListReceipts(patientId, payment, date, debit, pageable);
        List<ReceiptDTO> list = page.getContent();
        for (int i = 1; i < list.size(); i++) {
            list.get(i).setOldDebit(list.get(i - 1).getDebit());
        }

        return new PageImpl<>(list, pageable, list.size());
    }

    public ReceiptDTO getDetailReceipt(Long id) {

        Pageable pageable = PageRequest.of(FIRST_PAGE, LAST_TOW_RECORD, Sort.by(Sort.Direction.DESC, "receiptId"));

        List<ReceiptDTO> receiptDTOS = receiptRepository.findLastTwoReceipt(id, pageable);

        List<Long> ids = receiptDTOS.stream().map(ReceiptDTO::getReceiptId).collect(Collectors.toList());

        if (!ids.contains(id)) {
            throw new EntityNotFoundException(MessageConstant.Receipt.RECEIPT_NOT_FOUND, EntityName.Receipt.RECEIPT,
                    EntityName.Receipt.RECEIPT_ID);

        }
        ReceiptDTO receiptDTO = receiptDTOS.get(FIRST_RECORD);
        if (receiptDTOS.size() > MORE_THEN_1_RECORD) {
            receiptDTO.setOldDebit(receiptDTOS.get(SECOND_RECORD).getDebit());
        }

        return receiptDTO;
    }

    public ReceiptDTO getNewReceipts(Long treatmentId) {

        Receipt lastReceipt = receiptRepository.findFirstByTreatmentIdOrderByReceiptIdDesc(treatmentId);
        if (Objects.isNull(lastReceipt)) {
            lastReceipt = new Receipt();
        }
        ReceiptDTO receiptDTO = receiptMapper.toDto(lastReceipt);
        receiptDTO.setPayment(null);

        receiptDTO.setDate(LocalDate.now());

        List<TreatmentServiceMapDTO> treatmentServiceMapDTOS = treatmentServiceMapRepository.findAllServiceInLastRecord(treatmentId);
        receiptDTO.setNewServices(treatmentServiceMapDTOS);

        receiptDTO.setDebit(Objects.nonNull(receiptDTO.getDebit()) ? receiptDTO.getDebit()
                : treatmentServiceMapDTOS.stream().mapToInt(treatmentServiceMapDTO
                -> treatmentServiceMapDTO.getCurrentPrice()- treatmentServiceMapDTO.getDiscount()).sum());
        return receiptDTO;
    }

    public List<ReceiptDTO> getListReceiptsByTreatmentId(Long treatmentId, String payment, String date, String debit) {

        List<ReceiptDTO> receiptDTOS = receiptRepository.getListReceiptsByTreatmentId(treatmentId, payment, date, debit);

        for (int i = 1; i < receiptDTOS.size(); i++) {
            receiptDTOS.get(i).setOldDebit(receiptDTOS.get(i - 1).getDebit());
        }
        return receiptDTOS;
    }
}
