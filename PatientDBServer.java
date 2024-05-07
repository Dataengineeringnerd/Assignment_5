package patientdbserver;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

//import java.awt.datatransfer.SystemFlavorMap;
import java.io.*;


public class PatientDBServer {
	//static final String DB_URL = "jdbc:mysql://localhost/patientdb";
	//static final String USER = "root";
	//static final String PASS = "Blogger#1";
	
    public static void main(String[] args) throws IOException {
    	try {
	    	//Intializing variables
	    	String uname, pass, result="", result1="", query1=""; //can be used these variables for your implementation 	
	        Boolean login=false; //can be used to implement login successful or failure
	        
	        //Run PatientDBServer forever using IP 127.0.0.1 and port 10001
	        ServerSocket serverSocket = new ServerSocket(10001);
	        System.out.println("Connection Socket Created");
	        System.out.println("Waiting for a Connection");
	        
	        Socket connectionSocket = serverSocket.accept();	
	        System.out.println("Socket Connection Accepted");
	        
		// Create input and output streams to communicate with the client
	        OutputStream out = connectionSocket.getOutputStream();
	        InputStream in = connectionSocket.getInputStream();
	        
		// Wrap the streams with Readers and Writers for easier communication
	        PrintWriter outWrite = new PrintWriter(out, true);
	        BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
	
	        
		// Read the socket for username 
	        uname=  inReader.readLine();
	        
		// Read the socket for password
	        pass = inReader.readLine();
	        		
		// Print the username in server terminal
	        System.out.println(uname);
	        
		// Print the password in server terminal
	        System.out.println(pass);
	        
		 // Connect with mysql server for accessing login table from mydb database
		Conn c1 = new Conn();
                System.out.println("Connected to Database");
                
                // Verify the user using SQL where clause, received username, and password 
       
                //System.out.println("SELECT  * FROM mydb.login WHERE username = \"" + uname + "\" AND password = \"" + pass + "\"");
		ResultSet rs = c1.s.executeQuery("SELECT  * FROM mydb.login WHERE username = \"" + uname + "\" AND password = \"" + pass + "\"" );
		if(rs.next()){
                    System.out.println("Login Successful!\n");
                    login=true;
                    // Notify the user (PatientDBClient) Login is successful or not
                    outWrite.println(login);
                    query1 =  inReader.readLine();
                    //System.out.println(query1);
                }
		
                // If login is sucessful, then the server (PatientDBServer) will wait to receive the first SQL query from the client (PatientDBClient)
                else {
                    System.out.println("Sorry, login was not successful.");
                    login=false;
                    outWrite.println(login);
                    connectionSocket.close();
                    System.exit(0);
                }
			
	        
                // Exceute the SQL query to print the data from Patient Table in server Terminal
                // Extract data from result set
                rs = c1.s.executeQuery(query1);
                String output_str ="ID,NAME,SNN,";
                System.out.println("############### Patient Table################");
                while (rs.next()) {    
                // Retrieve by column name
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String SSN = rs.getString("SSN");
                System.out.println(id + "\t\t" + name + "\t\t"+ SSN + "\n");  	
                output_str = output_str.concat(id + "," + name + ","+ SSN + ",");   			
                }
                System.out.println("#################################################");
  	 		
		// Write the data from Patient Table into the socket for the client (PatientDBClient)
	        outWrite.println(output_str);
			
		// The server (PatientDBServer) should wait to receive new patient data (ID, NAME, SSN) from the client (PatientDBClient)
	        String ID = inReader.readLine();
                String NAME = inReader.readLine();
                String input_SSN =  inReader.readLine();

                // Execute the SQL query to insert the data (ID, NAME, SSN) received from the client into Patient Table
                //System.out.println ( "INSERT INTO mydb.Patient " + "VALUES (" + ID + ", \'" + NAME + "\', \'" + input_SSN + "\')");
                c1.s.executeUpdate ( "INSERT INTO mydb.Patient " + "VALUES (" + ID + ", \'" + NAME + "\', \'" + input_SSN + "\')"); 

                // Print the updated Patient Table
                rs = c1.s.executeQuery(query1);
                output_str ="ID,NAME,SNN,";
                System.out.println("############### Patient Table################");
                while (rs.next()) {    
                // Retrieve by column name
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String SSN = rs.getString("SSN");
                System.out.println(id + "\t\t" + name + "\t\t"+ SSN + "\n");  	
                output_str = output_str.concat(id + "," + name + ","+ SSN + ",");   			
                }
                System.out.println("#################################################");

                // Send the data to the client (PatientDBClient) from the updated Patient Table
                outWrite.println(output_str);
			
		    // Handle the exception
    	}  catch(Exception e) {
            e.printStackTrace();
            System.out.println("error: "+e);
    	}
	    
    }
}


