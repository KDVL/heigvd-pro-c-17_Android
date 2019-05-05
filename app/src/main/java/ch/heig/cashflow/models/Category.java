package ch.heig.cashflow.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable {

    private final long id;
    private String name;
    private String iconName;
    private Type type;
    private long quota;
    private boolean enabled;

    public Category(long id, String name, String iconName, Type type, long quota, boolean enabled) {
        this.id = id;
        this.name = name;
        this.iconName = iconName;
        this.type = type;
        this.quota = quota;
        this.enabled = enabled;
    }

    public long getID() {
        return id;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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