import java.util.ArrayList;

public class Customer
{
    private static ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
    private String name;
    private int idNumber;
    private double discountPercentage;

    public Customer(String name, int idNumber, double discountPercentage)
    {
        this.name = name;
        this.idNumber = idNumber;
        this.discountPercentage = discountPercentage;
    }

    public String getName()
    {
        return this.name;
    }

    public int getNumber()
    {
        return this.idNumber;
    }

    public ArrayList<BankAccount> getAccounts()
    {
        return this.accounts;
    }

    public double getDiscount()
    {
        return this.discountPercentage;
    }

    public void setDiscount(double discountPercentage)
    {
        this.discountPercentage = discountPercentage;
    }


}
