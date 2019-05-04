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
import ch.heig.cashflow.models.Type;

public class ExpenseService {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private List<Expense> list;

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

        Expense expense = new Expense(1, sdf.format(date),
                new Category(1, "Drink", "category_drink_black_24", Transaction.Type.EXPENSE, 100000, true),
                120, "My first expense");

        list.add(expense);

        map.put("05", list);

        return map;
    }
}
