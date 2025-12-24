/**
 * 買家（Buyer），實作 Observer 但 update 不直接印訊息
 * 改為讓 Auctioneer 統一陳述公開事件。
 */
public class Buyer extends User implements Observer {

    public Buyer(String name) {
        super(name);
    }

    /**
     * Buyer 的 update 現在不直接輸出（避免重複）
     * 若日後想要私人通知，可把內容存在 list 或傳送 email。
     */
    @Override
    public void update(String message) {
        // 不直接印出，交給拍賣官宣佈
        // 若要儲存私人訊息，這裡可以改為 push 到 List<String>
    }
}