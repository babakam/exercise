package com.me.gerimedica.exercise.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class CSVRecordBusinessException extends RuntimeException {

    private final String errorMessage;
    private final HttpStatus errorCode;
}
