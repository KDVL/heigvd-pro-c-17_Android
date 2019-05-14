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

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.TransactionDetailsActivity;
import ch.heig.cashflow.utils.Currency;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.utils.Type;
import ch.heig.cashflow.network.services.TransactionsService;

public class TransactionCardsAdapter extends BaseAdapter implements TransactionsService.Callback {

    private List<Transaction> currentMonthTransactions;
    private List<Transaction> currentMonthTransactionsGroupeByDay;
    private List<Transaction> transactionDailyList;
    private LayoutInflater layoutInflater;
    private Context context;
    private Type type;

    public TransactionCardsAdapter(Context context, List<Transaction> currentMonthExpenses, Type type) {
        this.context = context;
        this.currentMonthTransactions = currentMonthExpenses;
        if(context != null)
            layoutInflater = LayoutInflater.from(context);
        this.type = type;

        currentMonthTransactionsGroupeByDay = new ArrayList<>();
        groupByDay();
    }

    private void groupByDay() {
        for (int i = 0; i < currentMonthTransactions.size(); ++i) {
            for (int j = i + 1; j < currentMonthTransactions.size(); ++j) {
                if (!(currentMonthTransactions.get(j).getDate().equals(currentMonthTransactions.get(i).getDate()))) {
                    i = j - 1;
                    break;
                }
                if (j == currentMonthTransactions.size() - 1) {
                    i = j;
                    break;
                }
            }
            currentMonthTransactionsGroupeByDay.add(currentMonthTransactions.get(i));
        }
    }

    @Override
    public int getCount() {
        return currentMonthTransactionsGroupeByDay.size();
    }

    @Override
    public Object getItem(int pos) {
        return currentMonthTransactionsGroupeByDay.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_transaction_list_item, null);
            holder = new ViewHolder();
            holder.dateView = convertView.findViewById(R.id.transaction_date);
            holder.dayList = convertView.findViewById(R.id.dayList);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Transaction expense = currentMonthTransactionsGroupeByDay.get(pos);

        long total = 0;
        transactionDailyList = new ArrayList<>();
        for (Transaction t : currentMonthTransactions) {
            if (t.getDate().equals(expense.getDate())) {
                total += t.getAmountLong();
                transactionDailyList.add(t);
            }
        }

        holder.dateView.setText("Date: " + expense.getDate() + " Total: " + Currency.format(total));

        holder.dayList.setAdapter(new TransactionCardItemsAdapter(context, transactionDailyList));

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
                Transaction e = (Transaction) o;
                showTransactionDetail(e);
            }
        });

        return convertView;
    }


    private void showTransactionDetail(Transaction t) {
        Intent transactionDetails = new Intent(context, TransactionDetailsActivity.class);

        TransactionAddOrEditAdapter adapter = type == Type.EXPENSE ? new TransactionEditExpenseAdapter(t) : new TransactionEditIncomeAdapter(t);

        transactionDetails.putExtra(context.getString(R.string.transaction_adapter_key), adapter);
        context.startActivity(transactionDetails);
    }

    // TODO: Gerer Callback
    @Override
    public void connectionFailed(String error) {

    }

    @Override
    public void getFinished(List<Transaction> transactions) {

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
