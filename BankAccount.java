
public class BankAccount
{

    private double balance;
    private int accountNumber;
    private String accountType;

    public BankAccount(int accountNumber, double checkingBalance, String accountType)
    {
        this.accountNumber = accountNumber;
        this.balance = checkingBalance;
        this.accountType = accountType;
    }

    public double getBalance()
    {
        return this.balance;
    }

    public String getAccountType()
    {
        return this.accountType;
    }

    public int getAccountNumber()
    {
        return this.accountNumber;
    }

    public void setBalance(double newbalance)
    {
        this.balance = newbalance;
    }

    public void deposit(double amount, double discountPercentage, String accountType) //setter for bank account
    {
        if (accountType.equals("Saving"))
        {
            //get an extra 1$ for deposit in savings account or more based on discount %
            double balance = this.balance;
            setBalance(balance += amount + (1.0 + discountPercentage / 100.0));
        }
        if (accountType.equals("Checking"))
        {
            double balance = this.balance;
            setBalance(balance += amount);
        }
    }

    public boolean withdraw(double amount, double discountPercentage, String accountType ) //2nd setter for bank account
    {//Charge an extra dollar charge for withdrawal or less based on discount %

        if (accountType.equals("Checking"))
        {

            setBalance(this.balance -= amount + 1 - (discountPercentage / 100.0));
            return true;
        }
        if (accountType.equals("Saving"))
        {
            if (amount >= 1000)
            {
                setBalance(this.balance -= amount);
                return true;
            }
            return false;
        }
        return false;
    }
}
