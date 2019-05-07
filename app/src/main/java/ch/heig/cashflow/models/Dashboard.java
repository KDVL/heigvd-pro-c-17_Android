package ch.heig.cashflow.models;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Dashboard extends DashboardDetails implements Serializable {

    private List<DashboardDetails> categories;

    public Dashboard(String name, long expense, long income, long budget, List<DashboardDetails> categories) {
        super(name, expense, income, budget);
        this.categories = categories;
    }

    public List<DashboardDetails> getCategoryDetails() {
        return categories;
    }

    public void setCategoryDetails(List<DashboardDetails> categories) {
        this.categories = categories;
    }
}
