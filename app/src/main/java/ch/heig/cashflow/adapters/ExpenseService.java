package ch.heig.cashflow.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.heig.cashflow.models.Expense;

public class ExpenseService {

    public ExpenseService() {
    }


    public Map<String, ArrayList<Expense>> getAll() {
        HashMap<String, ArrayList<Expense>> map = new HashMap<>();

        ArrayList<Expense> list = new ArrayList<>();

        Expense expense = new Expense();

        list.add(expense);

        map.put("05", list);

        return map;
    }

}
