/**
 * 自動競標（AutoBid）
 */
public class AutoBid {
    private final Buyer bidder;
    private final double maxAmount;
    private StandardAuction auction;

    public AutoBid(Buyer bidder, double maxAmount) {
        this.bidder = bidder;
        this.maxAmount = maxAmount;
    }

    public Buyer getBidder() {
        return bidder;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    void setAuction(StandardAuction auction) {
        this.auction = auction;
    }

    /**
     * compete: 嘗試對 incomingBid 競標
     * - 若 incoming < maxAmount: 提出 autoBid = min(incoming + increment, maxAmount)
     * - 若 incoming >= maxAmount: 不出價，告知拍賣官其自動出價已被超越
     *
     * 回傳 true 表示已成功出價（產生新的 manual bid 並加入 auction）
     */
    public boolean compete(Bid incomingBid) {
        if (auction == null) {
            throw new IllegalStateException("AutoBid not registered to an auction");
        }
        double incomingAmount = incomingBid.getAmount();

        if (incomingAmount < maxAmount) {
            double proposed = incomingAmount + auction.getMinIncrement();
            if (proposed > maxAmount) {
                proposed = maxAmount;
            }
            Bid currentHighest = auction.getHighestBid();
            double currentHighestAmount = currentHighest == null ? 0.0 : currentHighest.getAmount();
            if (proposed <= currentHighestAmount) {
                return false;
            }
            Bid autoPlaced = new Bid(bidder, proposed);
            auction.addBidInternal(autoPlaced);
            return true;
        } else {
            // 通知拍賣官：自動上限被超越
            auction.announce("買家「" + bidder.getName()
                    + "」設定的自動出價上限（"
                    + maxAmount + " 萬）已被「"
                    + incomingBid.getBuyer().getName()
                    + "」以 " + incomingAmount + " 萬超越！");
            return false;
        }
    }
}