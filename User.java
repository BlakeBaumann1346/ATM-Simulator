import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    //Different data storage

    private String firstName;      //Stores First Name
    private String lastName;        //Stores Last Name
    private String uuid;            //Stores uuid
    private byte pinHash[];         //Stores Pin Value

    private ArrayList<Account> accounts;


    /* Creates an account for a user with all the various information aspects
    *
    *
    * */

    public User(String firstName, String lastName, String pin, Bank theBank)
    {
        this.firstName = firstName; //sets names
        this.lastName = lastName;


        //Stores the pin's MD5 rather than the pin itself for better security

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //get new uuid

        this.uuid = theBank.getNewUserUUID();

        //Creates an empty list of accounts

        this.accounts = new ArrayList<Account>();

        //prints log messages
        System.out.printf("New user %s, %s, with ID %s created.\n", lastName, firstName, this.uuid);
    }

    //Method to add an account for the user
    public void addAccount(Account anAcct)
    {
        this.accounts.add(anAcct);
    }

    //returns user's UUID
    public String getUUID()
    {
        return this.uuid;
    }

    public boolean validatePin(String aPin)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
    public String getFirstName()
    {
        return this.firstName;
    }
    public void printAccountsSummary()
    {
        System.out.printf("\n\n%s's account summary\n", this.firstName);
        for(int a = 0; a < this.accounts.size(); a++)
        {
            System.out.printf("%d) %s\n", a +1 ,  this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
    public int numAccounts()
    {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx)
    {
            this.accounts.get(acctIdx).printTransHistory();
    }
    public double getAcctBalance(int acctIdx)
    {
        return this.accounts.get(acctIdx).getBalance();
    }
    public String getAcctUUID(int acctIdx)
    {
        return this.accounts.get(acctIdx).getUUID();
    }
    public void addAcctTransaction(int acctIdx, double amount, String memo)
    {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
