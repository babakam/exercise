package com.me.gerimedica.exercise.adapters.in.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorApiDTO {

    private String errorMessage;
    private int errorCode;
}
