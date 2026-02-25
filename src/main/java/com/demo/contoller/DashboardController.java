package com.demo.contoller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.SensorData;
import com.demo.repository.SensorDataRepository;
import com.demo.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    
    private SensorDataRepository sensorDataRepository;
    private DashboardService dashboardService;
  
	
	public DashboardController(SensorDataRepository sensorDataRepository, DashboardService dashboardService) {
		super();
		this.sensorDataRepository = sensorDataRepository;
		this.dashboardService = dashboardService;
	}
	@GetMapping("/status")
    public ResponseEntity<?> getDashboard() {
         try {
        	 List<SensorData> data = dashboardService.getStatus();
			return ResponseEntity.ok(data);
		 } catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("Message", "sumthing went wrong"));
		 }
         
    }
}
