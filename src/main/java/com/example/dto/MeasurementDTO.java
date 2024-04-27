package com.example.dto;

import com.example.model.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {
    @NotNull(message = "value can`t be null")
    @Max(value = 100, message = "value can`t be more then 100")
    @Min(value = -100, message = "value can`t be less then 100")
    private Double value;

    @NotNull(message = "raining can`t be empty")
    private Boolean raining;

    @NotNull(message = "sensor can`t be empty")
    private Sensor sensor;

    public MeasurementDTO() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
