package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.connection.ModbusConnectionManager;
import com.demo.util.FloatConverter;
import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;

@Service
public class TemperatureService {

    @Autowired
    private ModbusConnectionManager manager;

    @Value("${modbus.slaves.temperature}")
    private int slaveId;

    public float readTemperature() throws Exception {

        TCPMasterConnection connection = manager.getConnection();
        ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);

        ReadMultipleRegistersRequest request =
                new ReadMultipleRegistersRequest(0, 2);

        request.setUnitID(slaveId);

        transaction.setRequest(request);
        transaction.execute();

        ReadMultipleRegistersResponse response =
                (ReadMultipleRegistersResponse) transaction.getResponse();

        int high = response.getRegisterValue(0);
        int low = response.getRegisterValue(1);


        float temperature = FloatConverter.convertRegistersToFloat(low, high);
        temperature = Math.round(temperature * 100f) / 100f;
        return temperature;
        
        
    }
}
