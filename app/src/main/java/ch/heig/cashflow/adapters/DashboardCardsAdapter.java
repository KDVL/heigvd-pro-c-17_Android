package ch.heig.cashflow.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.BudgetCategory;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Currency;

public class DashboardCardsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private DashboardCardsAdapter.ViewHolder holder;
    private List<BudgetCategory> budgets;

    public DashboardCardsAdapter(Context context, List<BudgetCategory> budgets) {

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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_dashboard_list_item, null);
            holder = new DashboardCardsAdapter.ViewHolder();
            holder.progBar = convertView.findViewById(R.id.progress_bar);
            holder.percentage = convertView.findViewById(R.id.percentage);
            holder.catName = convertView.findViewById(R.id.category_name);
            holder.catResult = convertView.findViewById(R.id.category_result);
            convertView.setTag(holder);
        } else
            holder = (DashboardCardsAdapter.ViewHolder) convertView.getTag();

        BudgetCategory budgetCategory = budgets.get(pos);
        Category category = budgetCategory.getCategory();

        int progress = 0;
        if (category.getQuota() > 0)
            progress = (int) (Math.abs(budgetCategory.getBudget()) * 100 / category.getQuota());

        holder.progBar.setProgress(progress);
        holder.progBar.setProgressBackgroundTintList(ColorStateList.valueOf(R.color.gray));

        if (budgetCategory.getIncome() > 0)
            holder.progBar.setProgressTintList(ColorStateList.valueOf(R.color.green));
        else
            holder.progBar.setProgressTintList(ColorStateList.valueOf(R.color.red));

        holder.percentage.setText(String.format("%s%%", progress));
        holder.catName.setText(category.getName());
        holder.catResult.setText(Currency.format(Math.abs(budgetCategory.getBudget())));

        return convertView;
    }

    static class ViewHolder {
        ProgressBar progBar;
        TextView percentage;
        TextView catName;
        TextView catResult;
    }
}
