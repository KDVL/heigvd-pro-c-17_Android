package ch.heig.cashflow.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Expense;

public class ExpenseService {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    List<Expense> list = null;

    public ExpenseService() {
        list = new ArrayList<>();
    }

    public Expense getExpenseById(long expenseID) {
        return getAll().get("05").get(0);
    }

    public void deleteExpense(long expenseId) {
        list.remove(0);
    }


    public Map<String, List<Expense>> getAll() {
        HashMap<String, List<Expense>> map = new HashMap<>();

        Date date = new Date();

        Expense expense = new Expense(1, new Category("Drink", "#222222", "category_drink_black_24", 100000),
                sdf.format(date), 120, "My first expense");

        list.add(expense);

        map.put("05", list);

        return map;
    }
}
