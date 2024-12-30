package com.me.gerimedica.exercise.adapters.in.web.controller;


import com.me.gerimedica.exercise.adapters.in.web.exceptions.GlobalExceptionHandler;
import com.me.gerimedica.exercise.adapters.in.web.mappers.ConvertCSVRecordToCSVRecordDTO;
import com.me.gerimedica.exercise.application.port.in.CSVRecordCommandPort;
import com.me.gerimedica.exercise.application.port.in.CSVRecordQueryPort;
import com.me.gerimedica.exercise.domain.CSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {CSVRecordController.class, GlobalExceptionHandler.class
        ,ConvertCSVRecordToCSVRecordDTO.class})
@AutoConfigureMockMvc
@EnableWebMvc
class CSVRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CSVRecordCommandPort csvRecordCommandPort;

    @MockitoBean
    private CSVRecordQueryPort csvRecordQueryPort;

    @Autowired
    private ConvertCSVRecordToCSVRecordDTO convertToDTO;

    @Test
    void uploadCSVRecord() throws Exception {
        var resource = new ClassPathResource("files/test-1.csv");
        var file = new MockMultipartFile("file",
                "test-1.csv",
                "text/csv",
                Files.readAllBytes(resource.getFile().toPath()));

        mockMvc.perform(multipart("/csv/upload").file(file))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findAllCSVRecord() throws Exception {
        when(csvRecordQueryPort.findAllCSVRecords())
                .thenReturn(List.of(CSVRecord.builder()
                                .id(UUID.randomUUID())
                                .sortingPriority(1)
                                .code("code")
                        .build()));
        mockMvc.perform(get("/csv/findAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("code"))
                .andExpect(jsonPath("$[0].sortingPriority").value("1"));
    }

    @Test
    void findCSVRecordByCode() throws Exception {

        when(csvRecordQueryPort.findCSVRecordByCode(anyString()))
                .thenReturn(CSVRecord.builder()
                        .id(UUID.randomUUID())
                        .sortingPriority(1)
                        .code("code")
                        .build());

        mockMvc.perform(get("/csv/anyCode/find"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("code"))
                .andExpect(jsonPath("$.sortingPriority").value("1"));
    }

    @Test
    void deleteAllCSVRecord() throws Exception {
        mockMvc.perform(delete("/csv/deleteAll"))
                .andDo(print())
                .andExpect(status().isOk());

    }

}