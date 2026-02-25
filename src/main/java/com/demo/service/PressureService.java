	package com.demo.service;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
	
	import com.demo.connection.ModbusConnectionManager;
	import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
	import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
	import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
	import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
	
	@Service
	public class PressureService {
	
		private final ModbusConnectionManager manager;
	    private final SensorCache cache;
	    
	
	    public PressureService(ModbusConnectionManager manager, SensorCache cache) {
			super();
			this.manager = manager;
			this.cache = cache;
			
		}

		@Value("${modbus.slaves.pressure}")
	    private int slaveId;
	
		@Scheduled(fixedRate = 2000)
	    public void pollPressure() {
	        try {
	            double pressure = readPressurePercentage();
	            cache.setPressure(pressure);
//	            System.out.println("Pressure Updated: " + pressure);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		
	    public double readPressurePercentage() throws Exception {
	
	        TCPMasterConnection connection = manager.getConnection();
	        synchronized (connection) {
	            ModbusTCPTransaction transaction =
	                    new ModbusTCPTransaction(connection);

	            ReadMultipleRegistersRequest request =
	                    new ReadMultipleRegistersRequest(2, 1);

	            request.setUnitID(slaveId);

	            transaction.setRequest(request);
	            transaction.execute();

	            ReadMultipleRegistersResponse response =
	                    (ReadMultipleRegistersResponse) transaction.getResponse();

	            int raw = response.getRegisterValue(0);
	            return Math.round(((raw / 10000.0) * 100.0) * 100.0) / 100.0;
	        }
	    }
	}
