	package com.demo.service;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.stereotype.Service;
	
	import com.demo.connection.ModbusConnectionManager;
	import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
	import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
	import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
	import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
	
	@Service
	public class PressureService {
	
	    @Autowired
	    private ModbusConnectionManager manager;
	
	    @Value("${modbus.slaves.pressure}")
	    private int slaveId;
	
	    public double readPressurePercentage() throws Exception {
	
	        TCPMasterConnection connection = manager.getConnection();
	        ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
	
	        ReadMultipleRegistersRequest request =
	                new ReadMultipleRegistersRequest(2, 1);
	
	        request.setUnitID(slaveId);
	
	        transaction.setRequest(request);
	        transaction.execute();
	
	        ReadMultipleRegistersResponse response =
	                (ReadMultipleRegistersResponse) transaction.getResponse();
	
	        int raw = response.getRegisterValue(0);
	        double pressure = (raw / 10000.0) * 100.0;

	        pressure = Math.round(pressure * 100.0) / 100.0;
	        
	        return pressure ;
	    }
	}
