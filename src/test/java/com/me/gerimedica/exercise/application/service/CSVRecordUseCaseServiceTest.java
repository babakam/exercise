package com.me.gerimedica.exercise.application.service;


import com.me.gerimedica.exercise.adapters.out.jpa.CSVRecordRepository;
import com.me.gerimedica.exercise.domain.CSVRecordCommand;
import com.me.gerimedica.exercise.domain.exceptions.CSVRecordBusinessException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class CSVRecordUseCaseServiceTest {

    @Autowired
    private CSVRecordUseCaseService csvRecordUseCaseService;

    @Autowired
    private CSVRecordRepository csvRecordRepository;


    @Test
    void shouldCreateListOfCSVRecords() {
        var generatedCSVRecords = generateListOfCSVRecords(5);

        csvRecordUseCaseService.saveCSVRecords(generatedCSVRecords);

        Assertions.assertAll(
                () -> assertEquals(csvRecordRepository.count(), generatedCSVRecords.size())
        );
    }
    @Test
    void shouldNotAcceptDuplicateCodeInRequestedList() {
        var csvRecordCommands = generateListOfCSVRecords(1);
        var csvRecordCommand = csvRecordCommands.get(0);
        var duplicateList = List.of(csvRecordCommand, csvRecordCommand);

        var csvRecordBusinessException = assertThrows(CSVRecordBusinessException.class,
                () -> csvRecordUseCaseService.saveCSVRecords(duplicateList));

        assertEquals(HttpStatus.BAD_REQUEST,csvRecordBusinessException.getErrorCode());
        assertEquals("Duplicate code has found in CSV File", csvRecordBusinessException.getErrorMessage());
    }

    @Test
    void shouldNotAcceptNullOrEmptyCodeInRequestedList() {
        var command = CSVRecordCommand.builder()
                .source("ZIB")
                .codeListCode("ZIB001")
                .displayValue("regelmatig")
                .longDescription("long description")
                .fromDate(LocalDate.now().toString())
                .toDate(LocalDate.now().plusMonths(3).toString())
                .sortingPriority(10)
                .build();

        var nullList = List.of(command);

        var csvRecordBusinessException = assertThrows(CSVRecordBusinessException.class,
                () -> csvRecordUseCaseService.saveCSVRecords(nullList));

        assertEquals(HttpStatus.BAD_REQUEST,csvRecordBusinessException.getErrorCode());
        assertEquals("Null or empty code has found in the CSV file", csvRecordBusinessException.getErrorMessage());

    }

    @Test
    void shouldNotAcceptDuplicateCodeInDB() {
        var generatedCSVRecords = generateListOfCSVRecords(3);
        csvRecordUseCaseService.saveCSVRecords(generatedCSVRecords);
        Assertions.assertAll(
                () -> assertEquals(csvRecordRepository.count(), generatedCSVRecords.size())
        );

        var duplicateRequestedList = generatedCSVRecords.stream().limit(2).toList();

        var csvRecordBusinessException = assertThrows(CSVRecordBusinessException.class,
                () -> csvRecordUseCaseService.saveCSVRecords(duplicateRequestedList));

        assertEquals(HttpStatus.BAD_REQUEST,csvRecordBusinessException.getErrorCode());
        assertEquals("Duplicate code has found in DB",csvRecordBusinessException.getErrorMessage());
    }

    @Test
    void shouldRemoveAllCsvRecords() {

        var generatedCSVRecords = generateListOfCSVRecords(10);
        csvRecordUseCaseService.saveCSVRecords(generatedCSVRecords);
        Assertions.assertAll(
                () -> assertEquals(csvRecordRepository.count(), generatedCSVRecords.size())
        );

        csvRecordUseCaseService.deleteAllCSVRecords();

        Assertions.assertAll(
                () -> assertEquals( 0,csvRecordRepository.count())

        );
    }


    private List<CSVRecordCommand> generateListOfCSVRecords(int size) {

        return IntStream.range(0, size).mapToObj(index -> CSVRecordCommand.builder()
                        .source("ZIB")
                        .codeListCode("ZIB001")
                        .code(index + "")
                        .displayValue("regelmatig")
                        .longDescription("long description")
                        .fromDate(LocalDate.now().toString())
                        .toDate(LocalDate.now().plusMonths(3).toString())
                        .sortingPriority(index)
                        .build())
                .toList();

    }
}
