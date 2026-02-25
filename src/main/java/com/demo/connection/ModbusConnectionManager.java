package com.demo.connection;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ghgande.j2mod.modbus.net.TCPMasterConnection;

import jakarta.annotation.PreDestroy;

@Component
public class ModbusConnectionManager {

    private TCPMasterConnection connection;

    @Value("${modbus.host}")
    private String host;

    @Value("${modbus.port}")
    private int port;

    public synchronized TCPMasterConnection getConnection() throws Exception {
        if (connection == null || !connection.isConnected()) {
            connectWithRetry();
        }
        return connection;
    }

    private void connectWithRetry() throws Exception {
        int retry = 1;
        int delay = 1000;

        while (true) {
            try {
                InetAddress address = InetAddress.getByName(host);
                connection = new TCPMasterConnection(address);
                connection.setPort(port);
                connection.connect();
                System.out.println("Connected to Modbus Server");
                break;
            } catch (Exception e) {
                System.out.println("Retrying in " + delay + " ms");
                Thread.sleep(delay);
                delay *= 2; // exponential backoff
                retry++;
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        if (connection != null && connection.isConnected()) {
            connection.close();
        }
        System.out.println("Connection closed gracefully");
    }
}
