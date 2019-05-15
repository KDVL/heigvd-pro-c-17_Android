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
    private int tab;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        pie = view.findViewById(R.id.pie_chart);

        Bundle b = getArguments();
        tab = b.getInt("Type");

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
            if (tab == 0 && cat.getType() == Type.EXPENSE)
                entries.add(new PieEntry(budgetCat.getExpense(), cat.getName()));
            else
                entries.add(new PieEntry(budgetCat.getIncome(), cat.getName()));
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
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }

    private void reload() {
        new DashboardService(this).getAllByMonth();
    }
}
