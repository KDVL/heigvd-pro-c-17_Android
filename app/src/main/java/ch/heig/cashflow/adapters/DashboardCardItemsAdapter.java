package ch.heig.cashflow.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.utils.Currency;

public class DashboardCardItemsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private DashboardCardItemsAdapter.ViewHolder holder;
    private Budget budget;
    private Category category;

    public DashboardCardItemsAdapter(Context context, Budget budget) {
        this.context = context;
        this.budget = budget;
        this.category = budget.getCategory();
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int pos) {
        return budget;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_transaction_listview, null);
            holder = new ViewHolder();
            holder.categorieImageView = convertView.findViewById(R.id.category_image);
            holder.noteView = convertView.findViewById(R.id.transaction_description);
            holder.amountView = convertView.findViewById(R.id.transaction_amount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.noteView.setText(category.getName());
        holder.amountView.setText(Currency.format(budget.getBudget()));
        if (budget.getBudget() < 0)
            holder.amountView.setTextColor(Color.RED);

        //int imageId = this.getDrawableResIdByName(category.getIconName());

        //if (imageId != 0) {
        //    holder.categorieImageView.setImageResource(imageId);
        //    holder.categorieImageView.getDrawable().setTint(Color.parseColor("#222222"));
        //}

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        return context.getResources().getIdentifier(resName, "drawable", pkgName);
    }

    static class ViewHolder {
        ImageView categorieImageView;
        TextView noteView;
        TextView amountView;
    }
}
