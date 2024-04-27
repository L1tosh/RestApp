package com.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Component
public class MeasurementsDAO {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public MeasurementsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Map<String, Long> getRainyDaysCount() {
        Long count = (Long) entityManager
                .createQuery("select count(m.raining) from Measurement m where m.raining = true")
                .getSingleResult();

        return Collections.singletonMap("count", count);
    }


}
