package com.demo.service;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.connection.ModbusConnectionManager;
import com.demo.util.FloatConverter;
import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;

@Service
public class TemperatureService {

    private final SensorCache cache;

    private ModbusConnectionManager manager;
    
    @Value("${modbus.slaves.temperature}")
    private int slaveId;
   

    public TemperatureService(SensorCache cache, ModbusConnectionManager manager) {
		super();
		this.cache = cache;
		this.manager = manager;
	}

	@Scheduled(fixedRate = 2000)
    public void pollTemperature() {
        try {
            float temp = readTemperature();
            cache.setTemperature(temp);
//            System.out.println("Temp Updated: " + temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float readTemperature() throws Exception {

    	TCPMasterConnection connection = manager.getConnection();

    	synchronized (connection) {

    	    ModbusTCPTransaction transaction =
    	            new ModbusTCPTransaction(connection);

    	    ReadMultipleRegistersRequest request =
    	            new ReadMultipleRegistersRequest(0, 2);

    	    request.setUnitID(slaveId);

    	    transaction.setRequest(request);
    	    transaction.execute();

    	    ReadMultipleRegistersResponse response =
    	            (ReadMultipleRegistersResponse) transaction.getResponse();

    	    int high = response.getRegisterValue(0);
    	    int low = response.getRegisterValue(1);

    	    float temperature =
    	            FloatConverter.convertRegistersToFloat(low, high);

    	    return Math.round(temperature * 100f) / 100f;
    	}
        
    }
}