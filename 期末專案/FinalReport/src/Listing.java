import java.time.LocalDateTime;

/**
 * 拍賣商品抽象類別
 */
public abstract class Listing {
    protected final String title;
    protected final Seller seller;
    protected LocalDateTime endTime;

    public Listing(String title, Seller seller, LocalDateTime endTime) {
        this.title = title;
        this.seller = seller;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public Seller getSeller() {
        return seller;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(endTime);
    }
}