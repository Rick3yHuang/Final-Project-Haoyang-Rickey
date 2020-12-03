/*
Haoyang Du & Ruiqi (Rickey) Huang
CS112 - Fall 2020
Section C
Blockchain Final Project
 */

package src;
import java.time.LocalDateTime;

public class Main {

    public static void main(String [] args)
    {
        Stakeholder a= new Stakeholder();
        a.setID(1234);
        a.setName("AA");
        a.setAddress("N/A");
        a.setBalance(1234.34);
        System.out.println(a);

        Artefact b= new Artefact();
        b.setID(1111);
        b.setName("BB");
        b.setCountry("N/A");
        b.setCurrentOwner(a);
        System.out.println(b);

        Transaction c=new Transaction();
        c.setArtefact(b);
        c.setTimestamp(LocalDateTime.now());
        c.setSeller(a);
        c.setBuyer(a);
        c.setAuctionHouse(a);
        c.setPrice(150.63);
        System.out.println(c);



    }

}
