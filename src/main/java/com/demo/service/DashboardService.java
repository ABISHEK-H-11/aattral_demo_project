package com.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.entity.SensorData;
import com.demo.repository.SensorDataRepository;

@Service
public class DashboardService {
  
    private TemperatureService temperatureService;
    private PressureService pressureService;
    private SensorDataRepository repository;
    
    public DashboardService(TemperatureService temperatureService, PressureService pressureService,
			SensorDataRepository repository) {
		super();
		this.temperatureService = temperatureService;
		this.pressureService = pressureService;
		this.repository = repository;
	}


    @Scheduled(fixedRate = 5000)
	public  void saveTheSensorData() throws Exception {

        float temp = temperatureService.readTemperature();
        double pressure = pressureService.readPressurePercentage();
        String status = (temp > 75 || pressure > 90) ? "ALARM" : "NORMAL";
        repository.save(new SensorData(temp, pressure, status, LocalDateTime.now()));

    }
    
    public List<SensorData> getStatus() throws Exception {
        return repository.findAll();
    }
    

}
