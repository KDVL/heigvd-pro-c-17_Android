package ch.heig.cashflow.models;

import java.io.Serializable;
import java.util.List;

public class Dashboard extends DashboardDetails implements Serializable {

    private List<DashboardDetails> categories;

    public Dashboard(long id, String name, long expense, long income, long budget, List<DashboardDetails> categories) {
        super(id, name, expense, income, budget);
        this.categories = categories;
    }

    public List<DashboardDetails> getCategories() {
        return categories;
    }

}
