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

import java.util.Observable;
import java.util.Observer;

import ch.heig.cashflow.R;
import ch.heig.cashflow.SimpleColor;
import ch.heig.cashflow.adapters.DashboardCardsAdapter;
import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.models.BudgetCategory;
import ch.heig.cashflow.models.Currency;
import ch.heig.cashflow.models.SelectedDate;
import ch.heig.cashflow.network.services.DashboardService;


public class DashboardFragment extends Fragment implements DashboardService.Callback, Observer {

    private static final String TAG = "DashboardFragment";

    private View view;
    private TextView title;
    private TextView budgetResult;
    private ListView categories;

    public DashboardFragment() {
        setHasOptionsMenu(true);
        SelectedDate.getInstance().addObserver(this);
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
        title = view.findViewById(R.id.title);
        budgetResult = view.findViewById(R.id.budget_result);
        categories = view.findViewById(R.id.categories);
        setHasOptionsMenu(true);
        return view;
    }

    private void reload() {
        new DashboardService(this).getAllByMonth();
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.dashboard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void update(Observable observable, Object o) {
        reload();
    }

    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }

    @Override
    public void getFinished(Budget budget) {
        SimpleColor sp = new SimpleColor(getContext());
        title.setText(budget.getName());
        budgetResult.setText(Currency.format(budget.getBudget()));
        budgetResult.setTextColor(budget.getBudget() >= 0 ? sp.get(R.color.green) : sp.get(R.color.red));
        categories.setAdapter(new DashboardCardsAdapter(getActivity(), budget.getCategories()));
    }
}
