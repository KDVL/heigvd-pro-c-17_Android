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
import ch.heig.cashflow.models.DashboardDetails;

public class DashboardCardsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private DashboardCardsAdapter.ViewHolder holder;
    private List<DashboardDetails> categories;

    public DashboardCardsAdapter(Context context, List<DashboardDetails> categories) {

        this.context = context;
        this.categories = categories;

        if (context != null)
            layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int pos) {
        return categories.get(pos);
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

        DashboardDetails category = categories.get(pos);
        holder.dayList.setAdapter(new DashboardCardItemsAdapter(context, category));

        int progress = 0;

        if (category.getExpense() > 0) {
            progress = (int) (category.getExpense() * 100 / 310983);
        } else if (category.getIncome() > 0) {
            progress = (int) (category.getIncome() * 100 / 310983);
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
