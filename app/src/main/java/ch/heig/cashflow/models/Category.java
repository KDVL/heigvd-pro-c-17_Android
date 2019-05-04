package ch.heig.cashflow.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable {

    private final long ID;
    private String name;
    private String iconName;
    private Transaction.Type type;
    private long quota;
    private boolean enabled;

    public Category(long id, String name, String iconName, Transaction.Type type, long quota, boolean enabled) {
        this.ID = id;
        this.name = name;
        this.iconName = iconName;
        this.type = type;
        this.quota = quota;
        this.enabled = enabled;
    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Transaction.Type getType() {
        return type;
    }

    public void setType(Transaction.Type type) {
        this.type = type;
    }

    public long getQuota() {
        return quota;
    }

    public void setQuota(long quota) {
        this.quota = quota;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}