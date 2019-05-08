package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.SelectedDate;


public class DashboardFragment extends Fragment implements Observer {
    private static final String TAG = "DashboardFragment";

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
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }



    private void reload(){

        //TODO : Call API Service
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
}

