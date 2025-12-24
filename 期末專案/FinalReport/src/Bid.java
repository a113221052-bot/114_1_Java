import java.time.LocalDateTime;

/**
 * 出價紀錄（手動出價 / 自動產生的 manual bid）
 */
public class Bid {
    private final Buyer buyer;
    private final double amount;
    private final LocalDateTime time;

    public Bid(Buyer buyer, double amount) {
        this.buyer = buyer;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }
}