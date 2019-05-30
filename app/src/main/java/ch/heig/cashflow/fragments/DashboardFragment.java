/**
 * TODO Thibaud
 *
 * @author Thibaud ALT
 * @version 1.0
 */

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
import ch.heig.cashflow.adapters.cards.DashboardCardsAdapter;
import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.network.services.DashboardService;
import ch.heig.cashflow.utils.Currency;
import ch.heig.cashflow.utils.SelectedDate;
import ch.heig.cashflow.utils.SimpleColor;

public class DashboardFragment extends Fragment implements DashboardService.Callback, Observer {

    private static final String TAG = DashboardFragment.class.getSimpleName();

    private View view;
    private ProgressBar progBar;
    private TextView percentage;
    private TextView title;
    private TextView result;
    private ListView categories;

    /**
     * The DashboardFragment consctructor
     */
    public DashboardFragment() {
        setHasOptionsMenu(true);
        SelectedDate.getInstance().addObserver(this);
    }

    /**
     * Create a new DashboardFragment instance
     *
     * @return DashboardFragment A new DashboardFragment instance
     */
    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    /**
     * TODO
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called to have the fragment instantiate its user interface view
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself, but this can be used to generate
     *                           the LayoutParams of the view. This value may be null.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return Return the View for the fragment's UI, or null.
     */
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

    /**
     * TODO
     */
    private void reload() {
        new DashboardService(this).getAllByMonth();
    }

    /**
     * TODO
     */
    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    /**
     * TODO
     *
     * @param menu     The menu
     * @param inflater The menu inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.dashboard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * TODO
     *
     * @param observable
     * @param obj
     */
    @Override
    public void update(Observable observable, Object obj) {
        reload();
    }

    /**
     * TODO
     *
     * @param error
     */
    @Override
    public void connectionFailed(String error) {
        if(getContext() != null)
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }

    /**
     * TODO
     *
     * @param budget The budget
     */
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
        if(gray != null)
            progBar.setProgressBackgroundTintList(gray);

        if(dark != null)
         progBar.setProgressTintList(dark);

        title.setText(budget.getName());

        result.setText(Currency.format(budget.getBudget()));

        if(red != null && white != null)
            result.setTextColor(budget.getBudget() < 0 ? red : white);

        percentage.setText(String.format("%s%%", progress));

        categories.setAdapter(new DashboardCardsAdapter(getActivity(), budget.getCategories()));
    }
}
