/*
Haoyang Du & Ruiqi (Rickey) Huang
CS112 - Fall 2020
Section C
Blockchain Final Project
 */

package src;
import javax.swing.text.html.HTMLDocument;
import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TimeZone;

public class Main
{
    public void mineBlock(int prefix, Block block, ArrayList<Block> blockchain)
    {

        // need to meet the StackHolders's agreement in TreatySC
        if (TreatySC(block.getProvenance(), blockchain))
        {
            // need to revise
            block.setNonce(10);
            String pre = null;
            for (int i = 0; i < prefix; i++){
                pre = pre + "0";
            }
            while (!block.getThisHash().substring(0, pre.length()).equals(pre)) {
                block.setNonce(block.getNonce() + 1);
                block.setThisHash(block.calculateBlockHash());
            }
            System.out.println("This block is successfully mined!!!");
        } else{
            System.out.println("Transaction Abort: The transaction doesn't meet the stakeholders agreement in TreatySC.");
        }
    }

    public boolean TreatySC(Transaction t, ArrayList<Block> blockchain)
    {
        if(retriveProvenance(t.getArtefact().getID(),978278400000L,blockchain).size() < 2)
            return false;
        if (t.getBuyer().getBalance() - t.getPrice() < 0)
            return false;

        Double price = t.getPrice();
        t.getBuyer().setBalance(t.getBuyer().getBalance() - price);
        t.getAuctionHouse().setBalance(t.getAuctionHouse().getBalance() + 0.1 * price);
        t.getArtefact().getCountry().setBalance(t.getArtefact().getCountry().getBalance() + 0.2*price);
        t.getSeller().setBalance(t.getSeller().getBalance()+0.7*price);
        return true;
    }

    public ArrayList<Transaction> retriveProvenance (String id, ArrayList<Block> blockchain)
    {
        ArrayList<Transaction> output = new ArrayList<Transaction>();
        for(int i=0;i<blockchain.size();i++)
        {
            if(blockchain.get(i).getProvenance().getArtefact().getID().equals(id))
                output.add(blockchain.get(i).getProvenance());
        }
        return output;
    }

    public ArrayList<Transaction> retriveProvenance(String id, long timestamp,ArrayList<Block> blockchain)
    {
        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId());
        ArrayList<Transaction> output = new ArrayList<Transaction>();

        for(int i=0;i<blockchain.size();i++)
        {
            if(blockchain.get(i).getProvenance().getArtefact().getID().equals(id)&&
                blockchain.get(i).getProvenance().getTimestamp().isAfter(triggerTime))
            {
                output.add(blockchain.get(i).getProvenance());
            }
        }
        return output;
    }

    public boolean verify_Blockchain(ArrayList<Block> BC)
    {
        //need to revise
        return false;

    }


    public static void main(String [] args)
    {
        Stakeholder a= new Stakeholder();
        a.setID("1234");
        a.setName("AA");
        a.setAddress("N/A");
        a.setBalance(1234.34);
        System.out.println(a);

        Artefact b= new Artefact();
        b.setID("1111");
        b.setName("BB");
        b.setCountry(a);
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

        System.out.println(LocalDateTime.now().toString());


        ArrayList<Block> blockchain = new ArrayList<>();
        

         /*
        int prefix = 4;   //we want our hash to start with four zeroes
        String prefixString = new String(new char[prefix]).replace('\0', '0');

        //data1-data3 should be filled by the user
        //changed all getHash to getThisHash

        Block genesisBlock = new Block(data1,   blockchain.get(blockchain.size() - 1).getThisHash(), new Date().getTime());
        newBlock.mineBlock(prefix);
        if (genesisBlock.getThisHash().substring(0, prefix).equals(prefixString) &&  verify_Blockchain(ArrayList<Block> BC))
            blockchain.add(newBlock);
        else
            System.out.println("Malicious block, not added to the chain");

        Block secondBlock = new Block(data2, blockchain.get(blockchain.size() - 1).getThisHash(),new Date().getTime());
        newBlock.mineBlock(prefix);
        if (secondBlock.getThisHash().substring(0, prefix).equals(prefixString) &&  verify_Blockchain(ArrayList<Block> BC))
            blockchain.add(newBlock);
        else
            System.out.println("Malicious block, not added to the chain");

        Block newBlock = new Block(data3,blockchain.get(blockchain.size() - 1).getThisHash(),
                new Date().getTime());
        newBlock.mineBlock(prefix);
        if (newBlock.getThisHash().substring(0, prefix).equals(prefixString) &&  verify_Blockchain(ArrayList<Block> BC))
            blockchain.add(newBlock);
        else
            System.out.println("Malicious block, not added to the chain");

         */




    }

}
