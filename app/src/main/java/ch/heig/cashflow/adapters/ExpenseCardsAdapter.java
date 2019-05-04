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

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.ExpenseDetailsActivity;
import ch.heig.cashflow.models.Expense;

public class ExpenseCardsAdapter extends BaseAdapter {
    private static final String[] MONTH_ARRAY = {". Janvier", ". Fevrier", ". Mars", ". Avril",
            ". Mai", ". Juin", ". Juillet", ". Aout", ". Septembre", ". Octobre", ". Novembre", ". Decembre"};

    private ExpenseService expenseService = null;

    private List<Expense> currentMonthExpensesArrayList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ExpenseCardsAdapter(Context context, List<Expense> currentMonthExpensesArrayList) {
        this.context = context;
        this.currentMonthExpensesArrayList = currentMonthExpensesArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return currentMonthExpensesArrayList.size();
    }

    @Override
    public Object getItem(int pos) {
        return currentMonthExpensesArrayList.get(pos);
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
        Expense expense = this.currentMonthExpensesArrayList.get(pos);

        holder.dateView.setText(expense.getDate());

        if (expenseService == null)
            expenseService = new ExpenseService();

        // Uniquement les dépense pour le jour donnée
        List<Expense> myDailyList = expenseService.getAll().get("05");

        holder.dayList.setAdapter(new ExpenseCardItemsAdapter(context, myDailyList));

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

    static class ViewHolder {
        TextView dateView;
        ListView dayList;
    }
}
