package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.ExpenseCardsAdapter;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.TransactionsService;


public class ExpenseFragment extends Fragment implements TransactionsService.Callback {
    private static final String TAG = "ExpenseFragment";

    // TODO: Observable classe date update changement

    private View view;

    private TextView expenseView;
    private ListView expensesListView;

    private TransactionsService ts = null;

    private String error = "";

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
    public void onResume() {
        super.onResume();

        //TODO : Call API Service
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_expense, container, false);

        ts = new TransactionsService(this);
        ts.getType(Type.EXPENSE);

        expenseView = view.findViewById(R.id.totalExpenses);

        expensesListView = view.findViewById(R.id.expenseCardView);

        return view;
    }

    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }

    @Override
    public void getFinished(List<Transaction> transactions) {

        for (Transaction t : transactions)
            totalExpenses += t.getAmount();

        expenseView.setText(String.valueOf(totalExpenses));

        if (transactions.isEmpty()) {
            view.findViewById(R.id.expenseEmptyLayout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }

        expensesListView.setAdapter(new ExpenseCardsAdapter(getActivity(), transactions));

        getActivity().setTitle(R.string.title_expenses);
    }
}
