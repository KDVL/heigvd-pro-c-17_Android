package ch.heig.cashflow.models;

public class Category {

    private String name;
    private String color;
    private String icon;
    private long amount;

    public Category(String name, String color, String icon, long amount) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
