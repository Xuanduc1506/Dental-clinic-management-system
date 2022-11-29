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
            serviceIncome = Objects.nonNull(treatmentServiceMapRepository.getTotalIncomeInMonth(number)) ? treatmentServiceMapRepository.getTotalIncomeInMonth(number) : 0 ;
            materialIncome = Objects.nonNull(materialExportRepository.getIncomeOfMaterialInMonth(number)) ? materialExportRepository.getIncomeOfMaterialInMonth(number) : 0;
            userSalary = Objects.nonNull(userRepository.totalMoneyOfUserInMonth(number)) ? userRepository.totalMoneyOfUserInMonth(number) : 0;
            buyMaterial = Objects.nonNull(materialImportRepository.getTotalMoneyInMonth(number)) ? materialImportRepository.getTotalMoneyInMonth(number) : 0;
            laboPayment = Objects.nonNull(specimenRepository.findTotalCostInMonth(number)) ? specimenRepository.findTotalCostInMonth(number) : 0;

        } else {
            if (Objects.isNull(number)) {
                number = LocalDate.now().getYear();
            }
            serviceIncome = Objects.nonNull(treatmentServiceMapRepository.getTotalIncomeInYear(number)) ? treatmentServiceMapRepository.getTotalIncomeInYear(number) : 0;
            materialIncome = Objects.nonNull(materialExportRepository.getIncomeOfMaterialInYear(number)) ? materialExportRepository.getIncomeOfMaterialInYear(number) : 0;
            userSalary = Objects.nonNull(userRepository.totalMoneyOfUserInYear(number)) ? userRepository.totalMoneyOfUserInYear(number) : 0;
            buyMaterial = Objects.nonNull(materialImportRepository.getTotalMoneyInYear(number)) ? materialImportRepository.getTotalMoneyInYear(number) : 0;
            laboPayment = Objects.nonNull(specimenRepository.findTotalCostInYear(number)) ? specimenRepository.findTotalCostInYear(number) : 0;
        }
        incomeDTO.setTotalIncome(serviceIncome + materialIncome);
        incomeDTO.setNetIncome(incomeDTO.getTotalIncome() - userSalary - buyMaterial - laboPayment);
        incomeDTO.setNotReceived(incomeDTO.getTotalIncome() - incomeDTO.getNetIncome());
        return incomeDTO;
    }
}
