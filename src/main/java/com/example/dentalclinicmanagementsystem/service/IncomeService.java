package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.dto.IncomeDTO;
import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
import com.example.dentalclinicmanagementsystem.entity.TreatmentServiceMap;
import com.example.dentalclinicmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class IncomeService {

    @Autowired
    private TreatmentServiceMapRepository treatmentServiceMapRepository;

    @Autowired
    private MaterialExportRepository materialExportRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private MaterialImportRepository materialImportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecimenRepository specimenRepository;

    public IncomeDTO getIncome(Integer month, Integer year) {

        if (Objects.isNull(month)) {
            month = LocalDate.now().getMonth().getValue();
        }

        if (Objects.isNull(year)) {
            year = LocalDate.now().getYear();
        }

        List<IncomeDetailDTO> incomeDetailDTOS = treatmentServiceMapRepository.findAllServiceInTime(month, year);

        incomeDetailDTOS.addAll(materialExportRepository.findAllMaterialExportInTime(month, year));

        Collections.sort(incomeDetailDTOS);
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTotalIncome(0L);
        incomeDTO.setIncomeDetailDTOS(incomeDetailDTOS);

        incomeDetailDTOS.forEach(item -> incomeDTO.setTotalIncome(incomeDTO.getTotalIncome() + item.getPrice().longValue()));

        return incomeDTO;
    }

    public IncomeDTO getNetIncome(Integer month, Integer year) {

        if (Objects.isNull(month)) {
            month = LocalDate.now().getMonth().getValue();
        }

        if (Objects.isNull(year)) {
            year = LocalDate.now().getYear();
        }

        List<IncomeDetailDTO> incomeDetailDTOS = receiptRepository.findIncomeInTime(month, year);
        incomeDetailDTOS.addAll(materialExportRepository.findAllMaterialExportInTime(month, year));

        Collections.sort(incomeDetailDTOS);
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTotalIncome(0L);
        incomeDTO.setIncomeDetailDTOS(incomeDetailDTOS);

        incomeDetailDTOS.forEach(item -> incomeDTO.setTotalIncome(incomeDTO.getTotalIncome() + item.getPrice().longValue()));

        return incomeDTO;
    }

    public IncomeDTO getTotalSpend(Integer month, Integer year) {

        if (Objects.isNull(month)) {
            month = LocalDate.now().getMonth().getValue();
        }

        if (Objects.isNull(year)) {
            year = LocalDate.now().getYear();
        }
        final String dateDescription = "Lương tháng "+ month + "/" + year;
        List<IncomeDetailDTO> incomeDetailDTOS = userRepository.findTotalSalary(month, year);
        incomeDetailDTOS.forEach(item -> item.setDate(dateDescription));
        incomeDetailDTOS.addAll(specimenRepository.findTotalPrice(month, year));
        incomeDetailDTOS.addAll(materialImportRepository.findAllPrice(month, year));

        Collections.sort(incomeDetailDTOS);
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTotalIncome(0L);
        incomeDTO.setIncomeDetailDTOS(incomeDetailDTOS);

        incomeDetailDTOS.forEach(item -> incomeDTO.setTotalIncome(incomeDTO.getTotalIncome() + item.getPrice().longValue()));

        return incomeDTO;
    }
}
