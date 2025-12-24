import java.util.UUID;
import java.util.Objects;

/**
 * 抽象使用者：包含唯一 id 與名稱
 * 其他使用者（Seller / Buyer）繼承此類別
 */
public abstract class User {
    protected final String id;
    protected final String name;

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * 以 id 判斷同一個使用者
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
