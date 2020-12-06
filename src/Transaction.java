package src;
import java.time.LocalDateTime;

public class Transaction
{
    private Artefact artefact;
    private LocalDateTime Timestamp;
    private Stakeholder Seller;
    private Stakeholder Buyer;
    private Stakeholder AuctionHouse;
    private double Price;

    public void setArtefact(Artefact artefact) { this.artefact = artefact; }
    public void setTimestamp(LocalDateTime timestamp) { Timestamp = timestamp; }
    public void setSeller(Stakeholder seller) { Seller = seller; }
    public void setBuyer(Stakeholder buyer) { Buyer = buyer; }
    public void setAuctionHouse(Stakeholder auctionHouse) { AuctionHouse = auctionHouse; }
    public void setPrice(double price) { Price = price; }

    public Artefact getArtefact() { return artefact; }
    public LocalDateTime getTimestamp() { return Timestamp; }
    public Stakeholder getSeller() { return Seller; }
    public Stakeholder getBuyer() { return Buyer; }
    public Stakeholder getAuctionHouse() { return AuctionHouse; }
    public double getPrice() { return Price; }

    @Override
    public String toString() {
        return "artefact= {" + artefact +
                "}, Timestamp=" + Timestamp +
                ", Seller= {" + Seller +
                "}, Buyer= {" + Buyer +
                "}, AuctionHouse= {" + AuctionHouse +
                "}, Price=" + Price;
    }
}
