package src;

public class Stakeholder
{
    private int ID;
    private String Name;
    private String Address;
    private double Balance;


    public void setID(int ID)
    {
        this.ID = ID;
    }
    public void setName(String name)
    {
        Name = name;
    }
    public void setAddress(String address)
    {
        Address = address;
    }
    public void setBalance(double balance)
    {
        Balance = balance;
    }


    public int getID()
    {
        return ID;
    }
    public String getName() { return Name; }
    public String getAddress()
    {
        return Address;
    }
    public double getBalance()
    {
        return Balance;
    }

    @Override
    public String toString()
    {
        return "ID=" + ID +
                ", Name=" + Name +
                ", Address=" + Address +
                ", Balance=" + Balance;
    }
}
