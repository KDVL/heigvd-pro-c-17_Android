package ch.heig.cashflow.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.models.Category;

public class DashboardCardsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private DashboardCardsAdapter.ViewHolder holder;
    private List<Budget> budgets;

    public DashboardCardsAdapter(Context context, List<Budget> budgets) {

        this.context = context;
        this.budgets = budgets;

        if (context != null)
            layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return budgets.size();
    }

    @Override
    public Object getItem(int pos) {
        return budgets.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_dashboard_list_item, null);
            holder = new DashboardCardsAdapter.ViewHolder();
            holder.progBar = convertView.findViewById(R.id.progress_bar);
            holder.percentage = convertView.findViewById(R.id.percentage);
            holder.catName = convertView.findViewById(R.id.category_name);
            holder.catAmmount = convertView.findViewById(R.id.category_ammount);
            convertView.setTag(holder);
        } else
            holder = (DashboardCardsAdapter.ViewHolder) convertView.getTag();

        Budget budget = budgets.get(pos);
        Category category = budget.getCategory();
        int progress = (int) (Math.abs(budget.getBudget()) * 100 / category.getQuota());

        holder.progBar.setProgress(progress);
        holder.progBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#34495e")));
        holder.progBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#1abc9c")));

        holder.percentage.setText(String.valueOf(progress) + '%');
        holder.catName.setText(category.getName());
        holder.catAmmount.setText(String.valueOf(Math.abs(budget.getBudget())));

        return convertView;
    }

    static class ViewHolder {
        ProgressBar progBar;
        TextView percentage;
        TextView catName;
        TextView catAmmount;
    }
}
