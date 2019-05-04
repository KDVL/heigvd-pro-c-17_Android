package ch.heig.cashflow.models;

public class Budget {

    private final long ID;
    private final String date;
    private long quota;

    public Budget(long ID, String date, long quota) {
        this.ID = ID;
        this.date = date;
        this.quota = quota;
    }

    public long getID() {
        return ID;
    }

    public String getDate() {
        return date;
    }

    public long getQuota() {
        return quota;
    }

    public void setQuota(long quota) {
        this.quota = quota;
    }
}
