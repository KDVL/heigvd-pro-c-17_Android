/**
 * Model category
 *
 * @author Aleksandar MILENKOVIC, Alt Thibaud, Kevin Do Vale
 * @version 1.0
 * @see ch.heig.cashflow.models.Category
 */
package ch.heig.cashflow.models;

import java.io.Serializable;

import ch.heig.cashflow.utils.Type;

public class Category implements Serializable {

    private final long id;
    private String name;
    private String iconName;
    private Type type;
    private long quota;
    private boolean enabled;

    /**
     * Constructor
     * @param id id
     * @param name name
     * @param iconName icon name
     * @param type type expense or income
     * @param quota quota
     * @param enabled state
     */
    public Category(long id, String name, String iconName, Type type, long quota, boolean enabled) {
        this.id = id;
        this.name = name;
        this.iconName = iconName;
        this.type = type;
        this.quota = quota;
        this.enabled = enabled;
    }

    /**
     * get category id
     * @return category id
     */
    public long getID() {
        return id;
    }

    /**
     * get category name
     * @return category name
     */
    public String getName() {
        return name;
    }

    /**
     * set category name
     * @param name name of category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get category icon name
     * @return icon name
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * set category name of icon
     * @param iconName name of icon
     */
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    /**
     * get category type
     * @return type
     */
    public Type getType() {
        return type;
    }

    /**
     * set category type
     * @param type type of category
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * get category quota
     * @return quota
     */
    public long getQuota() {
        return quota;
    }

    /**
     * set category quota
     * @param quota quota of category
     */
    public void setQuota(long quota) {
        this.quota = quota;
    }

    /**
     * get category state
     * @return actualy state
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * set category state
     * @param enabled state of category
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
