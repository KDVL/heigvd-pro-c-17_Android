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
import ch.heig.cashflow.adapters.TransactionCardsAdapter;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.TransactionsService;


public class TransactionFragment extends Fragment implements TransactionsService.Callback {
    private static final String TAG = "TransactionFragment";

    // TODO: Observable classe date update changement

    private View view;

    private TextView expenseView;
    private ListView expensesListView;

    //expense by default
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
        new TransactionsService(this).getType(type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_expense, container, false);

        expenseView = view.findViewById(R.id.totalExpenses);

        expensesListView = view.findViewById(R.id.expenseCardView);

        return view;
    }

    @Override
    public void connectionFailed(String error) {
//        Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }

    @Override
    public void getFinished(List<Transaction> transactions) {

        totalExpenses = 0;
        for (Transaction t : transactions)
            totalExpenses += t.getAmountFloat();

        expenseView.setText(String.valueOf(totalExpenses));

        if (transactions.isEmpty()) {
            view.findViewById(R.id.expenseEmptyLayout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }

        expensesListView.setAdapter(new TransactionCardsAdapter(getActivity(), transactions, type));
    }


    public void setType(Type type) {
        this.type = type;
    }
}
