package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.ExpenseCardsAdapter;
import ch.heig.cashflow.adapters.ExpenseService;
import ch.heig.cashflow.models.Expense;
import android.support.v4.app.Fragment;


public class DashboardFragment extends Fragment {

    private TextView expenseView;

    private ExpenseService expenseService = null;
    private List<Expense> currentMonthExpensesArrayList = null;

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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        expenseService = new ExpenseService();

        currentMonthExpensesArrayList = expenseService.getAll().get("05");

        expenseView = view.findViewById(R.id.totalExpenses);
        expenseView.setText(String.valueOf(currentMonthExpensesArrayList.get(0).getAmount()));

        if (currentMonthExpensesArrayList.isEmpty()) {
            view.findViewById(R.id.expenseEmptyLayout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }

        final ListView expensesListView = view.findViewById(R.id.expenseCardView);

        expensesListView.setAdapter(new ExpenseCardsAdapter(getActivity(), currentMonthExpensesArrayList));

        getActivity().setTitle(R.string.title_dashboard);

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

}

