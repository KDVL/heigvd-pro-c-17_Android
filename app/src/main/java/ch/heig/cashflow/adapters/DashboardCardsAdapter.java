package ch.heig.cashflow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Budget;

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
            holder.dayList = convertView.findViewById(R.id.dayList);
            holder.determinateBar = convertView.findViewById(R.id.determinateBar);
            convertView.setTag(holder);
        } else
            holder = (DashboardCardsAdapter.ViewHolder) convertView.getTag();

        Budget budget = budgets.get(pos);
        holder.dayList.setAdapter(new DashboardCardItemsAdapter(context, budget));

        int progress = 0;

        if (budget.getExpense() > 0) {
            progress = (int) (budget.getExpense() * 100 / 310983);
        } else if (budget.getIncome() > 0) {
            progress = (int) (budget.getIncome() * 100 / 310983);
        }

        holder.determinateBar.setProgress(progress);

        ListAdapter listAdapter = holder.dayList.getAdapter();

        if (listAdapter != null) {
            ViewGroup.LayoutParams params = holder.dayList.getLayoutParams();
            params.height = 100 + (holder.dayList.getDividerHeight() * (listAdapter.getCount() - 1));
            holder.dayList.setLayoutParams(params);
            holder.dayList.requestLayout();
        }

        return convertView;
    }

    static class ViewHolder {
        ListView dayList;
        ProgressBar determinateBar;
    }
}
