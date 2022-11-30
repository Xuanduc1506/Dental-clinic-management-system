package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.StatusConstant;
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

    public static final String TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ4MSIsImp0aSI6IjYiLCJpYXQiOjE2Njk3Mjg0MDMsImV4cCI6MTY3MDMzMzIwM30.bS_MDaIDSOcXRWB97NsWZbHJciR727Tatx8iytO9WP5bRLD9K91EtWxhoecPc55ryooPL_KDBKb0dOYEt9KRQg";

    @Nested
    @DisplayName("Add patient record")
    class TestAddPatientRecord {

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
                    "    \"reason\": \"reas1\",\n" +
                    "    \"services\": \"service1\",\n" +
                    "    \"laboName\": null,\n" +
                    "    \"patientRecordId\": 1,\n" +
                    "    \"treatment\": \"sdsd\",\n" +
                    "    \"prescription\": \"sdsdsdad\",\n" +
                    "    \"diagnostic\": \"sdsd\",\n" +
                    "    \"causal\": \"sdsd\",\n" +
                    "    \"note\": \"sdsd\",\n" +
                    "    \"marrowRecord\": \"cvcvcv\",\n" +
                    "    \"serviceDTOS\": [\n" +
                    "        {\n" +
                    "            \"serviceId\": 3,\n" +
                    "            \"serviceName\": \"service3\",\n" +
                    "            \"unit\": null,\n" +
                    "            \"marketPrice\": null,\n" +
                    "            \"price\": null,\n" +
                    "            \"categoryServiceId\": null,\n" +
                    "            \"status\": 1,\n" +
                    "            \"isNew\": null,\n" +
                    "            \"discount\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"serviceId\": 4,\n" +
                    "            \"serviceName\": \"service4\",\n" +
                    "            \"unit\": null,\n" +
                    "            \"marketPrice\": null,\n" +
                    "            \"price\": null,\n" +
                    "            \"categoryServiceId\": null,\n" +
                    "            \"status\": 1,\n" +
                    "            \"isNew\": null,\n" +
                    "            \"discount\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"serviceId\": 5,\n" +
                    "            \"serviceName\": \"service5\",\n" +
                    "            \"unit\": null,\n" +
                    "            \"marketPrice\": null,\n" +
                    "            \"price\": 1000,\n" +
                    "            \"categoryServiceId\": null,\n" +
                    "            \"status\": 1,\n" +
                    "            \"isNew\": 1,\n" +
                    "            \"discount\": 100\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            mockMvc.perform(post("/api/patient_record/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .header("Authorization", TOKEN))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Patient not found")
        void patientNotFound() throws Exception {
            when(patientRepository.findByPatientIdAndIsDeleted(1l, Boolean.FALSE)).thenReturn(null);

            String requestBody = "{\n" +
                    "    \"date\": \"2022-11-06\",\n" +
                    "    \"reason\": \"reas1\",\n" +
                    "    \"services\": \"service1\",\n" +
                    "    \"laboName\": null,\n" +
                    "    \"patientRecordId\": 1,\n" +
                    "    \"treatment\": \"sdsd\",\n" +
                    "    \"prescription\": \"sdsdsdad\",\n" +
                    "    \"diagnostic\": \"sdsd\",\n" +
                    "    \"causal\": \"sdsd\",\n" +
                    "    \"note\": \"sdsd\",\n" +
                    "    \"marrowRecord\": \"cvcvcv\",\n" +
                    "    \"serviceDTOS\": [\n" +
                    "        {\n" +
                    "            \"serviceId\": 3,\n" +
                    "            \"serviceName\": \"service3\",\n" +
                    "            \"unit\": null,\n" +
                    "            \"marketPrice\": null,\n" +
                    "            \"price\": null,\n" +
                    "            \"categoryServiceId\": null,\n" +
                    "            \"status\": 1,\n" +
                    "            \"isNew\": null,\n" +
                    "            \"discount\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"serviceId\": 4,\n" +
                    "            \"serviceName\": \"service4\",\n" +
                    "            \"unit\": null,\n" +
                    "            \"marketPrice\": null,\n" +
                    "            \"price\": null,\n" +
                    "            \"categoryServiceId\": null,\n" +
                    "            \"status\": 1,\n" +
                    "            \"isNew\": null,\n" +
                    "            \"discount\": null\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"serviceId\": 5,\n" +
                    "            \"serviceName\": \"service5\",\n" +
                    "            \"unit\": null,\n" +
                    "            \"marketPrice\": null,\n" +
                    "            \"price\": 1000,\n" +
                    "            \"categoryServiceId\": null,\n" +
                    "            \"status\": 1,\n" +
                    "            \"isNew\": 1,\n" +
                    "            \"discount\": 100\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            mockMvc.perform(post("/api/patient_record/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .header("Authorization", TOKEN))
                    .andExpect(status().isNotFound()).andExpect(content()
                            .string("{\"entityField\":\"patientRecordId\",\"entityName\":\"patientRecord\"" +
                                    ",\"message\":\"patient record not found\"}"));
        }

        @Test
        @DisplayName("Field null")
        void fieldNull() throws Exception {

            String requestBody = "{\n" +
                    "    \"date\": \"2022-11-06\",\n" +
                    "    \"reason\": \"\",\n" +
                    "    \"services\": \"service1\",\n" +
                    "    \"laboName\": null,\n" +
                    "    \"patientRecordId\": 1,\n" +
                    "    \"treatment\": \"\",\n" +
                    "    \"prescription\": \"sdsdsdad\",\n" +
                    "    \"diagnostic\": \"\",\n" +
                    "    \"causal\": \"\",\n" +
                    "    \"note\": \"sdsd\",\n" +
                    "    \"marrowRecord\": \"cvcvcv \n" +
                    "}";
            mockMvc.perform(post("/api/patient_record/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
                            .header("Authorization", TOKEN))
                    .andExpect(status().isBadRequest());

        }
    }

}