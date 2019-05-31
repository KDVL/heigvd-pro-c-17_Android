/**
 * Adapter for correctly display transactions cards items
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see ch.heig.cashflow.adapters.cards.TransactionCardsAdapter
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
     * @param context      The application context
     * @param transactions A list of transactions
     */
    public TransactionCardItemsAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
        this.layoutInflater = LayoutInflater.from(context);
        this.appRes = new ApplicationResources(context);
    }

    /**
     * Get the size of the transactions list
     *
     * @return int The size of the transactions list
     */
    @Override
    public int getCount() {
        return transactions.size();
    }

    /**
     * Get an item of the transactions list
     *
     * @param position The item position
     * @return Object The corresponding object
     */
    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    /**
     * Get an item id from his position
     *
     * @param position The item position
     * @return long The corresponding item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set of the
     *                    item whose view we want.
     * @param convertView convertView This value may be null.
     * @param parent      parent This value must never be null.
     * @return View This value will never be null.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        Transaction transaction = this.transactions.get(position);

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
     * A static class that define different view elements
     */
    private static class ViewHolder {
        ImageView categorieImageView;
        TextView noteView;
        TextView amountView;
    }
}
