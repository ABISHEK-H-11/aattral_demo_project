📡 Industrial Monitoring System – Spring Boot + Modbus TCP
📖 Project Overview

This project is a Spring Boot–based industrial monitoring system that communicates with a Modbus TCP device (PLC) to:

Read Temperature

Read Pressure

Trigger Relay based on alarm conditions

Store sensor data in MySQL

Expose REST API for dashboard access

The system uses multi-threaded scheduling with synchronized Modbus communication to ensure safe and reliable hardware interaction.

🚀 Features

Temperature polling (every 2 seconds)

Pressure polling (every 2 seconds)

Alarm control using relay (coil write)

Data persistence in MySQL (every 5 seconds)

REST API to fetch historical data

Thread-safe Modbus communication

Persistent TCP connection with retry logic

🏗 System Architecture

                Modbus Device (PLC)
                        ↓
           ModbusConnectionManager
                        ↓
     -----------------------------------
     |        |          |             |
Temperature  Pressure  Safety      Dashboard
 Service     Service   Service      Service
                        ↓
                   SensorCache
                        ↓
                    MySQL DB
                        ↓
                  REST Controller


🧵 Multi-Threading Design

Uses ThreadPoolTaskScheduler

Thread Pool Size: 4

Scheduled Tasks

Temperature polling → 2 seconds

Pressure polling → 2 seconds

Alarm monitoring → 2 seconds

Database persistence → 5 seconds

Since Modbus TCP is not thread-safe, all Modbus communication is protected using:

synchronized (connection)

This ensures only one Modbus transaction runs at a time, preventing response mixing and race conditions.

🔌 Modbus Communication

Temperature → Reads 2 registers

Pressure → Reads 1 register

Relay → Write coil request

Temperature values are converted from two 16-bit registers into a 32-bit float using:

Float.intBitsToFloat()
🗄 Database Structure

Table: sensor_data

Column	Type
id	Integer (PK)
temperature	float
pressure	double
status	String
timestamp	LocalDateTime
🌐 REST API
Get All Sensor Data
GET /api/dashboard/status

Example URL:

http://localhost:8080/api/dashboard/status
Example Response
[
  {
    "id": 1,
    "temperature": 70.5,
    "pressure": 65.0,
    "status": "NORMAL",
    "timestamp": "2026-02-25T18:20:00"
  }
]
🛠 Technologies Used

Java 17

Spring Boot

Spring Data JPA

MySQL

j2mod (Modbus TCP Library)

ThreadPoolTaskScheduler

REST API

▶️ How to Run the Project

1️⃣ Clone the Repository
git clone <https://github.com/ABISHEK-H-11/aattral_demo_project.git>

2️⃣ Configure application.properties
modbus.host=YOUR_MODBUS_IP
modbus.port=502
modbus.slaves.temperature=1
modbus.slaves.pressure=2
modbus.slaves.relay=3

spring.datasource.url=jdbc:mysql://localhost:3306/your_schema
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

3️⃣ Start MySQL

Make sure your database is running.

4️⃣ Run the Application

Using VS Code / Terminal:

java -jar target/aattral_demo_project-0.0.1-SNAPSHOT.jar

Or run directly from your IDE.

5️⃣ Access API
http://localhost:8080/api/dashboard/status