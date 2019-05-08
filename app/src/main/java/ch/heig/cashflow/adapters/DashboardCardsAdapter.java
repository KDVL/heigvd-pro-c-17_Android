package ch.heig.cashflow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
            convertView = layoutInflater.inflate(R.layout.fragment_transaction_list_item, null);
            holder = new DashboardCardsAdapter.ViewHolder();
            holder.dateView = convertView.findViewById(R.id.transaction_date);
            holder.dayList = convertView.findViewById(R.id.dayList);
            convertView.setTag(holder);
        } else
            holder = (DashboardCardsAdapter.ViewHolder) convertView.getTag();

        holder.dayList.setAdapter(new DashboardCardItemsAdapter(context, categories));

        ListAdapter listAdapter = holder.dayList.getAdapter();

        if (listAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, holder.dayList);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = holder.dayList.getLayoutParams();
            params.height = totalHeight + (holder.dayList.getDividerHeight() * (listAdapter.getCount() - 1));
            holder.dayList.setLayoutParams(params);
            holder.dayList.requestLayout();
        }

        return convertView;
    }

    static class ViewHolder {
        TextView dateView;
        ListView dayList;
    }
}
