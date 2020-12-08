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
import java.util.Date;
import java.util.TimeZone;

public class Main
{

    public static void mineBlock(int prefix, Block block, ArrayList<Block> blockchain)
    {
        if (TreatySC(block.getProvenance(), blockchain))
        {
            block.setNonce((int) Math.random());
            String pre = "";
            for (int i = 0; i < prefix; i++)
            {
                pre = pre + "0";
            }
            block.setThisHash(block.calculateBlockHash());
            while (!block.getThisHash().substring(0, pre.length()).equals(pre))
            {
                block.setNonce(block.getNonce() + 1);
                block.setThisHash(block.calculateBlockHash());
            }
            block.setMined(true);
            System.out.println("This block is successfully mined with hash value: " + block.getThisHash());
        }
        else
        {
            System.out.println("Transaction Abort: The transaction doesn't meet the stakeholders agreement in TreatySC.");
        }
    }

    public static boolean TreatySC(Transaction t, ArrayList<Block> blockchain)
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

    public static ArrayList<Transaction> retriveProvenance (String id, ArrayList<Block> blockchain)
    {
        ArrayList<Transaction> output = new ArrayList<Transaction>();
        for(int i=0;i<blockchain.size();i++)
        {
            if(blockchain.get(i).getProvenance().getArtefact().getID().equals(id))
                output.add(blockchain.get(i).getProvenance());
        }
        return output;
    }

    public static ArrayList<Transaction> retriveProvenance(String id, long timestamp,ArrayList<Block> blockchain)
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

    public static boolean verify_Blockchain(ArrayList<Block> BC)
    {
        for(int i=0;i<BC.size();i++)
        {
            if(i==0)
            {
                if(!BC.get(i).calculateBlockHash().equals(BC.get(i).getThisHash()))
                    return false;
                if(!BC.get(i).isMined())
                    return false;
            }
            else
            {
                if(!BC.get(i).calculateBlockHash().equals(BC.get(i).getThisHash()))
                    return false;
                if(!BC.get(i).getPreviousHash().equals(BC.get(i-1).getThisHash()))
                    return false;
                if(!BC.get(i).isMined())
                    return false;
            }
        }
        return true;
    }



    public static void main(String [] args)
    {
        Stakeholder a= new Stakeholder();
        a.setID("1234");
        a.setName("AA");
        a.setAddress("N/A");
        a.setBalance(123222224.34);
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

        int prefix = 4;
        String prefixString = new String(new char[prefix]).replace('\0', '0');

        //data1-data3 should be filled by the user
        //changed all getHash to getThisHash

        Block Block1 = new Block(c,  null, LocalDateTime.now());
        Block1.setThisHash(Block1.calculateBlockHash());
        Block1.setMined(true);
        Block Block2 = new Block(c,  Block1.getThisHash(), LocalDateTime.now());
        Block2.setThisHash(Block2.calculateBlockHash());
        Block2.setMined(true);
        blockchain.add(Block1);
        blockchain.add(Block2);

        Block genesisBlock = new Block(c,  Block2.getThisHash(), LocalDateTime.now());
        System.out.println("Start to mining the block");
        mineBlock(prefix,genesisBlock,blockchain);
        if (genesisBlock.getThisHash().substring(0, prefix).equals(prefixString) &&  verify_Blockchain(blockchain))
            blockchain.add(genesisBlock);
        else
            System.out.println("Malicious block, not added to the chain");
        System.out.println(blockchain.size()); //for testing

        Block secondBlock = new Block(c, blockchain.get(blockchain.size() - 1).getThisHash(),LocalDateTime.now());
        mineBlock(prefix,secondBlock,blockchain);
        if (secondBlock.getThisHash().substring(0, prefix).equals(prefixString) &&  verify_Blockchain(blockchain))
            blockchain.add(secondBlock);
        else
            System.out.println("Malicious block, not added to the chain");
        System.out.println(blockchain.size()); //for testing

        Block newBlock = new Block(c,blockchain.get(blockchain.size() - 1).getThisHash(),LocalDateTime.now());
        mineBlock(prefix,newBlock,blockchain);
        if (newBlock.getThisHash().substring(0, prefix).equals(prefixString) &&  verify_Blockchain(blockchain))
            blockchain.add(newBlock);
        else
            System.out.println("Malicious block, not added to the chain");
        System.out.println(blockchain.size()); //for testing


    }




}
