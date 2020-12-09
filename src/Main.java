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
import java.util.Scanner;
import java.util.TimeZone;

public class Main
{

    public static void mineBlock(int prefix, Block block, ArrayList<Block> blockchain)
    {
        //check to meet the TreatySC
        if (TreatySC(block.getProvenance(), blockchain))
        {
            //find an arbitrary nonce
            block.setNonce((int) Math.random());
            //produce String-type pre to compare with hash
            String pre = "";
            for (int i = 0; i < prefix; i++)
            {
                pre = pre + "0";
            }
            //find an initial hash with the random generated nonce
            block.setThisHash(block.calculateBlockHash());
            //increment the nonce and calculate new hash until find a desired one
            while (!block.getThisHash().substring(0, pre.length()).equals(pre))
            {
                block.setNonce(block.getNonce() + 1);
                block.setThisHash(block.calculateBlockHash());
            }
            //show that the block is mined
            block.setMined(true);
            System.out.println("This block is successfully mined with hash value: " + block.getThisHash());
        }
        else
        {
            //return a message to show that the treatySC is not meet
            System.out.println("Transaction Abort: The transaction doesn't meet the stakeholders agreement in TreatySC.");
        }
    }

    public static boolean TreatySC(Transaction t, ArrayList<Block> blockchain)
    {
        //implement the retriveProvenance method with parameter of ID and timeStamp
        if(retriveProvenance(t.getArtefact().getID(),978278400000L,blockchain).size() < 2)
            return false;
        //comparing the buyer balance and the price
        if (t.getBuyer().getBalance() - t.getPrice() < 0)
            return false;

        Double price = t.getPrice();
        //buyer pay the money
        t.getBuyer().setBalance(t.getBuyer().getBalance() - price);
        //distribute the revenue
        t.getAuctionHouse().setBalance(t.getAuctionHouse().getBalance() + 0.1 * price);
        t.getArtefact().getCountry().setBalance(t.getArtefact().getCountry().getBalance() + 0.2*price);
        t.getSeller().setBalance(t.getSeller().getBalance()+0.7*price);
        //show that the TreatySC has been met and the transaction has been processed
        return true;
    }

    public static ArrayList<Transaction> retriveProvenance (String id, ArrayList<Block> blockchain)
    {
        //create the output list of transaction
        ArrayList<Transaction> output = new ArrayList<Transaction>();
        //go through the whole block chain to find all related transactions
        for(int i=0;i<blockchain.size();i++)
        {
            //all transaction involving the specific atrfact will be add to the output list
            if(blockchain.get(i).getProvenance().getArtefact().getID().equals(id))
                output.add(blockchain.get(i).getProvenance());
        }
        //return the output list
        return output;
    }

    public static ArrayList<Transaction> retriveProvenance(String id, long timestamp,ArrayList<Block> blockchain)
    {
        //convert the long timeStamp to LocalDateTime type
        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId());

        //create the output list of transaction
        ArrayList<Transaction> output = new ArrayList<Transaction>();

