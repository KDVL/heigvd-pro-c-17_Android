package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Dashboard;
import ch.heig.cashflow.network.services.DashboardService;


public class DashboardFragment extends Fragment implements DashboardService.Callback {

    private static final String TAG = "DashboardFragment";

    private View view;

    private TextView title;
    private TextView budget;

    // TODO: Observable classe date update changement

    public DashboardFragment() {
        // Required empty public constructor
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

        view = inflater.inflate(R.layout.fragment_expense, container, false);
        title = view.findViewById(R.id.title);
        budget = view.findViewById(R.id.budget);

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        new DashboardService(this).getAll();
    }

    @Override
    public void getFinished(Dashboard dashboard) {
        title.setText(String.valueOf(dashboard.getName()));
        budget.setText(String.valueOf(dashboard.getBudget()));
    }

    @Override
    public void connectionFailed(String error) {
        // Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }
}

