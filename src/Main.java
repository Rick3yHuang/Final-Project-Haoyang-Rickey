/*
Haoyang Du & Ruiqi (Rickey) Huang
CS112 - Fall 2020
Section C
Blockchain Final Project
 */

package src;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public void mineBlock(int prefix){

        // need to meet the StackHolders's agreement in TreatySC
        String pre = Integer.toString(prefix);
        while(!ThisHash.substring(0, pre.length()).equals(pre)){
            nonce++;
            ThisHash = calculateBlockHash();
        }
    }

    public boolean TreatySC(Transaction t){

        // need to make sure the artefact doesn't have at least 2 transactions after 2001
        if (t.getBuyer().getBalance() - t.getPrice() < 0){
            return false;
        }

    }

    public Transaction retriveProvenance (String id){



    }

    public Transaction retriveProvenance(String id, long timestamp){



    }

    public boolean verify_Blockchain(ArrayList<Block> BC){



    }

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

        System.out.println(LocalDateTime.now().toString());

        /*
        ArrayList<Block> blockchain = new ArrayList<>();
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
