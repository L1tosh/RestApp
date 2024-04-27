package com.example.repositories;

import com.example.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findByName(String name);
}
