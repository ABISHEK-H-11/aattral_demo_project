package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.SensorData;

public interface SensorDataRepository extends JpaRepository<SensorData, Integer>{
	
}
