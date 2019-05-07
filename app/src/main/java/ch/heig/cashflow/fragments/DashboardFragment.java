package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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


public class DashboardFragment extends Fragment implements TransactionsService.Callback {
    private static final String TAG = "DashboardFragment";

    // TODO: Observable classe date update changement

    private View view;

    private TextView expenseView;
    private ListView expensesListView;

    private TransactionsService ts = null;

    private List<Transaction> currentMonthExpenses = null;
    private String error = "";

    private long totalExpenses;

    public DashboardFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ts = new TransactionsService(this);
        ts.getType(Type.EXPENSE);

        expenseView = view.findViewById(R.id.totalExpenses);

        expensesListView = view.findViewById(R.id.expenseCardView);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        //TODO : Call API Service
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.dashboard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }

    @Override
    public void getFinished(List<Transaction> transactions) {
        currentMonthExpenses = transactions;

        for (Transaction t : currentMonthExpenses)
            totalExpenses += t.getAmountLong();

        expenseView.setText(String.valueOf(totalExpenses));

        if (currentMonthExpenses.isEmpty()) {
            view.findViewById(R.id.expenseEmptyLayout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }

        expensesListView.setAdapter(new ExpenseCardsAdapter(getActivity(), currentMonthExpenses));

        getActivity().setTitle(R.string.title_expenses);
    }
}

