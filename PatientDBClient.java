package patientdbserver;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.ArrayDeque;


public class PatientDBClient {
    public static void main(String[] args)  throws IOException {
    	String uname=null, pass=null; // Use these variables to send username and password to login
        String row=null; // You can use this variable to receive data row-by-row from the server
        String ID, NAME, SSN; // Use these variables to send data for a new patient
        String query1 = "select * from mydb.patient"; // This query (first query) will be sent to the server for accessing the data from Patient Table
        Scanner reader = new Scanner(System.in);
        
        try {
		    // Connect to PatientDBServer using IP 127.0.0.1 and port 10001
	        Socket socket = new Socket("127.0.0.1", 10001);
	        System.out.println("Client Socket Created");
	
		    // Create input and output streams to communicate with the server (PatientDBServer)
	        OutputStream out = socket.getOutputStream();
	        InputStream in = socket.getInputStream();
	        System.out.println("Streams created");
	        
		    // Wrap the streams with Readers and Writers for easier communication
	        PrintWriter outWrite = new PrintWriter(out, true);
	        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		    // Read from the terminal for username 
	        System.out.println("Please, Enter your username: ");
	        uname = reader.nextLine();
	        
		    // Write the username into the socket (sending username to the server)
	        outWrite.println(uname);   
	        
		    // Read from the terminal for password
	        System.out.println("Please, Enter your password: ");
	        pass = reader.nextLine();
	        
		    // Write the password into the socket (sending password to the server)
	        outWrite.println(pass);
	        
	        // If login is successful, the client will print: Login Successful!  
                if(inFromServer.readLine().equals("true")) {
                    System.out.println("Login Successful");
                    outWrite.println(query1);

	        }   
                // If login is not successful, then print: Incorrect username or password! Exiting! Please Try Agian Later!
                else {
                       System.out.println("Incorrect username or password! Exiting! Please Try Agian Later!");
                       socket.close();
                       System.exit(0);
               }
	        
                // Also the client sends/writes the query (query1) to the server (PatientDBServer)

                // The client waits to receive the patient data from the server
               String patient_data = inFromServer.readLine();
               String[] elements = patient_data.split(",");
               for (int i = 0; i < elements.length; i++) {
                    System.out.print(elements[i] + "\t");
                    // Check if it's time to start a new row
                    if ((i + 1) % 3 == 0) {
                        System.out.println(); // Start a new line
                    }
                }

                // The client prints the patient data received from the server in the terminal
                //System.out.print(patient_data);

                // The client reads patient ID from the terminal
                System.out.println("Please, Enter your ID: ");
	        ID = reader.nextLine();
		    
		// The client writes patient ID to the socket (for sending to the server)
	        outWrite.println(ID);
	        
		// The client reads patient NAME from the terminal
	        System.out.println("Please, Enter your NAME: ");
	        NAME = reader.nextLine();
	        
                // The client writes patient NAME to the socket (for sending to the server)
                outWrite.println(NAME);
                
                // The client reads patient SSN from the terminal
                System.out.println("Please, Enter your SSN: ");
	        SSN = reader.nextLine();
	        
                // The client writes patient SSN to the socket (for sending to the server)
		outWrite.println(SSN);
	        
                // The client prints the updated patient data into the terminal
	       patient_data = inFromServer.readLine();
               elements = patient_data.split(",");
               for (int i = 0; i < elements.length; i++) {
                    System.out.print(elements[i] + "\t");
                    // Check if it's time to start a new row
                    if ((i + 1) % 3 == 0) {
                        System.out.println(); // Start a new line
                    }
                }
	        
		    // The client closes the socket
        } 
        catch (Exception e) {
	    // Handle the exceptions
            e.printStackTrace();
            System.out.println("error: "+e);
        	
        }
    }
}

