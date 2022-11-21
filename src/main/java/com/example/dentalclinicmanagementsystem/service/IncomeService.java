package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.dto.IncomeDTO;
import com.example.dentalclinicmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class IncomeService {

    @Autowired
    private TreatmentServiceMapRepository treatmentServiceMapRepository;

    @Autowired
    private MaterialExportRepository materialExportRepository;

    @Autowired
    private MaterialImportRepository materialImportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecimenRepository specimenRepository;

    public IncomeDTO getIncome(String statisticsBy, Integer number) {

        IncomeDTO incomeDTO = new IncomeDTO();
        Integer serviceIncome;
        Integer materialIncome;
        Integer userSalary;
        Integer buyMaterial;
        Integer laboPayment;

        if (Objects.equals(statisticsBy, "month")) {
            if (Objects.isNull(number)) {
                number = LocalDate.now().getMonth().getValue();
            }
            serviceIncome = treatmentServiceMapRepository.getTotalIncomeInMonth(number);
            materialIncome = materialExportRepository.getIncomeOfMaterialInMonth(number);
            userSalary = userRepository.totalMoneyOfUserInMonth(number);
            buyMaterial = materialImportRepository.getTotalMoneyInMonth(number);
            laboPayment = specimenRepository.findTotalCostInMonth(number);

        } else {
            if (Objects.isNull(number)) {
                number = LocalDate.now().getYear();
            }
            serviceIncome = treatmentServiceMapRepository.getTotalIncomeInYear(number);
            materialIncome = materialExportRepository.getIncomeOfMaterialInYear(number);
            userSalary = userRepository.totalMoneyOfUserInYear(number);
            buyMaterial = materialImportRepository.getTotalMoneyInYear(number);
            laboPayment = specimenRepository.findTotalCostInYear(number);
        }
        incomeDTO.setTotalIncome(serviceIncome + materialIncome);
        incomeDTO.setNetIncome(incomeDTO.getTotalIncome() - userSalary - buyMaterial - laboPayment);
        incomeDTO.setNotReceived(incomeDTO.getTotalIncome() - incomeDTO.getNetIncome());
        return incomeDTO;
    }
}
