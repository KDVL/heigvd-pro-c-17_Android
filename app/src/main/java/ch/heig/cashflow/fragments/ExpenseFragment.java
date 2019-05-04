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
import ch.heig.cashflow.activites.MainActivity;
import ch.heig.cashflow.adapters.ExpenseCardsAdapter;
import ch.heig.cashflow.adapters.ExpenseService;
import ch.heig.cashflow.models.Expense;


public class ExpenseFragment extends Fragment {
    private static final String TAG = "ExpenseFragment";
    private MainActivity mainActivity;

    private TextView expenseView;

    private ExpenseService expenseService = null;
    private List<Expense> currentMonthExpensesArrayList = null;


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
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        expenseService = new ExpenseService();

        currentMonthExpensesArrayList = expenseService.getAll().get("05");

        mainActivity = (MainActivity) getActivity();
        expenseView = view.findViewById(R.id.totalExpenses);
        expenseView.setText(String.valueOf(currentMonthExpensesArrayList.get(0).getMontant()));

        if (currentMonthExpensesArrayList.isEmpty()) {
            view.findViewById(R.id.expenseEmptyLayout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }

        final ListView depensesListView = view.findViewById(R.id.expenseCardView);

        depensesListView.setAdapter(new ExpenseCardsAdapter(getActivity(), currentMonthExpensesArrayList));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense, container, false);
    }
}
