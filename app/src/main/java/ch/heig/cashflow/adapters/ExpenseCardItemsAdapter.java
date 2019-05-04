package ch.heig.cashflow.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Expense;

public class ExpenseCardItemsAdapter extends BaseAdapter {
    private static final String[] MONTH_ARRAY = {". Janvier", ". Fevrier", ". Mars", ". Avril",
            ". Mai", ". Juin", ". Juillet", ". Aout", ". Septembre", ". Octobre", ". Novembre", ". Decembre"};

    private List<Expense> expensesListData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ExpenseCardItemsAdapter(Context context, List<Expense> expensesListData){
        this.context = context;
        this.expensesListData = expensesListData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return expensesListData.size();
    }

    @Override
    public Object getItem(int pos) {
        return expensesListData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_expense_listview, null);
            holder = new ViewHolder();
            holder.categorieImageView = convertView.findViewById(R.id.categoryImage);
            holder.depenseNoteView = convertView.findViewById(R.id.expenseDesc);
            holder.depenseMontantView = convertView.findViewById(R.id.expenseAmount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Expense expense = this.expensesListData.get(pos);

        holder.depenseNoteView.setText(expense.getDescription());
        holder.depenseMontantView.setText(String.valueOf(expense.getMontant()));

        //int imageId = this.getDrawableResIdByName(expense.getCategory().getIcon());

        //holder.categorieImageView.setImageResource(imageId);
        //holder.categorieImageView.getDrawable().setTint(Color.parseColor(expense.getCategory().getColor()));

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
        TextView depenseNoteView;
        TextView depenseMontantView;
    }
}