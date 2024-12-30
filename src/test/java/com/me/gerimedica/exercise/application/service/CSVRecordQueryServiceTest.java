package com.me.gerimedica.exercise.application.service;

import com.me.gerimedica.exercise.domain.CSVRecordCommand;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
class CSVRecordQueryServiceTest {

    @Autowired
    private CSVRecordUseCaseService csvRecordUseCaseService;

    @Autowired
    private CSVRecordQueryService csvRecordQueryService;

    @Test
    void findAllCSVRecords() {
        var csvRecordCommands = generateCSVRecordCommands();
        csvRecordUseCaseService.saveCSVRecords(csvRecordCommands);
        var allCSVRecords = csvRecordQueryService.findAllCSVRecords();
        Assertions.assertAll(
                () -> assertEquals(csvRecordCommands.size(), allCSVRecords.size())
        );
    }

    @Test
    void findCSVRecordByCode() {

        var csvRecordCommands = generateCSVRecordCommands();
        csvRecordUseCaseService.saveCSVRecords(csvRecordCommands);

        Assertions.assertAll(
                () -> assertEquals(generateCSVRecordCommands().size(),
                        csvRecordCommands.stream()
                                .filter(csvRecordCommand ->
                                        csvRecordCommand.getCode().equals(csvRecordQueryService
                                                .findCSVRecordByCode(csvRecordCommand.getCode()).code()))
                                .toList().size())
        );
    }

    private List<CSVRecordCommand> generateCSVRecordCommands() {
        var code1 = CSVRecordCommand.builder()
                .source("ZIB")
                .codeListCode("ZIB001")
                .code("code1")
                .displayValue("regelmatig")
                .longDescription("long description")
                .fromDate(LocalDate.now().toString())
                .toDate(LocalDate.now().plusMonths(3).toString())
                .sortingPriority(1)
                .build();

        var code2 = CSVRecordCommand.builder()
                .source("ZIB")
                .codeListCode("ZIB001")
                .code("code2")
                .displayValue("regelmatig")
                .longDescription("long description")
                .fromDate(LocalDate.now().toString())
                .toDate(LocalDate.now().plusMonths(3).toString())
                .sortingPriority(null)
                .build();

        var code3 = CSVRecordCommand.builder()
                .source("ZIB")
                .codeListCode("ZIB001")
                .code("code3")
                .displayValue("regelmatig")
                .longDescription("long description")
                .fromDate(LocalDate.now().toString())
                .toDate(LocalDate.now().plusMonths(3).toString())
                .sortingPriority(3)
                .build();

        return List.of(code1, code2, code3);
    }
}