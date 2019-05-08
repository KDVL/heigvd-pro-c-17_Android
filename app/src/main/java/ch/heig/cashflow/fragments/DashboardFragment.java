package ch.heig.cashflow.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Dashboard;
import ch.heig.cashflow.models.SelectedDate;
import ch.heig.cashflow.network.services.DashboardService;


public class DashboardFragment extends Fragment implements DashboardService.Callback, Observer {
    private static final String TAG = "DashboardFragment";

    private View view;

    private TextView title;
    private TextView budget;

    // TODO: Observable classe date update changement

    public DashboardFragment() {
        // Required empty public constructor
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
        budget = view.findViewById(R.id.budget);

        return view;
    }



    private void reload(){

        new DashboardService(this).getAllByMonth();
    }


    @Override
    public void onResume() {
        super.onResume();
        reload();
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
    public void getFinished(Dashboard dashboard) {
        title.setText(dashboard.getName());
        budget.setText(String.valueOf(dashboard.getBudget()));
        budget.setTextColor(dashboard.getBudget() >= 0 ? Color.GREEN : Color.RED);
    }
}
