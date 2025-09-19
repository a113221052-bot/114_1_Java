// 此類別代表一個銀行帳戶，包含帳戶號碼和餘額的基本資訊
public class Account {
    // 帳戶號碼
    private String accountNumber;
    // 帳戶餘額
    private double balance;

    // 建構子，初始化帳戶號碼和初始餘額
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // 獲取帳戶號碼
    public String getAccountNumber() {
        return accountNumber;
    }

    // 獲取帳戶餘額
    public double getBalance() {
        return balance;
    }

    // 存款方法，增加帳戶餘額
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            // 如果存款金額為負，拋出非法參數異常
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    // 提款方法，減少帳戶餘額
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            // 如果提款金額無效，拋出非法參數異常
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
    }
}
