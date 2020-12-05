package src;

public class  Artefact
{
    private int ID;
    private String Name;
    private String Country;
    private Stakeholder CurrentOwner;


    public void setID(int ID)
    {
        this.ID = ID;
    }
    public void setName(String name)
    {
        Name = name;
    }
    public void setCountry(String country)
    {
        Country = country;
    }
    public void setCurrentOwner(Stakeholder currentOwner)
    {
        CurrentOwner = currentOwner;
    }


    public int getID()
    {
        return ID;
    }
    public String getName()
    {
        return Name;
    }
    public String getCountry()
    {
        return Country;
    }
    public Stakeholder getCurrentOwner() { return CurrentOwner; }


    @Override
    public String toString()
    {
        return "ID=" + ID +
                ", Name=" + Name  +
                ", Country=" + Country +
                ", CurrentOwner= {" + CurrentOwner.toString() + "}";
    }
}
