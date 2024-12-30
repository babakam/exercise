package com.me.gerimedica.exercise.infrastructure.utils;

import com.me.gerimedica.exercise.adapters.in.web.exceptions.IllegalCSVException;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class CSVFileValidator {

    public void validate(final MultipartFile file) {
        checkFileIsNotEmpty(file);
        checkContentFileType(file);
    }

    private void checkFileIsNotEmpty(final MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalCSVException("File is empty. Please select a file");
        }
    }

    private void checkContentFileType(final MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("text/csv")) {
            throw new IllegalCSVException("Invalid file type. Only CSV files are supported.");
        }
    }
}
