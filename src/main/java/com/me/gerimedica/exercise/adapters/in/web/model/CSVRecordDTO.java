package com.me.gerimedica.exercise.adapters.in.web.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CSVRecordDTO {
    private UUID id;
    private String source;
    private String codeListCode;
    private String code;
    private String displayValue;
    private String longDescription;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer sortingPriority;
}
