
/**
 * Author: Daniel Chernis ID: 260707258
 Bank Application:
 Purpose: Create customers and bank accounts for those customers to perform transactions:
 Features:
    -initialise balance in Checking AND/OR Saving
    -Cover conditions for all possible types of input:
    - Deposit
    - Withdraw
    - Transfer
    - Anything else (like viewing account info)
    - Basic Text UI
 */
import java.util.ArrayList;
import java.util.Scanner;


public class Bank
{
    private static Scanner sc = new Scanner(System.in);
    private static Customer  currentlyLoggedInCustomer; //save the customer logged into the bank when performing transactions

    //arraylist will allow multiple accounts per key (customer)
    private static ArrayList<Customer> CustomerList = new ArrayList<Customer>();

    //Make sure that if an input is a valid floating point number
    private static boolean isDouble(String s)
    {
        try
        {
            Double.parseDouble(s);
        } catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    //Make sure that if an Id Number is a valid
    private static boolean isInteger(String s)
    {
        try
        {
            Integer.parseInt(s);
        } catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }


    public static void main(String args[])
    {
        try
        {
            mainMenu();

        } catch (Exception e)
        {
            System.out.println("Technical difficulties, please try again later. Sorry for the inconvenience");
        }
    }

    //transfer money between 2 bankaccounts with same Customer arraylist index (same owner)
    private static boolean transfer(double amount, double discountPercentage, BankAccount srcAccount, BankAccount destAccount)
    {
        //Transfer amount from one account to the other
        boolean withdrawSuccess = srcAccount.withdraw(amount,  discountPercentage, srcAccount.getAccountType());

        if (withdrawSuccess)
        {
            destAccount.deposit(amount, discountPercentage, destAccount.getAccountType());
        }

        //Check that you successfully withdrew if the source account was saving  (only 1000$ or more)
        return withdrawSuccess;
    }
    //Create new customer and add him to the bank database
    private static void createCustomer()
    {   //Get attributes to create the customer: String name, int idNumber, double discountPercentage
        //Make sure these values are valid
        int idNumber = CustomerList.size();

        System.out.println("Your unique customer ID is : "+ idNumber);
        System.out.print("Please enter a name for your Customer: ");
        String CustomerName = sc.nextLine();

        String discount;
        do {
            System.out.print("Please enter a discount percentage for your Customer (0.0 to 100.0). \nIt will be taken in absolute value: ");
            discount = sc.nextLine();
        } while (!isDouble(discount) || Double.parseDouble(discount) > 100.0);

        Customer newUser = new Customer(CustomerName, idNumber, Math.abs(Double.parseDouble(discount)));

        CustomerList.add(newUser);
        System.out.println("Customer created!");
        System.out.println();

    }

    //main menu method with ability to make a customer and bankaccount object and enter the account or quit the program
    private static void mainMenu()
    {
        String input;
        do
        {
            System.out.println(
                    "Welcome to your local Bank!\n"
                    + "Enter one of the following commands:\n"
                    + "1: Create a Checking account\n"
                    + "2: Create a Savings account\n"
                    + "3: Access your bank account\n"
                    + "4: Create a new Customer\n"
                    + "5: Quit\n"
            );
            input = sc.nextLine();
        } while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5"));

        if (input.equals("1") || input.equals("2"))
        {
            if (CustomerList.size() == 0)
            {
                System.out.println("Bank database empty. Create your Customer Account before creating a Bank Account: ");
                createCustomer();
                System.out.print("Now log into your newly created Customer Account to create your Bank Account:\n");

            }
            //Get the attributes for Customer to create a bank account

            //Check Customer Id is an integer, and that the CustomerList has that index
            String idNumber;
            boolean customerExists = false;
            do
            {
                System.out.println();
                System.out.print("Please enter your unique Customer ID number: ");
                idNumber = sc.nextLine();

                if (isInteger(idNumber))
                {
                    customerExists = true;
                    try
                    {
                        CustomerList.get(Integer.parseInt(idNumber));
                    } catch (Exception e)
                    {
                        customerExists = false;
                    }
                }
            } while (!customerExists);

            //Make sure the combination of account number and ID number is unique
             int accountNum = CustomerList.get(Integer.parseInt(idNumber)).getAccounts().size();

            System.out.println("Your unique Account Number is : "+ accountNum );
            //Get a valid (float) initial balance
            String balance;
            do {
                System.out.print("Please enter an valid initial Balance (e.g. 1000 or 1000.0). \nIt will be taken in absolute value: ");
                balance = sc.nextLine();
            } while(!isDouble(idNumber) || !isInteger(idNumber));

            //Create a checking account
            if (input.equals("1"))
            {
                BankAccount myAccount = new BankAccount(accountNum,Math.abs(Double.parseDouble(balance)), "Checking");
                CustomerList.get(Integer.parseInt(idNumber)).getAccounts().add(myAccount);
                System.out.println("Checking Account created!\n");
                mainMenu();
                input = "";
            }

            //Create a savings account
            if (input.equals("2"))
            {
                BankAccount myAccount = new BankAccount(accountNum,Math.abs(Double.parseDouble(balance)), "Saving");
                CustomerList.get(Integer.parseInt(idNumber)).getAccounts().add(myAccount);
                System.out.println("Savings Account created!\n");
                mainMenu();
                input = "";
            }
        }

        System.out.println("\n");
        //Access Account
        if (input.equals("3"))
        {
            if (CustomerList.size() == 0)
            {
                System.out.println("There is no Customer in the bank database!\n");
                mainMenu();
                input = "";
            }
            else
            {
                System.out.println("Please log into one of your accounts:");
                changeAccount(logInToAccount(""));
                input = "";
            }
        }

        //Create a new customer
        if (input.equals("4"))
        {
            createCustomer();
            mainMenu();
            input = "";
        }

        //Quit program
        if (input.equals("5"))
        {
            sc.close();
            System.exit(0);
        }
    }


    //account Change settings method
    //Menu after the user logged into his Customer account
    private static BankAccount logInToAccount(String message)
    {

        //Find the account with unique number and account number inputted
        String inputId;
        String inputNumber;
        boolean accountExists = false;
        do
        {
            System.out.print("Please enter your unique integer ID number: ");
            inputId = sc.nextLine();
            System.out.print("Please enter your unique integer account number " + message + ": ");
            inputNumber = sc.nextLine();
            System.out.println();

            if (isInteger(inputId) && isInteger(inputNumber))
            {
                accountExists = true;
                try
                {
                    CustomerList.get(Integer.parseInt(inputId));
                    CustomerList.get(Integer.parseInt(inputId)).getAccounts().get(Integer.parseInt(inputNumber));
                } catch (Exception e)
                {
                    accountExists = false;
                }
            }

        } while (!isInteger(inputId)|| !isInteger(inputNumber) || !accountExists);

        currentlyLoggedInCustomer =  CustomerList.get(Integer.parseInt(inputId)); //record the customer logged in
        return CustomerList.get(Integer.parseInt(inputId)).getAccounts().get(Integer.parseInt(inputNumber));
    }

    //Method to complete transactions and view account settings
    private static void changeAccount(BankAccount myAccount)
    {
        String input;
        Customer customerLoggedIn = currentlyLoggedInCustomer; //Save locally the customer logged in for future reference
        do
        {
            System.out.println();
            System.out.println(
                    "Please choose one of the following options:\n"
                    + "1: View current Balance\n"
                    + "2: Make a deposit\n"
                    + "3: Make a withdrawal\n"
                    + "4: Make a money transfer\n"
                    + "5: View Account Type\n"
                    + "6: View Account Number\n"
                    + "7: Return the the previous menu\n"
            );
            input = sc.nextLine();
        } while (!input.equals("1") && !input.equals("2") && !input.equals("3")
                && !input.equals("4") && !input.equals("5") && !input.equals("6") && !input.equals("7"));

        //check Current Balance
        if (input.equals("1"))
        {
            System.out.println("Your current account balance is " + myAccount.getBalance() + "$");

            changeAccount(myAccount);
        }

        //Make a deposit into current account
        if (input.equals("2"))
        {
            String amountToDeposit;
            do {
                 System.out.print("Please enter a valid amount to deposit (e.g. 100 or 100.0). \nIt will be taken in absolute value: ");
                 amountToDeposit = sc.nextLine();
            } while(!isDouble(amountToDeposit) );

            myAccount.deposit(Math.abs(Double.parseDouble(amountToDeposit)), currentlyLoggedInCustomer.getDiscount(), myAccount.getAccountType());
            System.out.println();
            System.out.println("Your new account balance is " + myAccount.getBalance() + "$");

            changeAccount(myAccount);
        }

        //Make a valid withdraw from current account that is at most the total balance of the account
        if (input.equals("3"))
        {
            String amountToWithdraw;
            do {
                System.out.print("Please enter a valid amount to withdraw (e.g. 100 or 100.0).\nIt will be taken in absolute value: ");
                amountToWithdraw = sc.nextLine();
            } while(!isDouble(amountToWithdraw) );

            myAccount.deposit(Math.abs(Double.parseDouble(amountToWithdraw)), currentlyLoggedInCustomer.getDiscount(), myAccount.getAccountType());
            System.out.println();
            System.out.println("Your new account balance is " + myAccount.getBalance() + "$");

            changeAccount(myAccount);
            input = "";
        }


        //Make a transfer between two of your own accounts
        //Check source and destination account names exist (cannot transfer from/to nonexisting account)
        if (input.equals("4"))
        {
            System.out.println("You are asked to relogin or safety.");

            //Make sure you log into an account that belongs to you (id Number should match with 'customerLoggedIn')
            BankAccount sourceAccount;
            boolean accountIsValid = false;
            int sourceAccountID;
            do
            {
                System.out.println();
                System.out.println("Fill your source account info (the account must belong to you):");
                sourceAccount = logInToAccount("FROM which you transfer");
                sourceAccountID = currentlyLoggedInCustomer.getNumber(); //save the value of source account customer's ID
                if (sourceAccountID == customerLoggedIn.getNumber()) accountIsValid = true;

            } while (!accountIsValid);


            System.out.println("Your current balance is " +sourceAccount.getBalance() +"$.");

            //Input a valid amount to transfer that does not exceed available balance
            String amount;

            do {
                System.out.print("Please enter a valid amount to transfer (e.g. 100 or 100.0): ");
                amount = sc.nextLine();
            } while (!isDouble(amount) || sourceAccount.getBalance() < Double.parseDouble(amount));



            //Make sure the destination account exists and belongs to the customer demanding the transfer
            BankAccount destinationAccount;
            accountIsValid = false;
            int destinationAccountID;
            do
            {
                System.out.println();
                System.out.println("Fill your destination account info (the account must belong to you):");
                destinationAccount = logInToAccount("TO which you transfer");

                destinationAccountID = currentlyLoggedInCustomer.getNumber(); //save the value of destination account customer's ID
                //For source and destination accounts : Customer ID same but account ID different
                if(destinationAccount.getAccountNumber() != (sourceAccount.getAccountNumber()) &&
                            (destinationAccountID == sourceAccountID))
                    accountIsValid = true;

            } while (!accountIsValid);



            boolean transferSuccess =
                    transfer(Double.parseDouble(amount), currentlyLoggedInCustomer.getDiscount() ,sourceAccount, destinationAccount);
            System.out.println();

            if (!transferSuccess) //alert if you did not transfer successfully
            {
                System.out.print("Transfer failed.");
                System.out.println();
                changeAccount(myAccount);
                input = "";
            } else System.out.print("Transfer successful!");

            changeAccount(myAccount);
            input = "";
        }

        //Check account type
        if (input.equals("5"))
        {
            System.out.println("The account you are logged into is a " +myAccount.getAccountType() +" account.");
            changeAccount(myAccount);
            input = "";
        }

        //Check account number
        if (input.equals("6"))
        {
            System.out.println("Your account number is: " +myAccount.getAccountNumber());
            changeAccount(myAccount);
            input = "";
        }

        //return to main menu if that command is called
        if (input.equals("7"))
        {
            mainMenu();
        }

    }
}
