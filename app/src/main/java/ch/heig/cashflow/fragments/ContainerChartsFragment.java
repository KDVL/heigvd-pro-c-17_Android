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

    /**
     * Called to have the fragment instantiate its user interface view
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     *                 The fragment should not add the view itself, but this can be used to generate
     *                 the LayoutParams of the view. This value may be null.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle;

        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        tabs = view.findViewById(android.R.id.tabhost);

        tabs.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        bundle = new Bundle();
        bundle.putSerializable("type", Type.EXPENSE);
        tabs.addTab(tabs.newTabSpec("Dépenses").setIndicator("Dépenses"), ChartsFragment.class, bundle);

        bundle = new Bundle();
        bundle.putSerializable("type", Type.INCOME);
        tabs.addTab(tabs.newTabSpec("Revenus").setIndicator("Revenus"), ChartsFragment.class, bundle);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.charts_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
