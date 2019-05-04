package ch.heig.cashflow.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Type;

public class ExpenseService {

    public ExpenseService() {
    }


    public Map<String, ArrayList<Expense>> getAll() {
        HashMap<String, ArrayList<Expense>> map = new HashMap<>();

        ArrayList<Expense> list = new ArrayList<>();
        Category cat = new Category(1, "Carte de Crédit", "carte", Type.EXPENSE, 123, true);
        Expense expense = new Expense(1, "2019-05-04", cat, 12345, "des");
        list.add(expense);

        map.put("05", list);

        return map;
    }

}
