package com.me.gerimedica.exercise.application.service;

import com.me.gerimedica.exercise.adapters.out.jpa.CSVRecordRepository;
import com.me.gerimedica.exercise.application.port.in.CSVRecordQueryPort;
import com.me.gerimedica.exercise.application.service.mapper.ConvertCSVRecordEntityToCSVRecord;
import com.me.gerimedica.exercise.domain.CSVRecord;
import com.me.gerimedica.exercise.domain.exceptions.CSVRecordBusinessException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
@AllArgsConstructor
public class CSVRecordQueryService implements CSVRecordQueryPort {

    private final ConvertCSVRecordEntityToCSVRecord covertToModel;
    private final CSVRecordRepository csvRecordRepository;

    @Override
    public List<CSVRecord> findAllCSVRecords() {
        var entities = StreamSupport.stream(
                csvRecordRepository.findAll().spliterator(),
                false
        ).toList();

        return entities.stream()
                .map(covertToModel::convert)
                .sorted(Comparator.comparing(
                        csvRecord -> csvRecord != null ?
                                csvRecord.sortingPriority() :
                                null,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .toList();
    }

    @Override
    public CSVRecord findCSVRecordByCode(String code) {
        var foundEntity = csvRecordRepository.findByCode(code)
                .orElseThrow(() -> new CSVRecordBusinessException("Record is not found..",
                        HttpStatus.NOT_FOUND));

        return covertToModel.convert(foundEntity);
    }
}