        //iterate the whole blockchain to find the all transactions after the timeStamp involving the artefact
        for(int i = 0; i < blockchain.size(); i++)
        {
            if(blockchain.get(i).getProvenance().getArtefact().getID().equals(id)&&
                    blockchain.get(i).getProvenance().getTimestamp().isAfter(triggerTime))
            {
                //add satisfactory transactions into the output list
                output.add(blockchain.get(i).getProvenance());
            }
        }
        //return the output list
        return output;
    }

    public static boolean verify_Blockchain(ArrayList<Block> BC)
    {
        //iterate all block in the blockchain
        for(int i = 0; i < BC.size(); i++)
        {
            //if the block is the genesis block, don't need to check the previous hash
            if(i==0)
            {
                //verify the calculated result is correct
                if(!BC.get(i).calculateBlockHash().equals(BC.get(i).getThisHash()))
                    return false;
                //verify the block is mined
                if(!BC.get(i).isMined())
                    return false;
            }
            //all blocks after the genesis block
            else
            {
                //verify the calculated result is correct
                if(!BC.get(i).calculateBlockHash().equals(BC.get(i).getThisHash()))
                    return false;
                //verify the block is connected to the previous one
                if(!BC.get(i).getPreviousHash().equals(BC.get(i-1).getThisHash()))
                    return false;
                //verify the block is mined
                if(!BC.get(i).isMined())
                    return false;
            }
        }
        // if there is no mistakes in the whole blockchain, return true
        return true;
    }



    public static void main(String [] args)
    {
        ArrayList<Artefact> artefactList = new ArrayList<Artefact>();
        ArrayList<Stakeholder> stakeholderList= new ArrayList<Stakeholder>();

        Stakeholder a = new Stakeholder();
        a.setID("1234");
        a.setName("AA");
        a.setAddress("N/A");
        a.setBalance(123222224.34);
        stakeholderList.add(a);

        Artefact b = new Artefact();
        b.setID("1111");
        b.setName("BB");
        b.setCountry(a);
        b.setCurrentOwner(a);
        artefactList.add(b);

        Transaction c = new Transaction(b,a,a,a,150.63);


        //create a blockchain
        ArrayList<Block> blockchain = new ArrayList<>();
        int prefix = 4;
        String prefixString = new String(new char[prefix]).replace('\0', '0');

        //data1-data3 should be filled by the user

        Block Block1 = new Block(c,  null, LocalDateTime.now());
        Block1.setThisHash(Block1.calculateBlockHash());
        Block1.setMined(true);
        blockchain.add(Block1);
        Block Block2 = new Block(c,  blockchain.get(blockchain.size() - 1).getThisHash(), LocalDateTime.now());
        Block2.setThisHash(Block2.calculateBlockHash());
        Block2.setMined(true);
        blockchain.add(Block2);


        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the ID of the artefact");
        String artID = sc.nextLine();
        System.out.println("Please enter the ID of the seller");
        String sellerID = sc.nextLine();
        System.out.println("Please enter the ID of the buyer");
        String buyerID = sc.nextLine();
        System.out.println("Please enter the ID of the auction house");
        String auctionHID = sc.nextLine();
        System.out.println("Please enter the price");
        Double price= sc.nextDouble();

        Artefact art= new Artefact();
        for(int i=0;i<artefactList.size();i++)
        {
            if(artefactList.get(i).getID().equals(artID))
                art=artefactList.get(i);
        }
        Stakeholder seller=new Stakeholder();
        for(int i=0;i<stakeholderList.size();i++)
        {
            if(stakeholderList.get(i).getID().equals(sellerID))
                seller=stakeholderList.get(i);
        }
        Stakeholder buyer=new Stakeholder();
        for(int i=0;i<stakeholderList.size();i++)
        {
            if(stakeholderList.get(i).getID().equals(buyerID))
                buyer=stakeholderList.get(i);
        }
        Stakeholder auctionH=new Stakeholder();
        for(int i=0;i<stakeholderList.size();i++)
        {
            if(stakeholderList.get(i).getID().equals(auctionHID))
                auctionH=stakeholderList.get(i);
        }

        Transaction data1 = new Transaction(art,seller,buyer,auctionH,price);



        Block genesisBlock = new Block(data1,  blockchain.get(blockchain.size() - 1).getThisHash(), LocalDateTime.now());
        System.out.println("Start to mining the block");
        mineBlock(prefix,genesisBlock,blockchain);
        if (genesisBlock.getThisHash().substring(0, prefix).equals(prefixString) &&  verify_Blockchain(blockchain))
            blockchain.add(genesisBlock);
        else
            System.out.println("Malicious block, not added to the chain");
        System.out.println("Current length of the blockchain is: "+blockchain.size()); //for testing

//        Block secondBlock = new Block(c, blockchain.get(blockchain.size() - 1).getThisHash(),LocalDateTime.now());
//        mineBlock(prefix,secondBlock,blockchain);
//        if (secondBlock.getThisHash().substring(0, prefix).equals(prefixString) &&  verify_Blockchain(blockchain))
//            blockchain.add(secondBlock);
//        else
//            System.out.println("Malicious block, not added to the chain");
//        System.out.println(blockchain.size()); //for testing
//
//        Block newBlock = new Block(c,blockchain.get(blockchain.size() - 1).getThisHash(),LocalDateTime.now());
//        mineBlock(prefix,newBlock,blockchain);
//        if (newBlock.getThisHash().substring(0, prefix).equals(prefixString) &&  verify_Blockchain(blockchain))
//            blockchain.add(newBlock);
//        else
//            System.out.println("Malicious block, not added to the chain");
//        System.out.println(blockchain.size()); //for testing


    }




}
