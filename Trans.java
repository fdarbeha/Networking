import java.net.*;
import java.io.*;
import java.util.*;
public class Trans extends Thread
{ 
    //*****************************************************************************
    // This is the Transaction class
    //
    // Server issues these messages to the client
    //      //Create//Close//Deposit//Withdraw//Inquire//Quit//...
    //      //The transaction was Successfull or not!
    //
    // Server receives these messages from a client
    //      //Chooses one of the options.
    //      //Continue the transaction
    //      //See if they want to do another transaction.
    //*****************************************************************************
    private Scanner scan;
    private PrintWriter print;
    private final int SLEEP = 100;
    private Socket s;
    private static ArrayList<Account> bank;
    private static int num;
    public Trans (Scanner scan, PrintWriter print, Socket connection, ArrayList<Account> bank)
    {
        this.scan = scan;
        this.print = print;
        s = connection;
        this.bank = bank;
    }
    public void run()
    {
        boolean condition = true;
        String command = "";
        num = bank.size();
        while (condition)
        {
            try{sleep(SLEEP);}
            catch(InterruptedException e){}
            
            if(command.equalsIgnoreCase("no"))
            {
                condition = false;
                continue;
            }
            
            say(print, "//Create//Close//Deposit//Withdraw//Inquire//Quit//...");
            command = hear(scan);
            command = command.toLowerCase();
            
            if(command.equals("create"))
            {
                say(print, "Name:");
                String name = hear(scan);
                
                say(print, "Account number:");
                String account = hear(scan);
                
                say(print, "Balance:");
                String balance = hear(scan);

                int check = 1;
                for(int i = 0; i < num; i++)
                {
                    if(bank.get(i).getNumber()==Integer.parseInt(account))
                        check = -1;
                }
                if(check > 0)
                {
                    Account acc = new Account(name, account, balance);
                    bank.add(num, acc);
                    num++;
                    say(print, "Account was successfully created! the balance is, $"+balance+"...Press Enter...");
                    
                }
                else
                {
                    say(print, "Sorry, this account already exists!"+"...Press Enter...");
                }
            }
            else if (command.equals("close"))
            {
                say(print, "Account number:");
                String number = hear(scan);

                int found = -1;
                for(int i = 0; i < num; i++)
                {
                    if (bank.get(i).getNumber()== Integer.parseInt(number))
                        found = i;
                }
                if(found >= 0)
                {
                    say(print,"Account with number "+number+", and balance $"+bank.get(found).getBalance()+
                    "is successfully deleted!"+"...Press Enter...");
                    bank.remove(found);
                    num--;
                }
                else
                {
                    say(print, "Account with this number was not found!"+"...Press Enter...");
                }
            }
            else if (command.equals("deposit"))
            {
                say(print, "Account number: ");
                String number = hear(scan);

                say(print, "Amount: ");
                String amount = hear(scan);

                int found = -1;
                for(int i = 0; i < num; i++)
                {
                    if (bank.get(i).getNumber()== Integer.parseInt(number))
                        found = i;
                }
                if(found >= 0)
                {
                    say(print, bank.get(found).deposit(Integer.parseInt(amount)) + ", new balance is $"+
                    bank.get(found).getBalance()+ " ."+"...Press Enter...");
                }
                else
                {
                    say(print, "Account with this number was not found!"+"...Press Enter...");
                }
            }
            else if(command.equals("withdraw"))
            {
                say(print, "Account number: ");
                String number = hear(scan);

                say(print, "Amount: ");
                String amount = hear(scan);

                int found = -1;
                for(int i = 0; i < num; i++)
                {
                    if (bank.get(i).getNumber()== Integer.parseInt(number))
                        found = i;
                }
                if(found >= 0)
                {
                    say(print, bank.get(found).withdraw(Integer.parseInt(amount)) + ", new balance is $"+
                    bank.get(found).getBalance()+ " ."+"...Press Enter...");
                }
                else
                {
                    say(print, "Account with this number was not found!"+"...Press Enter...");
                }
            }
            else if (command.equals("inquire"))
            {
                say(print, "Account number: ");
                String number = hear(scan);

                int found = -1;
                for(int i = 0; i < num; i++)
                {
                    if (bank.get(i).getNumber()== Integer.parseInt(number))
                        found = i;
                }
                if(found >= 0)
                {
                    say(print, "Balance is $"+bank.get(found).getBalance()+"...Press Enter...");
                }
                else
                {
                    say(print, "Account with this number was not found!"+"...Press Enter...");
                }
            }
            else if (command.equals("quit"))
            {
                say(print, "You have quit!");
                condition = false;
                continue;
            }
            else 
            {
                say(print, "Error, Invalid Command, try again?...[Yes/No]");
            }
            command = hear(scan);
            System.out.println(num);
            for(int i = 0; i < num; i++)
            {
                System.out.println(bank.get(i));
            }
        }
        System.out.println("one connection is closing...");
        try{s.close();}
        catch(IOException e){}
    }
    //-------------------------------------------------------------------------------
    //Hear/Say
    //-------------------------------------------------------------------------------
    public static String hear(Scanner scan) 
    {
        String s = null;
        System.out.println("[Waiting for the client to speak...]");
        s = scan.nextLine();
        System.out.println("***Client said: " + s);
        return s;
    }
    public static void say(PrintWriter p, String s) 
    {
        p.println(s);
        p.flush();
        System.out.println("[I said: " + s+" ]");
    }
}