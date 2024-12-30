package com.me.gerimedica.exercise.application.port.in;

import com.me.gerimedica.exercise.domain.CSVRecord;

import java.util.List;

public interface CSVRecordQueryPort {


    /**
     * Retrieves a list of all CSV Records.
     *
     * @return a list of {@code CSVRecord} objects containing all existing CSV Records.
     */
    List<CSVRecord> findAllCSVRecords();

    /**
     * Retrieves the CSV Record associated with the specified code.
     *
     * @param code the unique identifier for the CSV Record to retrieve.
     * @return the {@code CSVRecord} associated with the given code, or {@code null} if not found.
     */
    CSVRecord findCSVRecordByCode(String code);

}
