/**
 * Model category
 * <i>Any of several fundamental and distinct classes to which entities or concepts belong.</i>
 *
 * <p>
 * Defines a category with an id, a name, an icon, a type, a quota and a state.
 *
 * @author Aleksandar MILENKOVIC, Thibaud ALT, Kevin DO VALE
 * @version 1.0
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
    private boolean state;

    /**
     * The Category constructor
     *
     * @param id       The category id
     * @param name     The category name
     * @param iconName The category icon
     * @param type     The category {@code Type}
     * @param quota    The category quota
     * @param state    The category state
     */
    public Category(long id, String name, String iconName, Type type, long quota, boolean state) {
        this.id = id;
        this.name = name;
        this.iconName = iconName;
        this.type = type;
        this.quota = quota;
        this.state = state;
    }

    /**
     * Get the category id
     *
     * @return long The id of the category
     */
    public long getID() {
        return id;
    }

    /**
     * Get the category name
     *
     * @return String The name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Set the category name
     *
     * @param name The name of the category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the category icon name
     *
     * @return icon The icon name of the category
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * Set the category icon name
     *
     * @param iconName The icon name of the category
     */
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    /**
     * Get the category {@code Type}
     *
     * @return Type The {@code Type} of the category
     */
    public Type getType() {
        return type;
    }

    /**
     * Set the category type
     *
     * @param type The type of the category
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Get the category quota
     *
     * @return The quota of the category
     */
    public long getQuota() {
        return quota;
    }

    /**
     * Set the category quota
     *
     * @param quota The quota of the category
     */
    public void setQuota(long quota) {
        this.quota = quota;
    }

    /**
     * Get the category state
     *
     * @return The actual state of the category
     */
    public boolean isEnabled() {
        return state;
    }

    /**
     * Set the category state
     *
     * @param state The state of the category
     */
    public void setEnabled(boolean state) {
        this.state = state;
    }
}
