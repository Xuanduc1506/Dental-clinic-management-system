package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.StatusConstant;
import com.example.dentalclinicmanagementsystem.dto.PatientRecordDTO;
import com.example.dentalclinicmanagementsystem.entity.*;
import com.example.dentalclinicmanagementsystem.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private TreatmentRepository treatmentRepository;

    @MockBean
    private TreatmentServiceMapRepository treatmentServiceMapRepository;

    @MockBean
    private PatientRecordRepository patientRecordRepository;

    @MockBean
    private PatientRecordServiceMapRepository patientRecordServiceMapRepository;

    @Nested
    @DisplayName("Add patient record")
    class TestLogin {

        @Test
        @DisplayName("Add success")
        void addSuccess() throws Exception {

            Patient patient = new Patient();
            patient.setStatus(StatusConstant.DONE);
            when(patientRepository.findByPatientIdAndIsDeleted(1l, Boolean.FALSE)).thenReturn(patient);

            Treatment treatment = new Treatment();
            treatment.setTreatmentId(1L);
            when(treatmentRepository.save(any(Treatment.class))).thenReturn(treatment);
            PatientRecord patientRecord = new PatientRecord();
            patientRecord.setTreatmentId(treatment.getTreatmentId());

            when(patientRecordRepository.save(any(PatientRecord.class))).thenReturn(patientRecord);

            List<TreatmentServiceMap> treatmentServiceMaps = new ArrayList<>();
            when(treatmentServiceMapRepository.saveAll(treatmentServiceMaps)).thenReturn(treatmentServiceMaps);

            List<PatientRecordServiceMap> patientRecordServiceMaps = new ArrayList<>();
            when(patientRecordServiceMapRepository.saveAll(patientRecordServiceMaps)).thenReturn(patientRecordServiceMaps);

            String requestBody = "{\n" +
                    "    \"date\": \"2022-11-06\",\n" +
                    "    \"services\": \"service1\",\n" +
                    "    \"laboName\": null,\n" +
                    "    \"reason\": \"reas1\",\n" +
                    "    \"causal\": \"sdsd\",\n" +
                    "    \"patientRecordId\": 1,\n" +
                    "    \"diagnostic\": \"sdsd\",\n" +
                    "    \"treatment\": \"sdsd\",\n" +
                    "    \"marrowRecord\": \"cvcvcv\",\n" +
                    "    \"note\": \"sdsd\",\n" +
                    "    \"prescription\": \"sdsdsdad\"\n" +
                    "}";
            mockMvc.perform(post("/api/patient_record/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk());
        }
    }

}