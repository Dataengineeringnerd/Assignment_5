Hello! This program consists of both a client and a server that interact with a mysql database. 

The TCP server (PatientDBServer) will start and listen for a transmission from the TCP client (PatientDBClient).

PatientDBClient will start and contact the PatientDBServer on IP address 127.0.0.1 and port number 10001.

PatientDBClient should send username and password to PatientDBServer for the login.

If login is successful, then PatientDBClient should be able to send SQL Query and Patient data to PatientDBServer

Further, PatientDBClient should be able to receive patient data from PatientDBServer

If login is not successful, then PatientDBClient should exit.

On the other-hand, if login is successful, then PatientDBServer should be able to receive SQL Query and Patient data from PatientDBClient

Further, PatientDBServer should be able to execute SQL Query and update Patient data

Furthermore, PatientDBServer should be able to send Patient data to PatientDBClient

Your java program should handle the exceptions.

For more information, check PatientDBClient.java and PatientDBServer.java
