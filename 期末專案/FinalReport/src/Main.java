import java.time.LocalDateTime;

/**
 * 範例主程式：示範拍賣官統一陳述
 */
public class Main {
    public static void main(String[] args) {
        Seller seller = new Seller("Alice");
        Buyer bob = new Buyer("Bob");
        Buyer charlie = new Buyer("Charlie");
        Buyer dave = new Buyer("Dave");

        StandardAuction auction = new StandardAuction(
                "Gaming Laptop",
                seller,
                LocalDateTime.now().plusMinutes(2),
                10.0
        );

        // 不要把每個買家都當 watcher（避免重複印同樣內容）
        Auctioneer auctioneer = new Auctioneer("拍賣官");
        auction.addWatcher(auctioneer);

        // 註冊 AutoBid
        AutoBid autoCharlie = new AutoBid(charlie, 210.0);
        AutoBid autoDave = new AutoBid(dave, 180.0);
        AutoBid autobob = new AutoBid(bob, 250.0);
        auction.registerAutoBid(autoCharlie);
        auction.registerAutoBid(autoDave);
        auction.registerAutoBid(autobob);

        // 測試出價
        System.out.println("Bob places 100");
        BidResult r1 = auction.placeBid(bob, 100.0);
        System.out.println(
                "【拍賣官總結】"
                        + (r1.isHighest() ? "你目前領先所有出價者。" : "你目前尚未成為最高出價者。")
                        + " 目前拍賣最高出價為 "
                        + r1.getCurrentHighestAmount() + " 萬，由「" + r1.getCurrentHighestBuyerName() + "」領先。"
                        + " 你的出價排名為第 " + r1.getRank() + " 名。"
                        + (r1.isExtended() ? " 因出價接近結標時間，拍賣已延長。" : "")
        );

        System.out.println("\nDave places 150");
        BidResult r2 = auction.placeBid(dave, 150.0);
        System.out.println(
                "【拍賣官總結】"
                        + (r2.isHighest() ? "你目前領先所有出價者。" : "你目前尚未成為最高出價者。")
                        + " 目前拍賣最高出價為 "
                        + r2.getCurrentHighestAmount() + " 萬，由「" + r2.getCurrentHighestBuyerName() + "」領先。"
                        + " 你的出價排名為第 " + r2.getRank() + " 名。"
                        + (r2.isExtended() ? " 因出價接近結標時間，拍賣已延長。" : "")
        );

        System.out.println("\nBob places 190");
        BidResult r3 = auction.placeBid(bob, 190.0);
        System.out.println(
                "【拍賣官總結】"
                        + (r3.isHighest() ? "你目前領先所有出價者。" : "你目前尚未成為最高出價者。")
                        + " 目前拍賣最高出價為 "
                        + r3.getCurrentHighestAmount() + " 萬，由「" + r3.getCurrentHighestBuyerName() + "」領先。"
                        + " 你的出價排名為第 " + r3.getRank() + " 輪。"
                        + (r3.isExtended() ? " 因出價接近結標時間，拍賣已延長。" : "")
        );
    }
}