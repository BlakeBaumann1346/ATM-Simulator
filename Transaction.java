import java.util.ArrayList;
import java.util.Date;

public class Transaction {

    private double amount;  //Amount of transaction

    private Date timestamp;  //timestamp

    private String memo;  //A memo for the transaction

    private Account inAccount; //The account in which the transaction is done.

    public Transaction(double amount, Account inAccount)
    {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount)
    {
        this(amount, inAccount);
        this.memo = memo;
    }
    public double getAmount()
    {
        return this.amount;
    }
    public String getSummaryLine()
    {
        if(this.amount >= 0)
        {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        }
        else{
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), this.amount, this.memo);
        }
    }
}
