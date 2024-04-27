package com.example.sevices;

import com.example.model.Measurement;
import com.example.model.Sensor;
import com.example.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    public List<Measurement> getALl() {
        return measurementsRepository.findAll();
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setTime(new Date());
        measurement.setSensor(sensorsService.findOne(measurement.getSensor().getName()).get());
    }

}
