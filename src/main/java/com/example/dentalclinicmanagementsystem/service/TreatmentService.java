package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.TreatmentDTO;
import com.example.dentalclinicmanagementsystem.dto.TreatmentInterfaceDTO;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.repository.TreatmentRepository;
import com.example.dentalclinicmanagementsystem.repository.TreatmentServiceMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private TreatmentServiceMapRepository treatmentServiceMapRepository;

    public Page<TreatmentInterfaceDTO> getListBills(String patientName, String phone, Pageable pageable) {
        return treatmentRepository.getListBills(patientName, phone, pageable);

//        return page.stream().map(p -> {
//            TreatmentDTO treatmentDTO = new TreatmentDTO(p.getTreatmentId(), p.getPatientId(), p.getPatientName(), p.getPhone(),
//                    p.getTotalPrice()., p.getTotalDiscount(), p.getRealCost());
//
//        });
    }

    public TreatmentDTO getDetail(Long id) {

        TreatmentDTO treatmentDTO = treatmentRepository.getTreatmentById(id);
        if (Objects.isNull(treatmentDTO)) {
            throw new EntityNotFoundException(MessageConstant.Bill.BILL_NOT_FOUND,
                    EntityName.Bill.BILL, EntityName.Bill.BILL_ID);
        }
        treatmentDTO.setRealCost(treatmentDTO.getTotalPrice() - treatmentDTO.getTotalDiscount());
        treatmentDTO.setTreatmentServiceMapDTOList(treatmentServiceMapRepository.findAllByTreatmentId(id));

        return treatmentDTO;

    }
}
