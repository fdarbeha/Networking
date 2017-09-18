//*****************************************************************************
//By Alireza Darbehani/Fatemeh Darbehani      
//*****************************************************************************
import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{   
    //*****************************************************************************
    // This is the server class
    //
    // Server accepts a connection and passes it to trans.
    //      
    //*****************************************************************************
    public Server(){}
    //Main *****************************************************************************
    public static void main(String[] args)
    {
        Scanner scanIn = new Scanner(System.in);

        System.out.println("Port: ");
        int port = scanIn.nextInt();
        if(port < 0 || port > 65535)
        {
            System.err.println("Error:  invalid port " + port);
            System.exit(1);
        }
        
        try
        {
            Server server = new Server();
            ServerSocket ss = new ServerSocket(port);
            Socket connection = null;
            ArrayList<Account> bank = new ArrayList<Account>();
            while (true)
            {
                try 
                {
                    System.out.println("Waiting for socket connection on port " + port);
                    connection = ss.accept();
                    System.out.println("Connection accepted from: " + connection.getInetAddress().getHostName());
                    // set up the client's communication path to the server
                    Scanner scan = new Scanner(connection.getInputStream());
                    // set up the server's communication path to the client
                    PrintWriter print = new PrintWriter(connection.getOutputStream());
                    synchronized(bank)
                    {
                        Trans client = new Trans(scan, print, connection, bank);                     
                        System.out.println("transaction begins...");
                        client.start();
                    }
                }
                catch (IOException ioe) 
                {
                    System.err.println("IO Exception in writing: " + ioe);
                }
            }
       }
       catch (IOException ioe) {
           System.err.println("Server socket exception: " + ioe);
       }
    }
     
    
}            
    