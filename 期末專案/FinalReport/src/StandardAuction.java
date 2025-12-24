import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * StandardAuction（一般拍賣）
 * 主要變動：所有公開通知由 announce(...) 交給拍賣官（Auctioneer）陳述
 */
public class StandardAuction extends Listing {

    private Bid highestBid;
    private final List<Bid> bidHistory = new ArrayList<>();
    // watchers 現在預期只有拍賣官在裡面（但仍保留彈性）
    private final List<Observer> watchers = new ArrayList<>();
    private final List<AutoBid> autoBids = new ArrayList<>();
    private final double minIncrement;
    private boolean lastAddCausedExtend = false;

    public StandardAuction(String title, Seller seller, LocalDateTime endTime, double minIncrement) {
        super(title, seller, endTime);
        this.minIncrement = minIncrement;
    }

    public double getMinIncrement() {
        return minIncrement;
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public void addWatcher(Observer o) {
        if (!watchers.contains(o)) watchers.add(o);
    }

    public void removeWatcher(Observer o) {
        watchers.remove(o);
    }

    public void registerAutoBid(AutoBid auto) {
        if (!autoBids.contains(auto)) {
            auto.setAuction(this);
            autoBids.add(auto);
        }
    }

    /**
     * 由拍賣官統一宣布公開訊息
     */
    public void announce(String message) {
        for (Observer o : watchers) {
            o.update(message);
        }
    }

    /**
     * 內部: 新增一筆出價（更新最高、加歷史、通知拍賣官、延長時間）
     */
    public void addBidInternal(Bid bid) {
        lastAddCausedExtend = false;

        Bid prev = this.highestBid;
        if (prev != null && !prev.getBuyer().equals(bid.getBuyer())) {
            // 由拍賣官陳述「誰被超越」
            announce("原本的最高出價者「" + prev.getBuyer().getName()
                    + "」已被超越，目前出價領先的是「"
                    + bid.getBuyer().getName()
                    + "」，金額為 " + bid.getAmount() + " 萬。");
        }

        this.highestBid = bid;
        this.bidHistory.add(bid);

        // 由拍賣官陳述「新最高出價」
        announce("目前最高出價由「"
                + bid.getBuyer().getName()
                + "」以 " + bid.getAmount() + " 萬取得！");

        // 剩餘 < 5 分鐘延長，並由拍賣官宣布延長
        Duration remaining = Duration.between(LocalDateTime.now(), endTime);
        if (remaining.toMinutes() < 5) {
            endTime = endTime.plusMinutes(5);
            lastAddCausedExtend = true;
            announce("由於出價接近結標時間，為確保公平，拍賣時間延長 5 分鐘！");
        }
    }

    /**
     * placeBid 入口
     */
    public BidResult placeBid(Buyer buyer, double amount) {
        if (isExpired()) {
            throw new IllegalStateException("Auction has ended");
        }

        if (buyer.getId().equals(seller.getId())) {
            throw new IllegalArgumentException("Seller cannot bid on own listing");
        }

        double minAllowed = (highestBid == null) ? minIncrement : (highestBid.getAmount() + minIncrement);
        if (amount < minAllowed) {
            throw new IllegalArgumentException("Bid must be at least " + minAllowed);
        }

        Bid incoming = new Bid(buyer, amount);
        addBidInternal(incoming);
        boolean extendedOverall = lastAddCausedExtend;

        // AutoBid 處理（連鎖回應）
        boolean someoneAutoPlaced;
        do {
            someoneAutoPlaced = false;
            List<AutoBid> autoCopy = new ArrayList<>(autoBids);
            for (AutoBid auto : autoCopy) {
                if (highestBid != null && highestBid.getBuyer().equals(auto.getBidder())) continue;
                Bid currentTop = highestBid;
                boolean placed = auto.compete(currentTop);
                if (placed) {
                    someoneAutoPlaced = true;
                    if (lastAddCausedExtend) extendedOverall = true;
                    break;
                }
            }
        } while (someoneAutoPlaced);

        // 計算名次（以該買家最新出價為準）
        int rank = -1;
        if (!bidHistory.isEmpty()) {
            Bid latestOfBuyer = null;
            for (int i = bidHistory.size() - 1; i >= 0; i--) {
                Bid b = bidHistory.get(i);
                if (b.getBuyer().equals(buyer)) {
                    latestOfBuyer = b;
                    break;
                }
            }
            if (latestOfBuyer != null) {
                int better = 0;
                for (Bid b : bidHistory) {
                    if (b.getAmount() > latestOfBuyer.getAmount()) better++;
                }
                rank = 1 + better;
            } else {
                rank = -1;
            }
        }

        boolean isNowHighest = (highestBid != null && highestBid.getBuyer().equals(buyer));
        double currentHighestAmount = highestBid == null ? 0.0 : highestBid.getAmount();
        String currentHighestBuyerName = highestBid == null ? null : highestBid.getBuyer().getName();

        return new BidResult(isNowHighest, extendedOverall, currentHighestAmount, currentHighestBuyerName, rank);
    }

    public List<Bid> getBidHistory() {
        return new ArrayList<>(bidHistory);
    }
}