package com.me.gerimedica.exercise.application.service.mapper;

import com.me.gerimedica.exercise.adapters.out.jpa.entities.CSVRecordEntity;
import com.me.gerimedica.exercise.domain.CSVRecordCommand;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class ConvertCSVRecordCommandToCSVRecordEntity implements
        Converter<CSVRecordCommand, CSVRecordEntity> {

    private final DateTimeFormatter[] formatter = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy")
    };

    @Override
    public CSVRecordEntity convert(@NotNull CSVRecordCommand csvRecordCommand) {
        return CSVRecordEntity.builder()
                .source(csvRecordCommand.getSource())
                .codeListCode(csvRecordCommand.getCodeListCode())
                .code(csvRecordCommand.getCode())
                .displayValue(csvRecordCommand.getDisplayValue())
                .longDescription(csvRecordCommand.getLongDescription())
                .fromDate(parseDate(csvRecordCommand.getFromDate()))
                .toDate(parseDate(csvRecordCommand.getToDate()))
                .sortingPriority(csvRecordCommand.getSortingPriority())
                .build();
    }

    private LocalDate parseDate(@NotNull String date) {
        if (StringUtils.isNoneBlank(date)) {
            for (DateTimeFormatter dateTimeFormatter : formatter) {
                try {
                    return LocalDate.parse(date, dateTimeFormatter);
                } catch (Exception e) {
                    log.warn("Tried to parse date {} as a date", date, e);
                }
            }
        }
        return null;
    }
}
