package com.me.gerimedica.exercise.application.port.in;

import com.me.gerimedica.exercise.domain.CSVRecordCommand;

import java.util.List;

public interface CSVRecordCommandPort {

    /**
     * Processes and creates CSV record entities from the provided list of CSV Records.
     * This method handles the upload logic, including checking for duplicates or any other validation
     * before creating the CSV record entities in the system.
     *
     * @param csvRecordCommands a list of CSV record objects to be processed and created
     */
    void saveCSVRecords(List<CSVRecordCommand> csvRecordCommands);


    /**
     * Deletes all CSV Record entries.
     * This operation removes CSV Records from the database or memory store, clearing the entire dataset.
     */
    void deleteAllCSVRecords();
}
