package com.example.util.validators;

import com.example.dto.SensorDTO;
import com.example.model.Sensor;
import com.example.sevices.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorsValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public SensorsValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;

        if (sensorsService.findOne(sensor.getName()).isPresent())
            errors.rejectValue("name", "", "sensor with this name exist");

    }
}
