package com.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private float temperature;
    private double pressure;
    private String status;
    private LocalDateTime timestamp;
    
    public SensorData() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public SensorData(float temperature, double pressure, String status, LocalDateTime timestamp) {
		super();
		this.temperature = temperature;
		this.pressure = pressure;
		this.status = status;
		this.timestamp = timestamp;
	}

	public SensorData(Integer id, float temperature, double pressure, String status, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.temperature = temperature;
		this.pressure = pressure;
		this.status = status;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "SensorData [id=" + id + ", temperature=" + temperature + ", pressure=" + pressure + ", status=" + status
				+ ", timestamp=" + timestamp + "]";
	}
    
}