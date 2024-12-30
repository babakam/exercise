package com.me.gerimedica.exercise.adapters.in.web.mappers;

import com.me.gerimedica.exercise.adapters.in.web.model.CSVRecordDTO;
import com.me.gerimedica.exercise.domain.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConvertCSVRecordToCSVRecordDTO implements Converter<CSVRecord, CSVRecordDTO> {


    @Override
    public CSVRecordDTO convert(CSVRecord csvRecord) {
        return CSVRecordDTO.builder()
                .id(csvRecord.id())
                .source(csvRecord.source())
                .codeListCode(csvRecord.codeListCode())
                .code(csvRecord.code())
                .displayValue(csvRecord.displayValue())
                .longDescription(csvRecord.longDescription())
                .fromDate(csvRecord.fromDate())
                .toDate(csvRecord.toDate())
                .sortingPriority(csvRecord.sortingPriority())
                .build();
    }
}
