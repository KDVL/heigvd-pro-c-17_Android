/**
 * Generic category fragment
 *
 * @authors Kevin DO VALE, Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.adapters.TransactionAddOrEditAdapter
 */

package ch.heig.cashflow.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ch.heig.cashflow.R;
import ch.heig.cashflow.SimpleColor;
import ch.heig.cashflow.adapters.TransactionCardsAdapter;
import ch.heig.cashflow.models.Currency;
import ch.heig.cashflow.models.SelectedDate;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.TransactionsService;


public class TransactionFragment extends Fragment implements TransactionsService.Callback, Observer {

    private static final String TAG = "TransactionFragment";

    private View view;
    private TextView expenseView;
    private ListView expensesListView;
    private Type type = Type.EXPENSE;
    private long totalExpenses;

    public TransactionFragment() {
        // Required empty public constructor
    }

    public static TransactionFragment newInstance() {
        return new TransactionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        SelectedDate.getInstance().addObserver(this);
        reload();
    }

    private void reload() {
        new TransactionsService(this).getTypeByMonth(type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction, container, false);

        expenseView = view.findViewById(R.id.totalExpenses);
        expensesListView = view.findViewById(R.id.expenseCardView);

        return view;
    }

    @Override
    public void connectionFailed(String error) {
        //Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }

    @Override
    public void getFinished(List<Transaction> transactions) {

        SimpleColor sp = new SimpleColor(getContext());

        totalExpenses = 0;
        for (Transaction t : transactions)
            totalExpenses += t.getAmountLong();

        expenseView.setText(Currency.format(totalExpenses));
        expenseView.setTextColor(type.equals(Type.EXPENSE) ? sp.get(R.color.red) : sp.get(R.color.green));

        if (transactions.isEmpty()) {
            view.findViewById(R.id.expense_empty_layout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }

        expensesListView.setAdapter(new TransactionCardsAdapter(getActivity(), transactions, type));
    }


    public void setType(Type type) {
        this.type = type;
    }


    @Override
    public void update(Observable observable, Object o) {
        reload();
    }
}
