package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.ExpenseCardsAdapter;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.TransactionsService;


public class ExpenseFragment extends Fragment implements TransactionsService.Callback {
    private static final String TAG = "ExpenseFragment";

    private TextView expenseView;

    private TransactionsService ts = null;

    private List<Transaction> currentMonthExpenses = null;
    private long totalExpenses;


    public ExpenseFragment() {
        // Required empty public constructor
    }

    public static ExpenseFragment newInstance() {
        return new ExpenseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        ts = new TransactionsService(this);
        ts.getType(Type.EXPENSE, "2019", "05");

        expenseView = view.findViewById(R.id.totalExpenses);

        for(Transaction t : currentMonthExpenses)
            totalExpenses += t.getAmount();

        expenseView.setText(String.valueOf(totalExpenses));

        if (currentMonthExpenses.isEmpty()) {
            view.findViewById(R.id.expenseEmptyLayout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }

        final ListView expensesListView = view.findViewById(R.id.expenseCardView);

        expensesListView.setAdapter(new ExpenseCardsAdapter(getActivity(), currentMonthExpenses));

        return view;
    }

    @Override
    public void connectionFailed(String error) {
        // TODO: toast
    }

    @Override
    public void getAllFinished(List<Transaction> transactions) {
        currentMonthExpenses = transactions;
    }

    @Override
    public void getMonthFinished(List<Transaction> transactions) {

    }

    @Override
    public void getTypeFinished(List<Transaction> transactions) {

    }
}
