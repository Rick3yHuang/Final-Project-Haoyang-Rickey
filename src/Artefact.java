package src;

public class  Artefact
{
    private String ID;
    private String Name;
    private Stakeholder Country;
    private Stakeholder CurrentOwner;


    public void setID(String ID)
    {
        this.ID = ID;
    }
    public void setName(String name)
    {
        Name = name;
    }
    public void setCountry(Stakeholder country)
    {
        Country = country;
    }
    public void setCurrentOwner(Stakeholder currentOwner)
    {
        CurrentOwner = currentOwner;
    }


    public String getID()
    {
        return ID;
    }
    public String getName()
    {
        return Name;
    }
    public Stakeholder getCountry()
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
                ", CurrentOwner= {" + CurrentOwner + "}";
    }
}
