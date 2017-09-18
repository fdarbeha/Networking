public class Account
{
    private String name;
    private int number;
    private int balance;
    //===============================Constructor===============================//
    public Account(String userName, String accountNumber, String userBalance)
    {
        name = userName;
        number = Integer.parseInt(accountNumber);
        balance = Integer.parseInt(userBalance);
    }
    
    public synchronized int getBalance()
    {
        return balance;
    }
    
    public int getNumber()
    {
        return number;
    }
    
    public synchronized String deposit (int amount)
    {
        balance += amount;
        return "succesfully deposited!";
    }
    public synchronized String withdraw (int amount)
    {
        if(balance >= amount)
        {
            balance -= amount;
            return "successfully withdrew!";
        }
        return "you can't withdraw this amount of money!" +
                "your limit is $" + balance + " .";
    }
    public String toString()
    {
        return "Account with number "+number+" and balance "+balance;
    }
}