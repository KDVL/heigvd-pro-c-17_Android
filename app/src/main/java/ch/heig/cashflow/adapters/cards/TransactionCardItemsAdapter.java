/**
 * TODO
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.adapters.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.utils.ApplicationResources;
import ch.heig.cashflow.utils.Currency;

public class TransactionCardItemsAdapter extends BaseAdapter {

    private ApplicationResources appRes;
    private List<Transaction> transactions;
    private LayoutInflater layoutInflater;
    private Context context;

    /**
     * The TransactionCardItemsAdapter constructor
     *
     * @param context
     * @param transactions
     */
    public TransactionCardItemsAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
        this.layoutInflater = LayoutInflater.from(context);
        this.appRes = new ApplicationResources(context);
    }

    /**
     * TODO
     *
     * @return int
     */
    @Override
    public int getCount() {
        return transactions.size();
    }

    /**
     * TODO
     *
     * @param pos
     * @return Object
     */
    @Override
    public Object getItem(int pos) {
        return transactions.get(pos);
    }

    /**
     * TODO
     *
     * @param pos
     * @return long
     */
    @Override
    public long getItemId(int pos) {
        return pos;
    }

    /**
     * TODO
     *
     * @param pos
     * @param convertView
     * @param parent
     * @return View
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;
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

        Transaction transaction = this.transactions.get(pos);

        holder.noteView.setText(transaction.getDescription());
        holder.amountView.setText(Currency.format(transaction.getAmount()));

        int imageId = appRes.getDrawableResIdByName(transaction.getCategory().getIconName());

        if (imageId != 0) {
            holder.categorieImageView.setImageResource(imageId);
            holder.categorieImageView.getDrawable().setTint(appRes.getColor(R.color.black));
        }

        return convertView;
    }

    /**
     * TODO
     */
    static class ViewHolder {
        ImageView categorieImageView;
        TextView noteView;
        TextView amountView;
    }
}
