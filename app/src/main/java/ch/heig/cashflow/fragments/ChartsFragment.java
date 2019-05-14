package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.models.BudgetCategory;
import ch.heig.cashflow.network.callbacks.TransactionsCallback;
import ch.heig.cashflow.network.services.DashboardService;

public class ChartsFragment extends Fragment implements DashboardService.Callback {

    private static final String TAG = ChartsFragment.class.getSimpleName();

    private FragmentTabHost tabs;

    public ChartsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    public static ChartsFragment newInstance() {
        return new ChartsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransactionsCallback tc = new TransactionsCallback(getContext(), ServicesFragment.newInstance());
        tc.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        tabs = view.findViewById(android.R.id.tabhost);
        tabs.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        tabs.addTab(tabs.newTabSpec("Dépenses").setIndicator("Dépenses"), CategoryFragment.class, null);
        tabs.addTab(tabs.newTabSpec("Revenus").setIndicator("Revenus"), CategoryFragment.class, null);

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
        inflater.inflate(R.menu.charts_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void getFinished(Budget budget) {

        for (BudgetCategory cat : budget.getCategories()) {
            // TODO get each category
        }
    }

    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }
}
