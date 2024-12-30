package com.me.gerimedica.exercise.domain;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CSVRecord(
        UUID id,
        String source,
        String codeListCode,
        String code,
        String displayValue,
        String longDescription,
        LocalDate fromDate,
        LocalDate toDate,
        Integer sortingPriority
) {
}
