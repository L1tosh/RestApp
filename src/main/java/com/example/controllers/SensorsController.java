package com.example.controllers;

import com.example.dto.SensorDTO;
import com.example.model.Sensor;
import com.example.sevices.SensorsService;
import com.example.util.ResponseError;
import com.example.util.TextErrorGenerator;
import com.example.util.exceptions.SensorNotCreateException;
import com.example.util.validators.SensorsValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorsValidator sensorsValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorsValidator sensorsValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorsValidator = sensorsValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        sensorsValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new SensorNotCreateException(TextErrorGenerator.getErrorMessage(bindingResult));

        sensorsService.save(convertToSensor(sensorDTO));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> add() {


        return new ResponseEntity<>(HttpStatus.OK);
    }


    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseError> handleException(SensorNotCreateException exception) {
        ResponseError response = new ResponseError(
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
