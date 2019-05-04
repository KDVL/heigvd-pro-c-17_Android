package ch.heig.cashflow.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.ExpenseDetailsActivity;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionsService;

public class ExpenseCardsAdapter extends BaseAdapter implements TransactionsService.Callback {
    private static final String[] MONTH_ARRAY = {". Janvier", ". Fevrier", ". Mars", ". Avril",
            ". Mai", ". Juin", ". Juillet", ". Aout", ". Septembre", ". Octobre", ". Novembre", ". Decembre"};

    private List<Transaction> currentMonthExpenses;
    private List<Transaction> currentMonthExpensesGroupeByDay;
    private List<Transaction> expensesDailyList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ExpenseCardsAdapter(Context context, List<Transaction> currentMonthExpenses) {
        this.context = context;
        this.currentMonthExpenses = currentMonthExpenses;
        layoutInflater = LayoutInflater.from(context);

        currentMonthExpensesGroupeByDay = new ArrayList<>();
        expensesDailyList = new ArrayList<>();
        groupByDay();
    }

    private void groupByDay() {
        Transaction cur;
        for (int i = 0; i < currentMonthExpenses.size(); ++i) {
            cur = currentMonthExpenses.get(i);
            for (int j = i + 1; j < currentMonthExpenses.size(); ++j) {
                if (currentMonthExpenses.get(j).getDate().equals(cur.getDate())) {
                    cur.setAmount(cur.getAmount() + currentMonthExpenses.get(j).getAmount());
                } else {
                    break;
                }
            }
            currentMonthExpensesGroupeByDay.add(cur);
        }

    }

    @Override
    public int getCount() {
        return currentMonthExpensesGroupeByDay.size();
    }

    @Override
    public Object getItem(int pos) {
        return currentMonthExpensesGroupeByDay.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_expense_list_item, null);
            holder = new ViewHolder();
            holder.dateView = convertView.findViewById(R.id.expenseDate);
            holder.dayList = convertView.findViewById(R.id.dayList);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Transaction expense = currentMonthExpensesGroupeByDay.get(pos);
        holder.dateView.setText(expense.getDate());

        expensesDailyList.clear();
        for(Transaction t: currentMonthExpenses){
            if(t.getDate().equals(expense.getDate())){
                expensesDailyList.add(t);
            }
        }

        holder.dayList.setAdapter(new ExpenseCardItemsAdapter(context, expensesDailyList));

        // ADAPTE LA HAUTEUR DE LIST VIEW
        ListAdapter listadp = holder.dayList.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int i = 0; i < listadp.getCount(); i++) {
                View listItem = listadp.getView(i, null, holder.dayList);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = holder.dayList.getLayoutParams();
            params.height = totalHeight + (holder.dayList.getDividerHeight() * (listadp.getCount() - 1));
            holder.dayList.setLayoutParams(params);
            holder.dayList.requestLayout();
        }

        holder.dayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = holder.dayList.getItemAtPosition(position);
                Expense e = (Expense) o;
                getExpenseDetails(e);
            }
        });

        return convertView;
    }


    private void getExpenseDetails(Expense e) {
        Intent expenseDetails = new Intent(context, ExpenseDetailsActivity.class);

        AddOrEditAdapter editExpenseAdapter = new EditExpenseAdapter(e);

        expenseDetails.putExtra(context.getString(R.string.transaction_adapter_key), editExpenseAdapter);
        context.startActivity(expenseDetails);
    }

    @Override
    public void connectionFailed(String error) {

    }

    @Override
    public void getAllFinished(List<Transaction> transactions) {

    }

    @Override
    public void getTypeFinished(List<Transaction> transactions) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    static class ViewHolder {
        TextView dateView;
        ListView dayList;
    }
}
