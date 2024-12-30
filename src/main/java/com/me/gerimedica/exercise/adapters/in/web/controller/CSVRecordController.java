package com.me.gerimedica.exercise.adapters.in.web.controller;

import com.me.gerimedica.exercise.adapters.in.web.model.CSVRecordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/csv")
@Slf4j
public class CSVRecordController {


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
      // todo: to get the upload file
        // validation
        // call the service layer
       throw new RuntimeException("Not implemented yet");
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CSVRecordDTO>> findAllCSVRecords() {
      //todo: get all csv records from the DB
        throw new RuntimeException("Not implemented yet");
    }

    @GetMapping("/{code}/find")
    public ResponseEntity<CSVRecordDTO> findByCode(@PathVariable("code") String code) {
        //todo: Find by code
        throw new RuntimeException("Not implemented yet");
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllCSVRecords() {
        //todo: delete all the data
        throw new RuntimeException("Not implemented yet");
    }


}
