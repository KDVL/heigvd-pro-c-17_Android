package ch.heig.cashflow.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.ChartsAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Dashboard;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.callbacks.TransactionsCallback;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.CategoryService;
import ch.heig.cashflow.network.services.DashboardService;
import ch.heig.cashflow.network.services.TransactionService;
import ch.heig.cashflow.network.services.TransactionsService;

public class ChartsFragment extends Fragment {

    private ArrayList NoOfEmp = new ArrayList();
    private PieChart pieChart = null;
    private RecyclerView pieList = null;

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

        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true); // false c'est la vision remplis
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setData(getData());
        pieChart.setRotationEnabled(false);
        pieChart.animateXY(1000, 1000);

        pieList = view.findViewById(R.id.list_charts);
        Category c = new Category(1,"name", "iconName", Type.EXPENSE,100, true);
        ArrayList<Category> cats = new ArrayList<>();
        cats.add(c);
      
        ChartsAdapter adapter = new ChartsAdapter(cats);
        pieList.setAdapter(adapter);

        getActivity().setTitle(R.string.title_charts);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //TODO : Call API Service
    }

    private PieData getData() {
        NoOfEmp.add(new PieEntry(10f, 0));
        NoOfEmp.add(new PieEntry(20f, 1));
        NoOfEmp.add(new PieEntry(25f, 2));
        NoOfEmp.add(new PieEntry(40f, 3));
        NoOfEmp.add(new PieEntry(5, 4));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Dépenses");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        return data;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.dashboard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
