package com.me.gerimedica.exercise.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSVRecordCommand {

    @CsvBindByName(column = "source")
    private String source;

    @CsvBindByName(column = "codeListCode")
    private String codeListCode;

    @CsvBindByName(column = "code")
    private String code;

    @CsvBindByName(column = "displayValue")
    private String displayValue;

    @CsvBindByName(column = "longDescription")
    private String longDescription;

    @CsvBindByName(column = "fromDate")
    private String fromDate;

    @CsvBindByName(column = "toDate")
    private String toDate;

    @CsvBindByName(column = "sortingPriority")
    private Integer sortingPriority;
}
