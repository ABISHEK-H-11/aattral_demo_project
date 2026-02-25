	package com.demo.service;
	
	import java.time.LocalDateTime;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.scheduling.annotation.Scheduled;
	import org.springframework.stereotype.Service;
	
	import com.demo.connection.ModbusConnectionManager;
	import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
	import com.ghgande.j2mod.modbus.msg.WriteCoilRequest;
	import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
	
	@Service
	public class SafetyControllerService {
	
	    private boolean alarmState = false;
   
	    private ModbusConnectionManager manager;
	
	    private final SensorCache cache;
	    
	    
	    public SafetyControllerService( ModbusConnectionManager manager, SensorCache cache) {
			super();
			
			this.manager = manager;
			this.cache = cache;
			
		}

		@Value("${modbus.slaves.relay}")
	    private int relaySlave;
	
	    @Scheduled(fixedRate = 2000)
	    public void monitor() throws Exception {
//	    	System.out.println("Monitor method executing...");
	        float temp = cache.getTemperature();
	        System.out.println("Temperature = " + temp+"°C");
	        double pressure = cache.getPressure();
	        System.out.println("pressure = " + pressure + "%");
	        if (temp > 75.0 || pressure > 90.0) {
	            if (!alarmState) {
	                triggerRelay(true);
	                log("ALARM TRIGGERED: Temp=" + temp +"°C" + " Pressure=" + pressure + "%");
	                alarmState = true;
	            }
	        } else {
	            if (alarmState) {
	                triggerRelay(false);
	                log("ALARM RESET");
	                alarmState = false;
	            }
	        }
	    }

	    private void triggerRelay(boolean state) throws Exception {
	    	 TCPMasterConnection connection = manager.getConnection();

	    	    synchronized (connection) {

	    	        ModbusTCPTransaction transaction =
	    	                new ModbusTCPTransaction(connection);

	    	        WriteCoilRequest request = new WriteCoilRequest(0, state);
	    	        request.setUnitID(relaySlave);

	    	        transaction.setRequest(request);
	    	        transaction.execute();
	    	    }
	    }
	
	    private void log(String message) {
	        System.out.println(LocalDateTime.now() + " | " + message);
	    }
	}