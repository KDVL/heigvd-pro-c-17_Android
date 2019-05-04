package ch.heig.cashflow.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import ch.heig.cashflow.R;

public class ChartsFragment extends Fragment {

    private ArrayList NoOfEmp = new ArrayList();
    private PieChart pieChart = null;
    private ListView pieList = null;

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

        View view = inflater.inflate(R.layout.fragment_charts, container, false);

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

        pieList = view.findViewById(R.id.pie_chart_list);
        //ChartsAdapters adapter = new ChartsAdapters(ChartsFragment.this, NoOfEmp);
        //pieList.setAdapter(adapter);

        return view;
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


}
