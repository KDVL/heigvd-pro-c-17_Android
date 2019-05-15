package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.heig.cashflow.R;
import ch.heig.cashflow.utils.Type;

public class ContainerChartsFragment extends Fragment {

    private static final String TAG = ContainerChartsFragment.class.getSimpleName();

    private FragmentTabHost tabs;

    public ContainerChartsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    public static ContainerChartsFragment newInstance() {
        return new ContainerChartsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        tabs = view.findViewById(android.R.id.tabhost);

        Bundle tab0 = new Bundle();
        tab0.putInt("Type", 0);

        Bundle tab1 = new Bundle();
        tab1.putLong("Type", 1);

        tabs.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        tabs.addTab(tabs.newTabSpec("Dépenses").setIndicator("Dépenses"), ChartsFragment.class, tab0);
        tabs.addTab(tabs.newTabSpec("Revenus").setIndicator("Revenus"), ChartsFragment.class, tab1);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.charts_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
