/**
 * 拍賣官（Auctioneer）
 * 負責「統一陳述」所有拍賣公開事件
 */
public class Auctioneer implements Observer {
    private final String name;

    public Auctioneer(String name) {
        this.name = name;
    }

    /**
     * 拍賣官收到訊息就統一印出（或可改為收集後 batch 顯示）
     */
    @Override
    public void update(String message) {
        System.out.println("【拍賣官】" + message);
    }
}
