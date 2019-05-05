package ch.heig.cashflow.models;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Dashboard extends DashboardDetails implements Serializable {

    private List<DashboardDetails> categories;

    public Dashboard(long expense, long income, long budget, List<DashboardDetails> categoryDetails) {
        super(expense, income, budget);
        this.categories = categoryDetails;
    }

    public List<DashboardDetails> getCategoryDetails() {
        return categories;
    }

    public void setCategoryDetails(List<DashboardDetails> categoryDetails) {
        this.categories = categoryDetails;
    }
}
