/**
 * 觀察者介面（簡單版）
 * 任何想接收拍賣通知的物件（例如 Buyer）都可以實作此介面
 */
public interface Observer {
    void update(String message);
}
