package ch.heig.cashflow.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.DashboardCardsAdapter;
import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.models.SelectedDate;
import ch.heig.cashflow.network.services.DashboardService;
import ch.heig.cashflow.utils.Currency;
import ch.heig.cashflow.utils.SimpleColor;


public class DashboardFragment extends Fragment implements DashboardService.Callback, Observer {

    private static final String TAG = "DashboardFragment";

    private View view;
    private ProgressBar progBar;
    private TextView percentage;
    private TextView title;
    private TextView result;
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
        progBar = view.findViewById(R.id.progress_bar);
        percentage = view.findViewById(R.id.percentage);
        title = view.findViewById(R.id.title);
        result = view.findViewById(R.id.result);
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

        // Define used colors
        SimpleColor sp = new SimpleColor(getContext());
        ColorStateList gray = sp.getState(R.color.gray);
        ColorStateList dark = sp.getState(R.color.dark);
        ColorStateList red = sp.getState(R.color.red);
        ColorStateList white = sp.getState(R.color.white);

        int progress = 0;
        if (budget.getIncome() > 0)
            progress = (int) (Math.abs(budget.getExpense()) * 100 / budget.getIncome());

        progBar.setProgress(progress);
        progBar.setProgressBackgroundTintList(gray);
        progBar.setProgressTintList(dark);

        title.setText(budget.getName());

        result.setText(Currency.format(budget.getBudget()));
        result.setTextColor(budget.getBudget() < 0 ? red : white);

        percentage.setText(String.format("%s%%", progress));

        categories.setAdapter(new DashboardCardsAdapter(getActivity(), budget.getCategories()));
    }
}
