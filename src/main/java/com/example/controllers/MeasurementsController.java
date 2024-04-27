package com.example.controllers;

import com.example.dao.MeasurementsDAO;
import com.example.dto.MeasurementDTO;
import com.example.model.Measurement;
import com.example.sevices.MeasurementsService;
import com.example.util.MeasurementsResponse;
import com.example.util.ResponseError;
import com.example.util.exceptions.MeasurementNotCreateException;
import com.example.util.exceptions.MeasurementNotFoundException;
import com.example.util.validators.MeasurementsValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.util.ErrorsUtil.*;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final MeasurementsValidator measurementsValidator;
    private final ModelMapper modelMapper;
    private final MeasurementsDAO measurementsDAO;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementsValidator measurementsValidator,
                                  ModelMapper modelMapper, MeasurementsDAO measurementsDAO) {
        this.measurementsService = measurementsService;
        this.measurementsValidator = measurementsValidator;
        this.modelMapper = modelMapper;
        this.measurementsDAO = measurementsDAO;
    }

    @GetMapping
    public MeasurementsResponse all() {
        return new MeasurementsResponse(measurementsService.getALl().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Map<String, Long>> getRainyDaysCount() {
        return new ResponseEntity<>(measurementsDAO.getRainyDaysCount(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        measurementsValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors())
            throw new MeasurementNotCreateException(getErrorMessage(bindingResult));

        measurementsService.save(convertToMeasurement(measurementDTO));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }


    // All Handlers
    @ExceptionHandler
    private ResponseEntity<ResponseError> handleException(MeasurementNotCreateException e) {
        ResponseError response = new ResponseError(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseError> handleException(MeasurementNotFoundException e) {
        ResponseError response = new ResponseError(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
