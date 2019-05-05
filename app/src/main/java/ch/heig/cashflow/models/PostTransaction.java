package ch.heig.cashflow.models;

public class PostTransaction {

    private String date;
    private long categoryId;
    private long amount;
    private Transaction.Type type;
    private String description;

    public PostTransaction(Transaction transaction) {
        this.date = transaction.getDate();
        this.categoryId = transaction.getCategory().getID();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
    }
}