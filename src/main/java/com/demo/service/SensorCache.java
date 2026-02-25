package com.demo.service;

import org.springframework.stereotype.Component;

@Component
public class SensorCache {
	private volatile Float temperature = 0f;
    private volatile Double pressure = 0.0;

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
}
