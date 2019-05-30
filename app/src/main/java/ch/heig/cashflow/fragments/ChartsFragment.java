package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.models.BudgetCategory;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.DashboardService;
import ch.heig.cashflow.utils.Type;

public class ChartsFragment extends Fragment implements DashboardService.Callback {

    private PieChart pie;
    private Type type;

    public ChartsFragment() {
        // Required empty public constructor
    }

    public static ChartsFragment newInstance() {
        return new ChartsFragment();
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
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        pie = view.findViewById(R.id.pie_chart);

        Bundle bundle = getArguments();
        type = (Type) bundle.get("type");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    @Override
    public void getFinished(Budget budget) {

        List<PieEntry> entries = new ArrayList<>();

        for (BudgetCategory budgetCat : budget.getCategories()) {
            Category cat = budgetCat.getCategory();
            if (cat.getType().equals(type))
                entries.add(new PieEntry(Math.abs(budgetCat.getBudget()), cat.getName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // Define the chart data
        PieData data = new PieData(dataSet);
        pie.setData(data);

        // Pie chart ooptions
        pie.getDescription().setEnabled(false);
        pie.setUsePercentValues(true);
        pie.setRotationEnabled(false);
        pie.animateXY(1000, 1000);
        pie.setExtraOffsets(5, 10, 5, 5);
        pie.invalidate();
    }

    @Override
    public void connectionFailed(String error) {
        if(getContext() != null)
             Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }

    private void reload() {
        new DashboardService(this).getAllByMonth();
    }
}
