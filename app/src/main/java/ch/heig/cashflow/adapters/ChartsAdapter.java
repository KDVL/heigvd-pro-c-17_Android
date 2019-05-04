package ch.heig.cashflow.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;

public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ViewHolder> {

    private final List<Category> categories;

    public ChartsAdapter(List<Category> items) {
        categories = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Category cat = this.categories.get(position);
        holder.icon.setImageResource(Integer.parseInt(cat.getIconName()));
        holder.icon.getDrawable().setTint(0);
        holder.name.setText(cat.getName());
        holder.amount.setText(String.valueOf(cat.getQuota()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView icon;
        public final TextView name;
        public final TextView amount;

        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.cat_icon);
            name = view.findViewById(R.id.cat_name);
            amount = view.findViewById(R.id.cat_amount);
        }
    }
}