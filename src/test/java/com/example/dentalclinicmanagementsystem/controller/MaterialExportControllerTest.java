package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.entity.Material;
import com.example.dentalclinicmanagementsystem.entity.MaterialExport;
import com.example.dentalclinicmanagementsystem.entity.PatientRecord;
import com.example.dentalclinicmanagementsystem.mapper.MaterialExportMapper;
import com.example.dentalclinicmanagementsystem.repository.MaterialExportRepository;
import com.example.dentalclinicmanagementsystem.repository.MaterialRepository;
import com.example.dentalclinicmanagementsystem.repository.PatientRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MaterialExportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaterialExportRepository materialExportRepository;

    @MockBean
    private MaterialRepository materialRepository;

    @MockBean
    private PatientRecordRepository patientRecordRepository;

    @Nested
    @DisplayName("Add import material")
    class TestAddImportMaterial {

        @Test
        @DisplayName("Add success")
        void addSuccess() throws Exception {

            Material material = new Material();
            material.setAmount(10);

            when(materialRepository.findByMaterialId(anyLong())).thenReturn(material);
            when(patientRecordRepository.findByPatientRecordId(anyLong())).thenReturn(new PatientRecord());

            when(materialRepository.save(any(Material.class))).thenReturn(material);
            when(materialExportRepository.save(any(MaterialExport.class))).thenReturn(new MaterialExport());

            String requestBody = "{\n" +
                    "    \"materialId\": 1,\n" +
                    "    \"amount\": 2,\n" +
                    "    \"totalPrice\": 100000,\n" +
                    "    \"patientRecordId\": 1\n" +
                    "}";
            mockMvc.perform(post("/api/material_export")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk()).andExpect(content().string("{" +
                            "\"materialExportId\":null," +
                            "\"materialId\":1," +
                            "\"amount\":2," +
                            "\"patientRecordId\":1," +
                            "\"totalPrice\":100000," +
                            "\"materialName\":null," +
                            "\"date\":null," +
                            "\"patientName\":null,\"isDelete\":false}"));
        }

        @Test
        @DisplayName("Material not found")
        void materialNotFound() throws Exception {

            when(materialRepository.findByMaterialId(anyLong())).thenReturn(null);

            String requestBody = "{\n" +
                    "    \"materialId\": 1,\n" +
                    "    \"amount\": 2,\n" +
                    "    \"totalPrice\": 100000,\n" +
                    "    \"patientRecordId\": 1\n" +
                    "}";
            mockMvc.perform(post("/api/material_export")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isNotFound()).andExpect(content().string("{" +
                            "\"entityField\":\"materialName\"," +
                            "\"entityName\":\"material\"," +
                            "\"message\":\"material not found\"" +
                            "}"));
        }

        @Test
        @DisplayName("patient record not found")
        void patientRecordNotFound() throws Exception {

            when(materialRepository.findByMaterialId(anyLong())).thenReturn(new Material());
            when(patientRecordRepository.findByPatientRecordId(anyLong())).thenReturn(null);

            String requestBody = "{\n" +
                    "    \"materialId\": 1,\n" +
                    "    \"amount\": 2,\n" +
                    "    \"totalPrice\": 100000,\n" +
                    "    \"patientRecordId\": 1\n" +
                    "}";
            mockMvc.perform(post("/api/material_export")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isNotFound()).andExpect(content().string("{" +
                            "\"entityField\":\"patientRecordId\"," +
                            "\"entityName\":\"patientRecord\"," +
                            "\"message\":\"patient record not found\"" +
                            "}"));
        }

        @Test
        @DisplayName("Field blank")
        void fieldBlank() throws Exception {

            String requestBody = "";
            mockMvc.perform(post("/api/material_export")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest());
        }

    }


}