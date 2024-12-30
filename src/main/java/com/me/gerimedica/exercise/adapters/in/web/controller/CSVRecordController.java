package com.me.gerimedica.exercise.adapters.in.web.controller;

import com.me.gerimedica.exercise.adapters.in.web.exceptions.IllegalCSVException;
import com.me.gerimedica.exercise.adapters.in.web.mappers.ConvertCSVRecordToCSVRecordDTO;
import com.me.gerimedica.exercise.adapters.in.web.model.CSVRecordDTO;
import com.me.gerimedica.exercise.application.port.in.CSVRecordCommandPort;
import com.me.gerimedica.exercise.application.port.in.CSVRecordQueryPort;
import com.me.gerimedica.exercise.domain.CSVRecordCommand;
import com.me.gerimedica.exercise.infrastructure.utils.CSVFileValidator;
import com.opencsv.bean.CsvToBeanBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/csv")
@Slf4j
public class CSVRecordController {

    private final CSVRecordCommandPort csvRecordCommandPort;
    private final CSVRecordQueryPort csvRecordQueryPort;
    private final ConvertCSVRecordToCSVRecordDTO convertToDTO;


    @Operation(
            summary = "Upload a file",
            description = "Endpoint to upload a CSV file",
            parameters = {
                    @Parameter(
                            name = "file",
                            description = "File to be uploaded",
                            required = true,
                            content = @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(type = "file", format = "binary")
                            )
                    )
            })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
        CSVFileValidator.validate(file);
        csvRecordCommandPort.saveCSVRecords(convertFileToCommandModel(file));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Find all CSV records", description = "Fetches all CSV records from the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched all CSV records",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CSVRecordDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/findAll")
    public ResponseEntity<List<CSVRecordDTO>> findAllCSVRecords() {
        var allCSVRecords = csvRecordQueryPort.findAllCSVRecords();
        var dtos = allCSVRecords.stream()
                .map(convertToDTO::convert)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Find CSV record by code",
            description = "Fetches a CSV record by the given code",
            parameters = {
                    @Parameter(name = "code", description = "The code of the CSV record to fetch", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched the CSV record",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CSVRecordDTO.class))),
                    @ApiResponse(responseCode = "404", description = "CSV record not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/{code}/find")
    public ResponseEntity<CSVRecordDTO> findByCode(@PathVariable("code") String code) {
        var found = csvRecordQueryPort.findCSVRecordByCode(code);
        return ResponseEntity.ok(convertToDTO.convert(found));
    }


    @Operation(
            summary = "Delete all CSV records",
            description = "Deletes all CSV records from the database",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted all CSV records"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllCSVRecords() {
        csvRecordCommandPort.deleteAllCSVRecords();
        return ResponseEntity.ok().build();
    }

    private List<CSVRecordCommand> convertFileToCommandModel(final MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            var csvToBean = new CsvToBeanBuilder<CSVRecordCommand>(reader)
                    .withType(CSVRecordCommand.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new IllegalCSVException("Something went wrong while parsing CSV file");
        }
    }

}
