package com.example.sevices;

import com.example.model.Sensor;
import com.example.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    public Optional<Sensor> findOne(String name) {
        return sensorsRepository.findByName(name);
    }


}
