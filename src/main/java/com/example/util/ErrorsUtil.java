package com.example.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorsUtil {
    static public String getErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }

        return errorMsg.toString();
    }

}
