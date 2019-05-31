/**
 * Adapter for correctly display transactions cards
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see ch.heig.cashflow.adapters.cards.TransactionCardItemsAdapter
 */

package ch.heig.cashflow.adapters.cards;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.TransactionDetailsActivity;
import ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter;
import ch.heig.cashflow.adapters.transactions.TransactionEditExpenseAdapter;
import ch.heig.cashflow.adapters.transactions.TransactionEditIncomeAdapter;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionsService;
import ch.heig.cashflow.utils.Currency;
import ch.heig.cashflow.utils.Type;

public class TransactionCardsAdapter extends BaseAdapter implements TransactionsService.Callback {

    private List<Transaction> currentMonthTransactions;
    private List<Transaction> currentMonthTransactionsGroupeByDay;
    private List<Transaction> transactionDailyList;
    private LayoutInflater layoutInflater;
    private Context context;
    private Type type;

    /**
     * The TransactionCardsAdapter constructor
     *
     * @param context
     * @param currentMonthExpenses
     * @param type
     */
    public TransactionCardsAdapter(Context context, List<Transaction> currentMonthExpenses, Type type) {
        this.context = context;
        this.currentMonthTransactions = currentMonthExpenses;
        if (context != null)
            layoutInflater = LayoutInflater.from(context);
        this.type = type;

        currentMonthTransactionsGroupeByDay = new ArrayList<>();
        groupByDay();
    }

    /**
     * Group transactions by month then by day
     */
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

    /**
     * Get the size of the transactions list
     *
     * @return int The size of the transactions list
     */
    @Override
    public int getCount() {
        return currentMonthTransactionsGroupeByDay.size();
    }

    /**
     * Get an item of the transactions list
     *
     * @param position The item position
     * @return Object The corresponding object
     */
    @Override
    public Object getItem(int position) {
        return currentMonthTransactionsGroupeByDay.get(position);
    }

    /**
     * Get an item id from his position
     *
     * @param position The item position
     * @return long The corresponding item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set of the
     *                    item whose view we want.
     * @param convertView convertView This value may be null.
     * @param parent      parent This value must never be null.
     * @return View This value will never be null.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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

        Transaction expense = currentMonthTransactionsGroupeByDay.get(position);

        long total = 0;
        transactionDailyList = new ArrayList<>();
        for (Transaction t : currentMonthTransactions) {
            if (t.getDate().equals(expense.getDate())) {
                total += t.getAmount();
                transactionDailyList.add(t);
            }
        }

        holder.dateView.setText(expense.getDate() + " | " + Currency.format(total));
        holder.dayList.setAdapter(new TransactionCardItemsAdapter(context, transactionDailyList));

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

    /**
     * Display a specific transaction details
     *
     * @param transaction The transaction to display
     */
    private void showTransactionDetail(Transaction transaction) {
        Intent transactionDetails = new Intent(context, TransactionDetailsActivity.class);
        TransactionAddOrEditAdapter adapter;

        if (type == Type.EXPENSE)
            adapter = new TransactionEditExpenseAdapter(transaction);
        else
            adapter = new TransactionEditIncomeAdapter(transaction);

        transactionDetails.putExtra(context.getString(R.string.transaction_adapter_key), adapter);
        context.startActivity(transactionDetails);
    }

    /**
     * Used by service
     *
     * @return Context The context of the application
     */
    @Override
    public Context getContext() {
        return null;
    }

    /**
     * Return off call API if failed
     *
     * @param error The error message
     */
    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    /**
     * Needed by service but not used
     *
     * @param transactions The transactions list
     */
    @Override
    public void getFinished(List<Transaction> transactions) {
    }

    /**
     * A static class that define different view elements
     */
    private static class ViewHolder {
        TextView dateView;
        ListView dayList;
    }
}
