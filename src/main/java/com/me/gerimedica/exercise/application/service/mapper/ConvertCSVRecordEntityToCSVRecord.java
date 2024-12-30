package com.me.gerimedica.exercise.application.service.mapper;

import com.me.gerimedica.exercise.adapters.out.jpa.entities.CSVRecordEntity;
import com.me.gerimedica.exercise.domain.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ConvertCSVRecordEntityToCSVRecord implements Converter<CSVRecordEntity, CSVRecord> {

    @Override
    public CSVRecord convert(CSVRecordEntity entity) {

        return CSVRecord.builder()
                .id(entity.getId())
                .source(entity.getSource())
                .codeListCode(entity.getCodeListCode())
                .code(entity.getCode())
                .displayValue(entity.getDisplayValue())
                .longDescription(entity.getLongDescription())
                .fromDate(entity.getFromDate())
                .toDate(entity.getToDate())
                .sortingPriority(entity.getSortingPriority())
                .build();
    }
}
