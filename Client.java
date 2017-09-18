//*****************************************************************************
//By Alireza Darbehani/Fatemeh Darbehani      
//*****************************************************************************
import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client
{
    //*****************************************************************************
    // This is the client code.
    //Client each time hears to the server and responses to it.
    //*****************************************************************************
    public Client(){}
    public static void main(String[] args)
    {
        System.out.println("Host name: ");
        Scanner scanIn = new Scanner(System.in);
        String host = scanIn.nextLine();
        System.out.println("Port: ");
        int port = scanIn.nextInt();
        //------------------------------------------------------------
        if(port < 0 || port > 65535)
        {
            System.err.println("Error:  invalid port " + port);
            System.exit(1);
        }
        //-------------------------------------------------------------
        try 
        {
            System.out.println("Establishing connection to " + host + " on port " + port);
            Socket connection = new Socket(host, port);
            Scanner scan = new Scanner(connection.getInputStream());
            PrintWriter print = new PrintWriter(connection.getOutputStream());
            
            //hear(scan);
            String respond = "";
            // say(print, respond);
            scanIn.nextLine();
            while (!respond.equalsIgnoreCase("quit")&&!respond.equalsIgnoreCase("no")) 
            { 
                hear(scan);
                //-------------------------------
                System.out.println("[Your turn...]");
                //scanIn.nextLine();
                respond = scanIn.nextLine();
                say(print, respond);
                System.out.println("==========================================================================");
            }
            System.out.println("[Connection is closing...]");
            connection.close();
        }
        catch (IOException ioe) {
           System.out.println("IO Exception: " + ioe);
        }
    }
    // get input from the remote server
    public static void hear(Scanner scan) {
        String s = null;
        System.out.println("[Waiting for the server to speak.]");
        s = scan.nextLine();
        System.out.println("***Server said: " + s);
    }

    // send output to the remote server
    public static void say(PrintWriter p, String s) {
        p.println(s);
        p.flush();
        System.out.println("[I said: " + s + "]");
    }
}