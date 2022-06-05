import java.util.Scanner;

public class ATM {
    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);

        //initializes bank
        Bank theBank = new Bank("Bank of Duvall");

        User aUser = theBank.addUser("Blake", "Baumann", "1234");

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while(true)
        {
            //Stays in menu prompt until user logs in
            curUser = ATM.mainMenuPrompt(theBank, sc);

            //Stays in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }
    public static User mainMenuPrompt(Bank theBank, Scanner sc)
    {
        String userID;
        String pin;
        User authUser;

        do{
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.printf("Enter pin: ");
            pin = sc.nextLine();

            authUser = theBank.userLogin(userID, pin);
            if(authUser == null) //tries to get correct combination
            {
                System.out.println("Incorrect user ID/pin combination. " + "Please try again.");
            }
        }while (authUser == null);

        return authUser;
    }
    public static void printUserMenu(User theUser, Scanner sc)
    {
        theUser.printAccountsSummary();

        int choice;

        do{
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdrawal");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = sc.nextInt();

            if(choice < 1 || choice > 5)
            {
                System.out.println("Invalid choice. Please choose 1-5");
            }
        }while (choice < 1 || choice > 5);

        switch (choice)
        {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawalFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }

        //redisplay menu unless user quits
        if(choice != 5)
        {
            ATM.printUserMenu(theUser, sc);
        }
    }
    public static void showTransHistory(User theUser, Scanner sc)
    {
        int theAcct;

        do{
            System.out.printf("Enter the number (1-%d) of the accounts" + "whose transaction you want to see: ", theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if(theAcct < 0 || theAcct >= theUser.numAccounts())
            {
                System.out.println("You have an invalid account. Try again.");
            }
        }while (theAcct < 0 || theAcct >= theUser.numAccounts());

        theUser.printAcctTransHistory(theAcct);
    }

    public static void transferFunds(User theUser, Scanner sc)
    {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do{
            System.out.printf("Enter the number from (1-%d) of the account\n" + "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts())
            {
                System.out.println("Invalid account, try again.");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        do{
            System.out.printf("Enter the number from (1-%d) of the account\n" + "to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts())
            {
                System.out.println("Invalid account, try again.");
            }
        }while (toAcct < 0 || toAcct >= theUser.numAccounts());

        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount < 0)
            {
                System.out.println("Amount cannot be negative");
            }
            else if(amount > acctBal)
            {
                System.out.println("Amount must not be greater than account balance");
            }
        }while(amount < 0 || amount > acctBal);

        //Do the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));
    }
    public static void withdrawalFunds(User theUser, Scanner sc)
    {
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        do{
            System.out.printf("Enter the number from (1-%d) of the account\n" + "to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts())
            {
                System.out.println("Invalid account, try again.");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount < 0)
            {
                System.out.println("Amount cannot be negative");
            }
            else if(amount > acctBal)
            {
                System.out.println("Amount must not be greater than account balance");
            }
        }while(amount < 0 || amount > acctBal);

        sc.nextLine();

        //get memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do withdrawl
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }
    public static void depositFunds(User theUser, Scanner sc)
    {
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        do{
            System.out.printf("Enter the number from (1-%d) of the account\n" + "to deposit to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts())
            {
                System.out.println("Invalid account, try again.");
            }
        }while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount < 0)
            {
                System.out.println("Amount cannot be negative");
            }

        }while(amount < 0);

        sc.nextLine();

        //get memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do withdrawl
        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}
