import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    public Bank(String name)
    {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

public String getNewUserUUID()
{
String uuid;
Random ran = new Random();
int length = 6;
boolean nonUnique;

do{
uuid = "";
for(int i = 0; i < length; i++)
{
    uuid += ((Integer)ran.nextInt(10)).toString();   //Random number 0 to 10 exclusive
}
nonUnique = false;
for(User u: this.users)         //Checks if UUID already exists
{
    if(uuid.compareTo(u.getUUID()) == 0)
    {
        nonUnique = true;
        break;
    }
}
}while(nonUnique);


return uuid;
}
public String getNewAccountUUID()
{
    String uuid;
    Random ran = new Random();
    int length = 10;
    boolean nonUnique;

    do{
        uuid = "";
        for(int i = 0; i < length; i++)
        {
            uuid += ((Integer)ran.nextInt(10)).toString();   //Random number 0 to 10 exclusive
        }
        nonUnique = false;
        for(Account a: this.accounts)         //Checks if UUID already exists
        {
            if(uuid.compareTo(a.getUUID()) == 0)
            {
                nonUnique = true;
                break;
            }
        }
    }while(nonUnique);


    return uuid;
}


    public void addAccount(Account anAcct)
    {
        this.accounts.add(anAcct);
    }

    public User addUser(String firstName, String lastName, String pin)
    {
        //creates a user object and adds it to the list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //create a savings account
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }
    public User userLogin(String userID, String pin)
    {
        for(User u : this.users)
        {
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin))  //Checks if ID matches
            {
                return u;
            }
        }
        return null;  //returned if no user was found or pin is wrong
    }

    public String getName()
    {
        return  this.name;
    }
}
