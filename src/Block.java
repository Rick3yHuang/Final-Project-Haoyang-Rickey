package src;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class Block
{
    private String PreviousHash;
    private Transaction Provenance;
    private LocalDateTime Timestamp;
    private int nonce;
    private String ThisHash;

    public void setPreviousHash(String previousHash) { PreviousHash = previousHash; }
    public void setData(Transaction data) { Provenance = data; }
    public void setTimestamp(LocalDateTime timestamp) { Timestamp = timestamp; }
    public void setNonce(int nonce) { this.nonce = nonce; }
    public void setThisHash(String thisHash) { ThisHash = thisHash; }

    public String getPreviousHash() { return PreviousHash; }
    public Transaction getData() { return Provenance; }
    public LocalDateTime getTimestamp() { return Timestamp; }
    public int getNonce() { return nonce; }
    public String getThisHash() { return ThisHash; }

    public String calculateBlockHash()
    {
        String dataToHash = PreviousHash
                + Timestamp.toString()                  //Changed from: Long.toString(timeStamp)
                + Integer.toString(nonce)
                + Provenance.toString();
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes( "utf-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            System.out.println("The encoding is not supported");
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    public void mineBlock(int prefix){

        // need to meet the StackHolders's agreement in TreatySC
        String pre = Integer.toString(prefix);
        while(!ThisHash.substring(0, pre.length()).equals(pre)){
            nonce++;
            ThisHash = calculateBlockHash();
        }


    }
}
