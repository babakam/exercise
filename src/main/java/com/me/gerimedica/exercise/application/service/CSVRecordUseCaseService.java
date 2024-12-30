package com.me.gerimedica.exercise.application.service;

import com.me.gerimedica.exercise.adapters.out.jpa.CSVRecordRepository;
import com.me.gerimedica.exercise.adapters.out.jpa.entities.CSVRecordEntity;
import com.me.gerimedica.exercise.application.port.in.CSVRecordCommandPort;
import com.me.gerimedica.exercise.application.service.mapper.ConvertCSVRecordCommandToCSVRecordEntity;
import com.me.gerimedica.exercise.domain.CSVRecordCommand;
import com.me.gerimedica.exercise.domain.exceptions.CSVRecordBusinessException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CSVRecordUseCaseService implements CSVRecordCommandPort {

    private final CSVRecordRepository csvRecordRepository;
    private final ConvertCSVRecordCommandToCSVRecordEntity convertToEntity;


    @Override
    public void saveCSVRecords(List<CSVRecordCommand> csvRecordCommands) {

        if (findNullOrEmptyCodesInCSVFile(csvRecordCommands)) {
            log.error("Null or empty code has found in the CSV file");
            throw new CSVRecordBusinessException("Null or empty code has found in the CSV file", HttpStatus.BAD_REQUEST);
        }

        var duplicateCodes = findDuplicateCodesInCSVFile(csvRecordCommands);

        if (!duplicateCodes.isEmpty()) {
            log.error("Duplicate codes found in csv commands: {}", duplicateCodes);
            throw new CSVRecordBusinessException("Duplicate code has found in CSV File", HttpStatus.BAD_REQUEST);
        }

        var duplicateCodesFromDb = csvRecordRepository.
                findAllByCodeIn(csvRecordCommands.stream()
                        .map(CSVRecordCommand::getCode).toList());

        if (!duplicateCodesFromDb.isEmpty()) {
            log.error("Duplicate codes found in DB: {}", duplicateCodesFromDb.stream().map(CSVRecordEntity::getCode).toList());
            throw new CSVRecordBusinessException("Duplicate code has found in DB", HttpStatus.BAD_REQUEST);
        }

        log.info("Saving CSV records: {}", csvRecordCommands);

        var entities = csvRecordCommands.stream().map(convertToEntity::convert).toList();

        log.info("Saving CSV records entities: {}", entities);
        csvRecordRepository.saveAll(entities);
    }

    @Override
    public void deleteAllCSVRecords() {
        csvRecordRepository.deleteAll();
    }


    private List<String> findDuplicateCodesInCSVFile(List<CSVRecordCommand> csvRecordCommands) {
        return csvRecordCommands.stream()
                .collect(Collectors.groupingBy(CSVRecordCommand::getCode, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
    }

    private boolean findNullOrEmptyCodesInCSVFile(List<CSVRecordCommand> csvRecordCommands) {
        return csvRecordCommands.stream()
                .anyMatch(csvRecordCommand -> StringUtils.isBlank(csvRecordCommand.getCode()));
    }

}
