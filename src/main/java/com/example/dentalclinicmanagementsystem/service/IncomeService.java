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

    public IncomeDTO getIncome(LocalDate startDate, LocalDate endDate) {

        if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
            endDate = LocalDate.now();
        }

        List<IncomeDetailDTO> incomeDetailDTOS = treatmentServiceMapRepository.findAllServiceInTime(startDate, endDate);

        incomeDetailDTOS.addAll(materialExportRepository.findAllMaterialExportInTime(startDate, endDate));

        Collections.sort(incomeDetailDTOS);
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTotalIncome(0L);
        incomeDTO.setIncomeDetailDTOS(incomeDetailDTOS);

        incomeDetailDTOS.forEach(item -> incomeDTO.setTotalIncome(incomeDTO.getTotalIncome() + item.getPrice().longValue()));

        return incomeDTO;
    }

    public IncomeDTO getNetIncome(LocalDate startDate, LocalDate endDate) {

        if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
            endDate = LocalDate.now();
        }

        List<IncomeDetailDTO> incomeDetailDTOS = receiptRepository.findIncomeInTime(startDate, endDate);
        incomeDetailDTOS.addAll(materialExportRepository.findAllMaterialExportInTime(startDate, endDate));

        Collections.sort(incomeDetailDTOS);
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTotalIncome(0L);
        incomeDTO.setIncomeDetailDTOS(incomeDetailDTOS);

        incomeDetailDTOS.forEach(item -> incomeDTO.setTotalIncome(incomeDTO.getTotalIncome() + item.getPrice().longValue()));

        return incomeDTO;
    }

    public IncomeDTO getTotalSpend(LocalDate startDate, LocalDate endDate) {

        if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
            endDate = LocalDate.now();
        }
        final String dateDescription = "Lương tháng "+ LocalDate.now().getMonth().getValue() + "/" + LocalDate.now().getYear();
        List<IncomeDetailDTO> incomeDetailDTOS = userRepository.findTotalSalary(startDate.atStartOfDay(), endDate.atStartOfDay());
        incomeDetailDTOS.forEach(item -> item.setDate(dateDescription));
        incomeDetailDTOS.addAll(specimenRepository.findTotalPrice(startDate, endDate));
        incomeDetailDTOS.addAll(materialImportRepository.findAllPrice(startDate, endDate));

        Collections.sort(incomeDetailDTOS);
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTotalIncome(0L);
        incomeDTO.setIncomeDetailDTOS(incomeDetailDTOS);

        incomeDetailDTOS.forEach(item -> incomeDTO.setTotalIncome(incomeDTO.getTotalIncome() + item.getPrice().longValue()));

        return incomeDTO;
    }
}
