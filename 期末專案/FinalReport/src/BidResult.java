/**
 * 出價結果物件（回傳給使用者）
 * - isHighest: 此次出價後，該出價者是否為目前最高出價者
 * - extended: 是否觸發延長拍賣時間（任一次加價若發生延長則為 true）
 * - currentHighestAmount: 目前最高出價金額
 * - currentHighestBuyerName: 目前最高出價者名稱（若無則為 null）
 * - rank: 此次出價在所有出價中的名次（1 = 目前最高；若流標則為 -1）
 */
public class BidResult {
    private final boolean isHighest;
    private final boolean extended;
    private final double currentHighestAmount;
    private final String currentHighestBuyerName;
    private final int rank;

    public BidResult(boolean isHighest, boolean extended, double currentHighestAmount,
                     String currentHighestBuyerName, int rank) {
        this.isHighest = isHighest;
        this.extended = extended;
        this.currentHighestAmount = currentHighestAmount;
        this.currentHighestBuyerName = currentHighestBuyerName;
        this.rank = rank;
    }

    public boolean isHighest() {
        return isHighest;
    }

    public boolean isExtended() {
        return extended;
    }

    public double getCurrentHighestAmount() {
        return currentHighestAmount;
    }

    public String getCurrentHighestBuyerName() {
        return currentHighestBuyerName;
    }

    public int getRank() {
        return rank;
    }
}
