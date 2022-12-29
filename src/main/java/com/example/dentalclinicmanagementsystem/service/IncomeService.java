package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.dto.IncomeDTO;
import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
import com.example.dentalclinicmanagementsystem.entity.TreatmentServiceMap;
import com.example.dentalclinicmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public IncomeDTO getIncome(String startDate, String endDate) {

        LocalDate start;
        LocalDate end;
        if (StringUtils.hasLength(startDate) && StringUtils.hasLength(endDate)) {
            start = convertToDate(startDate);
            end = convertToDate(endDate);
        } else {
            start = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
            end = LocalDate.now();
        }

        List<IncomeDetailDTO> incomeDetailDTOS = treatmentServiceMapRepository.findAllServiceInTime(start, end);

        incomeDetailDTOS.addAll(materialExportRepository.findAllMaterialExportInTime(start, end));

        Collections.sort(incomeDetailDTOS);
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTotalIncome(0L);
        incomeDTO.setIncomeDetailDTOS(incomeDetailDTOS);

        incomeDetailDTOS.forEach(item -> incomeDTO.setTotalIncome(incomeDTO.getTotalIncome() + item.getPrice().longValue()));

        return incomeDTO;
    }

    public IncomeDTO getNetIncome(String startDate, String endDate) {

        LocalDate start;
        LocalDate end;
        if (StringUtils.hasLength(startDate) && StringUtils.hasLength(endDate)) {
            start = convertToDate(startDate);
            end = convertToDate(endDate);
        } else {
            start = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
            end = LocalDate.now();
        }

        List<IncomeDetailDTO> incomeDetailDTOS = receiptRepository.findIncomeInTime(start, end);
        incomeDetailDTOS.addAll(materialExportRepository.findAllMaterialExportInTime(start, end));

        Collections.sort(incomeDetailDTOS);
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTotalIncome(0L);
        incomeDTO.setIncomeDetailDTOS(incomeDetailDTOS);

        incomeDetailDTOS.forEach(item -> incomeDTO.setTotalIncome(incomeDTO.getTotalIncome() + item.getPrice().longValue()));

        return incomeDTO;
    }

    public IncomeDTO getTotalSpend(String startDate, String endDate) {

        LocalDate start;
        LocalDate end;
        if (StringUtils.hasLength(startDate) && StringUtils.hasLength(endDate)) {
            start = convertToDate(startDate);
            end = convertToDate(endDate);
        } else {
            start = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
            end = LocalDate.now();
        }
        final String dateDescription = "Lương tháng "+ LocalDate.now().getMonth().getValue() + "/" + LocalDate.now().getYear();
        List<IncomeDetailDTO> incomeDetailDTOS = userRepository.findTotalSalary(start.atStartOfDay(), end.atStartOfDay());
        incomeDetailDTOS.forEach(item -> item.setDate(dateDescription));
        incomeDetailDTOS.addAll(specimenRepository.findTotalPrice(start, end));
        incomeDetailDTOS.addAll(materialImportRepository.findAllPrice(start, end));

        Collections.sort(incomeDetailDTOS);
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTotalIncome(0L);
        incomeDTO.setIncomeDetailDTOS(incomeDetailDTOS);

        incomeDetailDTOS.forEach(item -> incomeDTO.setTotalIncome(incomeDTO.getTotalIncome() + item.getPrice().longValue()));

        return incomeDTO;
    }

    private LocalDate convertToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //convert String to LocalDate
        return LocalDate.parse(date, formatter);
    }
}
