package src;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TimeZone;

public class Block
{
    private String PreviousHash;
    private Transaction Provenance;
    private LocalDateTime Timestamp;
    private int nonce;
    private String ThisHash;
    //whether or not the block is mined; starting with false, if mined, change this to ture
    private boolean Mined = false;

    public Block( Transaction transaction, String previousHash,LocalDateTime timestamp)
    {
        PreviousHash=previousHash;
        Provenance=transaction;
        Timestamp=timestamp;
    }

    public void setPreviousHash(String previousHash) { PreviousHash = previousHash; }
    public void setProvenance(Transaction data) { Provenance = data; }
    public void setTimestamp(LocalDateTime timestamp) { Timestamp = timestamp; }
    public void setNonce(int nonce) { this.nonce = nonce; }
    public void setThisHash(String thisHash) { ThisHash = thisHash; }
    public void setMined(boolean ifMined) { this.Mined = ifMined; }

    public String getPreviousHash() { return PreviousHash; }
    public Transaction getProvenance() { return Provenance; }
    public LocalDateTime getTimestamp() { return Timestamp; }
    public int getNonce() { return nonce; }
    public String getThisHash() { return ThisHash; }
    public boolean isMined() { return Mined; }



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

    // overide toString for testing
    @Override
    public String toString() {
        return "Block{" +
                "PreviousHash='" + PreviousHash + '\'' +
                ", Provenance=" + Provenance +
                ", Timestamp=" + Timestamp +
                ", nonce=" + nonce +
                ", ThisHash='" + ThisHash + '\'' +
                ", Mined=" + Mined +
                '}';
    }
}
