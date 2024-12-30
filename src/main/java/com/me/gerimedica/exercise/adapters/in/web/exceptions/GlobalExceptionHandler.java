package com.me.gerimedica.exercise.adapters.in.web.exceptions;


import com.me.gerimedica.exercise.adapters.in.web.model.ErrorApiDTO;
import com.me.gerimedica.exercise.domain.exceptions.CSVRecordBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CSVRecordBusinessException.class)
    public ResponseEntity<ErrorApiDTO> handleCSVRecordBusinessException(CSVRecordBusinessException ex) {
        return new ResponseEntity<>(new ErrorApiDTO(ex.getErrorMessage(),
                ex.getErrorCode().value()),
                ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiDTO> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new ErrorApiDTO(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalCSVException.class)
    public ResponseEntity<ErrorApiDTO> handleIllegalCSVException(IllegalCSVException ex) {
        return new ResponseEntity<>(new ErrorApiDTO(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

}
