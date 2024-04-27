package com.example.util.validators;

import com.example.dto.MeasurementDTO;
import com.example.sevices.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementsValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurement = (MeasurementDTO) target;

        if (sensorsService.findOne(measurement.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "doesn't exist");
    }
}
